package com.emlakjet.videostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class VideoManagementSystemApplication {

	public static void main(String[] args) {
		log.info("starting video management system app...");
		SpringApplication.run(VideoManagementSystemApplication.class, args);
	}

//	@Bean
//	GraphQLSchema schema() {
//		DataFetcher<CompletableFuture<String>> test = env -> CompletableFuture.supplyAsync(() -> {
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}
//			log.info("test data fetcher is called");
//			return "hello world!";
//		});
//		return GraphQLSchema.newSchema()
//				.query(GraphQLObjectType.newObject().name("query")
//						.field(field -> field.name("test").type(Scalars.GraphQLString)).build())
//				.codeRegistry(GraphQLCodeRegistry.newCodeRegistry()
//						.dataFetcher(FieldCoordinates.coordinates("query", "test"), test).build())
//				.build();
//	}

}
