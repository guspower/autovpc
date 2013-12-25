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
            def ec2 = Mock(AmazonEC2)
            CreateVpcRequest request

            def expected = Stub(Vpc)
            def result = new CreateVpcResult(vpc: expected)

        and:
            1 * ec2.createVpc({ request = it }) >> result

        when:
            def builder = new VpcYamlBuilder(ec2)
            def actual = builder.build(config)

        then:
            cidrBlock == request.cidrBlock
            expected.is actual
    }

}
