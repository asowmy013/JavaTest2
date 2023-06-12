package it.ing.cucumber.stepdefs.rest.engine.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.ing.cucumber.RunCucumber;
import com.ing.environment.ContextConfig;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DataLoader class is used to read the data from dataList.json file
 */

public class DataLoader {

    private Context context;
    private String dataFolder;
    private ObjectMapper mapper;

    @Getter
    private static Map<String, Data> dataTest;

    public void loadData() {

        try {

            String dataPathFile = ContextConfig.getEnvironmentProperty() + "dataList.json";

            DataWrapper wrapper = new ObjectMapper().readValue(new File(dataPathFile), DataWrapper.class);

            if (wrapper.getDataList().isEmpty() || wrapper.getDataList().stream().anyMatch(c -> c.getId().isEmpty())) {
                throw new RuntimeException(MessageFormat.format("Data file specified {0} does not contain correct data. You must include valid data to test", "dataList.json"));
            }
            dataTest = wrapper.getDataList().stream()
                    .collect(Collectors.toMap(Data::getId, c -> c));

        } catch (IllegalArgumentException | MismatchedInputException e) {
            throw new RuntimeException("Fail trying to parse customer file", e);
        } catch (IOException e) {
            throw new RuntimeException("Fail trying to retrieve customer from property file", e);
        }
    }

    /**
     *
     * @param customerKey is the "id" attribute in the dataList.json file
     * @return Data object
     * @throws Exception when there is no key or any other Exception while loading json data
     */

    public Data getCustomerData(String customerKey) throws Exception {
        try{
            this.loadData();
            if (this.getDataTest().get(customerKey) != null) {
                return this.getDataTest().get(customerKey);
            } else {
                System.out.println("Key not found :  " + customerKey);
                throw new Exception();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String readPayload(String fileName) throws IOException {
        try {

            String filepath = ContextConfig.getEnvironmentProperty() + "/payloads/" + fileName;
            return  new String(Files.readAllBytes(Paths.get(filepath)));
        }catch (IOException exception)
        {
            System.out.println("File path or file not found, please check the path again");
            throw new IOException();
        }
    }

    public String readResponsePayload(String fileName) throws IOException {
        try {

            String filepath = ContextConfig.getEnvironmentProperty() + "/response-payload/" + fileName;
            return  new String(Files.readAllBytes(Paths.get(filepath)));
        }catch (IOException exception)
        {
            System.out.println("File path or file not found, please check the path again");
            throw new IOException();
        }
    }
}
