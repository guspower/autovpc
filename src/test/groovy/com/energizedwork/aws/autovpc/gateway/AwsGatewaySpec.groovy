package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.services.ec2.model.CreateInternetGatewayResult
import com.amazonaws.services.ec2.model.DescribeVpcsResult
import com.amazonaws.services.ec2.model.InternetGateway

import static com.energizedwork.aws.autovpc.data.DataScenarios.scenarios
import static com.energizedwork.aws.autovpc.graph.ObjectGraphBuilder.fromScript as asGraph

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.CreateVpcRequest
import com.amazonaws.services.ec2.model.CreateVpcResult
import com.amazonaws.services.ec2.model.Vpc
import spock.lang.Specification


class AwsGatewaySpec extends Specification {

    def 'gateway issues requests and extracts response values correctly'() {
        given:
            def requestData = scenarios.createVpc.request

            CreateVpcRequest sdkRequest
            def sdkResult = new CreateVpcResult(vpc: new Vpc(requestData))

        and:
            def ec2 = Mock(AmazonEC2)
            1 * ec2.createVpc({ sdkRequest = it }) >> sdkResult
            def gateway = new AwsGateway(ec2)

        and:
            def request = new AwsRequest('createVpc', requestData)

        when:
            def response = gateway.call(request)

        then:
            sdkRequest.cidrBlock       == requestData.cidrBlock
            sdkRequest.instanceTenancy == requestData.instanceTenancy

        and:
            response.data.vpc.cidrBlock       == requestData.cidrBlock
            response.data.vpc.instanceTenancy == requestData.instanceTenancy

        and:
            asGraph('''
vpc {
  cidrBlock = '10.0.0.0/16'
  dhcpOptionsId = null
  instanceTenancy = 'default'
  state = null
  tags = [autoConstruct: true, empty: true]
  vpcId = null
} ''')      == response.data

    }

    def 'gateway can handle calls w/out parameters'() {
        given:
            def responseData = scenarios.createInternetGateway.response
            def internetGateway = new InternetGateway(responseData)
            def result = new CreateInternetGatewayResult(internetGateway: internetGateway)

        and:
            def ec2 = Mock(AmazonEC2)
            1 * ec2.createInternetGateway(_) >> result
            def gateway = new AwsGateway(ec2)

        when:
            def response = gateway.call(new AwsRequest('createInternetGateway'))

        then:
            response.data.internetGateway.contains responseData
    }

    def 'gateway can handle void responses'() {
        given:
            def request = new AwsRequest('attachInternetGateway', scenarios.attachInternetGateway.request)

        and:
            def ec2 = Mock(AmazonEC2)
            1 * ec2.attachInternetGateway(_)
            def gateway = new AwsGateway(ec2)

        when:
            def response = gateway.call(request)

        then:
            response.callName == 'attachInternetGateway'
            !response.data
    }

    def 'gateway notifies observers'() {
        given:
            def request = new AwsRequest('describeVpcs')
            def response = new DescribeVpcsResult()

        and:
            def ec2 = Mock(AmazonEC2)
            1 * ec2.describeVpcs(_) >> response
            def gateway = new AwsGateway(ec2)

        and:
            def observer = new TestObserver()
            gateway.register observer

        when:
            gateway.call(request)

        then:
            observer.events.size()   == 1
            observer.events[0].key   == request
            observer.events[0].value instanceof AwsResponse
    }

}

