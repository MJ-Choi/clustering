package com.hz.clustering.service.cluster.api;

@FunctionalInterface
public interface TopicMessageListener<T> {

  void onMessage(TopicMessage<T> message);
}
