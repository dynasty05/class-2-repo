@slow
Feature: Demonstrating the usage of web elements
  Scenario: Identify text field and print its html
    When I open practiceselenium.com website
    Then I find first and last name and print the html
    @sanity
  Scenario: Identify link field and print its html
    When I open practiceselenium.com website
    Then I find menu and print the html
  Scenario: Identify button field and print its html
    When I open practiceselenium.com website
    Then I find button and print the html
  Scenario: Identify radio button and print its html
    When I open practiceselenium.com website
    Then I find radio button male and print the html
  Scenario: Identify checkbox and print html
    When I open practiceselenium.com website
    Then I find check box and print the html
  @beta
  Scenario: Identify select list and print html
    When I open practiceselenium.com website
    Then I find select list and print html
  @beta
  Scenario: Identify another select list and print html
    When I open practiceselenium.com website
    Then I find another select list and print html
  @beta
  Scenario: Identify a div and print html
    When I open practiceselenium.com website
    Then I find div and print html

#    Note: there are options to run cucumber tests apart from using the Cucumber Junit Runner
#    usually named RunCukesTest. options are:
#  1) Using the Cucumber Java run configuration: offers a GUI alternative to running cucumber from
#     the cli. The fields are the options you would supply to cucumber.cli.Main if you were running
#     the cucumber using javac/java. Note: the org.jetbrains.plugins.cucumber.java.run.CucumberJvmSMFormatter
#     value for the --plugin/--format option is only available within intellij and not pure command line.Feature:
#
#  2) Using command line cucumber: As mentioned above, the cucumber engine can be run in javac/java purely from
#     the command line, and supplying the cucumber options as command line options. This is a pain though, as all
#     classpath dependencies need to be supplied to the command line.
#
#  3) Using maven surefire plugin on the command line: issuing "mvn test" with the system property
#     -Dcucumber.options="<<all_options_you_can_supply_to_command_line_cucumber>>". Generally, mvn test actually
#     issues a command line java under the hood to run whatever the test runner is (in this case cucumber),
#     supplying the system properties as command line options to cucumber.cli.Main.