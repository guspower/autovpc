package com.energizedwork.aws.autovpc.gateway

import static com.energizedwork.aws.autovpc.data.DataScenarios.scenarios

import com.amazonaws.services.ec2.model.CreateVpcResult
import com.amazonaws.services.ec2.model.Vpc
import spock.lang.Specification


class AwsResponseSpec extends Specification {

    def 'can unmarshall aws sdk response into object graph'() {
        given:
            def requestData = scenarios.createVpc.request

            def sdkResponse = new CreateVpcResult()
            sdkResponse.vpc = new Vpc(requestData)

        when:
            def response = new AwsResponse('createVpc', sdkResponse)

        then:
            response.data.vpc.cidrBlock       == requestData.cidrBlock
            response.data.vpc.instanceTenancy == requestData.instanceTenancy
    }

}
