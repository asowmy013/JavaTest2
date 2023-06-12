package it.ing.cucumber.stepdefs.rest.engine.utilities;

import com.google.inject.Inject;
import cucumber.api.Scenario;
import cucumber.api.java.After;

import java.io.FileWriter;
import java.io.IOException;

public class CucumberHooks {

    static int passedCount=0;
    static int failedCount=0;
    static int undefinedCount=0;

    private final Context context;

    final static String FILEPATH = "./output/status.txt";

    @Inject
    public CucumberHooks(Context context) {
        this.context = context;
    }

    @After
    public void writeStatusToFile(Scenario scenario) throws IOException {
        if(scenario.isFailed())
        {
            failedCount = failedCount + 1;
        }else if(scenario.getStatus().toString().equalsIgnoreCase("Passed"))
        {
            passedCount = passedCount + 1;
        }else
        {
            undefinedCount = undefinedCount + 1;
        }

        FileWriter fileWriter = new FileWriter(FILEPATH);
        fileWriter.append("Passed: " + passedCount);
        fileWriter.append("  Failed: " + failedCount);
        fileWriter.append("  Undefined: " + undefinedCount);
        fileWriter.close();
    }

}
