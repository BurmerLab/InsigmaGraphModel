/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Michał
 */
public class Kalendarz {
      
    public static void main(String[] args) throws ParseException, InterruptedException{
      
      DateFormat format = new SimpleDateFormat("HH:mm:ss"); 
      long start = System.currentTimeMillis();
      Thread.sleep(3000);
      long goal = System.currentTimeMillis();
      
      long wyn = goal - start;
      Date result = new Date(wyn);
      System.out.println("Test1: " + format.format(result));
    }
}
