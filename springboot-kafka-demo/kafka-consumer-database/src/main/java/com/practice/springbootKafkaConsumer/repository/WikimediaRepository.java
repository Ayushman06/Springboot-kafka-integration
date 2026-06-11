package com.practice.springbootKafkaConsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.springbootKafkaConsumer.data.WikimediaEntity;

@Repository
public interface WikimediaRepository extends JpaRepository<WikimediaEntity, Long> {

}
