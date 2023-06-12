package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;
import org.hamcrest.Matchers;
import org.junit.Assert;

public class TaxCodeValidationEndPointSteps extends InternalSteps implements En {

    /**
     * TaxCodeValidationEndPointSteps class is implementing the steps from tax validation feature
     */
    private final Context context;
    private final String TAX_CODE_VALIDATION_EP = "/v1/customer-data/taxCode/validation";

    @Inject
    public TaxCodeValidationEndPointSteps(Context context)
    {
        this.context = context;


        When("^submitting tax code validation request$", () -> {
            String requestBody = context.getPayloadData("taxCodeValidationPayload.json")
                    .replace("$firstname", context.getData().getFirstname())
                    .replace("$lastname",context.getData().getLastname())
                    .replace("$taxCode",context.getData().getTaxCode());

            Response response = SecurityHttp.givenSecure()
                    .baseUri(getArtifact().getURI())
                    .port(getArtifact().getPort()).urlEncodingEnabled(false)
                    .contentType(ContentType.JSON)
                    .when()
                    .body(requestBody).log().all()
                    .post(TAX_CODE_VALIDATION_EP)
                    .then().log().all().extract().response();

            context.setResponse(response);
        });

        And("^result is \"([^\"]*)\"$", (String result) -> {
            context.getResponse().then().body("result", Matchers.equalTo(result));
        });
    }
}
