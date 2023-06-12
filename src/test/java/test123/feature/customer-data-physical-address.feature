# Author	: Vishwanath
# Date		: 24/08/2022

 @1678918  @customer-physical-address
Feature:  POST /v1/customer-data/involved-party/{UUID}/physical-address
  Customer Physical Address

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario Outline: TC_CPA_001_Validate the physical address endpoint  for new customers
    When submitting request to customer data involved party api with valid data
    Then endpoint response status is 201
    When request submitted to post customer physical address for "<uuidType>" uuid with different "<addressTypeCode>" and "<normalizedCode>"
    Then endpoint response status is <status>
    And  physical address endpoint returned valid response for "<addressTypeCode>" and "<normalizedCode>"

    Examples:
      | uuidType  | status | addressTypeCode | normalizedCode |
      | global    | 201    | 1               | FN             |
      | global    | 201    | 2               | NR             |

  Scenario Outline: TC_CPA_002_Validate the physical address endpoint  for existing customers
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When request submitted to post customer physical address for "<uuidType>" uuid with different "<addressTypeCode>" and "<normalizedCode>"
    Then endpoint response status is <status>
    And  physical address endpoint returned valid response for "<addressTypeCode>" and "<normalizedCode>"

    Examples:
      | uuidType  | status | addressTypeCode | normalizedCode |
      | global    | 201    | 1               | FN             |
      | global    | 201    | 2               | NR             |

  Scenario: TC_CPA_003_Validate the physical address endpoint with invalid addressTypeCode
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to post customer physical address for "global" uuid with different "3" and "NC"
    Then endpoint response status is 400
   # And response body has "INVALID_PARAMETERS" value for "error.message" attribute

  Scenario: TC_CPA_002_Validate the physical address endpoint with invalid normalizedCode
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to post customer physical address for "global" uuid with different "1" and "FX"
    Then endpoint response status is 400
    And response body has "INVALID_PARAMETERS" value for "error.message" attribute

  Scenario Outline: TC_CPA_002_Validate the physical address endpoint with invalid customer uuid
    Given a customer "<customer>"
    When request submitted to post customer physical address for "<uuidType>" uuid with different "<addressTypeCode>" and "<normalizedCode>"
    Then endpoint response status is <status>
    And response body has "<errorMessage>" value for "error.message" attribute

    Examples:
      | customer                       | uuidType | status | addressTypeCode | normalizedCode | errorMessage             |
      | USER_WITH_INVALID_GLOBAL_UUID  | global   | 404    | 2               | FN             | Involved Party not found |


  Scenario Outline: TC_CPA_003_Validate the physical address endpoint with invalid uuidType
    Given a customer "<customer>"
    When request submitted to post customer physical address for "<uuidType>" uuid with different "<addressTypeCode>" and "<normalizedCode>"
    Then endpoint response status is <status>

    Examples:
      | customer                   | uuidType     | status | addressTypeCode |
      | USER_WITH_VALID_LOCAL_UUID | local | 400    | 1               |

  Scenario Outline: TC_CPA_04_Validate the physical address endpoint with with out mandatory fields
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted customer physical address endpoint by removing "<mandatoryField>" and  "<uuidType>"
    Then endpoint response status is 400
    And response body has "INVALID_PARAMETERS" value for "error.message" attribute

    Examples:
      | mandatoryField  | uuidType |
      | streetTypeCode  | global   |
      | nationalityCode | global   |

  Scenario Outline: Field streetTypeCode validation in  customer data physical address api
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to validate "streetTypeCode" with digits "<fieldLength>" and "<uuidType>"
    Then endpoint response status is 400

    Examples:
      | fieldLength | uuidType |
      | 9           | global   |


  Scenario Outline: Field nationalityCode validation in  customer data physical address api
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to validate "nationalityCode" with digits "<fieldLength>" and "<uuidType>"
    Then endpoint response status is 400

    Examples:
      | fieldLength | uuidType |
      | 9           | global   |


  Scenario Outline: Field provinceCode validation in  customer data physical address api
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to validate "provinceCode" with digits "<fieldLength>" and "<uuidType>"
    Then endpoint response status is 400

    Examples:
      | fieldLength | uuidType |
      | 9           | global   |