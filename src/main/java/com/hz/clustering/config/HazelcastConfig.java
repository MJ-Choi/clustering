package com.hz.clustering.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
class HazelcastConfig {

  @Bean
  public Config hazelCastConfig() {
    Config config = new Config();
    config.setInstanceName("hazelcast-instance")        // hazel case instance name
      .addMapConfig(
        new MapConfig()                     // create map
          .setName("cluster")
          .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
          .setEvictionPolicy(EvictionPolicy.LRU)
          .setTimeToLiveSeconds(-1)
      );     // cache will be available until it will remove manually. less then 0 means never expired.
    return config;
  }

}
