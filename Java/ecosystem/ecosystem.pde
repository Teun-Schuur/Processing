
Population toine;
void setup(){
  size(600, 600);
  frameRate(60);
  toine = new Population(50);
}

void draw(){
  background(255);
  toine.update();
  toine.show();
}


void calcMean(){
  int populations = 2000;
  Population[] toines = new Population[populations];
  int[] lastPop = new int[populations];
  int sum = 0;

  for(int i = 0; i < populations; i++){
    toines[i] = new Population(5);
  }
  for(int j = 0; j < 99*60; j++){
    for(int i = 0; i < populations; i++){
      toines[i].update();
    }
  }
  for(int i = 0; i < lastPop.length; i++){
    sum += toines[i].creatures_history[99];
    lastPop[i] = toines[i].creatures_history[99];
  }
  println(float(sum)/lastPop.length);
}
