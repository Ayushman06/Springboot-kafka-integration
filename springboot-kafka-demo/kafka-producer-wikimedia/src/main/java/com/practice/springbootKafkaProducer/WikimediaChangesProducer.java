package com.practice.springbootKafkaProducer;

import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import okhttp3.Headers;

@Service
public class WikimediaChangesProducer {

	@Value("${spring.kafka.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, String> kafkaTemplate;
	private EventSource eventSource;

	public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostConstruct
	public void sendMessage() {

		EventHandler eventHandler = new WikimediaEventHandler(kafkaTemplate, topicName);

		Headers headers = Headers.of("User-Agent", "springboot-kafka-demo/1.0");

		eventSource = new EventSource.Builder(eventHandler,
				URI.create("https://stream.wikimedia.org/v2/stream/recentchange")).headers(headers)
				.reconnectTime(Duration.ofSeconds(10)).readTimeout(Duration.ofSeconds(60)).build();

		eventSource.start();
	}

	@PreDestroy
	public void shutdown() {
		if (eventSource != null) {
			eventSource.close();
		}
	}
}