package com.hz.clustering.controller;

import com.hz.clustering.service.cache.CacheService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

  private final CacheService cacheService;

  @Autowired
  public CacheController(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @RequestMapping(value = "/{key}")
  Map<String, Object> get(@PathVariable("key") String key) {
    return cacheService.get(key);
  }

  @RequestMapping(value = "/{key}/{value}", method = RequestMethod.POST)
  void add(@PathVariable("key") String key, @PathVariable("value") Object value) {
    cacheService.add(key, value);
  }

  @RequestMapping(value = "/remove/{key}", method = RequestMethod.POST)
  void remove(@PathVariable("key")String key){
    cacheService.remove(key);
  }
}
