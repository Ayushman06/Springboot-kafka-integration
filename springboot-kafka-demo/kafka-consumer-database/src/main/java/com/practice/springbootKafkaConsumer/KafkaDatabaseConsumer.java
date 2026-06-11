package com.practice.springbootKafkaConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.practice.springbootKafkaConsumer.data.WikimediaDTO;
import com.practice.springbootKafkaConsumer.mapper.WikimediaMapper;
import com.practice.springbootKafkaConsumer.repository.WikimediaRepository;

@Service
public class KafkaDatabaseConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

	private WikimediaRepository wikimediaRepository;
	private WikimediaMapper wikimediaMapper;

	public KafkaDatabaseConsumer(WikimediaRepository wikimediaRepository, WikimediaMapper wikimediaMapper) {
		super();
		this.wikimediaRepository = wikimediaRepository;
		this.wikimediaMapper = wikimediaMapper;
	}

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(String eventMessage) {

		LOGGER.info(String.format("Event message received : %s", eventMessage));
		WikimediaDTO wikimediaDTO = new WikimediaDTO(eventMessage);
		wikimediaRepository.save(wikimediaMapper.toEntity(wikimediaDTO));

	}
}
