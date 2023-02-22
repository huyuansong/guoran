package com.guoran.server.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@ConditionalOnProperty (name = "swagger.enable",  havingValue = "true")
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("wei")
				.apiInfo(apiInfo())
				.select()
				// 为当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.guoran.server"))
				.paths(PathSelectors.any())
				.build();
	}
	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("接口文档")
				// 创建人信息
				.contact(new Contact("",null,""))
				// 版本号
				.version("1.0")
				// 描述
				.description("API 描述")
				.build();
	}
}


