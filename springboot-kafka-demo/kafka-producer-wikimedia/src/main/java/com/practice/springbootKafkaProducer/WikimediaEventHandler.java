package com.practice.springbootKafkaProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

public class WikimediaEventHandler implements EventHandler {

	private static final Logger logger = LoggerFactory.getLogger(WikimediaEventHandler.class);

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final String topic;

	public WikimediaEventHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}

	@Override
	public void onOpen() {
		logger.info("SSE connection opened");
	}

	@Override
	public void onClosed() {
		logger.info("SSE connection closed");
	}

	@Override
	public void onMessage(String event, MessageEvent messageEvent) {

		logger.info(String.format("Event data : %s", messageEvent.getData()));

		kafkaTemplate.send(topic, messageEvent.getData()).whenComplete((result, ex) -> {
			if (ex != null) {
				logger.error("Failed to send message to Kafka", ex);
			}
		});
	}

	@Override
	public void onComment(String comment) {
	}

	@Override
	public void onError(Throwable t) {
		logger.warn("SSE error", t);
	}
}