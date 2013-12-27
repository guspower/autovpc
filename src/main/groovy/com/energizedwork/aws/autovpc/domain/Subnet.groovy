package com.energizedwork.aws.autovpc.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable


@EqualsAndHashCode
@Immutable final class Subnet {

    String availabilityZone
    String cidrBlock

}
