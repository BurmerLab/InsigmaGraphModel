package pl.pojo;

/**
 *
 * @author Michał
 */
public class Statistics {
  private Integer idStart;
  private Integer idEnd;
  private String cost;
  private double startLatitude = 0;
  private double startLongitude = 0;
  private double endLatitude = 0;
  private double endLongitude = 0;
  private double averageParmeter = 0;
  private int lengthOfQueueVehicle = 0;
  private boolean weatherConditions = false;
  
  private String lineString;

  
//  writer.append(entry.getKey() + ";" + statistic.getIdStart() + ";"
//                      + "LINESTRING(" + statistic.getStartLongitude() + " " + statistic.getStartLatitude() + ", "
//                      + statistic.getEndLongitude() + " " + statistic.getEndLatitude() + ")"
//                      + ";" + statistic.getIdEnd() + ";" + statistic.getCost()
//                      + "\n");

  public String getLineString() {
    return lineString;
  }

  public void setLineString(String lineString) {
    this.lineString = lineString;
  }
  
  public int getLengthOfQueueVehicle() {
    return lengthOfQueueVehicle;
  }

  public void setLengthOfQueueVehicle(int lengothOfQueueVehicle) {
    this.lengthOfQueueVehicle = lengothOfQueueVehicle;
  }

  public boolean isWeatherConditions() {
    return weatherConditions;
  }

  public void setWeatherConditions(boolean weatherConditions) {
    this.weatherConditions = weatherConditions;
  }

  public double getAverageParmeter() {
    return averageParmeter;
  }

  public void setAverageParmeter(double averageParmeter) {
    this.averageParmeter = averageParmeter;
  }

  public double getStartLatitude() {
    return startLatitude;
  }

  public void setStartLatitude(double startLatitude) {
    this.startLatitude = startLatitude;
  }

  public double getStartLongitude() {
    return startLongitude;
  }

  public void setStartLongitude(double startLongitude) {
    this.startLongitude = startLongitude;
  }

  public double getEndLatitude() {
    return endLatitude;
  }

  public void setEndLatitude(double endLatitude) {
    this.endLatitude = endLatitude;
  }

  public double getEndLongitude() {
    return endLongitude;
  }

  public void setEndLongitude(double endLongitude) {
    this.endLongitude = endLongitude;
  }
  

  public Integer getIdStart() {
    return idStart;
  }

  public void setIdStart(Integer idStart) {
    this.idStart = idStart;
  }

  public Integer getIdEnd() {
    return idEnd;
  }

  public void setIdEnd(Integer idEnd) {
    this.idEnd = idEnd;
  }

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

          

          }
