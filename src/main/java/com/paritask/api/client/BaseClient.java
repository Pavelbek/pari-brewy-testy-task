package com.paritask.api.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.Map;

public abstract class BaseClient {

    private RequestSpecBuilder reqSpec;

    @Value("${base.url}")
    private String baseUrl;

    @PostConstruct
    private void setUpBasePath() {
        RestAssured.baseURI = baseUrl;
    }

    public RequestSpecification setUp(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    protected Response executeGet(String path, Map<String, Object> params, Map<String, Object> pathParams) {
        reqSpec = new RequestSpecBuilder();
        if (params != null) {
            reqSpec.addParams(params);
        }
        if (pathParams != null)        {
            reqSpec.addPathParams(pathParams);
        }

        return  setUp()
                .when()
                .spec(reqSpec.build())
                .get(path)
                .andReturn();
    }

}
