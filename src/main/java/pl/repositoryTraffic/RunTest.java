package pl.repositoryTraffic;

import java.sql.Connection;
import java.sql.SQLException;
import pl.database.Database;
import pl.trush.ITrafficParameter;

/**
 *
 * @author Michał
 */
public class RunTest {

  public static void main(String[] args) throws SQLException, Exception{
    
    Database database = new Database();
    Connection connection = database.GetConnection();
    
    TrafficParametersController trafficParameterController = new TrafficParametersController();
    
    //AverageSpeed
    //LengthOfVehicleQueue
    //WeatherConditions
    
    ITrafficParameter trafficParameter = trafficParameterController.obtainTrafficParameterByType("WeatherConditions", 2954556);
    System.out.println("trafficParam: " + trafficParameter.getTrafficParameter());
    System.out.println("date: " + trafficParameter.getDate());
    System.out.println("startPoint: " + trafficParameter.getStartPoint());
    System.out.println("EndPoint: " + trafficParameter.getEndPoint());
    
  }
}
