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

public class myFile extends PApplet {

float radias = 1;

public void setup(){
  
}

public void draw(){

  background(100);
  line(0, 0, width, height);
  radias = radias * 1.02f;
  fill(255, 0, 0);
  ellipse(width/2, height/2, radias, radias);
}

// void mousePressed(){
//   fill(250);
// }
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "myFile" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
