Narrative:
As a user
I want to update a todo

Scenario: Label of an existing todo updated.
Given some todos available
When I replace the todo 2 by this label "Constituer les jeux de données de test en XML pour DbUnit."
Then the todo 2 label has been replaced by "Constituer les jeux de données de test en XML pour DbUnit."

Scenario: Done status of an existing todo updated.
Given some todos available
When I finish the todo 1
Then the todo 1 is done