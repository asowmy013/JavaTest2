package it.ing.cucumber.stepdefs.rest.engine.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

/**
 * Data class POJO class for entities for dataListJson fields
 */

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Data {
    private String id;
    private String productNumber;
    private String role;
    private String state;
    private String type;
    private String uuid;
    private String uuidtype;
    private String taxCode;
    private String firstname;
    private String lastname;
    private String lastUpdatedUser;
    private String localUuid;
    private String cif;
    private String provinceCode;
    private String nationalityCode;
    private String streetTypeCode;
    private String addressTypeCode;
    private String normalizedCode;
}