/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmgraphmodel;

/**
 *
 * @author Michał
 */
public class SegmentsDTO {
//  private String id; 
  private String id; 
  private long ids;
  private long wayId;
  private long start;
  private long end;
  private double length;
  private int maxspeed;
  private boolean directed;
  private double startAzimuth;
  private double endAzimuth;
//  private int passingTime;
  private int traffic;
//          public int id = -1;
//        public long ids;
//        public int wayId;
//        public Node start = null;
//        public Node end = null;
//        public double length = 0;
//        public int maxspeed = 50;
//        public boolean directed = false;
//        public double startAzimuth=0;
//        public double endAzimuth = 0;
//        public int traffic;
  

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getIds() {
    return ids;
  }

  public void setIds(long ids) {
    this.ids = ids;
  }

  public long getWayId() {
    return wayId;
  }

  public void setWayId(long wayId) {
    this.wayId = wayId;
  }

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(long end) {
    this.end = end;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public int getMaxspeed() {
    return maxspeed;
  }

  public void setMaxspeed(int maxspeed) {
    this.maxspeed = maxspeed;
  }

  public boolean isDirected() {
    return directed;
  }

  public void setDirected(boolean directed) {
    this.directed = directed;
  }

  public double getStartAzimuth() {
    return startAzimuth;
  }

  public void setStartAzimuth(double startAzimuth) {
    this.startAzimuth = startAzimuth;
  }

  public double getEndAzimuth() {
    return endAzimuth;
  }

  public void setEndAzimuth(double endAzimuth) {
    this.endAzimuth = endAzimuth;
  }

  public int getTraffic() {
    return traffic;
  }

  public void setTraffic(int traffic) {
    this.traffic = traffic;
  }
 
}
//  public void generateTrafficParameters(String range){
//    String RANGE_OF_GENERATED_TRAFFIC_PARAMETERS = "low";
//    
//    if(range.equals(RANGE_OF_GENERATED_TRAFFIC_PARAMETERS)){
//      passingTime = 20;
//      traffic = 1;
//    }else{
//      passingTime = 30;
//      traffic = 1;
//    }
//  }
