Feature: Demonstrate the json data formats
  Scenario: Serialisation - convert json object to bytes and print its string value
    When I create json string from object and write to file
    Then I print it as a string