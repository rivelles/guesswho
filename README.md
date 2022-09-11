# Guess Who
Back-end application to provide an API to the Guess Who game. This was created in order to put some DDD concepts in
practice.

## Endpoints
- GET /guesswho
- POST /guesswho/questions/#id/next-tip
- POST /guesswho/questions/#id/answer

## Domain specification
This application uses a domain-driven-design approach, so it has its own bounded context, 
which contains the following aggregates:
- Question: Formed by one root entity called Question and the following value objects:
    - Answer: Represents the correct answer for the question
    - QuestionTips: Represents the tips that the user can see and request during a session
    - DateOfAppearance: Represents the date that the question will appear, or was appeared.
- Session: Formed by one root entity called UserIdentifier and the following value objects:
    - QuestionId: Represents the current question for that session
    - StartDateTime: The instant representing when that session started
    - EndDateTime: The instant representing when that session finished

## Use cases
A **question** can be created.

A **session** can be **created** by a **user**. 
When that happens, the **question of the day** will be assigned to that session.

The **question of the day** will be the question that has the **DateOfAppearance** associated 
with the **current date**. If no question is found, the question of the day will be
the question that **appeared the last** between all. In this case, the **DateOfAppearance**
must change to the current date.