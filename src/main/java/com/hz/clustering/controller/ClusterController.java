package com.hz.clustering.controller;

import com.hz.clustering.domain.Node.NodeRole;
import com.hz.clustering.service.cluster.ClusteringService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cluster")
class ClusterController {

  private final ClusteringService clustering;

  @Autowired
  public ClusterController(ClusteringService clusteringService) {
    this.clustering = clusteringService;
  }

  @GetMapping("")
  Object status() {
    return clustering.getClusterStatus();
  }

  @GetMapping("/leader")
  NodeRole isLeader() {
    return clustering.isLeader();
  }

  @GetMapping("/members")
  Map<String, Object> getMemebers() {
    return clustering.getMembers();
  }

}
