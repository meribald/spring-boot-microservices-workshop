package io.javabrains.movieinfoservice.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//@RestController
public class HealthCheckResource {

	// @GetMapping("/health")
	public ResponseEntity<String> myCustomCheck() {
		String message = "Testing my healh check function";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
