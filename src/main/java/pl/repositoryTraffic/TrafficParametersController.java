package pl.repositoryTraffic;

import java.sql.Connection;
import pl.database.Database;
import pl.repositoryTraffic.DAO.TrafficAverageSpeedDAO;
import pl.repositoryTraffic.DAO.TrafficLengthOfVehicleQueueDAO;
import pl.repositoryTraffic.DAO.TrafficWeatherConditionsDAO;
import pl.repositoryTraffic.pojo.AverageSpeedParameter;
import pl.trush.ITrafficParameter;
import pl.trush.LengthOfVehicleQueueParameter;
import pl.trush.WeatherConditionsParameter;

/**
 *
 * @author Michał
 */
public class TrafficParametersController {
  private final String AVERAGE_SPEED = "average speed";
  private final String LENGTH_OF_VEHICLE_QUEUE = "vehicle queue";
  private final String WEATHER_CONDITIONS = "weather conditions";
  
  public ITrafficParameter obtainTrafficParameterByType(String parameterType, int segmentId) throws Exception{
    
    Database database = new Database();
    Connection connection = database.GetConnection();
    
    if(parameterType.equals(AVERAGE_SPEED)){
       TrafficAverageSpeedDAO averageSpeedDAO = new TrafficAverageSpeedDAO();
       
       
       
       AverageSpeedParameter averageSpeedParameter = averageSpeedDAO.obtainTrafficParameterFromTrafficMapById(connection, database, segmentId);
       connection.close();
       return averageSpeedParameter;
       
    }else if(parameterType.equals(LENGTH_OF_VEHICLE_QUEUE)){
      TrafficLengthOfVehicleQueueDAO lengthOfVehicleQueueDAO = new TrafficLengthOfVehicleQueueDAO();
      LengthOfVehicleQueueParameter lengthOfVehicleQueue = lengthOfVehicleQueueDAO.obtainTrafficParameterFromTrafficMapById(connection, database, segmentId);
      connection.close();
      return lengthOfVehicleQueue;
      
    }else if(parameterType.equals(WEATHER_CONDITIONS)){
      TrafficWeatherConditionsDAO weatherConditionsDAO = new TrafficWeatherConditionsDAO();
      WeatherConditionsParameter weatherConditions = weatherConditionsDAO.obtainTrafficParameterFromTrafficMapById(connection, database, segmentId);
      connection.close();
      return weatherConditions;
      
    }else{
      System.out.println("Wybranego parametru nie ma zaimplementowanego");
      connection.close();
      return null;
    }
  }
}
