package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.CreateVpcRequest
import com.amazonaws.services.ec2.model.CreateVpcResult
import com.amazonaws.services.ec2.model.Vpc
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification


class AwsGatewaySpec extends Specification {

    def 'gateway issues requests and extracts response values correctly'() {
        given:
            String cidrBlock = '10.0.0.0/16'
            String instanceTenancy = 'default'

        and:
            CreateVpcRequest createVpc
            def result = new CreateVpcResult(vpc: new Vpc(cidrBlock:cidrBlock, instanceTenancy:instanceTenancy))

        and:
            def ec2 = Mock(AmazonEC2)
            1 * ec2.createVpc({ createVpc = it }) >> result
            def gateway = new AwsGateway(ec2)

        and:
            def request = new AwsRequest('createVpc', [cidrBlock:cidrBlock, instanceTenancy:instanceTenancy])

        when:
            def response = gateway.call(request)

        then:
            createVpc.cidrBlock       == '10.0.0.0/16'
            createVpc.instanceTenancy == 'default'

        and:
            response.data.vpc.cidrBlock       == cidrBlock
            response.data.vpc.instanceTenancy == instanceTenancy

        and:
'''vpc:
  cidrBlock: 10.0.0.0/16
  dhcpOptionsId: null
  instanceTenancy: default
  state: null
  tags: {autoConstruct: true, empty: true}
  vpcId: null
'''         == new Yaml().dump(response.data)

    }


}
