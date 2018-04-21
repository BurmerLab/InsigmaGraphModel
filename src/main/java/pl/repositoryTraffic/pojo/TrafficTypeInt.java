package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficTypeInt extends TrafficParameters implements ITrafficType<Integer>{
  private Integer trafficParameter;

  @Override
  public Integer getTrafficParameter() {
    return trafficParameter;
  }

  @Override
  public void setTrafficParameter(Integer trafficParameter) {
    this.trafficParameter = trafficParameter;
  }
  
  
}
