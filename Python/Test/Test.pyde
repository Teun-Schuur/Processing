import snake

window = 40
sceal = 30
choise = [0, 1]
food = [floor(random(0, window))*sceal, floor(random(0, window))*sceal]
mySnake = 0
gamePlay = True


def setup():
    global mySnake
    size(window*sceal, window*sceal)
    frameRate(10)
    strokeWeight(sceal)
    stroke(255)
    mySnake = snake.Snake(sceal, window)
    
    
    
def draw():
    global gamePlay
    if(gamePlay):
        background(0)
        smooth()
        mySnake.show()
        if(mySnake.eatFood(food)):
            makeNewFood()
        mySnake.update(choise)
        if(mySnake.snakeIsDead()):
            gamePlay = False
        point(food[0], food[1])


def makeNewFood():
    global food
    food = [floor(random(0, window))*sceal, floor(random(0, window))*sceal]


def keyPressed():
    global choise, gamePlay, mySnake
    choises = {
            "a": [0, -1],  #left
            "d": [0, 1],   #right
            "s": [1, 0],   #down
            "w": [-1, 0]}  #up
    if choise != [0, 1] and key == "a":
        choise = choises[key]
    if choise != [0, -1] and key == "d":
        choise = choises[key]
    if choise != [-1, 0] and key == "s":
        choise = choises[key]
    if choise != [1, 0] and key == "w":
        choise = choises[key]
    if key == " ":
        mySnake = snake.Snake(sceal, window)
        gamePlay = True
        
