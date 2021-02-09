package com.hz.clustering.service.cache;

import java.util.Map;

public interface CacheService {

  Map<String, Object> get(String key);

  void add(String key, Object value);

  void remove(String key);

}
