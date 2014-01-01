package com.energizedwork.aws.autovpc.graph


class ObjectGraphBuilder {

    static ObjectGraph fromObject(Object object) {
        new ObjectGraphBuilder().build object
    }

    static ObjectGraph fromScript(String script) {
        ConfigObject data = new ConfigSlurper().parse(new GroovyShell().parse(script))
        new ObjectGraph(data)
    }

    private ConfigObject data = new ConfigObject()

    private ObjectGraph build(Object object) {
        convertObjectToProperties object, data

        new ObjectGraph(data)
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
