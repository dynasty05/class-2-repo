Feature: Demonstrate CRUD operations with Excel and read data from excel into a web form

  Scenario: Create, update excel workbook,add data and delete data
    When I create the excel workbook "Random.xlsx"
    Then I print the data inside the workbook
    And I perform delete operations on a workbook

