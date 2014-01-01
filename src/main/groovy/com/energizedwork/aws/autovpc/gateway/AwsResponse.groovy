package com.energizedwork.aws.autovpc.gateway

import com.energizedwork.aws.autovpc.graph.ObjectGraph
import groovy.transform.TupleConstructor


@TupleConstructor
class AwsResponse {

    String callName
    ObjectGraph data

}
