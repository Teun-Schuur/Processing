
Mutation toine;
// ParalelSim sim = new ParalelSim();

void setup(){
  size(800, 800);
  frameRate(100);
  toine = new Mutation(20);
}

void draw(){
  background(255);
  if(toine.food <= 10){
    for(int i = 0; i<3; i++){
      toine.update();
    }
  }
  else{
    for(int i = 0; i<1000; i++){
      toine.update();
    }
  }
  toine.show();
  // sim.step();
}
//
//
// class ParalelSim{
//   int size;
//   int beginSize;
//   Mutation[] sim;
//   int[] lastPop;
//   int sum;
//   ParalelSim(){
//     size = 2000;
//     beginSize = 5;
//     sim = new Mutation[size];
//     lastPop = new int[size];
//     sum = 0;
//     for(int i = 0; i < size; i++){
//       sim[i] = new Mutation(beginSize);
//     }
//   }
//
//   void step(){
//     for(int i = 0; i < size; i++){
//       sim[i].update();
//     }
//     for(int i = 0; i < lastPop.length; i++){
//       sum += sim[i].creatures_history[99];
//       lastPop[i] = sim[i].creatures_history[99];
//     }
//     println(float(sum)/lastPop.length);
//   }
// }
