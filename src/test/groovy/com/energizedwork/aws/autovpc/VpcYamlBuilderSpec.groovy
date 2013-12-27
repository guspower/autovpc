package com.energizedwork.aws.autovpc

import spock.lang.Specification


class VpcYamlBuilderSpec extends Specification {

    // yaml + defaults -> credentials + aggregrate graph -> realize as event stream -> gateway <-> aws api

    def 'can build vpc from minimal yaml config'() {
        given:
            def config =
'''
vpc:
 cidrBlock: 172.31.0.0/16
 region: eu-west-1
 instanceTenancy: default
 subnets: [
   { availabilityZone: eu-west-1a, cidrBlock: 172.31.0.0/20 },
   { availabilityZone: eu-west-1b, cidrBlock: 172.31.15.0/20 },
   { availabilityZone: eu-west-1c, cidrBlock: 172.31.30.0/20 }
  ]
 routeTable: [
   { destinationCidrBlock: 172.31.0.0/16, gatewayId: local },
   { destinationCidrBlock: 0.0.0.0/0 }
  ]
 dhcpOptions: [
   { domain-name: [ eu-west-1.compute.internal ] },
   { domain-name-servers: [ AmazonProvidedDNS ] }
  ]
'''

        when:
            def actual = new VpcYamlBuilder().build(config)

        then:
            actual.cidrBlock       == '172.31.0.0/16'
            actual.region          == 'eu-west-1'
            actual.instanceTenancy == 'default'

        and:
            actual.subnets.size()  == 3

    }

}
