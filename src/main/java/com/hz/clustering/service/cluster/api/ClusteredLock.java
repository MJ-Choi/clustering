package com.hz.clustering.service.cluster.api;

public interface ClusteredLock extends DestroyableObject {

  void lock();

  void unlock();
}
