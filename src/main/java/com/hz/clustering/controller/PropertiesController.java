package com.hz.clustering.controller;

import com.hz.clustering.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class PropertiesController {

  private PropertiesService propertiesService;

  @Autowired
  PropertiesController(PropertiesService propertiesService) {
    this.propertiesService = propertiesService;
  }

  @RequestMapping(value = "")
  public String getValue() {
    log.info("getValue() :: Run Controller");
    return propertiesService.getTest();
  }
}
