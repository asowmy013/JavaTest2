# Author	: Vishwanath
# Date		: 19/07/2022

@upm-customer-data-identifiers @1538612
Feature:GET /v1/customer-data/identifiers
  Customer Data Identifiers

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario Outline: TC_CI_001_Validate the customer identifiers details when user not in local db
      Given a customer "USER_NOT_IN_LOCAL_DB"
      When request submitted to get customer identifiers for "<uuidType>" uuid
      Then endpoint response status is <status>
      And response body has valid "personalIdentifiers" of customer

      Examples:
        | uuidType | status |
        | local    |   200  |
        | global   |   200  |

  Scenario Outline: TC_CI_002_Validate the customer identifiers details when user exists in local db
    Given a customer "USER_EXIST_IN_LOCAL_DB"
    When request submitted to get customer identifiers for "<uuidType>" uuid
    Then endpoint response status is <status>
    And response body has valid "personalIdentifiers" of customer

    Examples:
      | uuidType | status |
      | local    |   200  |
      | global   |   200  |

  Scenario: TC_CI_003_Validate the customer identifiers details for invalid uuidType
    Given a customer "USER_EXIST_IN_LOCAL_DB"
    When request submitted to get customer identifiers for "LOCAL_GLOBAL" uuid
    Then endpoint response status is 400
    And response body has "type can be in [local, global]" value for "error.innerErrors[0].message" attribute

  Scenario: TC_CI_004_Validate the customer identifiers details for local id not exists in global DB
    Given a customer "USER_NOT_IN_GLOBAL_DB"
    When request submitted to get customer identifiers for "global" uuid
    Then endpoint response status is 400
    And response body has "LOCAL_UUID_NOT_FOUND" value for "error.code" attribute


  Scenario Outline: TC_CI_005_Validate the customer identifiers details for invalid users
    Given a customer "<userData>"
    When request submitted to get customer identifiers for "<uuidType>" uuid
    Then endpoint response status is <status>

    Examples:
      | uuidType | status |userData|
      | local    |   404  |USER_WITH_INVALID_LOCAL_UUID|
      | global   |   404  |USER_WITH_INVALID_GLOBAL_UUID|