package com.hz.clustering.service.cluster.hazelcast;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import com.hz.clustering.service.cluster.api.ClusteredTopic;
import com.hz.clustering.service.cluster.api.TopicMessage;
import com.hz.clustering.service.cluster.api.TopicMessageListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
final class HazelcastTopic<T> implements ClusteredTopic<T>, MessageListener<T> {

  ITopic<T> delegate;
  List<TopicMessageListener<T>> listeners;

  HazelcastTopic(final ITopic<T> delegate) {
    super();
    this.delegate = requireNonNull(delegate);
    this.listeners = new CopyOnWriteArrayList<>();
    delegate.addMessageListener(this);
  }

  @Override
  public void destroy() {
    delegate.destroy();
  }

  @Override
  public void register(final TopicMessageListener<T> listener) {
    listeners.add(listener);
  }

  @Override
  public void unregister(final TopicMessageListener<T> listener) {
    listeners.remove(listener);
  }

  @Override
  public void publish(final T message) {
    delegate.publish(message);
  }

  @Override
  public void onMessage(final Message<T> message) {
    final TopicMessage<T> msg = new TopicMessage<>(message.getMessageObject());
    listeners.forEach(l -> l.onMessage(msg));
  }
}
