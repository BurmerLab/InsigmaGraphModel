package pl.repositoryTraffic.pojo;

import pl.trush.ITrafficParameter;
import pl.trush.MetaDataForTrafficParameters;
import java.sql.Date;
import org.joda.time.DateTime;

/**
 *
 * @author Michał
 */
public class AverageSpeedParameter extends MetaDataForTrafficParameters implements ITrafficParameter<Double> {
  private double averageSpeed;
  
  public AverageSpeedParameter() {
  }

  public double getAverageSpeed() {
    return averageSpeed;
  }

  public void setAverageSpeed(double averageSpeed) {
    this.averageSpeed = averageSpeed;
  }
  
  @Override
  public Double generateParameter(Double minimumNumber, Double maximalNumber) {
    return Math.random() * (maximalNumber - minimumNumber) + minimumNumber;
  }

  @Override
  public Double getTrafficParameter() {
    return averageSpeed;
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
