# UNO Rules Supported

This document lists the UNO-style rules implemented in this project and the simplifications used.

## Implemented rules

### Correct deck composition

Implemented.

The deck contains:

- four colors: red, yellow, green, blue
- one `0` card in each color
- two cards for each number `1-9` in each color
- two `Skip` cards in each color
- two `Reverse` cards in each color
- two `Draw Two` cards in each color
- four `Wild` cards
- four `Wild Draw Four` cards

Total deck size: `108` cards.

### Legal play validation

Implemented.

A card is legal if:

- its color matches the active color
- its number matches the top card number
- its action type matches the top card action type
- it is a `Wild`
- it is a `Wild Draw Four`

After a wild card is played, the chosen color becomes the active color.

### Skip

Implemented.

When a Skip card is played, the next player loses their turn.

### Reverse

Implemented.

In games with three or more players, Reverse changes the play direction.

In two-player games, Reverse is treated like Skip. The other player loses their turn and the player who played Reverse gets another turn.

### Draw Two

Implemented.

When Draw Two is played:

- the next player draws two cards
- the next player loses their turn

Draw Two stacking is not implemented.

### Wild

Implemented.

When Wild is played:

- the player chooses the next active color
- the chosen color controls future legal-play checks

### Wild Draw Four

Implemented.

When Wild Draw Four is played:

- the player chooses the next active color
- the next player draws four cards
- the next player loses their turn

Wild Draw Four challenge rules are not implemented.

### Draw and pass behavior

Implemented.

This project uses the variant:

- draw one card
- if the drawn card is legal, the player may play it immediately
- otherwise, the turn passes

### UNO call and missed-UNO penalty

Implemented.

When a player reaches one card, the player may call UNO.

If a player has one card and has not called UNO before their next relevant action, the missed-UNO penalty applies.

Penalty used:

- draw two cards

### Round scoring

Implemented.

A round ends when one player empties their hand.

The round winner receives points from the remaining cards in all other players' hands:

- number cards: face value
- Skip: 20
- Reverse: 20
- Draw Two: 20
- Wild: 50
- Wild Draw Four: 50

### Multi-round game target

Implemented.

The game continues across multiple rounds until a player reaches or exceeds the selected target score.

The CLI lets the user choose the target score.

## Simplifications

The project uses these simplifications:

- no Wild Draw Four challenge rule
- no Draw Two stacking
- simple bot strategy
- text-only CLI
- starting discard is forced to be a number card by redrawing if needed
- no network or GUI mode
