package com.hz.clustering.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

//// RESULT : 내장톰캣 실행되면서 성공
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("application")

//// RESULT: 내장 톰캣도 같이 실행되면서 성공
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@TestPropertySource(properties = {
//  "test.value=testValue"
//})

// RESULT = FAIL
//@TestPropertySource
//@SpringBootTest(properties = "test.value=testttt")

//@SpringBootConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@TestPropertySource(properties = {
//  "test.value=testValue",
//})

public class JunitPropertiesTest {

  @Value("${test.value}")
  String declareInTc;

//  @Autowired
//  private PropertiesService propertiesService;

//  private PropertiesService propertiesService = new PropertiesService();

  PropertiesService propertiesService;
  @Before
  public void setup() {
    this.propertiesService = new PropertiesService();
    ReflectionTestUtils.setField(PropertiesService.class, "test", "utilTest");
  }

  @Test
  public void getTest(){
    System.out.println(String.format("TEST RESULT = %s", propertiesService.getTest()));
    System.out.println("using @TestPropertySource in TestCode = " + declareInTc);
  }

}
