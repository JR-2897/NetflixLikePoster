package com.netflix.like.poster.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.netflix.like.poster.model.Poster;

@Repository
public interface PosterRepository extends MongoRepository<Poster, Integer> {

	Poster findByIdMedia(Integer id);
}
