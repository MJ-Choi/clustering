package com.hz.clustering.service.cluster.jvm;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.hz.clustering.service.cluster.api.ClusteredAtomicLong;
import com.hz.clustering.service.cluster.api.DestroyableObject;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JVMAtomicLong implements ClusteredAtomicLong {

  @NonNull
  DestroyableObject onDestroy;
  AtomicLong atomic = new AtomicLong();

  @Override
  public void destroy() {
    onDestroy.destroy();
  }

  @Override
  public long incrementAndGet() {
    return atomic.incrementAndGet();
  }

  @Override
  public long get() {
    return atomic.get();
  }
}
