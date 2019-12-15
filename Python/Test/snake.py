class Snake:
    def __init__(self, sceal, window):
        self.body = []
        for i in range(15):
            self.body.append([i*sceal, window*sceal/2])
        self.recelutie = sceal
        self.window = window
        self.show()
    
    
    def show(self):
        for i in range(len(self.body)-1):
            line(self.body[i][0], self.body[i][1], self.body[i+1][0], self.body[i+1][1])
        
        
    def eatFood(self, pointing):
        if (pointing[0] == self.body[len(self.body)-1][0]) and (pointing[1] == self.body[len(self.body)-1][1]):
            self.body.insert(0, [self.body[len(self.body)-1][0], self.body[len(self.body)-1][1]])
            return True
        
        
    def update(self, diraction):
        for i in range(len(self.body)-1):
            self.body[i][0] = self.body[i+1][0]
            self.body[i][1] = self.body[i+1][1]
            
        # for i in range(len(self.body)-1):
        #     self.body[i][0] += diraction[0]*self.recelutie
        #     self.body[i][1] += diraction[1]*self.recelutie
        
        if diraction == [-1, 0]:   # up
            self.body[len(self.body)-1][0] = self.body[len(self.body)-2][0] 
            self.body[len(self.body)-1][1] = self.body[len(self.body)-2][1] - self.recelutie
        if diraction == [1, 0]:    # douwn
            self.body[len(self.body)-1][0] = self.body[len(self.body)-2][0]
            self.body[len(self.body)-1][1] = self.body[len(self.body)-2][1] + self.recelutie
        if diraction == [0, -1]:   # left
            self.body[len(self.body)-1][0] = self.body[len(self.body)-2][0] - self.recelutie
            self.body[len(self.body)-1][1] = self.body[len(self.body)-2][1]
        if diraction == [0, 1]:    # right
            self.body[len(self.body)-1][0] = self.body[len(self.body)-2][0] + self.recelutie
            self.body[len(self.body)-1][1] = self.body[len(self.body)-2][1]
    

    def checkSnakeOfHitOwnBody(self):
        head = [self.body[len(self.body)-1][0], self.body[len(self.body)-1][1]]
        for i in range(len(self.body)-1):
            if head[0] == self.body[i][0] and head[1] == self.body[i][1]:
                return True
    
    
    def snakeIsDead(self):
        if (
            self.body[len(self.body)-1][0] > width or
            self.body[len(self.body)-1][0] < 0 or
            self.body[len(self.body)-1][1] > height or
            self.body[len(self.body)-1][1] < 0 or
            self.checkSnakeOfHitOwnBody()):
            push()
            textSize(185)
            fill(255, 0, 0)
            text("Game Over", 100, height/2)
            pop()
            return True
        
    
    
    
