import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ecosystem extends PApplet {


Mutation toine;
ParalelSim sim = new ParalelSim();

public void setup(){
  
  frameRate(100);
  toine = new Mutation(20);
}

public void draw(){
  background(255);
  for(int i = 0; i<3; i++){
    toine.update();
  }
  toine.show();
  sim.step();
}


class ParalelSim{
  int size;
  int beginSize;
  Mutation[] sim;
  int[] lastPop;
  int sum;
  ParalelSim(){
    size = 2000;
    beginSize = 5;
    sim = new Mutation[size];
    lastPop = new int[size];
    sum = 0;
    for(int i = 0; i < size; i++){
      sim[i] = new Mutation(beginSize);
    }
  }

  public void step(){
    for(int i = 0; i < size; i++){
      sim[i].update();
    }
    for(int i = 0; i < lastPop.length; i++){
      sum += sim[i].creatures_history[99];
      lastPop[i] = sim[i].creatures_history[99];
    }
    println(PApplet.parseFloat(sum)/lastPop.length);
  }
}


class Creature{
  static final float MUTATE_CHANGE = 0.4f;
  static final float BEGIN_ENERGY = 1800;
  static final int BEGIN_SIZE = 50;
  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector destination;
  float size;
  float speed;
  float sense;
  float energy = BEGIN_ENERGY;
  float food = 0;
  boolean home = false;
  boolean goingHome = false;

  Creature(PVector location){
    this.location = location;
    this.velocity = new PVector(0, 0);
    this.acceleration = new PVector(0, 0);
    this.size = Creature.BEGIN_SIZE;
    this.sense = 1;
    this.speed = 1;
    float tempX = floor(random(1, (width - size) / size)) * size;
    float tempY = floor(random(1, (width - size) / size)) * size;
    this.destination = new PVector(tempX, tempY);
  }

  public void update(PVector[] foods){
    if(destination == null || PVector.add(this.location, new PVector(size/2, size/2)).dist(this.destination) < size/2){
      newDestination(foods);
    }
    setDestinationSense(foods);
    newDestination();
    toLocation();
    this.velocity.add(this.acceleration);
    if(goingHome){
      this.velocity.limit(speed*1.8f+1);
    }else if(home){
      this.velocity.limit(0);
    }else{
      this.velocity.limit(speed);
    }
    this.location.add(this.velocity);
  }

  public void newLocation(){
    if(location.x > width/2){
      this.location = new PVector(width-(size+2), this.location.y);
    }else if(location.x <= width/2){
      this.location = new PVector(1, this.location.y);
    }if(location.y > height-height/3){
      this.location = new PVector(this.location.x, height-(size+2));
    }else if(location.y <= height/3){
      this.location = new PVector(this.location.x, 1);
    }
  }

  private void toLocation(){
    PVector dir = PVector.sub(this.destination, this.location);
    dir.normalize();
    dir.mult(0.9f);
    this.acceleration = dir;
  }

  private void newDestination(PVector[] foods){
    float tempX = floor(random(1, (width - size) / size)) * size;
    float tempY = floor(random(1, (width - size) / size)) * size;
    this.destination.set(tempX, tempY, 0);
  }

  private void setDestinationSense(PVector[] foods){
    for(int i = 0; i < foods.length; i++){
      if (foods[i] != null && location.dist(foods[i]) <= sense*size){
        this.destination = foods[i];
        break;
      }
    }
  }

  private void newDestination(){
    if(food>=2){
      goingHome = true;
      if(location.x > width/2){
        this.destination = new PVector(width, this.location.y);
      }else if(location.x <= width/2){
        this.destination = new PVector(0, this.location.y);
      }if(location.y > height-height/3){
        this.destination = new PVector(this.location.x, height);
      }else if(location.y <= height/3){
        this.destination = new PVector(this.location.x, 0);
      }
    }
  }

  public Creature mutate(){
    Creature child = new Creature(destination);
    if(random(1)<0.5f){
      child.speed = speed + random(Creature.MUTATE_CHANGE);
      child.sense = sense + random(Creature.MUTATE_CHANGE);
    }else{
      child.speed = speed - random(Creature.MUTATE_CHANGE);
      child.sense = sense - random(Creature.MUTATE_CHANGE);
    }
    // println("child: ", child.speed, "  parend: ", this.speed, "  same?: ", child==this);
    return child;
  }

  public void checkEdges(){
    if (location.x+size > width) {
      location.x = width-size;
      home = true;
    } else if (location.x < 0) {
      destination.y = location.y;
      location.x = 0;
      home = true;
    }

    if (location.y+size > height) {
      location.y = height-size;
      home = true;
    }  else if (location.y < 0) {
      location.y = 0;
      destination.x = location.x;
      home = true;
    }
    if(home){
      goingHome = false;
    }
  }

  public void energyCalculation(){
    if(!home){
      this.energy -= speed*speed + sense;
    }
  }

  public void foundFoot(){
    food++;
  }

  public void show(float max){
    fill(map(speed, 0, max+1, 20, 255), 0, map(speed, 0, max+1, 20, 255));
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}


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
/*
2500 limit
*/

class Population{
  static final int TIME_STEP = 60; // 60 / 15 = 4 times per second
  int timeSteps = 0;
  float spontaneous_birth_rate;
  float death_rate;
  float replication_rate;
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  int[] creatures_history = new int[100];

  Population(int begin_pop){
    for(int i = 0; i < begin_pop; i++){
      creatures.add(new Creature(new PVector(random(width), random(height))));
    }
    spontaneous_birth_rate = 0f; // x% chance per second for spaning a nieuw creature
    death_rate = 0.2f;           // x% chance per second for despaning a creature
    replication_rate = 0.3f;     // x% chance to replicate a creature per second
  }

  public void update(){
    timeSteps++;
    for(int i = 0; i < creatures.size(); i++){
      Creature c = creatures.get(i);
      //c.update();
      c.checkEdges();
    }
    if(timeSteps % Population.TIME_STEP == 0){
      creatures_history[(int) round(timeSteps/60)] = creatures.size();
      evolution();
      float delta = spontaneous_birth_rate + (replication_rate - death_rate) * creatures.size();
      println(delta);
    }
  }

  public void show(){
    for(int i = 0; i < creatures.size(); i++){
      creatures.get(i).show(3f);
    }
  }

  private void evolution(){
    for(int i = creatures.size() - 1; i >= 0; i--){
      Creature c = creatures.get(i);
      // spaning
      if(random(1) < replication_rate){
        creatures.add(new Creature(new PVector(c.location.x, c.location.y)));
      }
      // despaning
      if(random(1) < death_rate){
        creatures.remove(i);
      }
    }
    if(random(0, 1) < spontaneous_birth_rate){
      this.creatures.add(new Creature(new PVector(random(width), random(height))));
    }
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ecosystem" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
