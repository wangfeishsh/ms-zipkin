package com.bao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableAsync
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

//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//		factory.getContextValves();
//		factory.setPort(9000);
//		factory.setSessionTimeout(10, TimeUnit.MINUTES);
//		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
//		factory.getContextValves();
//		Collection collection = factory.getEngineValves();
//		System.out.println("========");
//		collection.forEach(v->{
//			System.out.println(v.toString());
//		});
//		System.out.println("========");
//		return factory;
//	}

	public static void main(String[] args) {
		SpringApplication.run(MsZipkinApplication.class, args);
	}
}
