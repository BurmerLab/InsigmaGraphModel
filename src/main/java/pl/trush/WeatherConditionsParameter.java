package pl.trush;

import java.sql.Date;
import org.joda.time.DateTime;

/**
 *
 * @author Michał
 */
public class WeatherConditionsParameter extends MetaDataForTrafficParameters implements ITrafficParameter<Boolean> {
  private Boolean wetRoad;

  public WeatherConditionsParameter() {
  }

  public Boolean getWetRoad() {
    return wetRoad;
  }

  public void setWetRoad(Boolean wetRoad) {
    this.wetRoad = wetRoad;
  }

  @Override
  public Boolean generateParameter(Boolean notUsedFrom, Boolean notUsedTo) {
    return Math.random() < 0.5;
  }

  @Override
  public Boolean getTrafficParameter() {
    return wetRoad;
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
  public void setTrafficParam(boolean isWetRoad) {
    setTrafficParamExist(isWetRoad);
  }

  @Override
  public Double obtainWage() {
    return getWage();
  }
  
}
