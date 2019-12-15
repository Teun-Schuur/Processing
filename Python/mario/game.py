from enamys import *
from extras import *
from mario import *
from opstacels import *



class PlayGame:
    def __init__(self, setings):
        self.setings = setings
        boxes = []
        for i in len(setings["boxes"]):
            boxes.append([setings["boxes"][str(i)]["x"],
                          setings["boxes"][str(i)]["y"],
                          setings["boxes"][str(i)]["width"],
                          setings["boxes"][str(i)]["height"],
                          setings["boxes"][str(i)]["color"],
                          setings["boxes"][str(i)]["removeIfOffscreen"],
                          setings["boxes"][str(i)]["halt"],
                          ])
        self.game = Game(boxes, speed=gameSetings["spriteSpeed"], massa=gameSetings["spriteMass"], 
                         forse=gameSetings["spriteForse"], spriteColor=gameSetings["spriteColor"], 
                         spriteSetings=[gameSetings["spriteHeight"], gameSetings["spriteWidth"]])



    def play(self):
        background(self.setings["backGround"])
        self.game.update()
        self.game.show()
        for i in range(1, len(self.game.boxes), 1):
            self.game.boxesOptions(i, "SetOffScreen", width)
        if keyPressed:
            if key == " ":
                self.game.SpriteJump(self.setings["spriteJumpVelocity"])
            if key == "q":
                return True

        return False
            



class Game:
    def __init__(self, boxes, speed=2, forse=0.5, enamys=None, spriteColor=color(0), massa=None, spriteSetings=[30, 30]):
        self.boxes = []
        self.sprite = Mario(width/3, height - 900, spriteSetings[0], spriteSetings[1], massa)
        for _box in boxes:
            self.boxes.append(Block(_box))
        for _box in self.boxes:
            _box.speed = speed
        self.hit = [False, None]
        self.haltGame = False
        self.spriteColor = spriteColor
            
    def addBox(self, myBox):
        self.boxes.append(myBox)
        
    def removeBox(self, id):
        self.boxes.pop(id)
        
    def update(self):
        self.hit[0] = False
        self.haltGame = False
        for _box in self.boxes:
            if not self.haltGame:
                self.haltGame = _box.testToStop(self.sprite)
        if not self.haltGame:
            for _box in self.boxes:
                _box.update()
                if not self.hit[0]:
                    self.hit = _box.testPosition(self.sprite)
        self.sprite.update(self.hit)
        
        
    def show(self):
        for _box in self.boxes:
            _box.show()
        self.sprite.show(self.spriteColor)

    def SpriteJump(self, JumpHeight):
        self.sprite.jump(JumpHeight)
        
    def boxesOptions(self, myBox, chois, Xas=None, trigerX=None): #--- triger on a given Xas --- triger if its off screen --- just remove the item --- remove if on a given Xas
        if chois == "SetOffScreen":
            if self.boxes[myBox].offScreen():
                self.boxes[myBox].x = Xas
        if chois == "RemOffScreen":
            if self.boxes[myBox].offScreen():
                self.boxes.pop(myBox)
        if chois == "Remove":
            self.boxes.pop(myBox)
        if chois == "SetXasIfXas":
            if self.boxes[myBox].x < trigerX:
                self.boxes[myBox].x = Xas
