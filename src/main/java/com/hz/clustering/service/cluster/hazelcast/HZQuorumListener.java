package com.hz.clustering.service.cluster.hazelcast;

@FunctionalInterface
interface HZQuorumListener {

  boolean isQuorum();
}
