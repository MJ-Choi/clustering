package com.hz.clustering.service.cluster.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hz.clustering.domain.Node;
import com.hz.clustering.domain.Node.NodeRole;
import com.hz.clustering.service.cluster.ClusteringService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
final class HzClusteringService implements ClusteringService {

  private HazelcastInstance hz;

  @Autowired
  public HzClusteringService(
    @Qualifier("hazelcastInstance") HazelcastInstance hz) {
    this.hz = hz;
  }

  @Override
  public Object getClusterStatus() {
    return hz.getCluster().getClusterState();
  }

  @Override
  public NodeRole isLeader() {
    return (Objects.equals(getLeaderIp(), getLocalIp())) ? NodeRole.LEADER : NodeRole.CANDIDATE;
  }

  @Override
  public Map<String, Object> getMembers() {
    Map<String, Object> map = new HashMap<>();
    List<Node> members = new ArrayList<>();
    for (Member m : hz.getCluster().getMembers()) {
      Node node = new Node();
      node.setIp(String.format("%s:%s", getMemberIp(m), getMemberPort(m)));
      node.setRole(isLeaderMember(m));
      members.add(node);
    }
    map.put("size", hz.getCluster().getMembers().size());
    map.put("members", members);
    return map;
  }

  private NodeRole isLeaderMember(Member m) {
    return Objects.equals(getMemberIp(m), getLeaderIp())
      && Objects.equals(getMemberPort(m), getLeaderPort())
      ? NodeRole.LEADER : NodeRole.CANDIDATE;
  }

  private String getLeaderIp() {
    Member leader = hz.getCluster().getMembers().iterator().next();
    return leader.getSocketAddress().getAddress().getHostAddress();
  }

  private String getMemberIp(Member m) {
    return m.getSocketAddress().getAddress().getHostAddress();
  }

  private String getLocalIp() {
    return hz.getCluster().getLocalMember().getSocketAddress().getAddress().getHostAddress();
  }

  private int getLeaderPort() {
    Member leader = hz.getCluster().getMembers().iterator().next();
    return leader.getAddress().getPort();
  }

  private int getMemberPort(Member member) {
    return member.getAddress().getPort();
  }
}

