package com.paritask.api.tests;

import com.paritask.api.client.SearchBreweriesClient;
import com.paritask.api.dto.response.Brewery;
import com.paritask.api.util.ApiUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.paritask.api.assertions.Assertions.checkStatusCode;

@SpringBootTest
public class SearchBreweriesTests extends BaseTest {

    @Autowired
    private SearchBreweriesClient searchBreweriesClient;

    @Test
    void checkCanGetListOfBreweries() {
        Response response = searchBreweriesClient.getAllBreweriesListBySpecifiedQuery("dog");
        List<Brewery> breweries = List.of(ApiUtils.getBody(Brewery[].class, response));

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            softly.assertThat(breweries)
                    .withFailMessage("Response is null")
                    .isNotNull();
            softly.assertThat(breweries.size())
                    .withFailMessage("Breweries list is empty.")
                    .isGreaterThan(0);
        });
    }

    @Test
    void checkAllBreweriesHaveSpecifiedKeywordInName() {
        Response response = searchBreweriesClient.getAllBreweriesListBySpecifiedQuery("dog");
        List<Brewery> breweries = List.of(ApiUtils.getBody(Brewery[].class, response));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(breweries.stream().allMatch(e -> e.getName().toLowerCase().contains("dog")))
                    .withFailMessage("Not all breweries contain specified name")
                    .isTrue();
        });
    }

    @Test
    void checkCanGetSpecifiedCountOfBreweriesByKeyword() {
        Response response = searchBreweriesClient.getSpecifiedNumberOfBreweriesPerPage("dog", "3");
        List<Brewery> breweries = List.of(ApiUtils.getBody(Brewery[].class, response));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(breweries.size())
                    .withFailMessage("Expected size was 3 but actual is: " + breweries.size())
                    .isEqualTo(3);
        });
    }

    @Test
    void checkCanGetBreweriesWithWhitespaceInSearchKeyword() {
        Response response = searchBreweriesClient.getSpecifiedNumberOfBreweriesPerPage("dog_cat", "3");
        List<Brewery> breweries = List.of(ApiUtils.getBody(Brewery[].class, response));

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            softly.assertThat(breweries)
                    .withFailMessage("Response is null")
                    .isNotNull();
            softly.assertThat(breweries.size())
                    .withFailMessage("Breweries list is empty.")
                    .isGreaterThan(0);
        });
    }

    @Test
    void checkCanFindBreweryByCertainSpecificName() {
        Response response = searchBreweriesClient.getAllBreweriesListBySpecifiedQuery("broken_rock_brewery");
        List<Brewery> breweries = List.of(ApiUtils.getBody(Brewery[].class, response));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(breweries.get(0).getName().toLowerCase())
                    .withFailMessage("Brewery name doesn't match specified")
                    .isEqualTo("broken rock brewery");
        });
    }
}
