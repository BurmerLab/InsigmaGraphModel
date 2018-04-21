package pl.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import osmgraphmodel.Point;

/**
 *
 * @author Michał
 */

  @XmlRootElement(name="segment")
  @XmlAccessorType(XmlAccessType.FIELD)
    public class Segment {
      
    public int id = -1;
    public long ids;
    public int wayId;
    public Point start = null;
    public Point end = null;
    public double length = 0;
    public int maxspeed = 50;
    public boolean directed = false;
    public double startAzimuth=0;
    public double endAzimuth = 0;
    public int trafficLevel;

    public int getTraffic() {
      return trafficLevel;
    }

    public void setTraffic(int traffic) {
      this.trafficLevel = traffic;
    }

    public int getId() {
      return id;
    }
    
    public void setId(int id) {
      this.id = id;
    }

    public long getIds() {
      return ids;
    }

    public void setIds(long ids) {
      this.ids = ids;
    }

    public int getWayId() {
      return wayId;
    }

    public void setWayId(int wayId) {
      this.wayId = wayId;
    }

    public Point getStart() {
      return start;
    }

    public void setStart(Point start) {
      this.start = start;
    }

    public Point getEnd() {
      return end;
    }

    public void setEnd(Point end) {
      this.end = end;
    }

    public boolean isDirected() {
      return directed;
    }

    public void setDirected(boolean directed) {
      this.directed = directed;
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

}
 




