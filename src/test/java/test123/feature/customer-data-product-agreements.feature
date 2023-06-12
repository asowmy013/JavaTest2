# Author	: Sumathi
# Date		: 08/04/2022

@retrieve-product-agreements-endpoints
Feature: GET /v1/customer-data/{0}/product-agreements
  Retrieve Product agreements information

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Test product agreements endpoint for valid uuid and uuidtype
    Given a customer "USER_WITH_VALID_UUID_AND_VALID_UUIDTYPE"
    When verify that product agreement info endpoint for "USER_WITH_VALID_UUID_AND_VALID_UUIDTYPE"
    Then endpoint response status is 200

   Scenario: Test product agreements endpoint for invalid uuid and uuidtype
    Given a customer "USER_WITH_INVALID_UUID_TYPE"
    When verify that product agreement info endpoint for "USER_WITH_INVALID_UUID_TYPE"
    Then endpoint response status is 400

     # Not yet developed for Cross component for Resource not found scenario
   #Scenario: Test product agreements endpoint for uuid and uuidtype not found
   #Given a customer "USER_WITH_UUID_AND_UUIDTYPE_NOT_FOUND"
   #When verify that product agreement info endpoint for "USER_WITH_UUID_AND_UUIDTYPE_NOT_FOUND"
   #Then endpoint responds status is 404










