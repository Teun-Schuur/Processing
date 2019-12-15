class ball:
    def __init__(self, beweging):
        self.bew = beweging
        self.x = random(0, width)
        self.y = random(0, height)
        self.collor = color(255, 255, 255)
        self.radis = 15
        
        
    def update(self):
        self.x += random(-self.bew, self.bew)
        self.y += random(-self.bew, self.bew)
        
        
    def fail(self, bal):
        # faildX = (bal.x <= self.x + self.radis) and (bal.x >= self.x)
        # faildY = (bal.y <= self.y + self.radis) and (bal.y >= self.y)
        faildX = self.x <= bal.x <= (self.x + self.radis)
        faildY = self.y <= bal.y <= (self.y + self.radis)
        
        if faildX and faildY:
            bal.collor = color(255, 0, 0)
            self.collor = color(255, 0, 0)
            return True

        else:
            self.collor = color(255, 255, 255)
            return False

    def removeBall(self):
        Xas = self.x > width - self.radis or self.x < 0
        Yas = self.y > height - self.radis or self.y < 0
        if Xas or Yas:
            return True
        else:
            return False


    def show(self):
        fill(self.collor)
        noStroke()
        rectMode(CENTER)
        rect(self.x, self.y, self.radis, self.radis)
