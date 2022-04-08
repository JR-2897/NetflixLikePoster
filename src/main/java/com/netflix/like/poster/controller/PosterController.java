package com.netflix.like.poster.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.like.poster.model.Poster;
import com.netflix.like.poster.repository.PosterRepository;

@RestController
public class PosterController {

	@Autowired
	PosterRepository posterRepo;

	RestTemplate restTemplate = new RestTemplate();

	//String urlUser = "http://localhost:8081/";
	String urlUser = "http://user-webservice-app:8081/";
	
	@PostMapping("/all/posters")
	public ResponseEntity<List<Poster>> getAllPoster(@RequestBody int idUser) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<String>(headers);

		Object obj = restTemplate.exchange(urlUser + "user/status/" + idUser, HttpMethod.GET, request, Object.class)
				.getBody();
		if (Objects.isNull(obj)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String jsonStr = mapper.writeValueAsString(obj);
		JSONObject json = new JSONObject(jsonStr);
		if (json.getString("status").equals("ADMIN")) {
			return ResponseEntity.ok(posterRepo.findAll());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/{idMedia}/poster")
	public ResponseEntity<Poster> getPosterByIdMedia(@PathVariable int idMedia, @RequestBody int idUser)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<String>(headers);

		Object obj = restTemplate.exchange(urlUser + "user/status/" + idUser, HttpMethod.GET, request, Object.class)
				.getBody();
		if (Objects.isNull(obj)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String jsonStr = mapper.writeValueAsString(obj);
		JSONObject json = new JSONObject(jsonStr);
		if (json.getString("status").equals("PROVIDER")) {
			return ResponseEntity.ok(posterRepo.findByIdMedia(idMedia));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/add/poster/{idUser}")
	public ResponseEntity<Object> addPosterToDatabase(@PathVariable int idUser, @RequestBody Poster posterToadd)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<String>(headers);

		Object obj = restTemplate.exchange(urlUser + "user/status/" + idUser, HttpMethod.GET, request, Object.class)
				.getBody();
		if (Objects.isNull(obj)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String jsonStr = mapper.writeValueAsString(obj);
		JSONObject json = new JSONObject(jsonStr);
		if (json.getString("status").equals("PROVIDER")) {
			Poster posterAdded = posterRepo.save(posterToadd);
			if (Objects.isNull(posterAdded)) {
				return ResponseEntity.noContent().build();
			}
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(posterAdded.getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PutMapping("/updater/poster/{id}/{idUser}")
	public ResponseEntity<Poster> updatePosterToDatabase(@PathVariable int id, @RequestBody Poster updatePoster,
			@PathVariable int idUser) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<String>(headers);

		Object obj = restTemplate.exchange(urlUser + "user/status/" + idUser, HttpMethod.GET, request, Object.class)
				.getBody();
		if (Objects.isNull(obj)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String jsonStr = mapper.writeValueAsString(obj);
		JSONObject json = new JSONObject(jsonStr);
		if (json.getString("status").equals("PROVIDER")) {
			Poster posterToUpdate = posterRepo.save(updatePoster);
			if (!Objects.isNull(posterToUpdate)) {
				return ResponseEntity.ok(posterToUpdate);
			}
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
