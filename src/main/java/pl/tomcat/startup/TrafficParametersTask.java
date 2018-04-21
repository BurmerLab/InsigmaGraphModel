package pl.tomcat.startup;

import java.util.Timer;
import java.util.TimerTask;
import test.SimpleTask;
import test.SimpleThread;

/**
 *
 * @author Michał
 */
public class TrafficParametersTask extends TimerTask{
  
  public void run() {
    TrafficParametersThread trafficParametersThread = new TrafficParametersThread();
    trafficParametersThread.start();
  }
//   public static void main(String[] args) {
//     Timer timer = new Timer();
//     timer.schedule(new SimpleTask(), 0, 15000);    
//   }
}
