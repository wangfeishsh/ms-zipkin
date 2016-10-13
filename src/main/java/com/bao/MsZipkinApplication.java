package com.bao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class MsZipkinApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// Use this for debugging (or if there is no Zipkin server running on port 9411)
	@Bean
	@ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
	public ZipkinSpanReporter spanCollector() {
		return new ZipkinSpanReporter() {
			@Override
			public void report(zipkin.Span span) {
				log.info(String.format("Reporting span [%s]", span));
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MsZipkinApplication.class, args);
	}
}
