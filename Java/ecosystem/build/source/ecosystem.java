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
  
  frameRate(60);
  toine = new Mutation(5, 2f);
}

public void draw(){
  background(255);
  for(int i = 0; i<2; i++){
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
  static final float MUTATE_CHANGE = 0.3f;
  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector destination;
  float size;
  float speed;
  float energy = 1;
  int food = 0;
  // int death_age = round(60*random(5, 20));

  Creature(PVector location){
    this.location = location;
    this.velocity = new PVector(0, 0);
    this.destination = new PVector(0, 0);
    this.newDestination();
    this.acceleration = new PVector(0, 0);
    this.size = 30;
  }

  public void update(){
    this.velocity.add(this.acceleration);
    this.velocity.limit(speed);
    this.location.add(this.velocity);
    this.toLocation();
    this.energyCalculation();
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
    do{
      int distance = (int) random(50, 300);
      this.destination = new PVector(random(this.location.x-distance, this.location.x+distance), random(this.location.y-distance, this.location.y+distance));
    }while((destination.x>=width-size||destination.x<=0)||(destination.y>=height-size||destination.y<=0));
  }

  public Creature mutate(){
    Creature child = new Creature(this.destination);
    if(random(1)<0.5f){
      child.speed = speed + Creature.MUTATE_CHANGE;
    }else{
      child.speed = speed - Creature.MUTATE_CHANGE;
    }
    // println("child: ", child.speed, "  parend: ", this.speed, "  same?: ", child==this);
    return child;
  }

  public void checkEdges(){
    if (location.x+size > width) {
      location.x = width-size;
    } else if (location.x < 0) {
      location.x = 1;
    }

    if (location.y+size > height) {
      location.y = height-size;
    }  else if (location.y < 0) {
      location.y = 1;
    }
  }

  private void energyCalculation(){
    this.energy -= this.speed/Mutation.ONE_DAY;
  }

  public void foundFoot(){
    energy++;
    food++;
  }

  public void show(){
    fill(map(speed, 0, 6, 20, 255), 0, map(speed, 0, 6, 20, 255));
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
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
      creatures.get(i).show();
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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ecosystem" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
