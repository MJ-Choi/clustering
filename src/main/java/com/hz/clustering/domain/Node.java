package com.hz.clustering.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
  private String ip;
  private NodeRole role;

  public enum NodeRole{
    LEADER,
    CANDIDATE,
    OUT_OF_SVC
  }

}
