package com.energizedwork.aws.autovpc.gateway


interface AwsObserver {

    void notify(AwsRequest request, AwsResponse response)

}
