### El Fakiri Rida S183747
### Abdelaleem Aly S206111

## What is AdventureGame ?

It's an interactive java object-oriented game where the interaction is done by entering commands expressed in a simple syntax.

## What are AdventureGame commands ?

- Turn (right | left) 
    - Turn the player around by 90 degrees in the specified direction.
- Move: 
    - Move forward to the cell located in front of the character.
- Take (key)
    - Pick up the key from the ground. 
- Drop (key)
    - Drop the key to the ground.
- (unlock | lock) (box | door) with key 
  - unlock or lock a door or box.
- Help
  - Display a list of all commands understood by the game, with a brief documentation. With the argument object.
- Help (object)
    - Display actions that can be applied to the object
	
## What are AdventureGame Objects?

- Door 
  - Can be unlocked and locked in order to go move forward.
- Key  
  - Can lock or unlock another object such as **Door** or **Box**
- Coin 
  - The goal of the game is to collect 3 **Coin** which can be found by unlocking **Box**.
- Box 
  - Can be unlocked/locked by **Key** and in it, you can find **Coin**.
  
## How to play AdventureGame ?

Simply use the supplied commands to interact with the surroundings and try to find all 5 boxes and unlocking them with their keys and collecting the 3 coins to win.

## Real Game Examples

There is one door in front of you\
There is a key on the ground\
You do not carry anything.

% Take key\
There is one door in front of you\
There is nothing on the ground\
You carry a key

%Unlock door with key\
door is unlocked\
There is one door in front of you\
There is nothing on the ground\
You carry a key

% Drop key\
There is one door in front of you\
There is a key on the ground\
You do not carry anything.

% Move\
There is a key on the ground\
You do not carry anything.

% Unlock box with key\
box is unlocked\
There is one door behind you, and one door to your right\
There is a key on the ground , and there is a box on the ground\
You carry a key and a coin

% Turn right\
There is one door to your right, and one door in front of you\
There is one box on the ground\
You carry 2 keys, and a coin

% Unlock door with key 2\
door is unlocked\
There is one door to your right, and one door in front of you\
There is one box on the ground\
You carry 2 keys, and a coin

% Turn left\
There is nothing on the ground\
You carry 3 keys, and a coin

% Move\
No more room to move !\
There is one box on the ground\
You carry 4 keys, and 3 coins

% Unlock box with key\
box is unlocked\
Congratulations you won !

## How to run AdventureGame ?

All you will need is a JVM with JDK 8 or higher, then unzip the file and run the following commands *javac AdventureGame.java* then *java AdventureGame*