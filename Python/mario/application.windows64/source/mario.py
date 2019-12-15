class Mario:
    def __init__(self):
        self.Mheight = 50
        self.Mwidth = 20
        self.y = height - (self.Mheight + 900)
        self.x = width/3
        self.velocity = 0
        self.mass = 0.7
        self.force = 1.1
        self.uper = [self.x, self.y, self.x + self.Mwidth, self.y+5]
        self.under = [self.x, self.y + self.Mheight, self.x + self.Mwidth, self.y + self.Mheight-5]
        self.richter = [self.x + self.Mwidth-5, self.y, self.x + self.Mwidth, self.y + self.Mheight]
        
        
    def show(self):
        push()
        fill(0)
        rectMode(CORNER)
        rect(self.x, self.y, self.Mwidth, self.Mheight)
        pop()
    
    
    def update(self, jump, hit):
        self.uper = [self.x, self.y, self.x + self.Mwidth, self.y+5]
        self.under = [self.x, self.y + self.Mheight, self.x + self.Mwidth, self.y + self.Mheight-5]
        self.richter = [self.x + self.Mwidth-5, self.y, self.x + self.Mwidth, self.y + self.Mheight]

        if hit[0]:
            self.velocity = 0
            self.y = hit[1] - self.Mheight
            if jump:
                self.velocity = -10
                self.y += self.velocity / self.mass
            
            
        else:
            if self.velocity > 20:
                self.velocity = 10
            else:
                self.velocity += self.mass * self.force
            self.y += self.velocity / self.mass


        
        
        
