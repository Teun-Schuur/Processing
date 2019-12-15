from libraryTeun import *


slideR = None
slideG = None
slideB = None
slideH = None
button = None

def setup():
    global slideR, slideG, slideB, slideH, button
    size(500, 500)
    slideR = SlideBar(0, height-30, width/3, 30, 0, 255)
    slideG = SlideBar(width/3, height-30, width/3, 30, 0, 255)
    slideB = SlideBar(width/3*2, height-30, width/3, 30, 0, 255)
    slideH = SlideBar(0, height-60, width-30, 30, 0, 255)
    button = Button(width-30, width-60, 30, color(50, 30, 200))
    
    
def draw():
    background(0)
    fill(255, 0, 0)
    slideR.update()
    fill(0, 255, 0)
    slideG.update()
    fill(0, 0, 255)
    slideB.update()
    fill(255, 100)
    slideH.update()
    button.update()
    
    fill(slideR.getValue(), slideG.getValue(), slideB.getValue(), slideH.getValue())
    rect(0, 0, width, height-60)
    
    if button.getState():
        print(slideR.getValue(), slideG.getValue(), slideB.getValue(), slideH.getValue())
    
        
