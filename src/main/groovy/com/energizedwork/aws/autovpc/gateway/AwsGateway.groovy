package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.services.ec2.AmazonEC2

class AwsGateway {

    private final String AWS_REQUEST_PACKAGE = 'com.amazonaws.services.ec2.model'

    private AmazonEC2 ec2
    private Set<AwsObserver> observers = []

    AwsGateway(AmazonEC2 ec2) {
        this.ec2 = ec2
    }

    def register(AwsObserver observer) {
        observers << observer
    }

    def unregister(AwsObserver observer) {
        observers.remove observer
    }

    AwsResponse call(String callName, Map parameters) {
        call new AwsRequest(callName, parameters)
    }

    AwsResponse call(AwsRequest ar) {
        def request = ar.asSDKRequest()

        def result = request ? ec2.invokeMethod(ar.callName, request) : ec2.invokeMethod(ar.callName)
        def response = new AwsResponse(ar.callName, result)

        notifyObservers ar, response

        response
    }

    private void notifyObservers(AwsRequest request, AwsResponse response) {
        observers.each { AwsObserver observer ->
            observer.notify request, response
        }
    }

}
