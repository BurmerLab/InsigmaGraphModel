package pl.tomcat.startup;

import java.util.logging.Level;
import java.util.logging.Logger;
import osmgraphmodel.Program;

/**
 *
 * @author Michał
 */
public class TrafficParametersThread extends Thread {
  
  @Override
  public void run() {
    Program program = new Program();
    try {
      program.loadTrafficParametersFromDatabase();
      System.out.println("Pobrano wszystkie parametry TRAFFIC");
    } catch (Exception ex) {
      Logger.getLogger(TrafficParametersThread.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
