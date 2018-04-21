package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficTypeDouble extends TrafficParameters implements ITrafficType<Double>{
  private Double trafficParameter;
  
  @Override
  public Double getTrafficParameter() {
    return trafficParameter;
  }

  @Override
  public void setTrafficParameter(Double trafficParameter) {
    this.trafficParameter = trafficParameter;
  }
  
}
