package com.hz.clustering.service.cluster.api;

public interface ClusteredAtomicLong extends DestroyableObject {

  long incrementAndGet();

  long get();
}
