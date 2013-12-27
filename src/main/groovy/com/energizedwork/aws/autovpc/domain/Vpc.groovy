package com.energizedwork.aws.autovpc.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Top level aggregate
 */


@EqualsAndHashCode
@ToString(includePackage=false)
class Vpc {

    private String cidrBlock, instanceTenancy, region
    private List<Subnet> _subnets = []
    private List<Route> _routeTable = []
    private List<DhcpOption> _dhcpOptions = []

    List<Subnet> getSubnets() { _subnets.asImmutable() }
    List<Route> getRouteTable() { _routeTable.asImmutable() }
    List<DhcpOption> getDhcpOptions() { _dhcpOptions.asImmutable() }

    void setSubnets(List subnets) {
        subnets?.each { Map subnetData -> _subnets << new Subnet(subnetData) }
    }

    void setRouteTable(List routes) {}
    void setDhcpOptions(List options) {}

}
