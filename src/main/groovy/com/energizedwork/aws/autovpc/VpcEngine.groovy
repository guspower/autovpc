package com.energizedwork.aws.autovpc

import com.energizedwork.aws.autovpc.domain.Vpc
import com.energizedwork.aws.autovpc.gateway.AwsGateway


class VpcEngine {

    AwsGateway gateway

    void create(Vpc vpc) {
        def response = gateway.call('createVpc', vpc.properties)

        response?.data.vpc.each { key, value ->
            if(vpc.hasProperty(key)) {
                vpc.setProperty(key, value)
            }
        }
    }

}
