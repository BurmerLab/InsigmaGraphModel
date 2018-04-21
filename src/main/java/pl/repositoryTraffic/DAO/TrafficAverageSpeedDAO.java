package pl.repositoryTraffic.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import pl.dao.traffic.QUERY_PARAMS;
import pl.database.Database;
import pl.repositoryTraffic.pojo.AverageSpeedParameter;
import pl.trush.ITrafficParameter;

/**
 *
 * @author Michał
 */
public class TrafficAverageSpeedDAO implements ITrafficPrametersDAO<AverageSpeedParameter>{
  
  @Override
  public AverageSpeedParameter obtainTrafficParameterFromTrafficMapById(Connection connection, Database database, int segmentId) 
          throws SQLException{
    
    PreparedStatement psAverageParameter = connection.prepareStatement(QUERY_PARAMS.SELECT_AVERAGE_SPEED_PARAMETER_BY_ID);
    psAverageParameter.setInt(1, segmentId);
    psAverageParameter.setInt(2, segmentId);
    ResultSet rsAverageParameter = psAverageParameter.executeQuery();
    AverageSpeedParameter averageSpeedParameter = new AverageSpeedParameter();
    
    while(rsAverageParameter.next()){
      averageSpeedParameter.setAverageSpeed(rsAverageParameter.getDouble(1));
      averageSpeedParameter.setDate(new DateTime(rsAverageParameter.getTimestamp(2).getTime()));
      averageSpeedParameter.setSegmentId(segmentId);
      averageSpeedParameter.setStartPoint(rsAverageParameter.getInt(4));
      averageSpeedParameter.setEndPoint(rsAverageParameter.getInt(5));
      averageSpeedParameter.setWage(rsAverageParameter.getDouble(6));
      averageSpeedParameter.setTrafficParamExist(rsAverageParameter.getBoolean(7));
    }
    
    psAverageParameter.close();
    rsAverageParameter.close();
    
    return averageSpeedParameter;
  }
}
