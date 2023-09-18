Feature: n11tests

  Scenario Outline: Successful login
    Given Login page is open on "<browser>"
    When User enters correct credentials and clicks login
    Then Login should be successful

    Examples: 
      | browser |
      | firefox |
      | chrome  |

  Scenario Outline: Failed login
    Given Login page is open on "<browser>"
    When User enters wrong credentials and clicks login
    Then Login should not be successful

    Examples: 
      | browser |
      | firefox |
      | chrome  |

  Scenario Outline: Search valid keyword
    Given Homepage is open on "<browser>"
    When user enters valid keyword
    Then Results should be present

    Examples: 
      | browser |
      | firefox |
      | chrome  |

  Scenario Outline: Search invalid keyword
    Given Homepage is open on "<browser>"
    When user enters invalid keyword
    Then Results should not be present

    Examples: 
      | browser |
      | firefox |
      | chrome  |
