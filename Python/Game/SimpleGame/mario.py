class Mario:
    def __init__(self, x, y, w, h, mass=None):
        self.Mheight = w
        self.Mwidth = h
        self.y = y
        self.x = x
        self.velocity = 0
        self.mass = map(sqrt(w * h), 0, 200, 0.5, 2) if mass == None else mass
        self.force = 0.5
        self.canJump = False
        
    def show(self, MyColor):
        push()
        fill(MyColor)
        rect(self.x, self.y, self.Mwidth, self.Mheight)
        pop()
    
    
    def jump(self, JumpHeight):
        if self.canJump:
            self.velocity = -JumpHeight
            self.y += self.velocity / self.mass
        
    
    def update(self, hit):
        if hit[0] and self.velocity > 0:
            self.velocity = 0
            self.y = hit[1] - self.Mheight
            self.canJump = True
        elif hit[0] and self.velocity == 0:
            self.velocity = 0

        else:
            self.canJump = False
            if self.velocity > 17:
                self.velocity = 17
            else:
                self.velocity += self.mass * self.force
            self.y += self.velocity / self.mass


        
        
        
