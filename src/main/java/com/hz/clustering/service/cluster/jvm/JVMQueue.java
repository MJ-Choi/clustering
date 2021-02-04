package com.hz.clustering.service.cluster.jvm;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.hz.clustering.service.cluster.api.AbstractClusteredQueue;
import com.hz.clustering.service.cluster.api.DestroyableObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JVMQueue<T> extends AbstractClusteredQueue<T> {
  @NonNull
  DestroyableObject onDestroy;

  BlockingQueue<T> queue = new LinkedBlockingQueue<>();

  @Override
  public void destroy() {
    onDestroy.destroy();
  }

  @Override
  protected BlockingQueue<T> queue() {
    return queue;
  }
}
