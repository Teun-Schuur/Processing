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

public void setup(){
  
  frameRate(100);
  toine = new Mutation(4, 1f);
}

public void draw(){
  background(255);
  for(int i = 0; i<3; i++){
    toine.update();
  }
  toine.show();
}


public void calcMean(){
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
  println(PApplet.parseFloat(sum)/lastPop.length);
}


class Creature{
  static final float MUTATE_CHANGE = 0.1f;
  static final float BEGIN_ENERGY = 1800;
  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector destination;
  float size;
  float speed;
  float energy = BEGIN_ENERGY;
  float food = 0;
  boolean home = false;
  boolean goingHome = false;

  Creature(PVector location){
    this.location = location;
    this.velocity = new PVector(0, 0);
    this.destination = new PVector(0, 0);
    this.newDestination();
    this.acceleration = new PVector(0, 0);
    this.size = 50;
  }

  public void update(){
    this.velocity.add(this.acceleration);
    if(goingHome){
      this.velocity.limit(speed*1.8f+1);
    }else if(home){
      this.velocity.limit(0);
    }else{
      this.velocity.limit(speed);
    }
    this.location.add(this.velocity);
    this.toLocation();
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
    if(this.location.dist(this.destination) < 30){
      newDestination();
    }
    PVector dir = PVector.sub(this.destination, this.location);
    dir.normalize();
    dir.mult(0.9f);
    this.acceleration = dir;
  }

  private void newDestination(){
    if(food>=1){
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
    }else{
      do{
        int distance = (int) random(50, 300);
        this.destination = new PVector(random(this.location.x-distance, this.location.x+distance), random(this.location.y-distance, this.location.y+distance));
      }while((destination.x>=width-size||destination.x<=0)||(destination.y>=height-size||destination.y<=0));
    }
  }

  public Creature mutate(){
    Creature child = new Creature(destination);
    if(random(1)<0.5f){
      child.speed = speed + random(Creature.MUTATE_CHANGE);
    }else{
      child.speed = speed - random(Creature.MUTATE_CHANGE);
    }
    // println("child: ", child.speed, "  parend: ", this.speed, "  same?: ", child==this);
    return child;
  }

  public void checkEdges(){
    if (location.x+size > width) {
      home = true;
    } else if (location.x < 0) {
      home = true;
    }

    if (location.y+size > height) {
      home = true;
    }  else if (location.y < 0) {
      home = true;
    }
    if(home){
      goingHome = false;
    }
  }

  public void energyCalculation(){
    if(!home){
      this.energy -= speed*speed+1;
    }
  }

  public void foundFoot(){
    food++;
  }

  public void show(float max){
    fill(map(speed, 0, max, 20, 255), 0, map(speed, 0, max, 20, 255));
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
/*
2500 limit
*/

class Mutation{
  static final int ONE_DAY = 10*100; // one day = 4 seconds
  static final int TIME_STEP = (int) (2000 / ONE_DAY); // 60000 times a day
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  ArrayList<PVector> foods = new ArrayList<PVector>();
  long frame = 0;
  int food = 100;
  Creature fastest;

  Mutation(int begin_pop, float begin_speed){
    for(int i = 0; i < begin_pop; i++){
      Creature c = new Creature(new PVector(random(50, width-50), random(50, height-50)));
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
      if(fastest==null || fastest.speed < c.speed){
        fastest = c;
      }
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
          // println("death by to low energy");
        }
      }
    }

    if(frame%ONE_DAY==0){
      // if((5 < food) && (food < 400)){
      //   food -= 1;
      // }else if(food >= 400){
      //   food -= 5;
      // }
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
      c.energy = Creature.BEGIN_ENERGY;
      c.newLocation();
      c.newDestination();
      c.home = false;
      prfC = c;
    }
    println("food: ", food, "size: ", cSize, "\tmean speed: ", speedSum/cSize, "\tmean energy: ", energySum/cSize);
  }
}


//  creatures.remove(i);
//  creatures.add(i);
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
      c.update();
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
