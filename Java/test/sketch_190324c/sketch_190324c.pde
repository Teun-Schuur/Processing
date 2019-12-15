Perceptron ptron;
Trainer[] training = new Trainer[100000];
int count = 0;
 
float f(float x) {
  return 2*x+20;
}
 
void setup() {
  size(300, 600);
 
  ptron = new Perceptron(3);
 
  for (int i = 0; i < training.length; i++) {
    float x = random(width);
    float y = random(height);
    int answer = 1;
    if (y < f(x)) answer = -1;
    training[i] = new Trainer(x, y, answer);
  }
  frameRate(1000);

}
 
 
void draw() {
  background(255);
  push();
  strokeWeight(5);
  line(0, f(0), width, f(width));
  pop();
  for(int j = 0; j < 50; j++){
    ptron.train(training[count].inputs, training[count].answer);
    count = (count + 1) % training.length;
   
    for (int i = 0; i < count; i++) {
      stroke(0);
      float guess = ptron.feedforward(training[i].inputs);
      
      if (guess > 0) noFill();
      else           fill(0);
      
      ellipse(training[i].inputs[0], training[i].inputs[1], 8, 8);
    }
  }
}

class Trainer{
  float[] inputs;
  int answer;
  
  Trainer(float x, float y, int a){
    inputs = new float[3];
    inputs[0] = x;
    inputs[1] = y;
    inputs[2] = 1;
    answer = a;
    
  }
  
}


class Perceptron{
 float[] weights; 
 float learningRate = 0.0001;
 
 Perceptron(int n){
  weights = new float[n];
  for(int i = 0; i < weights.length; i++){
    weights[i] = random(-1, 1);
  }
 }

 float feedforward(float[] inputs){
   float sum = 0;
   for (int i = 0; i < inputs.length; i++){
    sum += inputs[i]*weights[i];
  }
  return activate(sum);
 }
 
  int activate(float ok){
   if(ok > 0) return 1;
   else return -1;
  }
  
  void train(float[] inputs, int desired){
    float guess = feedforward(inputs);
    float error = desired - guess;
    for(int i = 0; i < weights.length; i++){
      weights[i] += learningRate * error * inputs[i];
    }
  }
}
