/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmgraphmodel;

/**
 *
 * @author Michał
 */

public class Point{
      
//        public String osmid="-1";
//        public String latitude = "0";
//        public String longitude = "0";
//        public String degree = "0";
        public int osmid=-1;
        public double latitude = 0;
        public double longitude = 0;
        public int degree = 0;

  public Point(){};

  public Point(int osmid, double latitude, double longitude, int degree) {
    this.osmid = osmid;
    this.latitude = latitude;
    this.longitude = longitude;
    this.degree = degree;
  }
}

