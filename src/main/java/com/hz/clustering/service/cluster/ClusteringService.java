package com.hz.clustering.service.cluster;

import com.hz.clustering.service.cluster.api.ClusteredAtomicLong;
import com.hz.clustering.service.cluster.api.ClusteredLock;
import com.hz.clustering.service.cluster.api.ClusteredMap;
import com.hz.clustering.service.cluster.api.ClusteredQueue;
import com.hz.clustering.service.cluster.api.ClusteredTopic;

public interface ClusteringService {

  boolean isLeader();

  <K, V> ClusteredMap<K, V> getMap(String id);

  <T> ClusteredQueue<T> getQueue(String id);

  <T> ClusteredTopic<T> getReliableTopic(String id);

  ClusteredAtomicLong getAtomicLong(String id);

  ClusteredLock getLock(String id);
}
