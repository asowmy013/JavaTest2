# Author	: Sumathi
# Date		: 08/04/2022

@product-loan-endpoints
Feature: GET /v1/customer-data/{0}/products/loans/leads
  Get Products loans leads

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Test loan data ok endpoint for uuid and uuidtype
    Given a customer "USER_WITH_VALID_UUID_UUIDTYPE"
    When verify that loan account info endpoint for the user with local uuid
    Then endpoint response status is 200


  Scenario: Test loan data endpoint for invalid uuid and uuidtype
    Given a customer "USER_WITH_INVALID_UUID_TYPE"
    When verify that loan account info endpoint for the user with local uuid
    Then endpoint response status is 400











