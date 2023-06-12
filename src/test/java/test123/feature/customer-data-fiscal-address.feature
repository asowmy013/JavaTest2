# Author	: Vishwanath
# Date		: 14/12/2022

@update-fiscal-address
Feature: POST /v1/customer-data/{UUID}/fiscal-addresses
  Save Fiscal Address Info

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Validate post fiscal address endpoint with global uuid
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When request submitted to post fiscal addresses endpoint with "global" UUID
    Then endpoint response status is 204

  Scenario: Validate post fiscal address endpoint with local uuid
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When request submitted to post fiscal addresses endpoint with "local" UUID
    Then endpoint response status is 204

  Scenario: Validate post fiscal address endpoint with invalid global uuid
    Given a customer "USER_WITH_INVALID_GLOBAL_UUID"
    When request submitted to post fiscal addresses endpoint with "global" UUID
    Then endpoint response status is 404

  Scenario: Validate post fiscal address endpoint with invalid local uuid
    Given a customer "USER_WITH_INVALID_LOCAL_UUID"
    When request submitted to post fiscal addresses endpoint with "local" UUID
    Then endpoint response status is 404

  Scenario: Validate post fiscal address endpoint with 7 digit local uuid
    Given a customer "USER_WITH_7DIGIT_LOCAL_UUID"
    When request submitted to post fiscal addresses endpoint with "local" UUID
    Then endpoint response status is 400

  Scenario: Validate post fiscal address endpoint without country name in request
    Given a customer "USER_WITH_7DIGIT_LOCAL_UUID"
    When request submitted to post fiscal addresses endpoint without "countryName"
    Then endpoint response status is 400

  Scenario: Validate post fiscal address endpoint without tin in request
    Given a customer "USER_WITH_FISCAL_ADDRESS"
    When request submitted to post fiscal addresses endpoint without "tin"
    Then endpoint response status is 204