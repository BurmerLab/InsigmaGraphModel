package pl.trush;

import java.sql.Date;
import java.util.Random;
import org.joda.time.DateTime;

/**
 *
 * @author Michał
 */
public class LengthOfVehicleQueueParameter extends MetaDataForTrafficParameters implements ITrafficParameter<Integer> {
  private int vehicleQueue;

  public LengthOfVehicleQueueParameter() {
  }
  
  public int getVehicleQueue() {
    return vehicleQueue;
  }

  public void setVehicleQueue(int vehicleQueue) {
    this.vehicleQueue = vehicleQueue;
  }

  @Override
  public Integer generateParameter(Integer minimumNumber, Integer maximalNumber) {
     Random random = new Random();
     return random.nextInt((maximalNumber - minimumNumber) + 1) + minimumNumber;
  }

  @Override
  public Integer getTrafficParameter() {
    return vehicleQueue;
  }
  
  @Override
  public DateTime getDate() {
    return date;
  }
  
  @Override
  public int getStartPoint(){
    return startPoint;
  }
  
  @Override
  public int getEndPoint(){
    return endPoint;
  }

  @Override
  public boolean isTrafficParam() {
    return isTrafficParamExist();
  }

  @Override
  public void setTrafficParam(boolean isTrafficExist) {
    setTrafficParamExist(isTrafficExist);
  }

  @Override
  public Double obtainWage() {
    return getWage();
  }
 

  
}
