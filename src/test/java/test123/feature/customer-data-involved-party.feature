# Author	: SN95LA-Vishwanath Shetty
# Date		: 16/05/2022

@user-profile-management @create-new-involved-party  @1365920
Feature:  POST /v1/customer-data/involved-party
  Create New Customer Involved Party

  Background:
    Given the "ITAPartyAgreementsAPI" component

  @test12
  Scenario: validate the response when customer not exists in the system -prospect
    Given a customer "TAX_NUMBER_PROSPECT"
    When submitting request to customer data involved party api with valid data
    Then endpoint response status is 201
    And response has following data
      |individual.dateOfBirth|individual.gender|involvedPartyExternalIdentifiers.dataSource|

  Scenario: validate the response without optional request  - prospect
    Given a customer "TAX_NUMBER_PROSPECT"
    When submitting request by removing optional fields from request payload
      |dateOfBirth|gender|maritalStatus|cityOfBirth|countryOfResidence|countryOfBirth|
    Then endpoint response status is 201

  Scenario Outline: validate the response of api when external identifier is not sent in request
    Given a customer "TAX_NUMBER_PROSPECT"
    When submitting request by removing "<mandatoryElement>"
    Then endpoint response status is 400
    Examples:
    |mandatoryElement|
    |involvedPartyExternalIdentifierType |
    |involvedPartyExternalIdentifierValue|
    |givenName                           |
    |lastName                            |


  Scenario: validate the response of api when external identifier type is not CD_FISCALE_IT
    Given a customer "TAX_NUMBER_PROSPECT"
    When submitting request to involved party api with invalid external identifier "CD_FISCAL_FR"
    Then endpoint response status is 400

  @test3
  Scenario: validate the response of api when customer is already exists in the system
    Given a customer "TAX_NUMBER_EXISTING_CUSTOMER"
    When submitting request to customer data involved party api with existing customer tax number
    Then endpoint response status is 500
    And response body has "ALREADY_CUSTOMER" value for "error.code" attribute
    And response body has "Tax number is related for an already customer" value for "error.message" attribute

