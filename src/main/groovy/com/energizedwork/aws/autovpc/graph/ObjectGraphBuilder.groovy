package com.energizedwork.aws.autovpc.graph


class ObjectGraphBuilder {

    ObjectGraph build(Object object) {
        def result = new ConfigObject()
        convertObjectToProperties object, result

        new ObjectGraph(result)
    }

    private void convertObjectToProperties(Object source, ConfigObject graph, String namespace = '') {
        if(source != null) {

            if(namespace && !namespace.endsWith('.')) { namespace += '.' }
            def current = populateNamespace(graph, namespace)

            properties(source).each { MetaProperty property ->
                def value = source."$property.name"
                if(canSerialize(property)) {
                    current."$property.name" = value
                } else if (!(property.type in [Class, MetaClass, MetaClassImpl])) {
                    convertObjectToProperties value, graph, "$namespace$property.name"
                }
            }
        }
    }

    private ConfigObject populateNamespace(ConfigObject graph, String namespace) {
        def names = namespace.tokenize('.')
        def current = graph

        if(names) {
            def next
            names.each { String name ->
                next = current.get(name)
                if(next == null) {
                    next = new ConfigObject()
                    current.put(name, next)
                }
                current = next
            }
        }

        current
    }

    private List<MetaMethod> properties(Object object) {
        object?.metaClass.properties.sort { it.name } ?: []
    }

    private canSerialize(MetaProperty property) {
        property.type.primitive || property.type == String
    }

}
