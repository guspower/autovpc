package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.services.ec2.AmazonEC2


class AwsGateway {

    private AmazonEC2 ec2

    AwsGateway(AmazonEC2 ec2) {
        this.ec2 = ec2
    }

    def register(AwsObserver observer) {

    }

    AwsResponse call(AwsRequest ar) {
        def request = Class.forName("com.amazonaws.services.ec2.model.${ar.callName.capitalize()}Request").newInstance()

        ar.parameters.each { key, value ->
            if(request.hasProperty(key)) {
                request."$key" = value
            }
        }

        def response = ec2.invokeMethod(ar.callName, request)

        new AwsResponse(ar.callName, response)
    }

}
