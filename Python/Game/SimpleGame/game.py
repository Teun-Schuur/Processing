from mario import *
from opstacels import *



class PlayGame:
    def __init__(self, setings):
        self.setings = setings
        self.respanBoxSettings = []
        boxes = []
        for boxy in setings["boxes"]:
            boxes.append([setings["boxes"][boxy]["x"],
                          setings["boxes"][boxy]["y"],
                          setings["boxes"][boxy]["width"],
                          setings["boxes"][boxy]["height"],
                          setings["boxes"][boxy]["color"],
                          setings["spriteSpeed"],
                          setings["boxes"][boxy]["halt"],
                          ])
            if "respan" in setings["boxes"][boxy]:
                self.respanBoxSettings.append(setings["boxes"][boxy]["respan"])
            else:
                self.respanBoxSettings.append(None)
            
        self.game = Game(boxes, speed=setings["spriteSpeed"], massa=setings["spriteMass"], 
                         forse=setings["spriteForse"], spriteColor=setings["spriteColor"], 
                         spriteSetings=[setings["spriteHeight"], setings["spriteWidth"]])
        frameRate(100 if setings["frameRate"]==None else setings["frameRate"])
        smooth()



    def play(self):
        background(self.setings["backGround"])
        self.game.update()
        self.game.show()
        self._respan()
            
            
        if keyPressed:
            if key == " ":
                self.game.SpriteJump(self.setings["spriteJumpVelocity"])
            if key == "q":
                return True
        return False
    
    
    def _respan(self):

        for i in range(len(self.game.boxes)):
            if not self.respanBoxSettings[i] == None:
                self.game.boxesOptions(i, "SetXasIfXas", self.respanBoxSettings[i][0], self.respanBoxSettings[i][1])
            
            else:
                if self.game.boxesOptions(i, "RemOffScreen"):
                    self.respanBoxSettings.pop(i)
                    break
                
            
            



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
            
    def addBox(self, newBox):
        self.boxes.append(newBox)
        
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
        
    def boxesOptions(self, myBox, chois, trigerX=None, Xas=None): #--- triger on a given Xas --- triger if its off screen --- just remove the item --- remove if on a given Xas
        if chois == "RemOffScreen":
            if self.boxes[myBox].offScreen():
                self.boxes.pop(myBox)
                return True
            else:
                return False
        if chois == "SetXasIfXas":
            if self.boxes[myBox].x < trigerX:
                self.boxes[myBox].x = Xas
