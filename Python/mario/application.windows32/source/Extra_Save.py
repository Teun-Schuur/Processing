from enamys import *
from extras import *
from mario import *
from opstacels import *

move = 0
PreOpjects = []

opjects = []
mario = None

hit = False

def setup():
    global PreOpjects, mario
    size(1500, 1200)
    frameRate(200)
    
    mario = Mario()
    PreOpjects = [[-width*2, height - 50, width*4, height, color(45, 48, 38)],
                  [width-100, height-100, width*4, 5, color(40)]]
    
    for opject in PreOpjects:
        opjects.append(Block(opject))
        

def draw():
    global move, hit
    background(26, 167, 212)
    hit = [False, None]
    for opject in opjects:
        opject.update()
        if not hit[0]:
            hit = opject.testPosition(mario)
        opject.show()
        
    mario.update(move, hit)
    mario.show()
    print move, opjects[0].x,(mario.x, mario.y, mario.velocity)
    move = 0
    



def keyPressed():
    global move
    if key == " ":
        move = 1
    
    
    
    
    
    
    
