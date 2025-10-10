# Dungeon Crawler

## Introduction
A 2D dungeon crawler game built in Java. The project follows the Model-View-Controller (MVC) and Observer design patterns to create a modular and extensible structure.

The backend game logic handles the dungeon layout, hero movement, enemies, treasures, and collisions. The frontend (JavaFX) provides an interactive grid-based interface where players can explore, collect treasure, and avoid enemies.

### Features
* Procedurally generated dungeon boards with walls, treasures, enemies, and an exit.
* Hero movement (up, down, left, right) with collision handling.
* Enemy AI with random movement and interaction rules.
* Scoring system with treasures and level progression.
* Multiple levels that increase in difficulty.
* Follows MVC + Observer for clear separation of logic, rendering, and input.

### Tech Stack
* Java
* JavaFX
* Maven

### Project Structure
* model/ → Core game logic (board, pieces, movement, scoring)
* view/ → JavaFX user interface
* controller/ → Handles user input and connects model & view
* resources/ → Game art (images for hero, enemies, walls, treasure, exit)

### Future Improvements
* Smarter enemy AI
* More diverse room generation
* Customizable character sprites
* Sound effects and music
