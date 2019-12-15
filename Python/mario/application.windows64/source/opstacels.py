

class Block:
    def __init__(self, setups):  # x, y, width, height, color
        self.x = setups[0]
        self.y = setups[1]
        self.secondX = setups[0] + setups[2] #not used
        self.secondY = setups[1] + setups[3] #not used
        self.Width = setups[2]
        self.Height = setups[3]
        self.Color = setups[4]
        self.speed = 2
        
    
    def show(self):
        push()
        fill(self.Color)
        rect(self.x, self.y, self.Width, self.Height)
        pop()
        
        
    def update(self):
        # if not mario.x > self.x:
        self.x -= self.speed
        self.secondX = self.x + self.Width
        
    def testPosition(self, mario):
        # hitUp = False
        hit = self.x < mario.x < self.secondX and mario.y + mario.Mheight > self.y 
        #hit = self.x < mario.x < self.secondX and (self.y < mario.y+mario.Mheight < self.secondY)
        return [hit, self.y]
        
        
