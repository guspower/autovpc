package com.energizedwork.aws.autovpc

import com.energizedwork.aws.autovpc.domain.Vpc
import org.yaml.snakeyaml.Yaml

class VpcYamlBuilder {

    Vpc build(String yaml) {
        def config = new Yaml().load(yaml)

        def vpc = new Vpc()
        config.vpc.each { key, value ->
            if(vpc.hasProperty(key)) {
                vpc.setProperty key, value
            }
        }

        vpc
    }

}
