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


Population toine;
public void setup(){
  
  frameRate(60);
  toine = new Population(5);
}

public void draw(){
  background(255);
  toine.update();
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
  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector destination;
  float size;
  float topspeed;
  // int age = 0;
  // int death_age = round(60*random(5, 20));

  Creature(PVector location){
    this.location = location;
    this.velocity = new PVector(0, 0);
    this.destination = new PVector(random(this.location.x-50, this.location.x+50), random(this.location.y-50, this.location.y+50));
    this.acceleration = new PVector(0, 0);
    size = 30;
    topspeed = 2.0f;
  }

  public void update(){
    // age++;
    this.toLocation();
    this.velocity.add(this.acceleration);
    this.velocity.limit(topspeed);
    this.location.add(this.velocity);
  }

  private void toLocation(){
    if(this.location.dist(this.destination) < 10){
      do{
        int distance = (int) random(50, 300);
        this.destination = new PVector(random(this.location.x-distance, this.location.x+distance), random(this.location.y-distance, this.location.y+distance));
      }while(destination.x>=width-size||destination.x<=0||destination.y>=height-size||destination.y<=0);
    }
    PVector dir = PVector.sub(this.destination, this.location);
    dir.normalize();
    dir.mult(0.6f);
    this.acceleration = dir;
  }

  public void checkEdges(){
    if (location.x > width) {
      location.x = width;
    } else if (location.x < 0) {
      location.x = 0;
    }

    if (location.y+size > height) {
      location.y = height-size;
    }  else if (location.y < 0) {
      location.y = 0;
    }
  }

  public void show(){
    fill(200, 0, 220);
    rect(this.location.x, this.location.y, this.size, this.size);
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


// Creature[] appends(Creature[] array, Creature value){
//   Creature[] arr = new Creature[array.length+1];
//   for(int i = 0; i < array.length; i++){
//     arr[i] = array[i];
//   }
//   arr[arr.length-1] = value;
//   return arr;
// }
//
// Creature[] removeByIndex(Creature[] array, index){
//   Creature[] temp;
//   if(array.length > 0){
//     temp = new Creature[array.length - 1];
//     arrayCopy(array, 0, temp, 0, index);
//     arrayCopy(array, index, temp, index, )
//   } else{
//     temp = array;
//   }
//   return temp;
// }
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ecosystem" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
