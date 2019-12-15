from game import PlayGame
from josnFileThings import loadGame, storeGame
from main import Main
# from levelCreator import *


"""
TODO: 
    - alles in json files
    - file Game afmaken en afronden:
        - alles laten klopen
        - respan enz
    - verschilende levels
    - level creater
    - een main
    - camera:
        - een deel van de carvas waar de sprite kan zijn,
          als het veder gaat dan die grensen gaan de bloken mee.       
"""

levels = []
game = None
main = None
mainNotDone = True
levelEditor = None
selection = None
bufer=False

def setup():
    global main, levels, levelEditor
    size(1200, 800)
    #storeGame(gameSetings, "testGame.json")
    levels.append(loadGame("levels/levelOne.json"))
    levels.append(loadGame("levels/levelTwo.json"))
    levelEditor = LevelCreator(levels[0])
    main = Main()
    

def draw():
    global mainNotDone, selection
    if mainNotDone:
        main.show()
        buffer = main.update()
        mainNotDone = mainSetup(main.update())
    else:
        if selection.play():
            mainNotDone = True
            selection = None


def mainSetup(value):
    global selection
    print value
    if value != None:
        if value[1] == 3:
            selection = PlayGame(levels[value[0]-1])
        if value[1] == 4:
            selection = LevelCreator(levels[value[0]-1])
        return False
    else:
        return True
