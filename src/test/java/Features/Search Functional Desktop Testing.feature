# Autor: Züleyha Şahin
Feature:Amazon Search box in Desktop Browser

  @Test
  Scenario: Validation 'Amazon Search box' field
    Given User go  to Amazon
    Then  Check if the search box is present
    Then  Check the length of "Amazon test" to be added to the search box.
    Then  Check ".*$%^#:.*" characters that can be entered in the search field


  @Test
  Scenario: Amazon Search Box check  results
    Given User go  to Amazon
    Then  Enter a "iPhone12" product name and click on the search button.
    And   The result with the product name "iphone 12" is displayed
    Then  Clear Search box and click on the search button
    And   User is on "https://www.amazon.com/" page
    Then  Enter a "iPhone12 iPhone12" product name and click on the search button.
    And   The result with the product name "iphone 12 iphone 12" is displayed
    Then  Enter a "1259403*4985409*3249854903*" product name and click on the search button.
    And   User get a message: "No results for""1259403*4985409*3249854903*"

    @Test
  Scenario: Amazon Search Box using language
    Given User go  to Amazon
    Then  Enter a "iPhone12" product name and click on the search button.
    Then  User select language: "Español - ES"
    And   The result with the product name "iphone 12" is displayed
    Then  User select language: "English - EN"

  @Test
  Scenario: Amazon Search Box using categorie
    Given User go  to Amazon
    Then  Click on DropDown Description and select "Computer"
    Then  Enter a "iPhone12" product name and click on the search button.
    And   The result with the product name "iphone 12" is displayed

  @Test
  Scenario: Amazon Search Box using deliver
    Given User go  to Amazon
    Then  Click on deliver button
    And   Choose your location "Germany"
    Then  Enter a "iPhone12" product name and click on the search button.
    And   The result with the product name "iphone 12" is displayed
