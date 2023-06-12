package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;

import java.text.MessageFormat;

public class CustomerNationalityCodeSteps extends InternalSteps implements En {

    private final Context context;
    private final String NATIONALITY_CODE_ENDPOINT = "/v1/customer-data/{0}/personal-data/nationality-code";

    @Inject
    public CustomerNationalityCodeSteps(Context context) {
        this.context = context;

        When("^request submitted to nationality code endpoint with \"([^\"]*)\" uuid and \"([^\"]*)\"$",
                (String uuidType, String nationalityCode) -> {

                    String requestBody = "{\n" +
                       "  \"nationalityCode\": \"$nationalityCode\"\n" +
                       "}";

                    requestBody = requestBody.replace("$nationalityCode",nationalityCode);

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .queryParam("UUIDType", uuidType)
                            .when()
                            .body(requestBody).log().all()
                            .patch(MessageFormat.format(NATIONALITY_CODE_ENDPOINT,context.getData().getUuid()))
                            .then().log().all().extract().response();

                    context.setResponse(response);
        });
        When("^request submitted to nationality code endpoint with \"([^\"]*)\" uuid without request body$",
                (String uuidType) -> {

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .queryParam("UUIDType", uuidType)
                            .when()
                            .body("").log().all()
                            .patch(MessageFormat.format(NATIONALITY_CODE_ENDPOINT,context.getData().getUuid()))
                            .then().log().all().extract().response();

                    context.setResponse(response);        });
    }
}
