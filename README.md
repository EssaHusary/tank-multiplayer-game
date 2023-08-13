# csc413-tankgame

Two players go ahead-to-head in tank combat where players can pick up power ups and whoever loses all lives, loses the game!

| Student Information |                |
|:-------------------:|----------------|
|  Student Name       |   Essa Husary    |
|  Student Email      |   ehusary2@mail.sfsu.edu   |

## src Folder Purpose 
src folder is to be used to store source code only.

## resources Folder Purpose 
resources folder is to be used to store the resources for your project only. This includes images, sounds, map text files, etc.

`The src and resources folders can be deleted if you want a different file structure`

## jar Folder Purpose 
The jar folder is to be used to store the built jar of your term-project.

`NO SOURCE CODE SHOULD BE IN THIS FOLDER. DOING SO WILL CAUSE POINTS TO BE DEDUCTED`

`THIS FOLDER CAN NOT BE DELETED OR MOVED`

# Required Information when Submitting Tank Game

## Version of Java Used:

Version 2022.2.1

## IDE used: 

Intellij Ultimate IDEA

## Steps to Import project into IDE:

- Clone the remote repository into your machine from GitHub.
- Delete the folders labeled “src” and “resources” inside the folder labeled “csc413-tankgame-username” and drag and drop the tank game base code from Canvas into the “csc413-tankgame-username” folder
- Open up Intellij and select “Import” on the Intellij home screen.
- If “Import” isn’t there for whatever reason, you can open the project from another project. Open up an existing project, click on “File” on the top left, and hover your cursor on “New”. Then from “New”, drag your cursor to where it says “Project from Existing Sources…” and click on “Project from Existing Sources…”.
- The root of the repository is the source of the project, so click the repo itself (which should look like “csc413-tankgame-username”) and hit “OK”.
- Proceed, clicking “next” on each screen (when ready) until the project is fully imported.
- Once the project has been imported, you need to set the “resources” folder as the source root of resources (basically copying everything from the “resources” folder to the “out” folder). To do so, right click on the “resources” folder in the “TankGame” directory and hover your mouse cursor over to the bottom of the menu that appears when you right click that “resources” folder. Hover your cursor over “Mark Directory as” and then another menu will appear where the option “Resources Root” appears. Click “Resources Root”.



## Steps to Build your Project:

- Next, the jar needs to be built. Click “File” on the top left of the screen, then click on “Project Structure”. Next, you will see options including the option “Artifacts”. Click “Artifacts”. Once you do so, there will be a little plus sign (+) above where it says “Add  Alt+Insert”. Click the + sign, then hover your cursor over the option “JAR” and once you do so, click on “From modules with dependencies…”. Where it says “Main Class” in the “Create JAR from Modules” menu, hit the little folder image and select “Launcher”, since that is our main class. Hit “OK”, then “OK” again when you’re back on the “Create JAR from Modules” menu, then hit “Apply” when you’re back on the “Project Structure" menu, then hit “OK”. Next, find “Build” on the top of the project screen and once on this menu, click “Build Artifacts…” and then click “Build” under the small “Action” menu.
- To run the jar, simply right click on “csc413-tankgame-username.jar” and hit “Run ‘csc413-tankgame-username…’ “. To make sure that we’re not running against old code, we should repeat the following steps (stated previously) every time we change something in our code or when we’ve changed things substantially: Find “Build” on the top of the project screen and once on this menu, click “Build Artifacts…” and then click “Build” under the small “Action” menu.


 
## Steps to run your Project:

- To run the game, simply right click on the class called “Launcher” and click on “Run ‘Launcher.main()’ “, since that is the main class.
- The rules of the game are quite simple: destroy the other tank and collect whatever powerups are necessary to destroy the other tank.
- Player 1’s controls are: Up = W, Down = S, Rotate Left = A, Rotate Right = D, and Shoot = Space bar
- Player 2’s controls are: Up = Up arrow key, Down = Down arrow key, Rotate Left = Left arrow key, Rotate Right = Right arrow key, and Shoot = ‘Enter’ key


## Controls to play your Game:

|               | Player 1 | Player 2 |
|---------------|----------|---------------------|
|  Forward      |    W     |   UP arrow key      |
|  Backward     |    S     |   DOWN arrow key    |
|  Rotate left  |    A     |   LEFT arrow key    |
|  Rotate Right |    D     |   RIGHT arrow key   |
|  Shoot        |  SPACE   |   ENTER key         |

<!-- you may add more controls if you need to. -->
