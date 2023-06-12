package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;
import it.ing.cucumber.stepdefs.rest.engine.utilities.JsonManager;

public class UpdateFiscalAddress extends InternalSteps implements En {

    private final Context context;
    private final JsonManager jsonManager;
    private final String FISCAL_ADDRESS_ENDPOINT = "/v1/customer-data/%s/fiscal-addresses";

    private String json = "{\n" +
            "  \"fiscalAddresses\": [\n" +
            "    {\n" +
            "      \"countryName\": \"ITALIA\",\n" +
            "      \"tin\": \"1231452\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Inject
    public UpdateFiscalAddress(Context context, JsonManager jsonManager) {
        this.context = context;
        this.jsonManager = jsonManager;

        When("^request submitted to post fiscal addresses endpoint with \"([^\"]*)\" UUID$", (String uuidType) -> {
            this.submitRequest(json,uuidType);
        });

        When("^request submitted to post fiscal addresses endpoint without \"([^\"]*)\"$", (String jsonNode) -> {
            String requestBody = jsonManager.removeElementFromJsonArray(json,"fiscalAddresses",jsonNode);
            this.submitRequest(requestBody,"local");
        });


    }
    private void submitRequest(String requestBody, String uuidType)
    {
        String uuid = uuidType.equalsIgnoreCase("local") ? context.getData().getLocalUuid() : context.getData().getUuid();

        Response response = SecurityHttp.givenSecure()
                .baseUri(getArtifact().getURI())
                .port(getArtifact().getPort()).urlEncodingEnabled(false)
                .contentType(ContentType.JSON)
                .queryParam("type",uuidType)
                .when()
                .body(requestBody).log().all()
                .post(String.format(FISCAL_ADDRESS_ENDPOINT,uuid))
                .then().log().all().extract().response();

        this.context.setResponse(response);}

}
