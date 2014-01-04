package com.energizedwork.aws.autovpc.gateway

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
            CreateVpcRequest createVpc
            def result = new CreateVpcResult(vpc: new Vpc(scenarios.createVpc))

        and:
            def ec2 = Mock(AmazonEC2)
            1 * ec2.createVpc({ createVpc = it }) >> result
            def gateway = new AwsGateway(ec2)

        and:
            def request = new AwsRequest('createVpc', scenarios.createVpc)

        when:
            def response = gateway.call(request)

        then:
            createVpc.cidrBlock       == scenarios.createVpc.cidrBlock
            createVpc.instanceTenancy == scenarios.createVpc.instanceTenancy

        and:
            response.data.vpc.cidrBlock       == scenarios.createVpc.cidrBlock
            response.data.vpc.instanceTenancy == scenarios.createVpc.instanceTenancy

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

}
