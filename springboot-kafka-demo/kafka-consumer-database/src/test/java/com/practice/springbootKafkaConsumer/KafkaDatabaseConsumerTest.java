package com.practice.springbootKafkaConsumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.practice.springbootKafkaConsumer.data.WikimediaDTO;
import com.practice.springbootKafkaConsumer.data.WikimediaEntity;
import com.practice.springbootKafkaConsumer.mapper.WikimediaMapper;
import com.practice.springbootKafkaConsumer.repository.WikimediaRepository;

class KafkaDatabaseConsumerTest {

	@Mock
	private WikimediaRepository wikimediaRepository;

	@Mock
	private WikimediaMapper wikimediaMapper;

	@InjectMocks
	private KafkaDatabaseConsumer kafkaDatabaseConsumer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void consume_shouldSaveMappedEntity() {
		// Arrange
		String eventMessage = "test-event-message";
		WikimediaDTO dto = new WikimediaDTO(eventMessage);
		WikimediaEntity entity = new WikimediaEntity();

		when(wikimediaMapper.toEntity(dto)).thenReturn(entity);

		// Act
		kafkaDatabaseConsumer.consume(eventMessage);

		// Assert
		verify(wikimediaMapper, times(1)).toEntity(dto);
		verify(wikimediaRepository, times(1)).save(entity);
	}
}
