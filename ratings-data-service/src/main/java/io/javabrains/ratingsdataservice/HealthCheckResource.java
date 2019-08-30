package io.javabrains.ratingsdataservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckResource {

	@GetMapping("/health")
	public ResponseEntity<String> myCustomCheck() {
		String message = "Testing my healh check function";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
