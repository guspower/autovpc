package com.energizedwork.aws.autovpc.gateway

import com.amazonaws.AmazonWebServiceResponse
import com.energizedwork.aws.autovpc.graph.ObjectGraph
import com.energizedwork.aws.autovpc.graph.ObjectGraphBuilder


class AwsResponse {

    String callName
    ObjectGraph data

    AwsResponse(String callName, Object sdkResult) {
        this.callName = callName
        this.data = new ObjectGraphBuilder().build(sdkResult)
    }

}
