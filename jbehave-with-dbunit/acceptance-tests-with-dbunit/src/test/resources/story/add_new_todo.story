Narrative:
As a user
I want to add a new todo to my todo list.

Scenario: New todo added.
Given some todos available
When I add a new todo "Refactoring."
Then the last added todo is labeled "Refactoring."
And the last added todo is not done
And the list of todos has grew by this last todo