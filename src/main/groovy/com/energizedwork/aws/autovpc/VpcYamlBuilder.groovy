package com.energizedwork.aws.autovpc

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.CreateVpcRequest
import com.amazonaws.services.ec2.model.Vpc
import org.yaml.snakeyaml.Yaml


class VpcYamlBuilder {

    private AmazonEC2 ec2

    VpcYamlBuilder(AmazonEC2 ec2) {
        this.ec2 = ec2
    }

    Vpc build(String yaml) {
        def config = new Yaml().load(yaml)

        def request = new CreateVpcRequest()
        request.cidrBlock = config.vpc

        ec2.createVpc(request).vpc
    }

}
