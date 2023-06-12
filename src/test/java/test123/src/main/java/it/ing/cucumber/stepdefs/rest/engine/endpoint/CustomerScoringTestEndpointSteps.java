package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.HTTP;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.AccessTokenManager;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;

import java.text.MessageFormat;

/**
 * CustomerScoring Endpoint Step Definition
 */

public class CustomerScoringTestEndpointSteps extends InternalSteps implements En {

    private Context context;
    private final AccessTokenManager accessTokenManager;

    private final String PRODUCT_AGREEMENT_ENDPOINT = "/v1/customer-data/{0}/products/loans/scoring";

    @Inject
    public CustomerScoringTestEndpointSteps(Context context, AccessTokenManager accessTokenManager) {
        this.context = context;
        this.accessTokenManager = accessTokenManager;


        When("^verify that customer scoring info endpoint for \"([^\"]*)\"$", (String userKey) -> {

            Response response = SecurityHttp.givenSecure()
                    .baseUri(getArtifact().getURI())
                    .port(getArtifact().getPort()).urlEncodingEnabled(false)
                    .contentType(ContentType.JSON)
                    .headers(HTTP.Header.X_ING_ACCESS_TOKEN.toString(),
                            accessTokenManager.getAccessToken())
                    .log().all()
                    .when().queryParam("UUIDType=" + context.getData().getUuidtype())
                    .get(MessageFormat.format(PRODUCT_AGREEMENT_ENDPOINT, context.getData().getUuid()))
                    .then().extract().response();

            context.setResponse(response);

        });
    }
}
