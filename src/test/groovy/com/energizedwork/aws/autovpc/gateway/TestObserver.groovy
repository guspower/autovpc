package com.energizedwork.aws.autovpc.gateway


class TestObserver implements AwsObserver {

    List<Map.Entry> events = []

    @Override
    void notify(AwsRequest request, AwsResponse response) {
        events << new MapEntry(request, response)
    }

}
