

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
    this.destination = new PVector(random(0, width), random(0, height));
    this.acceleration = new PVector(0, 0);
    size = 20;
    topspeed = 2.0f;
  }

  void update(){
    // age++;
    this.toLocation();
    this.velocity.add(this.acceleration);
    this.velocity.limit(topspeed);
    this.location.add(this.velocity);
  }

  private void toLocation(){
    if(this.location.dist(this.destination) < 50){
      this.destination = new PVector(random(0, width), random(0, height));
    }
    PVector dir = PVector.sub(this.destination, this.location);
    dir.normalize();
    dir.mult(random(0.01, 0.1));
    this.acceleration = dir;
  }

  void checkEdges(){
    if (location.x > width) {
      location.x = width;
    } else if (location.x < 0) {
      location.x = 0;
    }

    if (location.y > height) {
      location.y = height;
    }  else if (location.y < 0) {
      location.y = 0;
    }
  }

  void show(){
    fill(200, 0, 220);
    rect(this.location.x, this.location.y, this.size, this.size);
  }
}
