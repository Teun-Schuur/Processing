

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
    this.destination = new PVector(0, 0);
    newDestination();
    this.acceleration = new PVector(0, 0);
    size = 30;
    topspeed = 2.0f;
  }

  void update(){
    // age++;
    this.velocity.add(this.acceleration);
    this.velocity.limit(topspeed);
    this.location.add(this.velocity);
    this.toLocation();

  }

  private void toLocation(){
    if(this.location.dist(this.destination) < 30){
      newDestination();
    }
    PVector dir = PVector.sub(this.destination, this.location);
    dir.normalize();
    dir.mult(0.06);
    this.acceleration = dir;
  }

  void newDestination(){
    do{
      int distance = (int) random(30, 300);
      this.destination = new PVector(random(this.location.x-distance, this.location.x+distance), random(this.location.y-distance, this.location.y+distance));
    }while((destination.x>=width-size||destination.x<=0)||(destination.y>=height-size||destination.y<=0));
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

  void show(){
    fill(200, 0, 220);
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
