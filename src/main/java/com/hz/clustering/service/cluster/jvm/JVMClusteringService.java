package com.hz.clustering.service.cluster.jvm;

import static lombok.AccessLevel.PRIVATE;

import com.hz.clustering.service.cluster.api.ClusteredAtomicLong;
import com.hz.clustering.service.cluster.api.ClusteredLock;
import com.hz.clustering.service.cluster.api.ClusteredMap;
import com.hz.clustering.service.cluster.api.ClusteredQueue;
import com.hz.clustering.service.cluster.api.ClusteredTopic;
import com.hz.clustering.service.cluster.ClusteringService;
import com.hz.clustering.service.cluster.api.DestroyableObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConditionalOnProperty(name = "clustering.driver", havingValue = "noop", matchIfMissing = true)
final class JVMClusteringService implements ClusteringService {

  Map<String, ClusteredMap<?, ?>> maps = new ConcurrentHashMap<>();
  Map<String, ClusteredQueue<?>> queues = new ConcurrentHashMap<>();
  Map<String, ClusteredTopic<?>> topics = new ConcurrentHashMap<>();
  Map<String, ClusteredAtomicLong> atomics = new ConcurrentHashMap<>();
  Map<String, ClusteredLock> locks = new ConcurrentHashMap<>();
  Lock lock = new ReentrantLock();

  @Override
  public boolean isLeader() {
    return true;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <K, V> ClusteredMap<K, V> getMap(final String id) {
    return (ClusteredMap<K, V>) create(id, maps, JVMMap::new);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> ClusteredQueue<T> getQueue(final String id) {
    return (ClusteredQueue<T>) create(id, queues, JVMQueue::new);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> ClusteredTopic<T> getReliableTopic(final String id) {
    return (ClusteredTopic<T>) create(id, topics, JVMTopic::new);
  }

  @Override
  public ClusteredAtomicLong getAtomicLong(final String id) {
    return create(id, atomics, JVMAtomicLong::new);
  }

  @Override
  public ClusteredLock getLock(final String id) {
    return create(id, locks, JVMLock::new);
  }

  private <T extends DestroyableObject> T create(
    final String id,
    final Map<String, T> map,
    final Function<DestroyableObject, T> function) {
    try {
      lock.lock();
      final DestroyableObject onDestroy = () -> map.remove(id);
      return map.computeIfAbsent(id, key -> function.apply(onDestroy));
    } finally {
      lock.unlock();
    }
  }
}
