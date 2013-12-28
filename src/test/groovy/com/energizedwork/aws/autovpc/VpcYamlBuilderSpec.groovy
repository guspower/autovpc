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
            actual.subnets.size() == 3

            actual.subnets[0].availabilityZone == 'eu-west-1a'
            actual.subnets[0].cidrBlock        == '172.31.0.0/20'

            actual.subnets[1].availabilityZone == 'eu-west-1b'
            actual.subnets[1].cidrBlock        == '172.31.15.0/20'

            actual.subnets[2].availabilityZone == 'eu-west-1c'
            actual.subnets[2].cidrBlock        == '172.31.30.0/20'

        and:
            actual.routeTable.size() == 2

            actual.routeTable[0].destinationCidrBlock == '172.31.0.0/16'
            actual.routeTable[0].gatewayId            == 'local'

            actual.routeTable[1].destinationCidrBlock == '0.0.0.0/0'

        and:
            actual.dhcpOptions.size() == 2

            actual.dhcpOptions[0].key    == 'domain-name'
            actual.dhcpOptions[0].values == ['eu-west-1.compute.internal']

            actual.dhcpOptions[1].key    == 'domain-name-servers'
            actual.dhcpOptions[1].values == ['AmazonProvidedDNS']
    }

}
