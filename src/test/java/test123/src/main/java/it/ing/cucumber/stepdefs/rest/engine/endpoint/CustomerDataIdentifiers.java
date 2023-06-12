package it.ing.cucumber.stepdefs.rest.engine.endpoint;

import com.google.inject.Inject;
import com.ing.cucumber.stepdefs.rest.InternalSteps;
import com.ing.util.security.SecurityHttp;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.ing.cucumber.stepdefs.rest.engine.utilities.Context;
import org.hamcrest.Matchers;

public class CustomerDataIdentifiers extends InternalSteps implements En {

    /**
     * Customer data internal identifiers Endpoint Step Definition
     */

    private final String CUSTOMER_DATA_INTERNAL_IDENTIFIERS = "/v1/customer-data/identifiers";
    private final Context context;

    @Inject
    public CustomerDataIdentifiers(Context context) {
        this.context = context;

        When("^request submitted to get customer identifiers for \"([^\"]*)\" uuid$",
                (String uuidType) -> {

                    String id = !uuidType.equalsIgnoreCase("local") ? context.getData().getUuid()
                            : context.getData().getCif();
                    Response response = SecurityHttp.givenSecure()
                            .baseUri(getArtifact().getURI())
                            .port(getArtifact().getPort()).urlEncodingEnabled(false)
                            .contentType(ContentType.JSON)
                            .queryParam("id" ,id)
                            .queryParam("type" ,uuidType)
                            .when().log().all()
                            .get(CUSTOMER_DATA_INTERNAL_IDENTIFIERS)
                            .then().log().all().extract().response();

                    context.setResponse(response);
        });

        And("^response body has valid \"([^\"]*)\" of customer$", (String personalIdentifiers) -> {

            String uuid = personalIdentifiers  + "." + "uuid" ;
            String cif = personalIdentifiers  + "." + "cif" ;

            context.getResponse().then().assertThat().body(cif, Matchers.equalTo(context.getData().getCif()));
            context.getResponse().then().assertThat().body(uuid, Matchers.equalTo(context.getData().getUuid()));
        });
    }
}
