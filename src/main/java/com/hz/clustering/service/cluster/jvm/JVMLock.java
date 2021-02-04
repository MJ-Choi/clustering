package com.hz.clustering.service.cluster.jvm;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.hz.clustering.service.cluster.api.ClusteredLock;
import com.hz.clustering.service.cluster.api.DestroyableObject;
import java.util.concurrent.locks.ReentrantLock;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JVMLock implements ClusteredLock {
  @NonNull
  DestroyableObject onDestroy;

  ReentrantLock lock = new ReentrantLock();

  @Override
  public void lock() {
    lock.lock();
  }

  @Override
  public void unlock() {
    lock.unlock();
  }

  @Override
  public void destroy() {
    onDestroy.destroy();
  }
}
