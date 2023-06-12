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
 * UpdateProductAgreements Endpoint Step Definition
 */
public class UpdateProductAgreementsTestEndpointSteps extends InternalSteps implements En {

    private Context context;
    private final AccessTokenManager accessTokenManager;

    private final String PRODUCT_AGREEMENT_ENDPOINT = "/v1/customer-data/{0}/product-agreements";

    @Inject
    public UpdateProductAgreementsTestEndpointSteps(Context context, AccessTokenManager accessTokenManager) {

        this.context = context;
        this.accessTokenManager = accessTokenManager;
        When("verify that product agreement info endpoint for {string}", (String key) ->
        {

            Response response = SecurityHttp.givenSecure()
                    .baseUri(getArtifact().getURI())
                    .port(getArtifact().getPort()).urlEncodingEnabled(false)
                    .contentType(ContentType.JSON)
                    .headers(HTTP.Header.X_ING_ACCESS_TOKEN.toString(),
                            accessTokenManager.getAccessToken())
                    .log().all()
                    .when().queryParam("UUIDType=" + context.getData().getUuidtype())
                    .get(MessageFormat.format(PRODUCT_AGREEMENT_ENDPOINT, context.getData().getUuid(),
                            context.getData().getProductNumber(),
                            context.getData().getType(),
                            context.getData().getRole(),
                            context.getData().getState()))
                    .then().extract().response();
            context.setResponse(response);
        });

    }
}
