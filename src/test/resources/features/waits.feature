Feature: Demonstrate selenium waits

#  Scenario: Pageload timeout
#    When I open seleniumframework website
#    And I set pageload timeout

#  Scenario: Implicit waits timeout
#    When I open seleniumframework website
#    And I set implicit timeout
#    Then I print the text for target element
#
#  Scenario: Script timeout
#    When I open seleniumframework website
#    And I set script timeout

#  Scenario: Explicit Wait and Expected Conditions clickable
#    When I open seleniumframework website
#    Then I set explicit wait block on target element until it becomes clickable

  Scenario: Explicit Wait and Expected Conditions alert present
    When I open seleniumframework website
    Then I set explicit wait block on on alert present