package com.energizedwork.aws.autovpc.data

import com.energizedwork.aws.autovpc.graph.ObjectGraph


class DataScenarios {

    static ObjectGraph scenarios

    static {
        scenarios = new ObjectGraph()
        scenarios.createVpc.request.cidrBlock = '10.0.0.0/16'
        scenarios.createVpc.request.instanceTenancy = 'default'
        scenarios.createVpc.response.vpc.vpcId = 'vpc-1234'

        scenarios.attachInternetGateway.request.vpcId = 'vpc-1234'
        scenarios.attachInternetGateway.request.internetGatewayId = 'ig-1234'

        scenarios.createInternetGateway.response.internetGatewayId = 'ig-1234'

        scenarios.yaml.minimal = '''
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

    }

}
