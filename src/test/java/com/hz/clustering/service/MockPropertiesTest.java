package com.hz.clustering.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class MockPropertiesTest {

  @Spy
  private final PropertiesService propertiesService = new PropertiesService();

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(propertiesService, "test.value", "it's a security key");
  }

  @Test
  public void testUpdateUser() {
    System.out.println(propertiesService.getTest());
  }
}