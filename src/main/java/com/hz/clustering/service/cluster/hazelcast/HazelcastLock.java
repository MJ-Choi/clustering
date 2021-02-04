package com.hz.clustering.service.cluster.hazelcast;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import com.hazelcast.core.ILock;
import com.hz.clustering.service.cluster.api.ClusteredLock;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class HazelcastLock implements ClusteredLock {
  @NonNull
  ILock lock;

  @Override
  public void destroy() {
    lock.destroy();
  }

  @Override
  public void lock() {
    lock.lock();
  }

  @Override
  public void unlock() {
    lock.unlock();
  }
}
