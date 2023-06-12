package it.ing.cucumber.stepdefs.rest.engine.utilities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DataWrapper class for hold list of data entity
 */

@Getter
@Setter
public class DataWrapper {

    private List<Data> dataList;

}
