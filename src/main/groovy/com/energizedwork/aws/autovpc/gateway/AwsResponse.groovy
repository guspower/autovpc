package com.energizedwork.aws.autovpc.gateway

import groovy.transform.TupleConstructor


@TupleConstructor
class AwsResponse {

    String callName
    Object response

}
