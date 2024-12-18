# Backend Tech Task

## Installation
### Requirements
- Java 17
- Docker
- Preferably something like DBWeaver to look at data

### Steps
- Run `./generate-db-image.bat` to generate the DB in Docker
- Run `./gradlew.bat bootRun` - this will start the app and also run Liquibase to build the DB.

(Optionally) Run the following test data:

```sql
INSERT INTO dbo.Poll (Question, Active) 
VALUES ('Who won the 2008 Formula 1 World Championship?', 1),
('Who should get kicked off the Island?', 0),
('Wow, a poll with no options?', 0)

INSERT INTO dbo.Poll_Option (Content, Poll_Id) VALUES 
('Lewis Hamilton', 1), ('Fernando Alonso', 1), ('Kimi Raikkonen', 1), ('Michael Schumacher', 1), ('Robert Kubica', 1),
('Gordon Ramsay', 2), ('Cher', 2), ('Sean Lock', 2)

INSERT INTo dbo.Vote (Option_Id) VALUES (1), (1), (1), (1), (1), (1), (1), (1), (1), (1), (1), (1),
(2), (2),(2), (2),(2), (2),(2), (2),(2), (2),(2), (2), (3),(3), (3),(3), (4),
(5), (5), (5), (5), (5), (6), (6), (7), (7), (7), (8), (8)
```

You can hit endpoints via `http://locahost:8080/api/***`

Some examples:
- GET http://localhost:8080/api/poll/1
- GET http://localhost:8080/api/poll/active (used by the FE to fetch the current active poll)
- PUT http://localhost:8080/api/poll/1/active (to activate a poll to make it be visible on the FE app - this disables all other polls)
- 

## Overview
So you can, currently (via API):
- Create a poll.
- Set a poll to active (and disable all other polls).
- Add options to a poll (& configure max/min poll options via app properties).
  - Also soft-delete them.
- Fetch all polls.
  - This doesn't fetch the options & votes as there's likely no need if we're just displaying them in a table.
- Fetch a poll.
  - This fetches the poll with all of it's options, total votes and how many votes have been cast for each option.
- Submit a vote.
- Fetch all votes for a specific poll.
  - This gives you more details about the poll (poll, option, time voted at).
- Basic error catching.

It could, of course, be improved with pagination, possibly some caching, more edit functionality, more information saved in the DB - basically, given more time I definitely have ideas on how it can be jazzed up more.

## Testing

I added two integration tests on how I would test the DB methods directly.

Other services/controllers/resultSetExtractors can be tested either directly via integration tests with an actual DB, or via mockito mocks.