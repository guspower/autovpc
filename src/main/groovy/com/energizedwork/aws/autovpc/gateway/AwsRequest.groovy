package com.energizedwork.aws.autovpc.gateway

import groovy.transform.TupleConstructor

@TupleConstructor
class AwsRequest {

    String callName
    Map parameters = [:]

}
