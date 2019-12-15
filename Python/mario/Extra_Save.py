from enamys import *
from extras import *
from mario import *
from opstacels import *


PreOpjects = []

opjects = []
mario = None

hit = [False, None]

def setup():
    global PreOpjects, mario
    size(1500, 1200)
    frameRate(60)
    
    mario = Mario(width/3, height - 900, 20, 20)
    
    PreOpjects = [[-width*2, height - 50, width*9, height, color(45, 48, 38)],
                  [1000, height-200, 200, 50, color(0)],
                  [1300, height-400, 150, 50, color(20)],
                  [1550, height-600, 250, 50, color(40)],
                  [1970, height-300, 150, 50, color(60)]]
    
    for opject in PreOpjects:
        opjects.append(Block(opject))
        

def draw():
    global hit
    background(26, 167, 212)
    hit[0] = False
    for opject in opjects:
        opject.update()
        if not hit[0]:
            hit = opject.testPosition(mario)
        opject.show()
        
    mario.update(hit)
    mario.show()
    push()
    fill(255, 0, 0)
    rect(0, 0, 20, 20)
    pop()



def keyPressed():
    if key == " ":
        mario.jump()
    

def mouseClicked():
    if 0 < mouseX < 20 and 0 < mouseY < 20:
        background(250) 

    
    
    
    
    
    
