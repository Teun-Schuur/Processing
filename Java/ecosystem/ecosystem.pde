
Mutation toine;

void setup(){
  fullScreen();
  frameRate(60);
  toine = new Mutation(5, 2f);
}

void draw(){
  background(255);
  for(int i = 0; i<2; i++){
    toine.update();
  }
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
