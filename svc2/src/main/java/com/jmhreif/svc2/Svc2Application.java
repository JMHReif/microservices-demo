package com.jmhreif.svc2;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableRetry
public class Svc2Application {

	public static void main(String[] args) {
		SpringApplication.run(Svc2Application.class, args);
	}

	@Bean
	WebClient client() { return WebClient.create("http://localhost:8081"); }
}

@RestController
@AllArgsConstructor
@RequestMapping("/goodreads")
class Svc2Controller {
	private final WebClient client;

	@GetMapping
	@Retryable
	Flux<Book> getBooks() { return client.get().uri("/db").retrieve().bodyToFlux(Book.class); }
}

@Data
class Book {
	private String mongoId;
	private String book_id;
	private String title, format, isbn, isbn13, edition_information;
}