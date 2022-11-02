package com.tutorial.jwtsecurity.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  private static final String API_NAME = "강원독";
  private static final String API_VERSION = "1.0";
  private static final String API_DESCRIPTION = "Swagger 강원독 API 문서 \n"
      + "basic key : gangwondog value : gangwondog_diikanpp-9a0s-kkoa-aiikwnna900aa8c";

  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo()) // API 문서에 대한 내용
        .securityContexts(Arrays.asList(securityContext())) // swagger에서 jwt 토큰값 넣기위한 설정
        .securitySchemes(Arrays.asList(apiKey(),basicAuth())) // swagger에서 jwt 토큰값 넣기위한 설정
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.tutorial.jwtsecurity")) // Swagger를 적용할 package명 작성
        .paths(PathSelectors.any()) // PathSelectors.any() 해당패키지 하위에 있는 모든 url에 적용, 특정 url만 선택 가능
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(API_NAME) // API 이름지정
        .version(API_VERSION) // API 버전
        .description(API_DESCRIPTION) // API 설명
        //.license("라이센스 작성")
        //.licenseUrl("라이센스 URL 작성")
        .build();
  }

  // swagger에서 jwt 토큰값 넣기위한 설정
  private ApiKey apiKey() {
    return new ApiKey("bearer", "Authorization", "header");
  }

  private BasicAuth basicAuth(){
//    List<VendorExtension> vendorExtensions = new ArrayList<>();
//    vendorExtensions.add(new StringVendorExtension("CLIENT_ID","gangwondog"));
//    vendorExtensions.add(new StringVendorExtension("CLIENT_SECRET","gangwondog_diikanpp-9a0s-kkoa-aiikwnna900aa8c"));
    return new BasicAuth("basic");
  }


  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("basic", authorizationScopes));
  }
}