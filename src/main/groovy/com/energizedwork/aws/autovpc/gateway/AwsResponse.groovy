package com.energizedwork.aws.autovpc.gateway

import com.energizedwork.aws.autovpc.graph.ObjectGraph
import com.energizedwork.aws.autovpc.graph.ObjectGraphBuilder
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includePackage=false)
class AwsResponse {

    String callName
    ObjectGraph data

    AwsResponse(String callName, Object sdkResult) {
        this.callName = callName
        this.data = new ObjectGraphBuilder().build(sdkResult)
    }

    AwsResponse(String callName, Map data) {
        this.callName = callName
        this.data = new ObjectGraph()
        this.data.putAll(data)
    }

}
