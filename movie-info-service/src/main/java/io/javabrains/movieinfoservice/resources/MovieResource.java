package io.javabrains.movieinfoservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javabrains.movieinfoservice.config.MyConfig;
import io.javabrains.movieinfoservice.models.Movie;
import io.javabrains.movieinfoservice.models.MovieSummary;

@RestController
@RequestMapping("/movies")
@EnableConfigurationProperties(value = MyConfig.class)
public class MovieResource {

	@Value("${api.key}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MyConfig config;

	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		// MovieSummary movieSummary =
		// restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId +
		// "?api_key=" + apiKey, MovieSummary.class);

		MovieSummary movieSummary = new MovieSummary();
		movieSummary.setId("100");
		movieSummary.setOverview("overview " + config.getMyproperty());
		movieSummary.setTitle("title");
		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());

	}

}
