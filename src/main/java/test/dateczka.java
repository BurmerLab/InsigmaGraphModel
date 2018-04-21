/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Michał
 */
public class dateczka {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();
    Thread.sleep(59000);
    long goal = System.currentTimeMillis();

    long wyn = goal - start;
    System.out.println("Test1: " + dateczka.milisecondsToTimeHMS(wyn));
    
  }
    public static String milisecondsToTimeHMS(long milliseconds){
    DecimalFormat twoDigit = new DecimalFormat("00");
    long secs = milliseconds/1000;
    String secsString = twoDigit.format(secs%60);
    
    long minutes = secs/60;
    String minsString = twoDigit.format(minutes%60);
    
    long hours = minutes/60;
    String hoursString = twoDigit.format(hours);
    return hoursString + ":" + minsString + ":" + secsString;
  }
  
}
