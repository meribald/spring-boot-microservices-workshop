package io.javabrains.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.MovieInfoServiceDelegate;
import io.javabrains.moviecatalogservice.services.RatingDataServiceDelegate;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RatingDataServiceDelegate ratingDataServiceDelegate;

	@Autowired
	private MovieInfoServiceDelegate movieInfoServiceDelegate;

	@Autowired
	WebClient.Builder webClientBuilder;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/{userId}")
	// @HystrixCommand(fallbackMethod = "getFallbackCatalog") // does not work after
	// granular control has been added
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		logger.trace(String.format("getCatalog is called. user id: %s", userId));

		UserRating userRating = ratingDataServiceDelegate.getUserRating(userId);
		return userRating.getRatings().stream().map(movieInfoServiceDelegate::getCatalogItem)
				.collect(Collectors.toList());

	}

	private List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
		return Arrays.asList(new CatalogItem("no movie", "", 0));
	}

	@GetMapping("/granular/{userId}")
	public List<CatalogItem> getGranularCatalog(@PathVariable("userId") String userId) {

		UserRating userRating = ratingDataServiceDelegate.getUserRating(userId);
		return userRating.getRatings().stream().map(movieInfoServiceDelegate::getCatalogItem)
				.collect(Collectors.toList());
	}

}

/*
 * Alternative WebClient way Movie movie =
 * webClientBuilder.build().get().uri("http://localhost:8082/movies/"+
 * rating.getMovieId()) .retrieve().bodyToMono(Movie.class).block();
 */