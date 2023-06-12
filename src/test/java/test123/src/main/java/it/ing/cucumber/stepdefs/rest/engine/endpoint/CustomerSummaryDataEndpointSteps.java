package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;

import java.text.MessageFormat;

public class CustomerSummaryDataEndpointSteps extends InternalSteps implements En {

    private Context context;

    private final String CUSTOMER_DATA_PERSONAL = "/v1/customer-data/{0}/personal";
    private final String CUSTOMER_DATA_ADDRESSES = "/v1/customer-data/{0}/addresses";
    private final String CUSTOMER_DATA_DIGITAL_ADDRESSES = "/v1/customer-data/{0}/digital-addresses";

    @Inject
    public CustomerSummaryDataEndpointSteps(Context context) {
        this.context = context;

        When("^request submitted to get customer personal data endpoint with \"([^\"]*)\" UUID$",
                (String uuidType) -> {

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .queryParam("UUIDType" ,uuidType).log().all()
                            .when()
                            .get(MessageFormat.format(CUSTOMER_DATA_PERSONAL, context.getData().getUuid()))
                            .then().log().all().extract().response();

                    context.setResponse(response);
        });
        When("^request submitted to get customer digital addresses endpoint with \"([^\"]*)\" UUID$",
                (String uuidType) -> {

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .queryParam("UUIDType" ,uuidType).log().all()
                            .when()
                            .get(MessageFormat.format(CUSTOMER_DATA_DIGITAL_ADDRESSES, context.getData().getUuid()))
                            .then().log().all().extract().response();

                    context.setResponse(response);
        });

        When("^request submitted to get customer addresses endpoint with \"([^\"]*)\" UUID$",
                (String uuidType) -> {

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .queryParam("UUIDType" ,uuidType).log().all()
                            .when()
                            .get(MessageFormat.format(CUSTOMER_DATA_ADDRESSES, context.getData().getUuid()))
                            .then().log().all().extract().response();

                    context.setResponse(response);
        });
    }
}
