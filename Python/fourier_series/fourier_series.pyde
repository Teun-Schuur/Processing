import libraryTeun as te

sliderOne = None
plot = None
index = 0
i = 0
n_times = 2


def setup():
    global sliderOne, buttonOne, plot
    size(1500, 500)
    frameRate(30)
    plot = te.Plot(75,75,1360,360,1360,360)
    sliderOne = te.SlideBar(20, 10, 1460, 50, 1, 50)
    updateClass()
    background(100)

def draw():
    global n_times
    background(100)
    sliderOne.update()
    n_times = floor(sliderOne.getValue())*2
    updateClass()


def updateClass():
    global index, i
    index += 0.01
    i += 0.02
    #newCos = 4/PI* ((cos(1*PI*i)/1) - (cos(3*PI*i)/3) + (cos(5*PI*i)/5) - (cos(7*PI*i)/7) + (cos(9*PI*i)/9) - (cos(11*PI*i)/11) + (cos(13*PI*i)/13) - (cos(15*PI*i)/15) + (cos(17*PI*i)/17))

    plot.update(map(getCos(n_times, i), -1.5, 1.5, 0, 360))


def getCos(n, i):
    z = 1
    sums = []
    sum = 0
    for _ in range(n):
        sums.append(cos(z*PI*i)/z)
        z += 2
    for x in range(0, n, 2):
        sum += sums[x] - sums[x+1]
    return 4/PI*sum
