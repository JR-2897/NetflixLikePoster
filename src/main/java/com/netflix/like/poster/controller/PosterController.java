package com.netflix.like.poster.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.like.poster.model.Poster;
import com.netflix.like.poster.repository.PosterRepository;

@RestController
public class PosterController {

	@Autowired
	PosterRepository posterRepo;
	
	@GetMapping("/all/posters")
	public List<Poster> getAllPoster() {
		return posterRepo.findAll();
	}
	
	@GetMapping("/{idMedia}/poster")
	public Poster getPosterByIdMedia(@PathVariable int idMedia) {
		return posterRepo.findByIdMedia(idMedia);
	}
	
	@PostMapping("/add/poster")
	public ResponseEntity<Object> addPosterToDatabase(@RequestBody Poster posterToadd) {
		Poster posterAdded = posterRepo.save(posterToadd);
		if(Objects.isNull(posterAdded)) {
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(posterAdded.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/updater/poster/{id}")
	public Poster updatePosterToDatabase(@PathVariable int id, @RequestBody Poster updatePoster) {
		Poster posterToUpdate = posterRepo.save(updatePoster);
		if(!Objects.isNull(posterToUpdate))
		{
			return posterToUpdate;
		}
		return null;
	}
}
