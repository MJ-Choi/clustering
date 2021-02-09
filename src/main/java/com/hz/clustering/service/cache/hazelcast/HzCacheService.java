package com.hz.clustering.service.cache.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hz.clustering.service.cache.CacheService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HzCacheService implements CacheService {

  private HazelcastInstance hz;

  @Autowired
  public HzCacheService(@Qualifier("hazelcastInstance") HazelcastInstance hz) {
    this.hz = hz;
  }

  private Map<String, Object> getCache() {
    return hz.getMap("cluster");
  }

  @Override
  public Map<String, Object> get(String key) {
    return (Map<String, Object>) getCache().get(key);
  }

  @Override
  public void add(String key, Object value) {
    getCache().put(key, value);
  }

  @Override
  public void remove(String key) {
    getCache().remove(key);
  }
}
