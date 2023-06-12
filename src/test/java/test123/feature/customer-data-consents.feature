# Author	: Vishwanath Shetty
# Date		: 08/04/2022

@consents-endpoint
Feature: PATCH /v1/customer-data/{uuid}/consents
  Update Customer Consents

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Validate Update Customer Consents endpoint with valid local uuid
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When verify that consents info endpoint for "local" uuid with flag "true"
    Then endpoint response status is 204

  Scenario: Validate Update Customer Consents endpoint with valid local uuid and false flag
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When verify that consents info endpoint for "local" uuid with flag "false"
    Then endpoint response status is 204

  Scenario: Validate Update Customer Consents endpoint with valid global uuid
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When verify that consents info endpoint for "global" uuid with flag "true"
    Then endpoint response status is 204

  Scenario: Validate Update Customer Consents endpoint with valid global uuid and false flag
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When verify that consents info endpoint for "global" uuid with flag "false"
    Then endpoint response status is 204

  Scenario: Validate Update Customer Consents endpoint with invalid flag
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When verify that consents info endpoint for "global" uuid with flag "yes"
    Then endpoint response status is 400
    And response body has "INVALID_PARAMETERS" value for "error.message" attribute

  Scenario: Validate Update Customer Consents endpoint with incorrect local uuid
    Given a customer "USER_WITH_7DIGIT_LOCAL_UUID"
    When verify that consents info endpoint for "local" uuid with flag "true"
    Then endpoint response status is 400
    And response body has "INVALID_PARAMETERS" value for "error.message" attribute

  Scenario: Validate Update Customer Consents endpoint with invalid local uuid
    Given a customer "USER_WITH_INVALID_LOCAL_UUID"
    When verify that consents info endpoint for "local" uuid with flag "true"
    Then endpoint response status is 404

  Scenario: Validate Update Customer Consents endpoint with invalid global uuid
    Given a customer "USER_WITH_INVALID_GLOBAL_UUID"
    When verify that consents info endpoint for "global" uuid with flag "true"
    Then endpoint response status is 404
    And response body has "Involved Party not found" value for "error.message" attribute

