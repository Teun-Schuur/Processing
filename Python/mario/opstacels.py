class Block:
    def __init__(self, setups):  # x, y, width, height, color, speed, removeIfOffscreen, halt
        self.x = setups[0]
        self.y = setups[1]
        self.secondX = setups[0] + setups[2] #not used
        self.secondY = setups[1] + setups[3] #not used
        self.Width = setups[2]
        self.Height = setups[3]
        self.Color = setups[4]
        self.speed = 2.
        self.halt = False#False if setups[-1] == None else True
        self.removeOffScreen = False if setups[-2] == None else True
    
    def show(self):
        push()
        fill(self.Color)
        rect(self.x, self.y, self.Width, self.Height)
        pop()
        
    def update(self):
        if not self.halt:
            self.x -= int(self.speed)
            self.secondX = self.x + self.Width
        
    def testPosition(self, sprite):
        hit = (self.x <= sprite.x <= self.secondX) and (self.y <= sprite.y + sprite.Mheight <= self.secondY)
        return [hit, self.y]
    
    def testToStop(self, sprite):
        return (self.x <= sprite.x+sprite.Mwidth <= self.x + 17) and (self.y <= sprite.y + sprite.Mheight/2 <= self.secondY)
    
    def offScreen(self):
        return self.secondX <= 0
