package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficTypeBoolean extends TrafficParameters implements ITrafficType<Boolean>{
  private Boolean trafficParameter;

  
  @Override
  public Boolean getTrafficParameter() {
    return trafficParameter;
  }

  @Override
  public void setTrafficParameter(Boolean trafficParameter) {
    this.trafficParameter = trafficParameter;
  }
  
  
}
