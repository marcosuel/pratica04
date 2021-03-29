package com.company.pratica04.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)  
                .select()                           
                .apis(RequestHandlerSelectors.basePackage("com.company.pratica04"))              
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Prática 04 API")
				.description("*insira uma boa descrição aqui*")
				.version("v1.0.1")
				.contact(new Contact("Marcos Suel", "https://github.com/marcosuel", "marcosuelns@gmail.com"))
				.license("ISC License")
				.licenseUrl("https://opensource.org/licenses/ISC")
				.build();
	}
	
}
