
package pl.trush;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.joda.time.DateTime;
import pl.pojo.Segment;


/**
 *
 * @author Michał
 */
public class MetaDataForTrafficParameters {
  public DateTime date;
  public int segmentId;
  public int startPoint;
  public int endPoint;
  private double wage;
  private boolean TrafficParamExist;

  public double getWage() {
    return wage;
  }

  public void setWage(double wage) {
    this.wage = wage;
  }

  public boolean isTrafficParamExist() {
    return TrafficParamExist;
  }

  public void setTrafficParamExist(boolean TrafficParamExist) {
    this.TrafficParamExist = TrafficParamExist;
  }

  public DateTime getDate() {
    return date;
  }

  public void setDate(DateTime date) {
    this.date = date;
  }

 
  public int getStartPoint() {
    return startPoint;
  }

  public void setStartPoint(int startPoint) {
    this.startPoint = startPoint;
  }

  public int getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(int endPoint) {
    this.endPoint = endPoint;
  }

  public int getSegmentId() {
    return segmentId;
  }

  public void setSegmentId(int segmentId) {
    this.segmentId = segmentId;
  }
  
  public void generateCurrentCalendarDate(){
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    System.out.println(dateFormat.format(calendar.getTime().toString()));
  }
  
  
}
