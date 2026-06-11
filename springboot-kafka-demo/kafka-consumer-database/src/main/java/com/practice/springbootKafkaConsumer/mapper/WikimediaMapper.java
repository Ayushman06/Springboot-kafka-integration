package com.practice.springbootKafkaConsumer.mapper;

import org.mapstruct.Mapper;

import com.practice.springbootKafkaConsumer.data.WikimediaDTO;
import com.practice.springbootKafkaConsumer.data.WikimediaEntity;

@Mapper(componentModel = "spring")
public interface WikimediaMapper {

	WikimediaDTO toDTO(WikimediaEntity entity);

	WikimediaEntity toEntity(WikimediaDTO dto);

}
