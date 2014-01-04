package com.energizedwork.aws.autovpc

import com.energizedwork.aws.autovpc.domain.Vpc
import com.energizedwork.aws.autovpc.gateway.AwsGateway
import com.energizedwork.aws.autovpc.gateway.AwsResponse

import static com.energizedwork.aws.autovpc.data.DataScenarios.scenarios

import spock.lang.Specification


class VpcEngineSpec extends Specification {

    def 'can create a vpc and update the aggregate accordingly'() {
        given:
            def responseData = scenarios.createVpc.response
            Vpc vpc = new VpcYamlBuilder().build(scenarios.yaml.minimal)

        and:
            def gateway = Mock(AwsGateway) {
                call('createVpc', _) >> new AwsResponse('createVpc', responseData)
            }
            def engine = new VpcEngine(gateway: gateway)

        when:
            engine.create vpc

        then:
            vpc.vpcId

    }


}
