package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;
import it.ing.cucumber.stepdefs.rest.engine.utilities.JsonManager;
import org.hamcrest.Matchers;
import org.junit.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.File;
import java.text.MessageFormat;

public class CustomerPhysicalAddress extends InternalSteps implements En {

    private final String PHYSICAL_ADDRESS_ENDPOINT = "/v1/customer-data/involved-party/{0}/physical-address";
    private final Context context;
    private final JsonManager jsonManager;
    private String customerUuid ;
    private String additionalDescription;

    @Inject
    public CustomerPhysicalAddress(Context context , JsonManager jsonManager) {
        this.context = context;
        this.jsonManager = jsonManager;

        When("^request submitted to post customer physical address for \"([^\"]*)\" uuid with different \"([^\"]*)\" and \"([^\"]*)\"$",
                (String uuidType, String addressTypeCode , String normalizedCode) -> {

                    customerUuid = context.getCustomerId(uuidType);
                    additionalDescription = context.getRandomString();
                    String requestBody = context.getPayloadData("physical_address_payload.json")
                            .replace("$addressTypeCode",addressTypeCode)
                            .replace("$normalizedCode", normalizedCode)
                            .replace("$additionalDescription",additionalDescription);

                    this.executeRequest(uuidType,requestBody,customerUuid);
                });

        When("^request submitted customer physical address endpoint by removing \"([^\"]*)\" and  \"([^\"]*)\"$",
                (String mandatoryField, String uuidType) -> {

            String requestBody = context.getPayloadData("physical_address_payload.json")
                    .replace("$addressTypeCode",context.getData().getAddressTypeCode())
                    .replace("$normalizedCode", context.getData().getNormalizedCode())
                    .replace("$additionalDescription",context.getRandomString());

            requestBody = jsonManager.removeElementFromJsonElement(requestBody,mandatoryField);

            this.executeRequest(uuidType,requestBody,context.getCustomerId(uuidType));

        });

        When("^request submitted to validate \"([^\"]*)\" with digits \"([^\"]*)\" and \"([^\"]*)\"$",
                (String jsonNode, Integer fieldLength, String uuidType) -> {

                    String requestBody = context.getPayloadData("physical_address_payload.json")
                            .replace("$addressTypeCode",context.getData().getAddressTypeCode())
                            .replace("$normalizedCode", context.getData().getNormalizedCode())
                            .replace("$additionalDescription",context.getRandomString());

                    requestBody = jsonManager.removeElementFromJsonObject(requestBody,jsonNode);
                    requestBody = jsonManager.addOrModifyJsonObject(requestBody,jsonNode, context.generateRandomNumberAsString(fieldLength));

                    this.executeRequest(uuidType,requestBody,context.getCustomerId(uuidType));
        });

        And("^physical address endpoint returned valid response for \"([^\"]*)\" and \"([^\"]*)\"$",
                (String addressTypeCode, String normalizeCode) -> {

            ObjectMapper objectMapper = new ObjectMapper();

                    String expectedResponseBody = context.getResponsePayload("physical_address_response.json")
                            .replace("$addressTypeCode",addressTypeCode)
                            .replace("$normalizedCode", normalizeCode)
                            .replace("$uuid",customerUuid)
                            .replace("$additionalDescription",additionalDescription);

                    JsonNode expected = objectMapper.readTree(expectedResponseBody);
                    JsonNode actual = objectMapper.readTree(context.getResponse().body().asString());
                    Assert.assertEquals(expected,actual);
        });
    }

    private void executeRequest(String uuidType, String requestBody, String customerId)
    {
        Response response = SecurityHttp.givenSecure()
                .baseUri(getArtifact().getURI())
                .port(getArtifact().getPort())
                .contentType("application/json")
                .log().all()
                .queryParam("UUIDType",uuidType)
                .when()
                .body(requestBody)
                .post(MessageFormat.format(PHYSICAL_ADDRESS_ENDPOINT,customerId))
                .then().log().all()
                .extract()
                .response();

        context.setResponse(response);
    }


}
