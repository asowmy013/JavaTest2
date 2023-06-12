package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveIdentificationInfoSteps extends InternalSteps implements En {

    private final String SAVE_IDENTIFICATION_INFO = "/v1/customer-data/involved-party/{0}/identification";
    private final Context context;

    @Inject
    public SaveIdentificationInfoSteps(Context context) {
        this.context = context;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String docNumber = context.getSaltString(2, 4);

        When("^request submitted to save identification endpoint with \"([^\"]*)\" and \"([^\"]*)\" flag$",
                (String identificationMethod, String identificationFlag) -> {
                    String customer;


                    try {
                        customer = context.getData().getUuid();
                    } catch (NullPointerException e) {
                        customer = context.getCustomerId("global");
                    }
                    String requestBody = context.getPayloadData("saveIdentificationInfo.json")
                            .replace("$identificationMethod", identificationMethod)
                            .replace("$identificationFlag", identificationFlag)
                            .replace("$docNumberType", "1")
                            .replace("$idDocNumber", docNumber)
                            .replace("$identificationDate", dateFormat.format(date))
                            .replace("$dateOfIssue", dateFormat.format(date));

                    Response response = this.getSaveIdentificationInfoResponse(requestBody, customer);
                    context.setResponse(response);
                });

        When("^request submitted to save identification endpoint with different \"([^\"]*)\"$",
                (String docNumberType) -> {


                    String requestBody = context.getPayloadData("saveIdentificationInfo.json")
                            .replace("$identificationMethod", "1")
                            .replace("$identificationFlag", "S")
                            .replace("$idDocNumber", docNumber)
                            .replace("$docNumberType", docNumberType)
                            .replace("$identificationDate", dateFormat.format(date))
                            .replace("$dateOfIssue", dateFormat.format(date));

                    Response response = this.getSaveIdentificationInfoResponse(requestBody,context.getCustomerId("global"));
                    context.setResponse(response);
                });

        When("^request submitted to save identification endpoint without mandatory requests$", () -> {
            Response response = this.getSaveIdentificationInfoResponse("", context.getData().getUuid());
            context.setResponse(response);
        });
        When("^request submitted to save identification endpoint with future date in identification date$", () -> {

            Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));

            String requestBody = context.getPayloadData("saveIdentificationInfo.json")
                    .replace("$identificationMethod", "1")
                    .replace("$identificationFlag", "S")
                    .replace("$idDocNumber", docNumber)
                    .replace("$docNumberType", "1")
                    .replace("$identificationDate", dateFormat.format(tomorrow))
                    .replace("$dateOfIssue", dateFormat.format(date));

            Response response = this.getSaveIdentificationInfoResponse(requestBody, context.getData().getUuid());
            context.setResponse(response);

        });
    }

    private Response getSaveIdentificationInfoResponse(String requestBody, String uuid) {
        return SecurityHttp.givenSecure()
                .baseUri(getArtifact().getURI())
                .port(getArtifact().getPort()).urlEncodingEnabled(false)
                .contentType(ContentType.JSON)
                .queryParams("UUIDType","global")
                .when().log().all()
                .body(requestBody)
                .post(MessageFormat.format(SAVE_IDENTIFICATION_INFO, uuid))
                .then().log().all().extract().response();
    }
}
