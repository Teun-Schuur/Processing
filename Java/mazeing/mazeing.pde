import Game;

Game game;

void setup(){
  game = new Game(500, 500);
  println(game);
}

void draw(){
  game.tick();
}
