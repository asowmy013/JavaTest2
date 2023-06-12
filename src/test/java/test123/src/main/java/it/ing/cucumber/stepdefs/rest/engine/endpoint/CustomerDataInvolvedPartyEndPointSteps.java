package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;
import it.ing.cucumber.stepdefs.rest.engine.utilities.JsonManager;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CustomerDataInvolvedPartyEndPointSteps extends InternalSteps implements En {

    /**
     * CustomerDataInvolvedPartyEndPointSteps class is implementing the steps from the feature
     */
    private final Context context;
    private final JsonManager jsonManager;


    private final String CUSTOMER_DATA_INVOLVED_PARTY_EP = "/v1/customer-data/involved-party";
    private final String INVOLVED_PARTY_PAYLOAD_FILENAME = "customerDataInvolvedParty.json";

    private final String EXTERNAL_IDENTIFIER_TYPE = "CD_FISCALE_IT";

    @Inject
    public CustomerDataInvolvedPartyEndPointSteps(Context context, JsonManager jsonManager) throws IOException {
        this.context = context;
        this.jsonManager = jsonManager;

       String requestPayload = context.getPayloadData(INVOLVED_PARTY_PAYLOAD_FILENAME);

        When("^submitting request to involved party api with invalid external identifier \"([^\"]*)\"$",
                (String externalIdentifierType) -> {

                    String [] names =  this.getFirstNameAndLastName();
                    String taxCode = this.getTaxCode(names[0],names[1]);

                    String body = requestPayload.replace(EXTERNAL_IDENTIFIER_TYPE, externalIdentifierType)
                            .replace("$taxCode", taxCode)
                            .replace("$givenName", names[0])
                            .replace("$lastName",names[1]);

                    submitRequest(body);
                });

        When("^submitting request by removing \"([^\"]*)\"$",
                (String jsonElementToRemove) -> {

                    String body = requestPayload.replace("$taxCode", context.getData().getTaxCode());
                    body = jsonManager.removeElementFromJsonElement(body,jsonElementToRemove);

                    this.submitRequest(body);
                });


        When("^submitting request to customer data involved party api with valid data$",
                () -> {

                    String [] names =  this.getFirstNameAndLastName();
                    String taxCode = this.getTaxCode(names[0],names[1]);

                    String body = requestPayload.replace("$taxCode", taxCode)
                            .replace("$givenName", names[0])
                            .replace("$lastName",names[1]);

                    submitRequest(body);

                });

        When("^submitting request by removing optional fields from request payload$", (DataTable dataTable) -> {

            String [] names =  this.getFirstNameAndLastName();
            String taxCode = this.getTaxCode(names[0],names[1]);
            System.out.println(taxCode);

            List<String> data = dataTable.asList();
            String body = requestPayload.replace("$taxCode", taxCode)
                    .replace("$givenName", names[0])
                    .replace("$lastName",names[1]);

            for (String jsonData:data) {
                 body = jsonManager.removeElementFromJsonObject(body,jsonData);
            }
            submitRequest(body);
        });

        When("^submitting request to customer data involved party api with existing customer tax number$", () -> {

            String [] names =  this.getFirstNameAndLastName();

            String body = requestPayload.replace("$taxCode", context.getData().getTaxCode())
                    .replace("$givenName", names[0])
                    .replace("$lastName",names[1]);

            submitRequest(body);
        });
    }

    private void submitRequest(String requestBody)
    {
        Response response = SecurityHttp.givenSecure()
                .baseUri(getArtifact().getURI())
                .port(getArtifact().getPort()).urlEncodingEnabled(false)
                .contentType(ContentType.JSON)
                .header("X-ING-LastUpdateUser","SN95LA")
                .when()
                .body(requestBody).log().all()
                .post(CUSTOMER_DATA_INVOLVED_PARTY_EP)
                .then().log().all().extract().response();

        context.setResponse(response);
    }

    private String[] getFirstNameAndLastName()
    {
            String fnRandom = context.getRandomString();
            String lnRandom = context.getRandomString();
            String str[] = {fnRandom,lnRandom};
            return str;
    }

    private String getTaxCode(String firstName, String lastName)
    {
            LocalDate currentDate = LocalDate.now();
            String fnConsonants = firstName.replaceAll("(?i)[\\saeiou]", "");
            String lnConsonants = lastName.replaceAll("(?i)[\\saeiou]", "");
            String yearOfBirth = String.valueOf(currentDate.minusYears(65)).split("-")[0];
            String monthOfBirth = context.getRandomString(1, "LRT");
            String dateOfBirth = String.valueOf(currentDate.getDayOfMonth());
            if(dateOfBirth.length() != 2)
            {
                dateOfBirth = "0" + dateOfBirth;
            }
            String belfioreCode = "F205";
            String position16 = context.getRandomString().substring(0,1);

        return lnConsonants.substring(0,3) + fnConsonants.substring(0,3) + yearOfBirth.substring(2,4)
                + monthOfBirth +dateOfBirth
                +belfioreCode +position16;
    }
}
