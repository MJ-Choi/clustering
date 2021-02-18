package com.hz.clustering.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

  @Value("${test.value}")
  private String test;

  public String getTest() {
    return test;
  }
}
