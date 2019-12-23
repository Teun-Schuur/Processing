import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import Game; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class mazeing extends PApplet {



Game game;

public void setup(){
  game = new Game(500, 500);
  println(game);
}

public void draw(){
  game.tick();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "mazeing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
