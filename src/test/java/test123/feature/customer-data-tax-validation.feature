# Author	: SN95LA
# Date		: 16/05/2022

@user-profile-management @tax-validation   @1161494 #@1365920
Feature: POST /v1/customer-data/taxCode/validation
  Tax number validation

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Validate tax code with valid data
    Given a customer "USER_WITH_VALID_TAX_CODE"
    When submitting tax code validation request
    Then endpoint response status is 200
     And response has following data
       |result|firstname|lastname|taxCode|gender|birthDate|birthPlace|birthPlaceProvince|
     And result is "Valid"

  Scenario Outline: Validate tax code with invalid data
    Given a customer "<userId>"
    When submitting tax code validation request
    Then endpoint response status is 200
    And result is "Not Valid"
    Examples:
    |userId|
    |USER_WITH_INVALID_FIRST_NAME_LAST_NAME|

  Scenario:Validate tax code with incorrect tax code (more than 16 digit)
    Given a customer "USER_WITH_IN_CORRECT_TAX_CODE"
    When submitting tax code validation request
    Then endpoint response status is 400
    And response body has "TaxNumber range is 16" value for "error.innerErrors[0].message" attribute
