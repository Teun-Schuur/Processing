from game import *
import json


gameSetings = {
              "screen": [1000, 600],
              "backGround": color(26, 167, 212),
              "spriteSpeed": 2,
              "spriteJumpVelocity": 13,
              "spriteForse": 0.4,
              "spriteWidth": 30,
              "spriteHeight": 30,
              "spriteColor": color(0),
              "spriteMass": None,
              "frameRate": None,
              "boxes": {
                        0: {
                            "x":      0,
                            "y":      550,
                            "width":  1200,
                            "height": 55,
                            "color":  color(233, 0, 0),
                            "removeIfOffscreen": False,
                            "halt":   True
                            },
                        1:  {
                            "x":      0,
                            "y":      550,
                            "width":  1200,
                            "height": 55,
                            "color":  color(255, 0, 0),
                            "removeIfOffscreen": True,
                            "halt":   False
                           },
                        2:  {
                            "x":      0,
                            "y":      550,
                            "width":  1200,
                            "height": 55,
                            "color":  color(0, 0, 255),
                            "removeIfOffscreen": True,
                            "halt":   False
                           },
                        3:  {
                            "x":      0,
                            "y":      550,
                            "width":  1200,
                            "height": 55,
                            "color":  color(0, 255, 0),
                            "removeIfOffscreen": True,
                            "halt":   False
                           }
                        }
              }

game = None
boxes = []
def setup():
    global game, boxes
    size(gameSetings["screen"][0], gameSetings["screen"][1])
    smooth()
    frameRate(100 if gameSetings["frameRate"]==None else gameSetings["frameRate"])
    boxes = [[0, height - 50, width*90, 55, color(45, 255, 255)],
            [1000, height-200, 200, 50, color(0, 255, 255)],
            [1300, height-400, 150, 50, color(20, 255, 255)],
            [1550, height-600, 250, 50, color(40, 255, 255)],
            [1970, height-500, 150, 50, color(60, 255, 255)],
            [2300, height-900, 300, 50, color(80, 255, 255)],
            [1270, height-700, 200, 50, color(100, 255, 255)],
            [2120, height-800, 100, 50, color(120, 255, 255)]]
    game = Game(boxes, speed=gameSetings["spriteSpeed"], massa=gameSetings["spriteMass"], 
                forse=gameSetings["spriteForse"], spriteColor=gameSetings["spriteColor"], 
                spriteSetings=[gameSetings["spriteHeight"], gameSetings["spriteWidth"]])
    #storeGame(gameSetings, "testGame.json")
    loadGame("testGame.json")
    
    

def draw():
    #background(26, 167, 212)
    background(gameSetings["backGround"])
    game.update()
    game.show()
    for i in range(1, len(game.boxes), 1):
        game.boxesOptions(i, "SetOffScreen", width)
    
    



def loadGame(dict):
    global game
    newGameSetings = None
    with open(dict, "r") as f:
        newGameSetings = json.load(f)

    size(newGameSetings["screen"][0], newGameSetings["screen"][1])
    smooth()
    frameRate(100 if newGameSetings["frameRate"]==None else newGameSetings["frameRate"])
    game = Game(boxes, speed=newGameSetings["spriteSpeed"], massa=newGameSetings["spriteMass"], 
                forse=newGameSetings["spriteForse"], spriteColor=newGameSetings["spriteColor"], 
                spriteSetings=[newGameSetings["spriteHeight"], newGameSetings["spriteWidth"]])
    gameSetings = newGameSetings
    

def storeGame(data, dict):
    with open(dict, "w") as f:
        json.dump(data, f, indent=6)


def keyPressed():
    if key == " ":
        game.SpriteJump(gameSetings["spriteJumpVelocity"])
        
