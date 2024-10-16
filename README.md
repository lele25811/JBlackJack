# JBlackJack

## Project Overview
**JBlackJack** is a Java-based simulation of the popular card game BlackJack. The project follows the Model-View-Controller (MVC) design pattern to separate game logic from the graphical user interface and input control. It supports multiple bot players. The graphical interface is implemented using Swing, providing an interactive display of cards, player actions.

## Project Structure

The project is divided into the following packages:

### 1. **Model**
   The `Model` package handles all the game logic, including BlackJack rules, card distribution, and bot decision-making.
   
   - `BlackJackPlayer`: Represents a BlackJack player and manages their hand, score, and actions.
   - `BlackJackBot`: Represents a BlackJack bot and manage logic for decisions based on the current hand.
   - `Deck`: Represents the deck of cards, shuffling and dealing cards to players and bots.
   - `Card`: Represents a single card with its suit, rank, and value.
   - `GameTable`: Manages the game’s flow, including distributing cards, handling turns, and game rules.
   - `Database`: Manages file I/O for storing and retrieving player data.

### 2. **View**
   The `View` package contains classes responsible for rendering the graphical interface and interacting with the user.

   - `GamePage`: Displays the game board, showing each player's hand and current score.
   - `PlayerPanel`: Displays the human player's cards and score, also handling user actions like hit or stand.
   - `BotPanel`: Displays bot players’ cards and scores, updating automatically as the game progresses.
   - `ActionPlayerPanel`: Manages the player’s actions (hit, stand, split) and updates the game state.
   - `LoginPage`: Provides a user interface for players to log in or create new profiles before starting the game.

### 3. **Controller**
   The `Controller` package connects the Model and View, managing user interactions and updating the game state accordingly.
   
   - `GameController`: Handles the flow of the game, processing player input and updating the View based on game logic in the Model.

## Installation & Setup

To run **JBlackJack**, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/lele25811/JBlackJack.git
2. Navigate to the project directory:
   ```bash
   cd JBlackJack
3. Compile the project using your preferred IDE.
4. Run the `Main` class to launch the game.
