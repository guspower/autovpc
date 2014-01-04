package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.services.ec2.model.CreateVpcRequest
import spock.lang.Specification

import static com.energizedwork.aws.autovpc.data.DataScenarios.scenarios

class AwsRequestSpec extends Specification {

    def 'can convert to amazon sdk request type'() {
        given:
            def request = new AwsRequest('createVpc', scenarios.createVpc)

        when:
            AmazonWebServiceRequest awsRequest = request.asSDKRequest()

        then:
            awsRequest.class           == CreateVpcRequest
            awsRequest.cidrBlock       == scenarios.createVpc.cidrBlock
            awsRequest.instanceTenancy == scenarios.createVpc.instanceTenancy
    }

}
