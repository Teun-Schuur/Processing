float radias = 1;

void setup(){
  size(600, 600);
}

void draw(){

  background(100);
  line(0, 0, width, height);
  radias = radias * 1.02;
  fill(255, 0, 0);
  ellipse(width/2, height/2, radias, radias);
}

// void mousePressed(){
//   fill(250);
// }
