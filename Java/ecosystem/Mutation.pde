/*
2500 limit
*/

class Mutation{
  static final int ONE_DAY = 10*60 // one day = 10 seconds
  ArrayList<Creature> creatures = new ArrayList<Creature>();
  int[] creatures_history = new int[100];

  Mutation(int begin_pop, float begin_speed){
    for(int i = 0; i < begin_pop; i++){
      creatures.add(new Creature(new PVector(random(width), random(height))));
      creatures.get(i).speed = begin_speed;
    }
  }

  public void update(){
    for(int i = 0; i < creatures.size(); i++){
      Creature c = creatures.get(i);
      c.update();
      c.checkEdges();
    }
    if(frameRate%ONE_DAY)
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
