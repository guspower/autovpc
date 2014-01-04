package com.energizedwork.aws.autovpc.graph

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import org.yaml.snakeyaml.Yaml

@EqualsAndHashCode
@ToString(includePackage=false)
@TupleConstructor
class ObjectGraph extends ConfigObject {

    String toString() {
        new Yaml().dump(this)
    }

    boolean contains(Map map) {
        map == this.intersect(map)
    }

    Object getProperty(String name) {
        Object result = super.getProperty(name)
        if(result instanceof ConfigObject) {
            result = new ObjectGraph().merge(result)
            setProperty(name, result)
        }

        result
    }

}
