/*
2500 limit
*/

class Mutation{
  static final int ONE_DAY = 4*60; // one day = 4 seconds
  static final int TIME_STEP = 600 / ONE_DAY; // 4 times per second
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  ArrayList<PVector> foods = new ArrayList<PVector>();
  int[] creatures_history = new int[100];
  long frame = 0;
  int food = 80;

  Mutation(int begin_pop, float begin_speed){
    for(int i = 0; i < begin_pop; i++){
      Creature c = new Creature(new PVector(random(width), random(height)));
      c.speed = begin_speed;
      creatures.add(c);
    }
    // food
    makeNiewFood();
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
      if(frame%TIME_STEP==0){
        c.energyCalculation();
        if(c.energy <= 0){
          creatures.remove(i);
          println("death by to low foot");
        }
      }
    }

    if(frame%ONE_DAY==0){
      if((5 < food) && (food < 400)){
        food -= 1;
      }else if(food >= 400){
        food -= 5;
      }
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
    for(int i = 0; i < food; i++){
      foods.add(new PVector(random(20, width-20), random(20, height-20)));
    }
  }

  private void evolution(){
    float speedSum = 0f;
    float energySum = 0f;
    int cSize = creatures.size();
    Creature prfC = creatures.get(0);
    for(int j = creatures.size() - 1; j >= 0; j--){
      Creature c = creatures.get(j);
      speedSum += c.speed;
      energySum += c.energy;
      if(c.food <= 0){
        creatures.remove(j);
      }
      if(c.food >= 2){
        creatures.add(c.mutate());
      }
      c.food = 0;
      c.energy = 800;
      prfC = c;
    }
    println("food: ", food, "size: ", cSize, "\tmean speed: ", speedSum/cSize, "\tmean energy: ", energySum/cSize);
  }
}


//  creatures.remove(i);
//  creatures.add(i);
