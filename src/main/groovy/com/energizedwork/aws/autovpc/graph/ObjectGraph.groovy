package com.energizedwork.aws.autovpc.graph

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import org.yaml.snakeyaml.Yaml

@EqualsAndHashCode
@ToString(includePackage=false)
@TupleConstructor
class ObjectGraph {

    ConfigObject _data = new ConfigObject()

    String toString() {
        new Yaml().dump(_data)
    }

    boolean equals(def graph) {
        if(graph instanceof ObjectGraph) {
            _data.equals(graph.get_data())
        } else {
            super.equals(graph)
        }
    }

    int size() {
        _data.size()
    }

    boolean isEmpty() {
        _data.isEmpty()
    }

    boolean containsKey(Object key) {
        _data.containsKey(key)
    }

    boolean containsValue(Object value) {
        _data.containsValue(value)
    }

    Object getProperty(String name) {
        _data.getProperty(name)
    }

    void setProperty(String propertyName, Object newValue) {
        _data.setProperty(propertyName, newValue)
    }

    Object get(Object key) {
        _data.get(key)
    }

    Object put(Object key, Object value) {
        _data.put(key, value)
    }

    Object remove(Object o) {
        _data.remove(o)
    }

    void putAll(Map m) {
        _data.putAll(m)
    }

    void clear() {
        _data.clear()
    }

    Set keySet() {
        _data.keySet()
    }

    Collection values() {
        _data.values()
    }

    Set<Map.Entry> entrySet() {
        _data.entrySet()
    }

}
