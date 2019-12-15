/*
2500 limit
*/

class Mutation{
  static final int ONE_DAY = 10*60; // one day = 10 seconds
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  PVector[] foods = new PVector[20];
  int[] creatures_history = new int[100];

  Mutation(int begin_pop, float begin_speed){
    for(int i = 0; i < begin_pop; i++){
      creatures.add(new Creature(new PVector(random(width), random(height)), begin_speed));
    }
    for(int i = 0; i < foods.length; i++){
      foods[i] = new PVector(random(width), random(height));
    }
  }

  public void update(){
    for(int i = 0; i < creatures.size(); i++){
      Creature c = creatures.get(i);
      c.update();
      c.checkEdges();
    }
    if(frameRate%ONE_DAY==0){
      evolution();
    }
  }

  public void show(){
    for(int i = 0; i < creatures.size(); i++){
      creatures.get(i).show();
    }
    for(int i = 0; i < foods.length; i++){
      push();
      fill(0, 255, 0);
      ellipse(foods[i].x, foods[i].y, 10, 10);
      pop();
    }
  }

  private void evolution(){
    for(int i = creatures.size() - 1; i >= 0; i--){
      Creature c = creatures.get(i);
    }
  }
}


//  creatures.remove(i);
//  creatures.add(i);
