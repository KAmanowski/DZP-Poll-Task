# Front End
This is the front-send for the technical task. It's made via the usual create-react-app package.

## Installation
1. Install with `npm install`.
2. Run with `npm run start`
3. Acces via `https://localhost:3000`

## Functionality

This is just a basic voting front-end UI, and so it doesn't have any routes (it only has one page). 

It fetches the current active poll from the DB, and presents all option on-screen. Once the user votes for an option, it submits the vote, refetches the poll to get the latest data, and presents the results of the poll.

Of course there's a lot to improve here given enough time:
- Providing functionality to edit the polls/add new polls.
  - This is already available in the backend API.
- Better error handling.
- Formatting/design (could be nicer showing the result).
- Loading icons via boolean state.
- ...lots more

## Testing
Added an example of a very simple test in MainVote.test.tsx - just finds the logo to confirm it's there.

Other tests could include:
- Provide mock api responses to render out the MainVote page and check that all of the active poll options render correctly.
- Check that the onClick is fired once we simulate clicking on an options.
- Verify that the option buttons have turned a certain colour onhover/onclick.