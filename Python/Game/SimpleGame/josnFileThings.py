import json

def loadGame(dict):
    newGameSetings = None
    with open(dict, "r") as f:
        newGameSetings = json.load(f)
    return newGameSetings
    

def storeGame(data, dict):
    with open(dict, "w") as f:
        json.dump(data, f, indent=6)
