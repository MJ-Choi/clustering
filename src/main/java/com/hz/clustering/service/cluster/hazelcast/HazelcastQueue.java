package com.hz.clustering.service.cluster.hazelcast;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.hazelcast.core.IQueue;
import com.hz.clustering.service.cluster.api.AbstractClusteredQueue;
import java.util.concurrent.BlockingQueue;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class HazelcastQueue<T> extends AbstractClusteredQueue<T> {
  @NonNull
  IQueue<T> queue;

  @Override
  public void destroy() {
    queue.destroy();
  }

  @Override
  protected BlockingQueue<T> queue() {
    return queue;
  }
}
