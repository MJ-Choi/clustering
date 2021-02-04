package com.hz.clustering.service.cluster.api;

public interface ClusteredTopic<T> extends DestroyableObject {

  void register(TopicMessageListener<T> listener);

  void unregister(TopicMessageListener<T> listener);

  void publish(T s);
}