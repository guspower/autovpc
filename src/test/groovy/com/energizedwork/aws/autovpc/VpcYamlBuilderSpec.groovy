package com.energizedwork.aws.autovpc

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.CreateVpcRequest
import com.amazonaws.services.ec2.model.CreateVpcResult
import com.amazonaws.services.ec2.model.Vpc
import spock.lang.Specification


class VpcYamlBuilderSpec extends Specification {

    def 'can build vpc from minimal yaml config'() {
        given:
            def cidrBlock = '10.0.0.0/16'
            def config = "vpc: $cidrBlock"

        and:
            def expected = new Vpc(cidrBlock: cidrBlock)
            def result = new CreateVpcResult(vpc: expected)

            CreateVpcRequest request
            def ec2 = Stub(AmazonEC2) {
                createVpc({ request = it }) >> result
            }

        when:
            def builder = new VpcYamlBuilder(ec2)
            def actual = builder.build(config)

        then:
            request.cidrBlock == cidrBlock
            actual.is expected
    }

}
