package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.HTTP;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;

import java.text.MessageFormat;

public class PersonalDataItalyResidenceSteps extends InternalSteps implements En {

    private final Context context;

    private final String ITALY_RESIDENCE_ENDPOINT = "/v1/customer-data/{0}/personal-data/italy-residence";

    @Inject
    public PersonalDataItalyResidenceSteps(Context context) {
        this.context = context;

        When("^request submitted to personal data italy-residence with UUID type \"([^\"]*)\" and last two years is \"([^\"]*)\"$",
                (String uuidType, String lastTwoYears) -> {

                    String requestBody = "{\n" +
                            "  \"italianResidenceLastTwoYears\": \"$italianResidenceLastTwoYears\"\n" +
                            "}";

                    requestBody = requestBody.replace("$italianResidenceLastTwoYears",lastTwoYears);

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .header("x-ing-country-code","IT")
                            .queryParam("UUIDType", uuidType)
                            .when()
                            .body(requestBody).log().all()
                            .patch(MessageFormat.format(ITALY_RESIDENCE_ENDPOINT,context.getData().getUuid()))
                            .then().log().all().extract().response();

                    context.setResponse(response);
                });

        When("^request submitted with out json body with UUID type \"([^\"]*)\"$", (String uuidType) -> {

            Response response = SecurityHttp.givenSecure()
                    .baseUri(getArtifact().getURI())
                    .port(getArtifact().getPort()).urlEncodingEnabled(false)
                    .contentType(ContentType.JSON)
                    .queryParam("UUIDType", uuidType)
                    .when()
                    .body("{ }").log().all()
                    .patch(MessageFormat.format(ITALY_RESIDENCE_ENDPOINT,context.getData().getUuid()))
                    .then().log().all().extract().response();

            context.setResponse(response);

        });
    }
}
