# Author	: Vishwanath
# Date		: 16/09/2022

Feature: POST /v1/customer-data/involved-party/{UUID}/identification
  Save Identification Info

  Background:
    Given the "ITAPartyAgreementsAPI" component


  Scenario Outline: Validate save identification information endpoint with different identificationMethod
    When submitting request to customer data involved party api with valid data
    Then endpoint response status is 201
    When request submitted to save identification endpoint with "<identificationMethod>" and "<identificationFlag>" flag
    Then endpoint response status is <status>
    Examples:
      | identificationMethod | identificationFlag | status |
          #valid data
      | 0                    | S                  | 201    |
      | 3                    | N                  | 201    |
#         #invalid identificationFlag
      | 3                    | Y                  | 400    |
#          #invalid identificationMethod
      | 6                    | S                  | 400    |


  Scenario Outline: Validate save identification information endpoint with invalid UUID
    Given a customer "USER_WITH_INVALID_GLOBAL_UUID"
    When request submitted to save identification endpoint with "<identificationMethod>" and "<identificationFlag>" flag
    Then endpoint response status is 404
    Examples:
      | identificationMethod | identificationFlag |
      | 0                    | S                  |

  Scenario Outline: Validate save identification information endpoint with different docNumberType
    When submitting request to customer data involved party api with valid data
    Then endpoint response status is 201
    When request submitted to save identification endpoint with different "<docNumberType>"
    Then endpoint response status is <status>
    And response body has "INVALID_DOC_NUMBER_TYPE " value for "error.code" attribute
    Examples:
      | docNumberType | status |
      | 4             | 400    |

  Scenario: Validate save identification information endpoint without mandatory requests
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to save identification endpoint without mandatory requests
    Then endpoint response status is 400

  Scenario: Validate save identification info endpoint when future date for identification date is passed
      Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to save identification endpoint with future date in identification date
    Then endpoint response status is 400
