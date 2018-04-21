package test;

import java.util.TimerTask;

/**
 *
 * @author Michał
 */
public class SimpleTask extends TimerTask{
   public void run() {
      SimpleThread simpleThread = new SimpleThread();
      simpleThread.start();
   }
}
