# Author	: Anbarasan Ramasamy
# Date		: 20/07/2021
@ichp-poc-endpoints
Feature: generic-test-endpoints
  Test running common step utilities in thor environment

  Background:
    Given the "ITAPartyAgreementsAPI" component

  Scenario: Test endpoint in GenericTestEndpointSteps.java
    Given a keepAlive
    Then keepAlive response status is "200"




