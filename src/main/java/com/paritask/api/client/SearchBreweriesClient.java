package com.paritask.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.paritask.api.endpoints.Endpoints.AUTOCOMPLETE;
import static com.paritask.api.endpoints.Endpoints.SEARCH;

@Component
public class SearchBreweriesClient extends BaseClient {

    @Step("Getting list of all breweries by query: {query}")
    public Response getAllBreweriesListBySpecifiedQuery(String query) {
        return executeGet(SEARCH, Map.of("query", query), null);
    }

    @Step("Getting list of breweries by query")
    public Response getSpecifiedNumberOfBreweriesPerPage(String query, String amount) {
        return executeGet(SEARCH, Map.of("query", query, "per_page", amount), null);
    }

    @Step("Getting list of names in IDs of breweries by query: {query}")
    public Response getSpecifiedNumberOfBreweriesPerPage(String query) {
        return executeGet(AUTOCOMPLETE, Map.of("query", query), null);
    }

}
