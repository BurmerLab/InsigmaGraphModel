package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficTypeLong extends TrafficParameters implements ITrafficType<Long>{
  private Long trafficParameter;

  @Override
  public Long getTrafficParameter() {
    return trafficParameter;
  }

  @Override
  public void setTrafficParameter(Long trafficParameter) {
    this.trafficParameter = trafficParameter;
  }
  
}
