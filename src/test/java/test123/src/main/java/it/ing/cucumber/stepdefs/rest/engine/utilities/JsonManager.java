package it.ing.cucumber.stepdefs.rest.engine.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cucumber.runtime.java.guice.ScenarioScoped;

import java.io.IOException;

@ScenarioScoped
public class JsonManager {

    /**
     * JsonManager class is a helper class which can be used to remove/add the json object.
     */

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param jsonPayload
     * @param jsonArrayName this a name of array list in the json object
     * @param nodeToRemove  this json element will be removed
     * @return
     */

    public String removeElementFromJsonArray(String jsonPayload, String jsonArrayName, String nodeToRemove)
            throws IOException {

        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        JsonNode arrayNode = rootNode.get(jsonArrayName);
        for (JsonNode node : arrayNode) {((ObjectNode) node).remove(nodeToRemove);}
        return rootNode.toString();
    }

    /**
     * @param jsonPayload
     * @param nodeToRemove - this is element from an unnamed json array.
     * @return
     */
    public String removeElementFromJsonArray(String jsonPayload, String nodeToRemove) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        for (JsonNode node : rootNode) {((ObjectNode) node).remove(nodeToRemove);}
        return rootNode.toString();
    }

    /**
     * @param jsonPayload
     * @param nodeToRemove this is a non-array json element which need to be removed.
     * @return
     */
    public String removeElementFromJsonObject(String jsonPayload, String nodeToRemove) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        ((ObjectNode) rootNode).remove(nodeToRemove);
        return rootNode.toString();
    }
    public String removeElementFromJsonElement(String jsonPayload, String nodeToRemove) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        JsonNode path = rootNode.findParent(nodeToRemove);
        ((ObjectNode) path).remove(nodeToRemove);
        return rootNode.toString();
    }

    /**
     *
     * @param jsonPayload
     * @param jsonArrayName
     * @param nodeToModify adding/modifying new or existing Json Node to Json Array based on the array Name
     * @param valueToUpdate this value will get updated
     * @return
     * @throws IOException
     */
    public String addOrModifyJsonArray(String jsonPayload, String jsonArrayName,
                                       String nodeToModify, String valueToUpdate) throws IOException {

        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        JsonNode arrayNode = rootNode.get(jsonArrayName);
        for (JsonNode node : arrayNode) {((ObjectNode) node).put(nodeToModify, valueToUpdate);}
        return rootNode.toString();
    }

    /**
     *
     * @param jsonPayload
     * @param nodeToModify  add or modify the new/existing json node to unnamed JSON array
     * @param valueToUpdate this is value to update to existing/new json node
     * @return
     * @throws IOException
     */
    public String addOrModifyJsonArray(String jsonPayload, String nodeToModify,
                                        String valueToUpdate) throws IOException {

        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        for (JsonNode node : rootNode) {((ObjectNode) node).put(nodeToModify, valueToUpdate);}
        return rootNode.toString();
    }

    /**
     *
     * @param jsonPayload
     * @param nodeToModify add or modify the new/existing json node to root JSON (non -array JSON)
     * @param valueToUpdate this is value to update to existing/new json node
     * @return
     * @throws IOException
     */

    public String addOrModifyJsonObject(String jsonPayload, String nodeToModify,
                                       String valueToUpdate) throws IOException {

        JsonNode rootNode = objectMapper.readTree(jsonPayload);
        ((ObjectNode) rootNode).put(nodeToModify, valueToUpdate);
        return rootNode.toString();
    }
}
