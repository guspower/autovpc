package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.AmazonWebServiceRequest
import groovy.transform.TupleConstructor

@TupleConstructor
class AwsRequest {

    private final String AWS_REQUEST_PACKAGE = 'com.amazonaws.services.ec2.model'

    String callName
    Map parameters = [:]

    AmazonWebServiceRequest asSDKRequest() {
        def request = newSDKRequest(callName)
        populateRequest request, parameters

        request
    }

    private AmazonWebServiceRequest newSDKRequest(String name) {
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
