# Author	: SN95LA-Vishwanath Shetty
# Date		: 09/11/2022
@italy-residency
Feature: PATCH /v1/customer-data/{UUID}/personal-data/italy-residence
  Update last 2 years residence address

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario Outline: Update last 2 years residence address for valid data
    Given a customer "<User>"
    When request submitted to personal data italy-residence with UUID type "<uuidType>" and last two years is "<lastTwoYears>"
    Then endpoint response status is 204
    Examples:
      | User                        | uuidType | lastTwoYears |
      | USER_WITH_VALID_LOCAL_UUID  | local    | true         |
      | USER_WITH_VALID_GLOBAL_UUID | global   | false        |

  Scenario Outline: Update last 2 years residence address for invalid data
    Given a customer "<User>"
    When request submitted to personal data italy-residence with UUID type "<uuidType>" and last two years is "<lastTwoYears>"
    Then endpoint response status is <status>
    Examples:
      | User                          | uuidType     | lastTwoYears | status |
      | USER_WITH_INVALID_LOCAL_UUID  | local        | true         | 404    |
      | USER_WITH_INVALID_GLOBAL_UUID | global       | false        | 404    |
      | USER_WITH_VALID_LOCAL_UUID    | global_local | false        | 400    |
      | USER_WITH_VALID_LOCAL_UUID    | local        | true_false   | 400    |


  Scenario Outline: Validate italy-residence end point with out body
    Given a customer "USER_WITH_VALID_LOCAL_UUID"
    When request submitted with out json body with UUID type "<uuidType>"
    Then endpoint response status is 400

    Examples:
      | uuidType |
      | local    |
      | global   |