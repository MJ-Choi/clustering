package com.hz.clustering.service.cluster.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hz.clustering.domain.Node;
import com.hz.clustering.domain.Node.NodeRole;
import com.hz.clustering.service.cluster.ClusteringService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
final class HzClusteringService implements ClusteringService {

  private HazelcastInstance hz;

  @Autowired
  public HzClusteringService(
    @Qualifier("hazelcastInstance") HazelcastInstance hz) {
    this.hz = hz;
  }

  @Override
  public NodeRole isLeader() {
    Member oldestMember = hz.getCluster().getMembers().iterator().next();
    return oldestMember.localMember() ? NodeRole.LEADER : NodeRole.CANDIDATE;
  }

  @Override
  public Map<String, Object> getMembers() {
    Map<String, Object> map = new HashMap<>();
    List<Node> members = new ArrayList<>();
    for (Member m : hz.getCluster().getMembers()) {
      Node node = new Node();
      node.setIp(m.getAddress().toString());
      node.setRole(isLeader());
      members.add(node);
    }
    map.put("size", hz.getCluster().getMembers().size());
    map.put("members", members);
    return map;
  }
}

