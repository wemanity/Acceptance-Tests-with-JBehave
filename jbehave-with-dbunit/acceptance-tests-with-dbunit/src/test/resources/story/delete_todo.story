Narrative:
As a user
I want to delete a todo

Scenario: One todo to be deleted.
Given some todos available
When I delete the todo number 3
Then the list of todos has decreased by 1 todo