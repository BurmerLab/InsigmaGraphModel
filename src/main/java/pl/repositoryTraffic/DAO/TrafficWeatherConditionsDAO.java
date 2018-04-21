package pl.repositoryTraffic.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.time.DateTime;
import pl.dao.traffic.QUERY_PARAMS;
import pl.database.Database;
import pl.repositoryTraffic.pojo.AverageSpeedParameter;
import pl.trush.WeatherConditionsParameter;

/**
 *
 * @author Michał
 */
public class TrafficWeatherConditionsDAO implements ITrafficPrametersDAO<WeatherConditionsParameter> {

  @Override
  public WeatherConditionsParameter obtainTrafficParameterFromTrafficMapById(Connection connection, Database database, int segmentId) 
          throws SQLException {
    
    WeatherConditionsParameter weatherConditionsParameter = new WeatherConditionsParameter();
    
//    vehicle_queue, actual_date, segment_id
    PreparedStatement psWeatherConditions = connection.prepareStatement(QUERY_PARAMS.SELECT_WEATHER_CONDITIONS_BY_ID);
    psWeatherConditions.setInt(1, segmentId);
    psWeatherConditions.setInt(2, segmentId);
    
    ResultSet rsWeatherConditions = psWeatherConditions.executeQuery();
    while(rsWeatherConditions.next()){
      weatherConditionsParameter.setWetRoad(rsWeatherConditions.getBoolean(1));
      weatherConditionsParameter.setDate(new DateTime(rsWeatherConditions.getTimestamp(2).getTime()));
      weatherConditionsParameter.setSegmentId(segmentId);
      weatherConditionsParameter.setStartPoint(rsWeatherConditions.getInt(4));
      weatherConditionsParameter.setEndPoint(rsWeatherConditions.getInt(5));
      weatherConditionsParameter.setWage(rsWeatherConditions.getDouble(6));
      weatherConditionsParameter.setTrafficParamExist(rsWeatherConditions.getBoolean(7));
    }
    
    psWeatherConditions.close();
    rsWeatherConditions.close();
    
    return weatherConditionsParameter;
  }
  
}
