from ball import *


balls = []
doodeBalls = []


def setup():
    global balls
    fullScreen(-500)

    frameRate(60)
    for i in range(100):
        balls.append(ball(1))
        
        
def draw():
    global doodeBalls
    background(0)
    
    # for bally in balls:
        
        
        
    #     for other in balls:
    #         #if bally != other:
    #             bally.fail(other)

    #     bally.update()       
    #     bally.show()
    

    
    for i in range(len(balls)):
        for j in range(len(balls)):
            if balls[i] != balls[j]:
                if balls[i].fail(balls[j]):
                    #balls[i].collor = color(255, 0, 0)
                    pass

                    break
                else:
                    #balls[i].collor = color(255, 255, 255)
                    pass

              
        balls[i].show()
        balls[i].update() 
        
        if balls[i].removeBall():
            if not i in doodeBalls:
                doodeBalls.append(i)

                
    
    
    for q in range(len(doodeBalls)):
        try:
            print(doodeBalls)
            balls.pop(doodeBalls[q])
        except IndexError as err:
             print(err)
        
    doodeBalls = []

            
            
