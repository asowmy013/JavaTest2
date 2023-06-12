package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.HTTP;
import com.ing.util.Log4test;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import it.ing.cucumber.stepdefs.rest.engine.utilities.AccessTokenManager;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;
import it.ing.cucumber.stepdefs.rest.engine.utilities.DataLoader;
import org.junit.Assert;

import java.util.List;

/**
 * KeepAliveSteps and Customer data step definition
 */

public class GenericTestEndpointSteps extends InternalSteps implements En {


    private final Context context;
    private final AccessTokenManager accessTokenManager;

    @Inject
    public GenericTestEndpointSteps(Context context, AccessTokenManager accessTokenManager) {
        this.context = context;
        this.accessTokenManager = accessTokenManager;
        DataLoader dataloader = new DataLoader();
        Given("a keepAlive", () -> Log4test.logTid("1"));

        Then("keepAlive response status is {string}",
                (String httpStatus) -> SecurityHttp.givenSecure()
                        .baseUri(getArtifact().getURI())
                        .port(getArtifact().getPort())
                       .contentType(ContentType.TEXT)
                        .headers(HTTP.Header.X_ING_ACCESS_TOKEN.toString(),
                                accessTokenManager.getAccessToken())
                        .log().all()
                        .when()
                        .get("/keepalive")
                        .then()
                        .statusCode(Integer.parseInt(httpStatus))
                        .contentType(ContentType.TEXT)
                        .log().all());

        Given("a customer {string}", (String keyCustomerData) -> {
            if (dataloader.getDataTest() == null) {
                dataloader.loadData();
            }
            context.setData(dataloader.getCustomerData(keyCustomerData));
        });

        Then("^endpoint response status is (\\d+)$", (Integer statusCode) -> {
            this.context.getResponse().then().statusCode(statusCode);
        });

        And("^response has following data$", (DataTable dataTable) -> {
            List<String> data = dataTable.asList();
            data.stream().map(x -> context.getResponse().jsonPath().get(x)).forEach(Assert::assertNotNull);
        });

        And("^response body has \"([^\"]*)\" value for \"([^\"]*)\" attribute$",
                (String value, String attribute) -> {
                    Assert.assertEquals(value, context.getResponse().body().jsonPath().getString(attribute));
                });
    }
}
