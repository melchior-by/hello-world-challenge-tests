Feature: User greeted with Hello
  As a user
  I want to send Hello request
  So that I am greeted by the app in response

  Scenario Outline: Send hello request
    Given request to "hello" endpoint
    And User authorization is "<authorization>"
    When GET request
    Then I should get <code> status code
    Examples:
      | authorization    | code |
      | authorized       | 200  |
      | no authorization | 401  |
      | anything else!   | 500  |
