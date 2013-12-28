package com.energizedwork.aws.autovpc.gateway


interface AwsObserver {

    void event(AwsRequest request, AwsResponse response)

}
