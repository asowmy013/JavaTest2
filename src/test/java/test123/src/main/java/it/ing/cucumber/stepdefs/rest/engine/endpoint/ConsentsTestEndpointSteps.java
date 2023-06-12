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
 * ConsentsTest Endpoint Step Definition
 */
public class ConsentsTestEndpointSteps extends InternalSteps implements En {

    private final Context context;
    private final AccessTokenManager accessTokenManager;

    private final String CONSENTS_ENDPOINT = "/v1/customer-data/{0}/consents";

    @Inject
    public ConsentsTestEndpointSteps(Context context, AccessTokenManager accessTokenManager) {
        this.context = context;
        this.accessTokenManager = accessTokenManager;

        When("^verify that consents info endpoint for \"([^\"]*)\" uuid with flag \"([^\"]*)\"$",
                (String uuidType, String flag) -> {

            String request = context.getPayloadData("flagPayload.json").replace("$flag",flag);

            String uuid = uuidType.equalsIgnoreCase("local") ? context.getData().getLocalUuid() : context.getData().getUuid();

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .queryParam("UUIDType",uuidType)
                            .contentType(ContentType.JSON)
                            .log().all()
                            .when()
                            .body(request)
                            .patch(MessageFormat.format(CONSENTS_ENDPOINT, uuid))
                            .then().log().all().extract().response();

                    context.setResponse(response);

        });
    }
}
