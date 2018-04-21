package pl.repositoryTraffic.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.time.DateTime;
import pl.dao.traffic.QUERY_PARAMS;
import pl.database.Database;
import pl.trush.LengthOfVehicleQueueParameter;
import pl.trush.WeatherConditionsParameter;

/**
 *
 * @author Michał
 */
public class TrafficLengthOfVehicleQueueDAO implements ITrafficPrametersDAO<LengthOfVehicleQueueParameter> {

  @Override
  public LengthOfVehicleQueueParameter obtainTrafficParameterFromTrafficMapById(Connection connection, Database database, int segmentId) 
          throws SQLException {
    
    LengthOfVehicleQueueParameter lengthOfVehicleQueueParameter = new LengthOfVehicleQueueParameter();
    
    PreparedStatement psLengthOfVehicleQueue = connection.prepareStatement(QUERY_PARAMS.SELECT_LENGTH_OF_VEHICLE_QUEUE_BY_ID);
    psLengthOfVehicleQueue.setInt(1, segmentId);
    psLengthOfVehicleQueue.setInt(2, segmentId);
    ResultSet rsLengthOfVehicleQueue = psLengthOfVehicleQueue.executeQuery();
   
    while(rsLengthOfVehicleQueue.next()){
      lengthOfVehicleQueueParameter.setVehicleQueue(rsLengthOfVehicleQueue.getInt(1));
      lengthOfVehicleQueueParameter.setDate(new DateTime(rsLengthOfVehicleQueue.getTimestamp(2).getTime()));
      lengthOfVehicleQueueParameter.setSegmentId(segmentId);
      lengthOfVehicleQueueParameter.setStartPoint(rsLengthOfVehicleQueue.getInt(4));
      lengthOfVehicleQueueParameter.setEndPoint(rsLengthOfVehicleQueue.getInt(5));
      lengthOfVehicleQueueParameter.setWage(rsLengthOfVehicleQueue.getDouble(6));
      lengthOfVehicleQueueParameter.setTrafficParamExist(rsLengthOfVehicleQueue.getBoolean(7));
    }
    
    psLengthOfVehicleQueue.close();
    rsLengthOfVehicleQueue.close();
    
    return lengthOfVehicleQueueParameter;
  }
}
