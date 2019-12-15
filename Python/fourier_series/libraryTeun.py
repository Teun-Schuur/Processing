class SlideBar:
    def __init__(self, x, y, w, h, minVal, maxVal):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.minVal = minVal
        self.maxVal = maxVal
        
        self.value = 2
        self.mouseXas = self.x+self.w/2
        
    def update(self):
        self.value, xAs = self._updateMouse()
        rect(self.x, self.y, self.w, self.h)
        push()
        fill(255, 0, 0)
        strokeWeight(5)
        line(self.x + self.w/20, self.y+self.h/2, xAs, self.y+self.h-self.h/2)
        pop()
        
    def getValue(self):
        return self.value
    
    def _updateMouse(self):
        if mousePressed:
            newX = mouseX
            newY = mouseY
            if self.x+self.w/20 <= newX <= self.x+self.w-self.w/20 and self.y <= newY <= self.y+self.h:
                self.mouseXas = newX
            return map(self.mouseXas, self.x+self.w/20, self.x+self.w-self.w/20, self.minVal, self.maxVal), self.mouseXas
        else:
            return self.value, self.mouseXas
        
    
class RadioHead:
    def __init__(self, x, y, w, h):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.state = False
        self.frame = frameCount
        self.canUpdate = True
    
    def getState(self):
        return self.state
    
    def update(self):
        if self.canUpdate and mousePressed and self.x <= mouseX <= self.x+self.w and self.y <= mouseY <= self.y+self.h:
            # if self.x <= mouseX <= self.x+self.w and self.y <= mouseY <= self.y+self.h:
            self.state = not self.state
            self.canUpdate = False
            self.frame = frameCount
            
        else:
            if frameCount - self.frame > 5:
                self.canUpdate = True
                
        rect(self.x, self.y, self.w, self.h)
        myColor = color(0, 255, 0, 155) if self.state else color(255, 0, 0, 155)
        push()
        fill(myColor)
        ellipse(self.x+self.w/2, self.y+self.h/2, self.w*0.7, self.h*0.7)
        pop()
            

class Button:
    def __init__(self, x, y, r, c):
        self.x = x
        self.y = y
        self.r = r
        self.state = False
        self.buttonColor = c
    
    def getState(self):
        return self.state
    
    def update(self):
        _color = self.buttonColor
        if mousePressed and self.x <= mouseX <= self.x+self.r and self.y <= mouseY <= self.y+self.r:
            self.state = True
            _color = getOpesetColor(red(self.buttonColor), green(self.buttonColor), blue(self.buttonColor))
        else:
            self.state = False
        push()
        fill(_color)
        #ellipseMode(CORNER)
        rect(self.x, self.y, self.r, self.r)
        pop()
        
class Plot:
    def __init__(self, x, y, w, h, Xas, Yas):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.Xas = Xas
        self.Yas = Yas
        self.data = [[]]
        self.colors = []
    
    def update(self, *agrs):
        self._draw_data()
        self._draw_canvas()
        for index, agr in enumerate(agrs):
            if 0 < agr < self.h:
                try:
                    self.data[index].append(agr)
                except IndexError:
                    self.data.append([])
                    self.data[index].append(agr)
            else:
                if agr >= self.h:
                    self.data[index].append(self.h)
                else:
                    self.data[index].append(0)
        
    def _draw_data(self):
        for lin in range(len(self.data)):
            for index, Yas in enumerate(self.data[lin]):
                if index > 0:
                    push()
                    stroke(color(255, 0, 0))
                    try:
                        line(self.x+index-1, self.y+self.h - self.data[lin][index-1], self.x+index, self.y+self.h - Yas)
                    except:
                        pass
                    pop()
            if len(self.data[lin]) > self.Xas:
                self.data[lin].pop(0)
        
    def _draw_canvas(self):
        line(self.x, self.y, self.x + self.w, self.y)
        line(self.x, self.y, self.x, self.y + self.h)
        line(self.x + self.w, self.y, self.x + self.w, self.y + self.h)
        line(self.x, self.y + self.h, self.x + self.w, self.y + self.h)

            
        
        


def getOpesetColor(R, G, B):
    R = abs(R-255)
    G = abs(G-255)
    B = abs(B-255)
    return color(R, G, B)

    
    
    
