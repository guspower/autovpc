package com.energizedwork.aws.autovpc.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString


@EqualsAndHashCode
@ToString(includePackage=false)
@Immutable final class Route {

    String destinationCidrBlock
    String gatewayId

}
