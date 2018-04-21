package osmgraphmodel;

//import com.sun.xml.internal.ws.message.saaj.SAAJHeader;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.database.Database;
import pl.pojo.GeneralPathWithStatisticsResult;

/**
 *
 * @author Michał
 */
    public class Program {
      //AverageSpeed
      //LengthOfVehicleQueue
      //WeatherConditions
      public static final String POINTS_AND_SEGMENTS_FILE = "c:/OSM/poland-roads-segmented.csv";
      
      public static String[] trafficParametersTypesNumber = {
                                      "empty",
                                      "double",//1
                                      "int",//2
                                      "boolean",//3
                                      "long"//4
      };
      
      public static int trafficParameterNumber;

      public static String trafficParameterName;//np. AverageSpeed
      public static String trafficParameterTypeName;//np. "double"

      public static final String CURRENT_TIME = "2014-11-13 21:26:21";
      public static final String USER_TYPE = "";

      public List<Integer> finalathResult = new ArrayList<Integer>();
      
      
      public static void main(String[] args) throws Exception{
        
          OSMGraphModelImp.Get().readVectorsFromFileAndSaveToMaps(POINTS_AND_SEGMENTS_FILE);
          OSMGraphModelImp.Get().makePointSegmentIndex();
          
          Database database = new Database();
          Connection connection = database.GetConnection();
          OSMGraphModelImp.Get().loadTrafficParametersFromDatabase(connection);
          
          Program program = new Program();
          GeneralPathWithStatisticsResult generalStatisticsOfPath = program.obtainFinalPath(261732023, 289143029, 1);//1-double
          
          GeneralPathWithStatisticsResult generalStatisticsOfPathTwo = program.obtainFinalPath(261732023, 289143029, 1);//1-double
          
          System.out.println("KONIEC");
      }

      public GeneralPathWithStatisticsResult obtainFinalPath(int startPoint, int endPoint, int trafficParameterType) throws Exception {
        Program.setTrafficParameterTypeName(Program.trafficParametersTypesNumber[trafficParameterType]);//ustawienie nazwy parametru np "double"
        Program.setTrafficParameterNumber(trafficParameterType);//ustawoeinie numer - np. 1 (double)

        System.out.println("Traffic type: " + trafficParameterType);

        TestDef algorithm = new TestDef();
        GeneralPathWithStatisticsResult generalPathWithStatisticsResult = algorithm.RunAstarTest(startPoint, endPoint);

        return generalPathWithStatisticsResult;
      }
        
        
      public void loadTrafficParametersFromDatabase() throws Exception{
        Database database = new Database();
        Connection connection = database.GetConnection();

        OSMGraphModelImp.Get().loadTrafficParametersFromDatabase(connection);
      }

      public void loadVectorDataFromFile() {
        OSMGraphModelImp.Get().readVectorsFromFileAndSaveToMaps(POINTS_AND_SEGMENTS_FILE);
      }
        

      public void makeIndexForPointSegment() {
        OSMGraphModelImp.Get().makePointSegmentIndex();
      }

      static void testTurnCosts(){
          double azimuth1, azimuth2;
          azimuth1 = 90;
          azimuth2 = 180;
          double speed1 = 70;
          double speed2 = 50;

          // we calculate vector product
          azimuth1 *= Math.PI / 180;
          azimuth2 *= Math.PI / 180;
          double x1 = Math.cos(azimuth1 ), x2 = Math.cos(azimuth2), y1 = Math.sin(azimuth1), y2 = Math.sin(azimuth2);

          double vprod = x1*y2-x2*y1;
          //double refSpeed = speed2 < speed1 ? speed2 : speed1;
          double turnSpeed = Math.min(speed1,speed2) * (1 - 0.90*Math.abs(vprod) );
          if (turnSpeed < 0) turnSpeed = 0;

          double acceleration = 100 / 15 * 3600;
          // http://www.prawko-kwartnik.info/hamowanie.html
          double breaking = 70 / 6.4 * 3600;
          double cost = 0;
          if (speed1 > turnSpeed) cost += (speed1 - turnSpeed) / breaking;
          if(speed2>turnSpeed)cost+=(speed2 - turnSpeed) / acceleration;

          double mul = 1;

          if (vprod < -.8) mul = 1.3; // skręt w lewo
          if (x1*x2+y1*y2 < 0 * Math.PI) mul = 2; // u-turn scalar product

          // NORTH 0
          // WEST -90
          // EAST 90
          // SOUTH -180, 180

          System.out.println("Vprod: " + vprod);
          System.out.println("Vprod: " + vprod);
          System.out.println("Turn speed: " + turnSpeed);
          System.out.println("Turn cost: "+cost * mul*3600 + " sec");

      }
        
        
      public static String getTrafficParameterName() {
        return trafficParameterName;
      }

      public static void setTrafficParameterName(String trafficParameterName) {
        Program.trafficParameterName = trafficParameterName;
      }

      public static String getTrafficParameterTypeName() {
        return trafficParameterTypeName;
      }

      public static void setTrafficParameterTypeName(String trafficParameterTypeName) {
        Program.trafficParameterTypeName = trafficParameterTypeName;
      }

    public static int getTrafficParameterNumber() {
      return trafficParameterNumber;
    }

    public static void setTrafficParameterNumber(int trafficParameterNumber) {
      Program.trafficParameterNumber = trafficParameterNumber;
    }
      
        
        
 
}


