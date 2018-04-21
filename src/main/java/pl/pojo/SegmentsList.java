package pl.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michał
 */

@XmlRootElement(name="segments")
@XmlAccessorType(XmlAccessType.FIELD)
public class SegmentsList {
  
  private ArrayList<Segment> segment;

  public ArrayList<Segment> getSegments() {
    return segment;
  }

  public void setSegments(ArrayList<Segment> segments) {
    this.segment = segments;
  }

}