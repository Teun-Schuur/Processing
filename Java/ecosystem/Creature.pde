

class Creature{
  static final float MUTATE_CHANGE = 0.4;
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

  void update(PVector[] foods){
    if(destination == null || PVector.add(this.location, new PVector(size/2, size/2)).dist(this.destination) < size/2){
      newDestination(foods);
    }
    setDestinationSense(foods);
    newDestination();
    toLocation();
    this.velocity.add(this.acceleration);
    if(goingHome){
      this.velocity.limit(speed*1.8+1);
    }else if(home){
      this.velocity.limit(0);
    }else{
      this.velocity.limit(speed);
    }
    this.location.add(this.velocity);
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
    PVector dir = PVector.sub(this.destination, this.location);
    dir.normalize();
    dir.mult(0.9);
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

  Creature mutate(){
    Creature child = new Creature(destination);
    if(random(1)<0.5){
      child.speed = speed + random(Creature.MUTATE_CHANGE);
      child.sense = sense + random(Creature.MUTATE_CHANGE);
    }else{
      child.speed = speed - random(Creature.MUTATE_CHANGE);
      child.sense = sense - random(Creature.MUTATE_CHANGE);
    }
    // println("child: ", child.speed, "  parend: ", this.speed, "  same?: ", child==this);
    return child;
  }

  void checkEdges(){
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

  void energyCalculation(){
    if(!home){
      this.energy -= speed*speed + sense;
    }
  }

  void foundFoot(){
    food++;
  }

  void show(float max){
    fill(map(speed, 0, max+1, 20, 255), 0, map(speed, 0, max+1, 20, 255));
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
