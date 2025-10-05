# Number Planet

 A small educational arcade-style Java game that combines quick reflexes with basic data-structure concepts.

 ## About

 Number Planet is a desktop Java game built with Swing. Players interact with graphical UI elements (bucket, cannon, droplets, fireballs) and progress through rounds. The project was implemented as an educational exercise and demonstrates use of custom UI components, simple game loop mechanics, and two selectable underlying data structures used by the game logic (a binary search tree or a hash table).

 ## Features

 - Simple, keyboard/mouse-driven gameplay using Swing.
 - Two selectable data structure backends: Binary Search Tree and Hash Table (see `utilz.Constants` to configure).
 - Multiple rounds and UI panels (intro, rounds, south/west panels, scorecard).
 - Uses local resources (images) in the `res/` folder for sprites and backgrounds.

 ## Requirements

 - Java JDK 8 or later (project was written for Java 8+; newer JDKs should work but verify Swing compatibility).
 - Git (to publish to GitHub)

 ## Project structure (important files)

 - `src/` - Java source code.
   - `main/Main.java` - Program entry point.
   - `main/GameFrame.java` - Main JFrame and panel management.
   - `main/ImplementedDS.java` - Enum of available data structures (BINARYSEARCHTREE, HASHTABLE).
   - `panels/` - Swing panels (IntroPanel, GamePanel, Round1Panel, Round2Panel, etc.).
   - `ui/` - UI components (Bucket, Cannon, Droplet, Fireball, IntroButton, etc.).
   - `utilz/Constants.java` - Game configuration and constants (screen size, shots per round, etc.).
 - `res/` - Game image assets used by the UI (bgImage.jpg, bucket.png, droplet.png, playerCannon.png, shootingDroplet.png, glowBucket.png, fireball.png, splash.png, enemyCannon.png).
 - `bin/` - Compiled .class files (not required for source distribution).

 ## Installation

 1. Clone the repository locally:

    git clone <your-repo-url>

 2. Change into the project directory:

    cd NumPlanet

 3. Ensure the `res/` directory is present and contains the image assets. The game expects to load images with relative paths such as `res/bgImage.jpg`.

 4. Compile the source files. From the project root you can use `javac` and the `src` directory as the source root. Example (from the project root):

    mkdir -p bin
    javac -d bin $(find src -name "*.java")

 Note: On zsh, you may want to run the find command in quotes as shown to avoid globbing issues.

 ## Running the game

 After compilation, run the `main.Main` class from the `bin` directory:

    java -cp bin main.Main

 This will open the game's window (GameFrame) and show the intro panel. Use the Start button to begin playing.

 ## Configuration

 - Switch the implemented data structure used by the game logic by editing `src/utilz/Constants.java`. Change the value of `IMPLEMENTED_DS` to `ImplementedDS.BINARYSEARCHTREE` or `ImplementedDS.HASHTABLE` and recompile.
 - Game dimensions and some constants are located inside `utilz.Constants.GameConstants`.

 ## Assets

 The game uses images in `res/`. Ensure you include these files when you publish the repo so the game can load them with the relative paths used in code (for example: `new ImageIcon("res/bgImage.jpg")`).

 ## Contributing

 Contributions are welcome. Suggested improvements:

 - Add build scripts (Gradle or Maven) for easier compilation and distribution.
 - Package the game as an executable JAR with resources bundled.
 - Improve input handling and add settings (volume, resolution, data-structure selection at runtime).