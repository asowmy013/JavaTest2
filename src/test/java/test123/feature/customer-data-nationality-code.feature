# Author	: SN95LA-Vishwanath Shetty
# Date		: 21/06/2022

@nationality-code
Feature: PATCH /v1/customer-data/{UUID}/personal-data/nationality-code
  Update nationality code

  Background:
    Given the "ITAPartyAgreementsAPI" component

    Scenario Outline: validate the nationality code update for valid customer id
      Given a customer "<uuid>"
      When request submitted to nationality code endpoint with "<uuidType>" uuid and "<nationalityCode>"
      Then endpoint response status is 204
      Examples:
       |uuid | uuidType |nationalityCode|
       |USER_WITH_VALID_LOCAL_UUID | local    | IT|
       |USER_WITH_VALID_GLOBAL_UUID| global   | IT|


  Scenario Outline: validate the nationality code update for invalid customer id
    Given a customer "<uuid>"
    When request submitted to nationality code endpoint with "<uuidType>" uuid and "<nationalityCode>"
    Then endpoint response status is <status>
    Examples:
      |uuid | uuidType |nationalityCode|status|
      |USER_WITH_INVALID_LOCAL_UUID | local    | IT|404|
      |USER_WITH_INVALID_GLOBAL_UUID| global   | IT|404|
      |USER_WITH_7DIGIT_LOCAL_UUID  | local    | IT|400|

  Scenario Outline: validate the nationality code update for invalid nationality code
    Given a customer "<uuid>"
    When request submitted to nationality code endpoint with "<uuidType>" uuid and "<nationalityCode>"
    Then endpoint response status is 400
    Examples:
      |uuid | uuidType |nationalityCode|
      |USER_WITH_VALID_LOCAL_UUID | local    | FRANCE|
      |USER_WITH_VALID_GLOBAL_UUID| global   | ITALY |


  Scenario Outline: validate the nationality code update for without nationality code
    Given a customer "<uuid>"
    When request submitted to nationality code endpoint with "<uuidType>" uuid without request body
    Then endpoint response status is 400
    Examples:
      |uuid | uuidType |
      |USER_WITH_VALID_LOCAL_UUID | local    |
      |USER_WITH_VALID_GLOBAL_UUID| global   |


  Scenario Outline: validate the nationality code update with invalid uuid type code
    Given a customer "<uuid>"
    When request submitted to nationality code endpoint with "<uuidType>" uuid and "<nationalityCode>"
    Then endpoint response status is 400
    Examples:
      |uuid | uuidType |nationalityCode|
      |USER_WITH_VALID_LOCAL_UUID | local_global    | IT|
