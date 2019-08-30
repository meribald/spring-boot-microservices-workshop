package io.javabrains.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;

@Service
public class RatingDataServiceDelegate {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackUserRating", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "50"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
	public UserRating getUserRating(String userId) {
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId,
				UserRating.class);
		return userRating;
	}

	private UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("no movie found - rating service", 100)));
		return userRating;
	}
}
