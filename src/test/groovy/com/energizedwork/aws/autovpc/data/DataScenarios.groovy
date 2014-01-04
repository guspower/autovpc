package com.energizedwork.aws.autovpc.data


class DataScenarios {

    static ConfigObject scenarios

    static {
        scenarios = new ConfigObject()
        scenarios.createVpc.cidrBlock = '10.0.0.0/16'
        scenarios.createVpc.instanceTenancy = 'default'
    }

}
