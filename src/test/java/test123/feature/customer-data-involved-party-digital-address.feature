# Author	: Vishwanath
# Date		: 23/05/2022

@upm-involved-party-digital-address-api @1161764 @1161770
Feature: POST /v1/customer-data/involved-party/{UUID}/digital-address
  Involved party digital address

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario Outline: Validate involved-party digital address when digitalAddressType is valid
    Given a customer "TAX_NUMBER_PROSPECT"
    When submitting request to customer data involved party api with valid data
    Then endpoint response status is 201
    When submit a request to involved party digital address api with  "<digitalAddressType>"
    Then endpoint response status is 201

    Examples:
      |digitalAddressType|
      |EMAIL             |
      |MOBILE            |
      |FAX               |
      |OFFICE            |
      |HOME              |


  Scenario: Validate involved-party digital address api when digitalAddressType is invalid
    Given a customer "TAX_NUMBER_PROSPECT"
    When submitting request to customer data involved party api with valid data
    Then endpoint response status is 201
    When submit a request to involved party digital address api with  "TEST_MAIL"
    Then endpoint response status is 400
    And response body has "ADDRESS_TYPE_NOT_EXIST" value for "error.code" attribute

  Scenario: Validate involved-party digital address api when global UUID is invalid
    When submit a request to involved party digital address api invalid global UUID
    Then endpoint response status is 404


