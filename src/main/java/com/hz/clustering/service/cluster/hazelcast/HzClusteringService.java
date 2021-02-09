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
  public NodeRole isLeader() {
    return (Objects.equals(getLeaderIp(), getLocalIp())) ? NodeRole.LEADER : NodeRole.CANDIDATE;
  }

  @Override
  public Map<String, Object> getMembers() {
    Map<String, Object> map = new HashMap<>();
    List<Node> members = new ArrayList<>();
    for (Member m : hz.getCluster().getMembers()) {
      Node node = new Node();
      node.setIp(m.getAddress().toString());
      node.setRole(isLeaderMember(m));
      members.add(node);
    }
    map.put("size", hz.getCluster().getMembers().size());
    map.put("members", members);
    return map;
  }

  private NodeRole isLeaderMember(Member m) {
    return Objects.equals(getMemberIp(m), getLeaderIp()) ? NodeRole.LEADER : NodeRole.CANDIDATE;
  }

  private InetAddress getLeaderIp() {
    InetAddress ip = null;
    try {
      Member oldestMember = hz.getCluster().getMembers().iterator().next();
      ip = oldestMember.getAddress().getInetAddress();
      log.debug(String.format("getInetAddress() :: LEADER IP = %s", ip.toString()));
    } catch (UnknownHostException e) {
      log.error(e.getMessage());
    }
    return ip;
  }

  private InetAddress getMemberIp(Member m) {
    InetAddress ip = null;
    try {
      ip = m.getAddress().getInetAddress();
      log.debug(String.format("MEMBER IP = %s", ip.toString()));
    } catch (UnknownHostException e) {
      log.error(e.getMessage());
    }
    return ip;
  }

  private InetAddress getLocalIp() {
    InetAddress ip = null;
    try {
      ip = InetAddress.getLocalHost();
      log.debug(String.format("LOCAL IP = %s", ip.toString()));
    } catch (UnknownHostException e) {
      log.error(e.getMessage());
    }
    return ip;
  }
}

