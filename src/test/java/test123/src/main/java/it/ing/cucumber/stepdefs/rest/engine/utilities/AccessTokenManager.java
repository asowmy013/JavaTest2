package it.ing.cucumber.stepdefs.rest.engine.utilities;

import com.google.inject.Inject;
import com.ing.cucumber.RunCucumber;
import cucumber.runtime.java.guice.ScenarioScoped;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

@ScenarioScoped
public class AccessTokenManager {

    /**
     *This class will generate the Access token.
     * Access token payload is placed under payloads folder.
     */

    String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);

    private final String PAYLOAD_FILE_NAME = "access_token_payload.json";
    String ENV_SYSTEM_PROPERTY = "environment.target";
    String ACC_ACCESS_TOKEN_URL = "access.token.acc";
    String TEST_ACCESS_TOKEN_URL = "access.token.test";
    String DEFAULT_PROFILE_ID = "default.profile.id";

    private final String environment ;
    private final Context context;
    private final String acceptanceAccessTokenUrl;
    private final String testAccessTokenUrl;
    private final String defaultProfileId;

    @Inject
    public AccessTokenManager(Context context) {

        this.context = context;
        this.environment = RunCucumber.systemProp.getProperty(ENV_SYSTEM_PROPERTY);
        this.acceptanceAccessTokenUrl = RunCucumber.systemProp.getProperty(ACC_ACCESS_TOKEN_URL);
        this.testAccessTokenUrl = RunCucumber.systemProp.getProperty(TEST_ACCESS_TOKEN_URL);
        this.defaultProfileId = RunCucumber.systemProp.getProperty(DEFAULT_PROFILE_ID);
    }

    /**
     *
     * @return valid unencrypted access token which can be used as X-ING-Accesstoken
     * @throws IOException
     */
    public String getAccessToken() throws Exception {

        String requestBody = context.getPayloadData(PAYLOAD_FILE_NAME)
                .replace("$not_before", time)
                .replace("$time_to_live", "600000")
                .replace("$profile",defaultProfileId);

        return  this.getAccessTokenApiResponse(requestBody)
                .getBody()
                .jsonPath()
                .getString("unencrypted_access_token[0]");
    }

    /**
     *
     * @param profile is the profile of Global UUID ; use this if you want to generate token for specific profile.
     * @return Access token
     * @throws IOException
     */
    public String getAccessToken(String profile) throws Exception {

        String requestBody = context.getPayloadData(PAYLOAD_FILE_NAME)
                .replace("$not_before", time)
                .replace("$time_to_live", "600000")
                .replace("$profile",profile);

        return  this.getAccessTokenApiResponse(requestBody)
                .getBody()
                .jsonPath()
                .getString("unencrypted_access_token[0]");
    }

    /**
     *
     * @return expired access token
     * @throws IOException
     * @throws InterruptedException
     */
    public String getExpiredAccessToken() throws Exception {

        String requestBody = context.getPayloadData("access_token_payload.json")
                .replace("$not_before", time)
                .replace("$time_to_live", "5");

        Response response = this.getAccessTokenApiResponse(requestBody);

        Thread.sleep(5000);

        return response.getBody()
                .jsonPath()
                .getString("unencrypted_access_token[0]");
    }

    private String getAccessTokenUrl() throws IOException {

        String env = System.getProperty("env") == null
                ? environment
                : System.getProperty("env");
        if (env.equalsIgnoreCase("acc"))
        {
            return acceptanceAccessTokenUrl;
        }else  if(env.equalsIgnoreCase("tst"))
        {
            return testAccessTokenUrl;
        }
        return null;
    }

    private Response getAccessTokenApiResponse(String requestBody) throws Exception {
        Response response;

        try {

            RequestSpecification request = RestAssured.given().relaxedHTTPSValidation();

            if(environment.equalsIgnoreCase("acc"))
            {
                request = request.proxy("giba-accp-proxy.wps.ing.net",8080);
            }

            response = request.contentType("application/json")
                   .when()
                   .body(requestBody)
                   .post(getAccessTokenUrl())
                   .then()
                   .extract()
                   .response();
            if(response.statusCode()!=200)
            {
                response.then().log().all();
            }
            return response;

        }catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
