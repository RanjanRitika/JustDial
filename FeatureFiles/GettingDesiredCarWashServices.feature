Feature: Getting car washing services at current location

  Scenario: Closing the pop-up
    Given the user is on Just Dial home page
    When the sign-up pop-up shows the user clicks on Maybe later
    Then the user should be re-directed to the Just Dial home page

  Scenario: Entering the search details
    When the search info is typed
    And the search button is clicked
    Then the user should be re-directed to the search page

  Scenario: Setting the filters
    Given the user is on the search page
    When the filters have loaded
    Then apply the filters

  Scenario: Getting the desired info
    Given the results have loaded
    Then get name, phone number and rating

  Scenario: Printing out the error message
    Given the user is on the free listing page
    When the invalid number is entered
    And the start now button is clicked
    Then error message is displayed and captured

  Scenario: Getting the sub menu items from gym
    Given the user is back on Just Dial home page
    When the Gym option is clicked
    And the sub menu items are visible
    Then printing out the sub menu items
