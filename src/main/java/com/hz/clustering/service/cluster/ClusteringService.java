package com.hz.clustering.service.cluster;

import com.hz.clustering.domain.Node.NodeRole;
import java.util.Map;

public interface ClusteringService {

  NodeRole isLeader();

  Map<String, Object> getMembers();
}
