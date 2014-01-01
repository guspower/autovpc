package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.AmazonWebServiceRequest
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
        def request = newRequest(ar.callName)
        populateRequest request, ar.parameters

        def response = ec2.invokeMethod(ar.callName, request)

        new AwsResponse(ar.callName, new com.energizedwork.aws.autovpc.graph.ObjectGraphBuilder().build(response))
    }

    private AmazonWebServiceRequest newRequest(String name) {
        Class.forName(calculateRequestClassName(name)).newInstance()
    }

    private void populateRequest(AmazonWebServiceRequest request, Map parameters) {
        parameters.each { key, value ->
            if(request.hasProperty(key)) {
                request."$key" = value
            }
        }
    }

    private String calculateRequestClassName(String name) {
        "${AWS_REQUEST_PACKAGE}.${name.capitalize()}Request"
    }

}
