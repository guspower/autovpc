package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.CreateVpcRequest
import com.amazonaws.services.ec2.model.CreateVpcResult
import spock.lang.Specification


class AwsGatewaySpec extends Specification {

    def 'gateway issues requests, handles responses and notifies observer'() {
        given:
            CreateVpcRequest createVpc
            def result = new CreateVpcResult()
            def ec2 = Mock(AmazonEC2)
            1 * ec2.createVpc({ createVpc = it }) >> result

            def gateway = new AwsGateway(ec2)

        and:
            def request = new AwsRequest('createVpc', [cidrBlock:'10.0.0.0/16', instanceTenancy:'default'])

        and:
            def observer = [event:{ req, resp -> }] as AwsObserver
            gateway.register observer

        when:
            def response = gateway.call(request)

        then:
            createVpc.cidrBlock       == '10.0.0.0/16'
            createVpc.instanceTenancy == 'default'

        and:
            response.response.is result
    }


}
