package com.jmhreif.svc1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Svc1Application {

	public static void main(String[] args) {
		SpringApplication.run(Svc1Application.class, args);
	}

}

@RestController
@AllArgsConstructor
@RequestMapping("/db")
class Svc1Controller {
	private final BookRepository repo;

	@GetMapping
	Flux<Book> getBooks() { return repo.findAll(); }
}

interface BookRepository extends ReactiveCrudRepository<Book, String> {}

@Data
@Document
class Book {
	@Id
	private String mongoId;
	@NonNull
	private String book_id;

	private String title, format, isbn, isbn13, edition_information;
}