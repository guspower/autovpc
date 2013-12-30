package com.energizedwork.aws.autovpc.graph


class ObjectToGraph {

    ConfigObject convert(Object object) {
        def result = new ConfigObject()
        convertObjectToProperties object, result
        result
    }

    private void convertObjectToProperties(Object source, ConfigObject target, String namespace = '') {
        if(source != null) {

            if(namespace && !namespace.endsWith('.')) { namespace += '.' }

            properties(source).each { MetaProperty property ->
                def value = source."$property.name"
                def propertyNamespace = "$namespace$property.name"

                if(canSerialize(property)) {
                    def names = propertyNamespace.tokenize('.')
                    def current = target
                    def next
                    names[0..-2].each { String name ->
                        next = current.get(name)
                        if(next == null) {
                            next = new ConfigObject()
                            current.put(name, next)
                        }
                        current = next
                    }
                    current."${names[-1]}" = value
                } else if (property.type != Class) {
                    convertObjectToProperties value, target, propertyNamespace
                }
            }
        }
    }

    private List<MetaMethod> properties(Object object) {
        object?.metaClass.properties.sort { it.name } ?: []
    }

    private canSerialize(MetaProperty property) {
        property.type.primitive || property.type == String
    }

}
