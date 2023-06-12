# Author	: Vishwanath Shetty
# Date		: 08/04/2022

@customer-scoring-endpoints
Feature: GET /v1/customer-data/{uuid}/products/loans/scoring
  customer-scoring-endpoints

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Test customer scoring endpoint for valid uuid and uuidtype
    Given a customer "USER_WITH_VALID_LOCAL_UUID"
    When verify that customer scoring info endpoint for "USER_WITH_VALID_UUID_UUIDTYPE"
    Then endpoint response status is 200


  Scenario: Test customer scoring endpoint for invalid uuid and uuidtype
    Given a customer "USER_WITH_INVALID_UUID_TYPE"
    When verify that customer scoring info endpoint for "USER_WITH_UUID_AND_UUIDTYPE_INVALID"
    Then endpoint response status is 400

   # Not yet developed for Cross component for Resource not found scenario
   #Scenario: Test customer scoring endpoint for uuid and uuidtype not found
   #Given a customer "USER_WITH_UUID_UUIDTYPE_NOT_FOUND"
   #When verify that customer scoring info endpoint for "USER_WITH_UUID_UUIDTYPE_NOT_FOUND"
   # Then endpoint responds status is 404











