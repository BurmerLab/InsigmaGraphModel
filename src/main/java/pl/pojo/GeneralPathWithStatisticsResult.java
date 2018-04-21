/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pojo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Michał
 */
public class GeneralPathWithStatisticsResult {
  private List<Integer> finalPathResult;
  private double time;
  private double distance;
  private double avgSpeed;
  private Map<Integer, Statistics> statistics;

  @Override
  public String toString() {
      return new StringBuffer(" FinalPath: ").append(this.finalPathResult)
              .append(" Time : ").append(this.time)
              .append(" Distance : ").append(this.distance)
              .append(" AVG Speed: ").append(this.avgSpeed)
              .append(" Statistics: ").append(this.statistics)
              .toString();
  }
  
  public GeneralPathWithStatisticsResult() {
  }

  public GeneralPathWithStatisticsResult(List<Integer> finalPathResult, double time, double distance, double avgSpeed,
                                                  Map<Integer, Statistics> statistics) {
    this.finalPathResult = finalPathResult;
    this.time = time;
    this.distance = distance;
    this.avgSpeed = avgSpeed;
    this.statistics = statistics;
  }

  public List<Integer> getFinalPathResult() {
    return finalPathResult;
  }

  public void setFinalPathResult(List<Integer> finalPathResult) {
    this.finalPathResult = finalPathResult;
  }

  public double getTime() {
    return time;
  }

  public void setTime(double time) {
    this.time = time;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public double getAvgSpeed() {
    return avgSpeed;
  }

  public void setAvgSpeed(double avgSpeed) {
    this.avgSpeed = avgSpeed;
  }
  
}
