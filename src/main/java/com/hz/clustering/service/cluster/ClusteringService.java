package com.hz.clustering.service.cluster;

import com.hz.clustering.domain.Node.NodeRole;
import java.util.Map;
import java.util.Objects;

public interface ClusteringService {

  Object getClusterStatus();

  NodeRole isLeader();

  Map<String, Object> getMembers();
}
