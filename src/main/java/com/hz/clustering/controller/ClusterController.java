package com.hz.clustering.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.hz.clustering.service.cluster.api.ClusteredMap;
import com.hz.clustering.service.cluster.ClusteringService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cluster")
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class ClusterController {
  private static final String MAP_ID = "map";

  @NonNull
  ClusteringService clustering;

  @GetMapping("/role") String isLeader() {
    return clustering.isLeader() ? "leader" : "candidate";
  }

  @GetMapping("/{key}")
  Optional<String> get(@PathVariable("key") final String key) {
    final ClusteredMap<String, String> map = clustering.getMap(MAP_ID);
    return map.get(key);
  }

  @PostMapping("/{key}/{value}")
  void get(@PathVariable("key") final String key, @PathVariable("value") final String value) {
    final ClusteredMap<String, String> map = clustering.getMap("map");
    map.put(key, value);
  }

  @PostMapping("/remove/{key}")
  void delete(@PathVariable("key") final String key) {
    final ClusteredMap<String, String> map = clustering.getMap("map");
    map.remove(key);
  }

}
