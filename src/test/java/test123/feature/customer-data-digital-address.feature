# Author	: SN95LA-Vishwanath Shetty
# Date		: 21/06/2022

@user-profile-management  @customer-data-digital-address
Feature: GET /v1/customer-data/{UUID}/digital-addresses
  Get digital address data of Customer

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Validate Get Personal Data Endpoint with valid UUID
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to get customer digital addresses endpoint with "global" UUID
    Then endpoint response status is 200

    Scenario: Validate Get Personal Data Endpoint with invalid UUID
    Given a customer "USER_WITH_INVALID_GLOBAL_UUID"
    When request submitted to get customer digital addresses endpoint with "global" UUID
    Then endpoint response status is 404

  Scenario: Validate Get Personal Data Endpoint with invalid UUID Type
    Given a customer "USER_WITH_VALID_GLOBAL_UUID"
    When request submitted to get customer digital addresses endpoint with "local" UUID
    Then endpoint response status is 400