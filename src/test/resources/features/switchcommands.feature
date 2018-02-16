Feature: Switch commands in Selenium webdriver

  Scenario: Switch to new window
    When I open seleniumframework practiceform
    And I open a new window
    Then I switch to the new window
