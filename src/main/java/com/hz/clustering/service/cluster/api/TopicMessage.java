package com.hz.clustering.service.cluster.api;

import static java.util.Objects.requireNonNull;

import lombok.Value;

@Value
public class TopicMessage<T> {
  T value;

  public TopicMessage(final T value) {
    super();
    this.value = requireNonNull(value);
  }
}
