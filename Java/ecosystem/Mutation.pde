/*
2500 limit
*/

class Mutation{
  static final int ONE_DAY = 5*60; // one day = 10 seconds
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  ArrayList<PVector> foods = new ArrayList<PVector>();
  int[] creatures_history = new int[100];
  long frame = 0;

  Mutation(int begin_pop, float begin_speed){
    for(int i = 0; i < begin_pop; i++){
      Creature c = new Creature(new PVector(random(width), random(height)));
      c.speed = begin_speed;
      creatures.add(c);
    }
    // food
    for(int i = 0; i < 100; i++){
      foods.add(new PVector(random(width), random(height)));
    }
  }

  public void update(){
    frame++;
    for(int i = 0; i < creatures.size(); i++){
      Creature c = creatures.get(i);
      c.update();
      c.checkEdges();
      for(int j = foods.size() - 1; j >= 0; j--){
        if(foods.get(j).dist(PVector.add(c.location, new PVector(c.size/2, c.size/2))) < c.size){
          c.foundFoot();
          foods.remove(j);
        }
      }
    }
    if(frame%ONE_DAY==0){
      makeNiewFood();
      evolution();
    }
  }

  public void show(){
    for(int i = 0; i < creatures.size(); i++){
      creatures.get(i).show();
    }
    for(PVector food : foods){
      push();
      fill(0, 255, 0);
      ellipse(food.x, food.y, 5, 5);
      pop();
    }
  }

  private void makeNiewFood(){
    foods.clear();
    for(int i = 0; i < 100; i++){
      foods.add(new PVector(random(20, width-20), random(20, height-20)));
    }
  }

  private void evolution(){
    float speedSum = 0f;
    int cSize = creatures.size();
    Creature prfC = creatures.get(0);
    for(int j = creatures.size() - 1; j >= 0; j--){
      Creature c = creatures.get(j);
      if(c.location == prfC.location){
        println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
      }
      speedSum += c.speed;
      if(c.food == 0){
        creatures.remove(j);
      }
      if(c.food >= 2){
        creatures.add(c.mutate());
      }
      c.food = 0;
      c.energy = 1;
      prfC = c;
    }
    println("size: ", cSize, "\tmean speed: ", speedSum/cSize);
  }
}


//  creatures.remove(i);
//  creatures.add(i);
