package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.services.ec2.AmazonEC2

class AwsGateway {

    private final String AWS_REQUEST_PACKAGE = 'com.amazonaws.services.ec2.model'

    private AmazonEC2 ec2

    AwsGateway(AmazonEC2 ec2) {
        this.ec2 = ec2
    }

    def register(AwsObserver observer) {

    }

    AwsResponse call(AwsRequest ar) {
        def request = ar.asSDKRequest()

        def response = ec2.invokeMethod(ar.callName, request)

        new AwsResponse(ar.callName, response)
    }

}
