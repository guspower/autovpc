package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.services.ec2.model.CreateVpcRequest
import spock.lang.Specification

import static com.energizedwork.aws.autovpc.data.DataScenarios.scenarios

class AwsRequestSpec extends Specification {

    def 'can convert to amazon sdk request type'() {
        given:
            def requestData = scenarios.createVpc.request
            def request = new AwsRequest('createVpc', requestData)

        when:
            AmazonWebServiceRequest awsRequest = request.asSDKRequest()

        then:
            awsRequest.class           == CreateVpcRequest
            awsRequest.cidrBlock       == requestData.cidrBlock
            awsRequest.instanceTenancy == requestData.instanceTenancy
    }

}
