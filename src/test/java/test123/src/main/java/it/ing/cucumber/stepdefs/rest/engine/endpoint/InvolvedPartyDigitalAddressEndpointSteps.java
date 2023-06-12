package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.RunCucumber;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class InvolvedPartyDigitalAddressEndpointSteps extends InternalSteps implements En {

    private final Context context;
    private final String INVOLVED_PARTY_DIGITAL_ADDRESS = "/v1/customer-data/involved-party/{0}/digital-address";
    private final String peerToken;

    String PEER_TOKEN_PROPERTY = "peer.token";

    @Inject
    public InvolvedPartyDigitalAddressEndpointSteps(Context context) {
        this.context = context;
        this.peerToken = RunCucumber.systemProp.getProperty(PEER_TOKEN_PROPERTY);

        Map<String, String> headers = new HashMap<>();

        headers.put("X-ING-LastUpdateUser", "SN95LA");
        headers.put("X_ING_PEERTOKEN", peerToken);

        When("^submit a request to involved party digital address api with  \"([^\"]*)\"$",
                (String digitalAddressUsageType) -> {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date = new Date();
                    String currentDate = sdf.format(date);

                    String payload = context.getPayloadData("involvedPartyDigitalAddress.json")
                            .replace("$digitalAddressUsageType",digitalAddressUsageType)
                            .replace("$effectiveDate",currentDate);

                    String uuid = context.getResponse()
                                    .body()
                                    .jsonPath()
                                    .getString("individual.involvedPartyIdentifier");

                    payload = payload.replace("$fullDigitalAddress", digitalAddressUsageType.equalsIgnoreCase("EMAIL")
                            ? "auto.test.ping@ing.com" : "3391232201");

                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort())
                            .contentType("application/json")
                            .headers(headers)
                            .log().all()
                            .when()
                            .body(payload)
                            .post(MessageFormat.format(INVOLVED_PARTY_DIGITAL_ADDRESS,uuid))
                            .then().log().all()
                            .extract()
                            .response();

                    this.context.setResponse(response);
        });

        When("^submit a request to involved party digital address api invalid global UUID$", () -> {

            String payload = context.getPayloadData("involvedPartyDigitalAddress.json")
                    .replace("$digitalAddressUsageType","EMAIL")
                    .replace("$fullDigitalAddress", "auto.test.ping@ing.com");

            String uuid = "88ae297c-554c-47fa-a453-17d462fd8061";

            Response response = SecurityHttp.givenSecure()
                    .baseUri(getArtifact().getURI())
                    .port(getArtifact().getPort())
                    .contentType("application/json")
                    .headers(headers)
                    .log().all()
                    .when()
                    .body(payload)
                    .post(MessageFormat.format(INVOLVED_PARTY_DIGITAL_ADDRESS,uuid))
                    .then().log().all()
                    .extract()
                    .response();

            this.context.setResponse(response);
        });
    }
}
