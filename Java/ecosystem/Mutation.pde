

class Mutation{
  static final int ONE_DAY = 10*100; // one day = 4 seconds
  static final int TIME_STEP = (int) (2000 / ONE_DAY); // 60000 times a day
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  ArrayList<PVector> foods = new ArrayList<PVector>();
  long frame = 0;
  int food = 100;
  Creature fastest;

  Mutation(int begin_pop){
    for(int i = 0; i < begin_pop; i++){
      Creature c = new Creature(new PVector(random(50, width-50), random(50, height-50)));
      creatures.add(c);
    }
    // food
    makeNiewFood();
  }

  public void update(){
    frame++;
    for(int i = 0; i < creatures.size(); i++){
      Creature c = creatures.get(i);
      c.update(foods.toArray(new PVector[foods.size()]));
      c.checkEdges();
      if(fastest==null || fastest.speed < c.speed){
        fastest = c;
      }
      if(c.food <= 1){
        for(int j = foods.size() - 1; j >= 0; j--){
          if(foods.get(j).dist(PVector.add(c.location, new PVector(c.size/2, c.size/2))) < c.size){
            c.foundFoot();
            foods.remove(j);
          }
        }
      }
      if(frame%TIME_STEP==0){
        c.energyCalculation();
        if(c.energy <= 0){
          creatures.remove(i);
          // println("death by to low energy");
        }
      }
    }

    if(frame%ONE_DAY==0){
      if((10 < food) && (food < 400)){
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
      creatures.get(i).show(fastest.speed);
    }
    for(PVector food : foods){
      push();
      fill(0, 255, 0);
      ellipse(food.x, food.y, 15, 15);
      pop();
    }
  }

  private void makeNiewFood(){
    foods.clear();
    for(int i = 0; i < food; i++){
      foods.add(new PVector(random(Creature.BEGIN_SIZE+5, width-(Creature.BEGIN_SIZE+5)), random(Creature.BEGIN_SIZE+5, height-(Creature.BEGIN_SIZE+5))));
    }
  }

  private void evolution(){
    float speedSum = 0f;
    float energySum = 0f;
    float senseSum = 0f;
    int cSize = creatures.size();
    Creature prfC = creatures.get(0);
    for(int j = creatures.size() - 1; j >= 0; j--){
      Creature c = creatures.get(j);
      speedSum += c.speed;
      senseSum += c.sense;
      energySum += c.energy;
      if(c.food <= 0){
        creatures.remove(j);
      }
      if(c.food >= 2){
        creatures.add(c.mutate());
      }
      c.food = 0;
      c.energy = Creature.BEGIN_ENERGY;
      c.newLocation();
      c.newDestination(foods.toArray(new PVector[foods.size()]));
      c.home = false;
      prfC = c;
    }
    println("food: ", food, "size: ", creatures.size(), "\tmean speed: ", speedSum/cSize, "\tmean sense: ", senseSum/cSize, "\tmean energy: ", energySum/cSize);
  }
}
