

class Creature{
  static final float MUTATE_CHANGE = 0.3;
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

  void update(){
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
    dir.mult(0.9);
    this.acceleration = dir;
  }

  private void newDestination(){
    do{
      int distance = (int) random(50, 300);
      this.destination = new PVector(random(this.location.x-distance, this.location.x+distance), random(this.location.y-distance, this.location.y+distance));
    }while((destination.x>=width-size||destination.x<=0)||(destination.y>=height-size||destination.y<=0));
  }

  Creature mutate(){
    Creature child = new Creature(this.destination);
    if(random(1)<0.5){
      child.speed = speed + Creature.MUTATE_CHANGE;
    }else{
      child.speed = speed - Creature.MUTATE_CHANGE;
    }
    // println("child: ", child.speed, "  parend: ", this.speed, "  same?: ", child==this);
    return child;
  }

  void checkEdges(){
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

  void foundFoot(){
    energy++;
    food++;
  }

  void show(){
    fill(map(speed, 0, 6, 20, 255), 0, map(speed, 0, 6, 20, 255));
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
