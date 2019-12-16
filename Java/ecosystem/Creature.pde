

class Creature{
  static final float MUTATE_CHANGE = 0.1;
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

  void update(){
    this.velocity.add(this.acceleration);
    if(goingHome){
      this.velocity.limit(speed*1.8+1);
    }else if(home){
      this.velocity.limit(0);
    }else{
      this.velocity.limit(speed);
    }
    this.location.add(this.velocity);
    this.toLocation();
  }

  void newLocation(){
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
    dir.mult(0.9);
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

  Creature mutate(){
    Creature child = new Creature(destination);
    if(random(1)<0.5){
      child.speed = speed + random(Creature.MUTATE_CHANGE);
    }else{
      child.speed = speed - random(Creature.MUTATE_CHANGE);
    }
    // println("child: ", child.speed, "  parend: ", this.speed, "  same?: ", child==this);
    return child;
  }

  void checkEdges(){
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

  void energyCalculation(){
    if(!home){
      this.energy -= speed*speed+1;
    }
  }

  void foundFoot(){
    food++;
  }

  void show(float max){
    fill(map(speed, 0, max, 20, 255), 0, map(speed, 0, max, 20, 255));
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
