"""
TODO:
    - update function moet werken
    - het moet het level trerug sturen en of het een edit word of een Play
    - moet worden versimpeld
"""


class Main:
    def __init__(self):
        self.levelOne = [150, 200, 350, 100]
        self.levelTwo = [650, 200, 350, 100]
        self.playButton = [300, 600, 200, 100]
        self.editButton = [700, 600, 200, 100]
        self.selectLevel = None
        self.selectPlayOrEdit = None
        
    
    def show(self):
        background(66)
        textSize(100)
        fill(200, 50, 0)
        
        push()
        fill(255) if self.mouse() == 1 else fill(200, 50, 0)
        rect(self.levelOne[0], self.levelOne[1], self.levelOne[2], self.levelOne[3])
        pop()
        push()
        fill(255) if self.mouse() == 2 else fill(200, 50, 0)
        rect(self.levelTwo[0], self.levelTwo[1], self.levelTwo[2], self.levelTwo[3])
        pop()
        
        rect(self.playButton[0], self.playButton[1], self.playButton[2], self.playButton[3])
        rect(self.editButton[0], self.editButton[1], self.editButton[2], self.editButton[3]) 
        fill(0) 
        text("Level 1", self.levelOne[0], self.levelOne[1] + self.levelOne[3])
        text("Level 2", self.levelTwo[0], self.levelTwo[1] + self.levelTwo[3])
        text("Play", self.playButton[0], self.playButton[1] + self.playButton[3])
        text("Edit", self.editButton[0], self.editButton[1] + self.editButton[3])
    
    def update(self):
        value = self.mouse()
        if value:
            if self.selectLevel == None and value == 1 or value == 2:
                self.selectLevel = value
            elif self.selectPlayOrEdit == None and value == 3 or value == 4:
                self.selectPlayOrEdit = value
        if self.selectLevel != None and self.selectPlayOrEdit != None:
            return [self.selectLevel, self.selectPlayOrEdit]
            
    
    
    def mouse(self):
        if mousePressed:
            if self.levelOne[0] < mouseX < self.levelOne[0]+self.levelOne[2] and self.levelOne[1] < mouseY < self.levelOne[1]+self.levelOne[3]:
                return 1
            if self.levelTwo[0] < mouseX < self.levelTwo[0]+self.levelTwo[2] and self.levelTwo[1] < mouseY < self.levelTwo[1]+self.levelTwo[3]:
                return 2
            if self.playButton[0] < mouseX < self.playButton[0]+self.playButton[2] and self.playButton[1] < mouseY < self.playButton[1]+self.playButton[3]:
                return 3
            if self.editButton[0] < mouseX < self.editButton[0]+self.editButton[2] and self.editButton[1] < mouseY < self.editButton[1]+self.editButton[3]:
                return 4
        else:
            return False
