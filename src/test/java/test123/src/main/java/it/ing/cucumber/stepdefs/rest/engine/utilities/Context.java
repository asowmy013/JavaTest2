package it.ing.cucumber.stepdefs.rest.engine.utilities;

import cucumber.runtime.java.guice.ScenarioScoped;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.Random;

@ScenarioScoped
public class Context {

    @Getter
    @Setter
    private Response response;

    @Getter
    @Setter
    private String globalUUID;

    @Getter
    @Setter
    private String customerCif;

    @Getter
    @Setter
    private  Data data;

    /**
     *
     * @param uuidType - local or global
     *  before calling this method customer-data-involved-party endpoint should be executed
     * @return customer id (local or global) which is present in the response of customer-data-involved-party endpoint
     */

    public String getCustomerId(String uuidType)
    {
        String customerId;
        try
        {
            customerId = this.getData().getUuid();

        }catch (NullPointerException e)
        {

            System.out.println("====retrieving " + uuidType.toUpperCase() + "UUID from previous endpoint response====");

            this.setCustomerCif(this.getResponse()
                    .body()
                    .jsonPath()
                    .getString("individual.involvedPartyInternalIdentifiers[0].involvedPartyInternalIdentifierValue"));


            this.setGlobalUUID(this.getResponse()
                    .body()
                    .jsonPath()
                    .getString("individual.involvedPartyIdentifier"));

            customerId = uuidType.equalsIgnoreCase("local")
                    ? this.getCustomerCif()
                    : this.getGlobalUUID();
        }

        return customerId;
    }

    public String getPayloadData(String fileName) throws IOException {
        DataLoader dataLoader = new DataLoader();
        return dataLoader.readPayload(fileName);
    };

    public String getResponsePayload(String fileName) throws IOException {
        DataLoader dataLoader = new DataLoader();
        return dataLoader.readResponsePayload(fileName);
    };

    public String generateRandomNumberAsString(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }

    public String getSaltString(int numberOfAlphabet, int numberOfDigits) {
        String SALT_DIGITS = "1234567890";
        String SALT_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        for(int i =0; i < numberOfAlphabet ; i++){
            int index = (int) (rnd.nextFloat() * SALT_ALPHABETS.length());
            salt.append(SALT_ALPHABETS.charAt(index));
        }
        for(int i =0; i < numberOfDigits ; i++){
            int index = (int) (rnd.nextFloat() * SALT_DIGITS.length());
            salt.append(SALT_DIGITS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public String getRandomNumbers() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public String getRandomString()
    {
        return RandomStringUtils.randomAlphabetic(10).toUpperCase();
    }

    public String getRandomString(int requiredStrings, String inputString)
    {
        return  RandomStringUtils.random(requiredStrings, inputString);
    }
}
