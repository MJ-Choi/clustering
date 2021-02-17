package com.hz.clustering.service.cluster.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hz.clustering.domain.Node.NodeRole;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HzClusteringServiceTest {

  private HzClusteringService service;

  final HazelcastInstance instance = Hazelcast.newHazelcastInstance();

  @Before()
  public void setup() {
    service = new HzClusteringService(instance);
  }

  @After
  public void shutdonw(){
    instance.shutdown();
  }

  @Test
  public void isLeader() {
    Assert.assertEquals(service.isLeader(), NodeRole.LEADER);
  }

  @Test
  public void getMembers() {
    Map<String, Object> map = service.getMembers();
    Assert.assertEquals(map.get("size"), instance.getCluster().getMembers().size());
  }
}