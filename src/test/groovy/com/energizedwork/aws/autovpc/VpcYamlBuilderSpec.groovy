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

            def response = new CreateVpcResult(vpc: new Vpc(cidrBlock: cidrBlock))

        when:
            def builder = new VpcYamlBuilder(ec2)
            def vpc = builder.build(config)

        then:
            ec2.createVpc({ request = it }) >> response

        and:
            request
            cidrBlock == request.cidrBlock
            cidrBlock == vpc.cidrBlock
    }

}
