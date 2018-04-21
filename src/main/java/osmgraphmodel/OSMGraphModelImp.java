package osmgraphmodel;

import pl.pojo.Segment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import pl.dao.traffic.QUERY_PARAMS;
import pl.pojo.SegmentsList;
import pl.repositoryTraffic.TrafficParametersController;
import pl.repositoryTraffic.pojo.ITrafficType;
import pl.trush.ITrafficParameter;
import pl.repositoryTraffic.pojo.TrafficParameter;
import pl.repositoryTraffic.pojo.TrafficTypeFactory;


/**
 *
 * @author Michał
 */

public class OSMGraphModelImp{
      
        static OSMGraphModelImp theImp = new OSMGraphModelImp();
        public static Map<String, TrafficParameter> allTrafficParametersNew = new HashMap<String, TrafficParameter>();
          
          //AverageSpeed
          //LengthOfVehicleQueue
          //WeatherConditions
        public static final String AVERAGE_SPEED_PARAMETER = "AverageSpeedParameter";
        public static final String LENGTH_OF_VEHICLE_QUEUE_PARAMETER = "LengthOfVehicleQueueParameter";
        public static final String WEATHER_CONDITIONS_PARAMETER = "WeatherConditionsParameter";
        
        public static double TRAFFIC_PARAMETER = 0;
        
        public int offsetBetweenTrafficParameterTime = 100000; //in minutes
        
        public static Map<Integer, Point> allPointsMap = new HashMap<Integer, Point>();
        public static Map<Integer, Segment> allSegmentsMap = new HashMap<Integer, Segment>();
//        Map<Integer, Segment> allTrafficParameters = new HashMap<Integer, Segment>();
        
        
        Map<Integer, List <Segment>> allPointsSegmentsMap = new HashMap<Integer, List <Segment>>();
        ArrayList<Integer> trafficsInPath = new ArrayList<Integer>();
        
        public static OSMGraphModelImp Get() {
            return theImp;
        }
 
        public boolean readNodesREST() throws Exception{
          
          final String jsonAddressREST = readUrl("http://localhost:8084/InsigmaREST/REST/InsigmaWebService/Points/GetAllRecordsFromTablePoint");
          
          Gson gson = new Gson();
          JsonParser jsonParser = new JsonParser();
          JsonArray pointArray = jsonParser.parse(jsonAddressREST).getAsJsonArray();

//          List<PointDTO> pointList = new ArrayList<PointDTO>();
          List<Point> nodeList = new ArrayList<Point>();
          for (JsonElement point : pointArray){
              Point pointObject = gson.fromJson(point, Point.class);
              nodeList.add(pointObject);
          }

          for (Point nodeObject : nodeList)	{
              allPointsMap.put(nodeObject.osmid, nodeObject);
//                if (progress % 10000 == 0) 
//                          System.out.println("At edge: " + progress);
          }
          return true;
        }
        
        public boolean readEdgesREST() throws Exception{
          
//          final String jsonAddressREST = readUrl("http://localhost:8084/InsigmaREST/REST/InsigmaWebService/Segments/GetOnlyFirstHalfRecordsFromTableSegments");
          final String jsonAddressREST = readUrl("http://localhost:8084/InsigmaREST/REST/InsigmaWebService/Segments/GetAllRecordsFromTableSegments");
          
          Gson gson = new Gson();
          JsonParser jsonParser = new JsonParser();
          JsonArray pointArray = jsonParser.parse(jsonAddressREST).getAsJsonArray();

          List<SegmentsDTO> edgeList = new ArrayList<SegmentsDTO>();
          for (JsonElement point : pointArray){
              SegmentsDTO pointObject = gson.fromJson(point, SegmentsDTO.class);
              edgeList.add(pointObject);
          }

          for (SegmentsDTO edgeObject : edgeList)	{
              
              Segment edge = new Segment();
              edge.id = (int) edgeObject.getIds();
              edge.wayId = (int) edgeObject.getWayId();
              
              int osmStartNode = (int) edgeObject.getStart();
              if(allPointsMap.containsKey(osmStartNode)){
                edge.start = allPointsMap.get(osmStartNode);
              }else{
                throw new Exception("Error in line: start node ");
              }
              
              int osmEndNode = (int) edgeObject.getEnd();
              
              if(allPointsMap.containsKey(osmEndNode)){
                edge.end = allPointsMap.get(osmEndNode);
              }else{
                throw new Exception("Error in line: end node ");
              }
              edge.length = edgeObject.getLength();          
              edge.directed = edgeObject.isDirected();
              edge.maxspeed = edgeObject.getMaxspeed();
              edge.startAzimuth = edgeObject.getStartAzimuth();
              edge.endAzimuth = edgeObject.getEndAzimuth();
                      
              allSegmentsMap.put(edge.id, edge);
          }
          
          return true;
        }
        
        public boolean loadTrafficParametersFromDatabase(Connection connection) throws SQLException{
          boolean done = true;
          //na poczatek czyszczenie ze starych danych
          allTrafficParametersNew.clear();
          
          PreparedStatement psAllTrafficParameters = connection.prepareStatement(QUERY_PARAMS.SELECT_FOR_ALL_TRAFFIC_PARAMETERS);
          ResultSet rs = psAllTrafficParameters.executeQuery();
          
          // 1-id_pomiaru(int), 2-pomiar(double), 3-id_miejsca_pomiaru(int), 4-id_miejsca_pomiaru(int),
          // 5-id_typu_pomiaru(int), 6-typ_pomiaru(text), 7- id_typu_pomiaru(int) 8-nazwa_typu(text)
          while(rs.next()){
            
            int idPomiaru = rs.getInt(1);
            double pomiar = rs.getDouble(2);
            int idUnikalneMiejscaPomiar = rs.getInt(3);//56
            int idTypuPomiaru = rs.getInt(6);//602.... =1
            String nazwaTypuPomiaru = rs.getString(7);  //1    
            String nazwaPomiaru = rs.getString(9);//1
                    
            TrafficParameter trafficParameter = new TrafficParameter(
                    rs.getInt(1), rs.getDouble(2),
                    rs.getInt(3), rs.getInt(6),
                    rs.getString(7), rs.getString(9));

            //factor- aby klucz mapy byl unialny dodaje idTypuPomiaru + idMiejscaPomiaru
            String typeAndSegmentIdFactor = Integer.toString(rs.getInt(6)) + Integer.toString(rs.getInt(5));
            
            allTrafficParametersNew.put(typeAndSegmentIdFactor, trafficParameter);
            System.out.println("Added to map: " + typeAndSegmentIdFactor + ", " + rs.getString(1));
          }
          if(allTrafficParametersNew.isEmpty()){
            done = false;
          }            
          return done;
        }
        
        public boolean readVectorsFromFileAndSaveToMaps(String fileName){
          try{
            LineNumberReader reader = new LineNumberReader(new FileReader(fileName));

            String line;
            boolean thatIsPoint = true;

            for (int progress = 0; ; progress++){
              line = reader.readLine();
                if (line == null) break;
                if (line.equals("NODES")){
                    line = reader.readLine();
                    progress++;
                    thatIsPoint = true;
                    continue;
                }

                if (line.equals("SEGMENTS")){
                    line = reader.readLine();
                    thatIsPoint = false;
                    progress++;
                    continue;
                }

                if (thatIsPoint){
                    putPointToAllPointMap( line);
//                    System.out.println("At point: " + progress);
//                    if (progress % 10000 == 0) System.out.println("At point: " + progress);
                }else{
                    putSegmentToAllSegmentsMap( line, progress);
//                    if (progress % 10000 == 0) System.out.println("At segment: " + progress);
                }
            }
          }catch (Exception e){
             System.out.println(e.getMessage());
             return false;
          }
          System.out.println("CORRECTLY -> End of loading VECTORS TO MAP");
          return true;
        }
        
        
        
        
        
//      public void readTrafficFromXmlFile(String fileName) {
//         try {
//            File file = new File(fileName);
//            JAXBContext jaxbContext = JAXBContext.newInstance(SegmentsList.class);
//
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//
//            SegmentsList segmentsList = (SegmentsList) jaxbUnmarshaller.unmarshal(file);
//            List segments = segmentsList.getSegments();
//            Iterator segmentIterator = segments.iterator();
//
//            //Za dlugo trwa chociaz jesli bedziemy brali pod uwage ze wazne jest to ze za 1 razemzaladuje sie dlugo a pozniej to nam rura
//           // to w takim razie lepiej bedzie zrobic jak ponziej :
//            
////            while(segmentIterator.hasNext()) {
//////                Pobranie obiektu segment z akrualnej linii (iteracji) z pliku XML
////                Segment segmentTrafficFromXMLFile = (Segment) segmentIterator.next(); 
//////                Pobranie obiektu segment bez parametru o zakorkowaniu (traffic) z mapy wszystkich obiektow, wczesniej zapakowalismy te parametry z piku CSV
////                Segment segmentFromMap = getEdgeById(segmentTrafficFromXMLFile.getId());
////                
//////                Zaladowanie parametru o ruchu drogowym do obiektu segment wzietego z mapy
////                segmentFromMap.setTraffic(segmentTrafficFromXMLFile.getTraffic());
//////                Zaladowanie do mapy obiektow segment, biektu segment z parametrem o ruchu drogowym
////                allSegmentsMap.put(segmentFromMap.getId(), segmentFromMap);
////                
////                System.out.println("Dodano segment id: " + segmentFromMap.id + "z parametrem: " + segmentFromMap.getTraffic());
////                
////            }
//            
//            
////          Tylko ladowanie do mapy a pozniej bedziemy pytac tylko o traffic z id segmentu ktory algorytm przechodzi w poszukiwaniu sciezki  
//           while(segmentIterator.hasNext()) {
////           Pakowanie segmentu z xml do mapy segmentow
//             Segment segmentTrafficFromXMLFile = (Segment) segmentIterator.next(); 
//             allTrafficParameters.put(segmentTrafficFromXMLFile.getId(), segmentTrafficFromXMLFile);
//             
//           }
//          } catch (JAXBException e) {
//                e.printStackTrace();
//          }
//        System.out.println("Zaladowalem traffic do mapy");
//        }
        
        
        
        
        
        
        public void makePointSegmentIndex(){
            for (Segment segment : allSegmentsMap.values()){
                
                List<Segment> localSegmentsList = null;
                  
                if (allPointsSegmentsMap.containsKey(segment.start.osmid)){
                  localSegmentsList = allPointsSegmentsMap.get(segment.start.osmid);
                }else{
                  localSegmentsList = new ArrayList<Segment>();
                  allPointsSegmentsMap.put(segment.start.osmid, localSegmentsList);
                }
                localSegmentsList.add(segment);
                
                if (segment.directed) 
                  continue;
                
                localSegmentsList = null;
                
                
                if (allPointsSegmentsMap.containsKey(segment.end.osmid)){
                  localSegmentsList = allPointsSegmentsMap.get(segment.end.osmid);
                }else{
                  localSegmentsList = new ArrayList<Segment>();
                  allPointsSegmentsMap.put(segment.end.osmid, localSegmentsList);
                }
                localSegmentsList.add(segment);
                
            }
        }
        
        // some queries...
        public boolean isPoint(int pointId){
          Point value;
          if (allPointsMap.containsKey(pointId)){
            value = allPointsMap.get(pointId);
          }else{
            return false;
          }
          return (value != null); 
        }
        
        public List<Integer> getOutcommingEdges(int nodeId){
            List<Segment> edgeList;
//            if (!allPointsSegmentsMap.TryGetValue(nodeId, out list)) return null;
             if (allPointsSegmentsMap.containsKey(nodeId)){
              edgeList = allPointsSegmentsMap.get(nodeId);
            }else{
              return null;
            }
            
            List<Integer> ret = new ArrayList<Integer>();
            
            for (Segment edge : edgeList){
                ret.add(edge.id);
            }
            return ret;
        }
        
        public List<Integer> getNeighbourPoints(int pointId){
          
            List<Segment> segmentList;
            if (allPointsSegmentsMap.containsKey(pointId)){
              segmentList = allPointsSegmentsMap.get(pointId);
            }else{
              return null;
            }
            
            List<Integer> neighbourPointsList = new ArrayList<Integer>();
            for (Segment segment : segmentList){
                if (pointId == segment.start.osmid) 
                  neighbourPointsList.add(segment.end.osmid);
                if (pointId == segment.end.osmid) 
                  neighbourPointsList.add(segment.start.osmid);
            }
            return neighbourPointsList;
        }
        
        public List<Segment> getOutcomingEdges(int nodeId){
            List<Segment> list;
            if (allPointsSegmentsMap.containsKey(nodeId)){
              list = (List<Segment>) allPointsSegmentsMap.get(nodeId);
            }else{
              return null;
            }
            
            return list;
        }

        //Wyszukiwanie odcinkow z listy wszytkich -podajemy od kod(id1) do kod (id2)
        public int obtainSegmentIdFromAllPointsSegmentsMap(int nodeIdFirst, int nodeIdSecond){
          
//            19270;21072913;261732024;261732023;0.114562;50;0;-80.590779;-80.658910
            List<Segment> segmentsList;
            if (allPointsSegmentsMap.containsKey(nodeIdFirst)){
              segmentsList = allPointsSegmentsMap.get(nodeIdFirst);
            }else{
              return -1;
            }
            
            //sprawdzamy segmenty ktorych start zaczyna sie w punkcie nodeIdFirst a konczy w punkcie nodeIdSecond
            //zwracamy jego id klucza mapy allPointsSegmentsMap
            for (Segment segment : segmentsList){
                if (nodeIdFirst == segment.start.osmid && nodeIdSecond == segment.end.osmid) 
                  return segment.id;
                if (nodeIdFirst == segment.end.osmid && nodeIdSecond == segment.start.osmid)
                  return segment.id;
            }
            return -1;
        }
        
        public int obtainSegmentWayIdFromAllPointsSegmentsMap(int nodeIdFirst, int nodeIdSecond){
          
//            19270;21072913;261732024;261732023;0.114562;50;0;-80.590779;-80.658910
            List<Segment> segmentsList;
            if (allPointsSegmentsMap.containsKey(nodeIdFirst)){
              segmentsList = allPointsSegmentsMap.get(nodeIdFirst);
            }else{
              return -1;
            }
            
            //sprawdzamy segmenty ktorych start zaczyna sie w punkcie nodeIdFirst a konczy w punkcie nodeIdSecond
            //zwracamy jego id klucza mapy allPointsSegmentsMap
            for (Segment segment : segmentsList){
                if (nodeIdFirst == segment.start.osmid && nodeIdSecond == segment.end.osmid) 
                  return segment.wayId;
                if (nodeIdFirst == segment.end.osmid && nodeIdSecond == segment.start.osmid)
                  return segment.wayId;
            }
            return -1;
        }
        
        
        public Segment getEdgeById(int edge){
            Segment e;
             if (allSegmentsMap.containsKey(edge)){
              e = allSegmentsMap.get(edge);
            }else{
              return null;
            }
            return e;
        }
        
        public double getSegmentsCost(int segmentId, int segmentWayId) throws Exception {
          
          //segment id:
          //19270;21072913;261732024;261732023;0.114562;50;0;-80.590779;-80.658910
          
          Segment segment = allSegmentsMap.get(segmentId);
          String criteriaFactor = Program.getTrafficParameterNumber() + Integer.toString(segmentWayId);
          
          //jesli mapa zawiera faktor z tym id: 
          if(allTrafficParametersNew.containsKey(criteriaFactor)){
             System.out.println("TRAFFIC MAP CONTAINS criteria: " + criteriaFactor);  
             
             TrafficTypeFactory trafficTypeFactory = new TrafficTypeFactory();
             
              //DOUBLE
              if(Program.getTrafficParameterTypeName().equals("double")){
                    ITrafficType trafficParamNew = trafficTypeFactory.obtainTrafficParameterInstance(Program.getTrafficParameterTypeName());
                    //dopasowac ITraficType z TraficParameter!!!!!!!!!!!!!!569
                    TrafficParameter trafficParameter = allTrafficParametersNew.get(criteriaFactor);

                    trafficParamNew.setTrafficParameter(trafficParameter.getPomiar());
                    trafficParamNew.setIdTypuPomiaru(trafficParameter.getIdTypuPomiaru());
                    trafficParamNew.setNazwaPomiaru(trafficParameter.getNazwaPomiaru());
                    trafficParamNew.setNazwaTypuPomiaru(trafficParameter.getNazwaTypuPomiaru());

                    double averageSpeedFromTraffic = (Double) trafficParamNew.getTrafficParameter();

                    TRAFFIC_PARAMETER = averageSpeedFromTraffic; // zapisuje do dodatkowej zmiennej bo uzywam jeszcze w kilku miejscach

                    
                    if(averageSpeedFromTraffic != 0){
                      //parametr AverageSpeed dzieli dlugosc miedzy odcinkami
                      double time = segment.length / averageSpeedFromTraffic;
                      return time;
                    }else{
                      //przelicz bez traffic
                      return getSegmentsCostWithoutTrafficParameters(segmentId);
                    }

              //INTEGER  
              }else if(Program.getTrafficParameterTypeName().equals("int")){
                
                    ITrafficType trafficParamNew = trafficTypeFactory.obtainTrafficParameterInstance(Program.getTrafficParameterTypeName());
                    TrafficParameter trafficParameter = allTrafficParametersNew.get(criteriaFactor);

                    trafficParamNew.setTrafficParameter(trafficParameter.getPomiar());
                    trafficParamNew.setIdTypuPomiaru(trafficParameter.getIdTypuPomiaru());
                    trafficParamNew.setNazwaPomiaru(trafficParameter.getNazwaPomiaru());
                    trafficParamNew.setNazwaTypuPomiaru(trafficParameter.getNazwaTypuPomiaru());

                    int lengthOfVehicleQueue = (Integer) trafficParamNew.getTrafficParameter();

                    TRAFFIC_PARAMETER = lengthOfVehicleQueue; // zapisuje do dodatkowej zmiennej bo uzywam jeszcze w kilku miejscach

                     if(lengthOfVehicleQueue == 999999){
                        System.out.println("TRAFFIC == 999999");
                        return getSegmentsCostWithoutTrafficParameters(segmentId);
                      }else{
                        //parametr AverageSpeed dzieli dlugoscmiedzy odcinkami
                        double time = (segment.length / segment.maxspeed) + obtainTimeToRidingWithQueues(lengthOfVehicleQueue);
                        return time;
                      }
              }else{
                System.out.println("Nie ma reguly dla obliczania");
                return getSegmentsCostWithoutTrafficParameters(segmentId);
              }
              
              
          //STANDARDOWE LICZENIE- BEZ PARAMETROW TRAFFIC
          }else{
            System.out.println("TRAFFIC MAP NOOT!!! CONTAINS " + criteriaFactor);  
            return getSegmentsCostWithoutTrafficParameters(segmentId);
          }
            
//          TrafficParametersController trafficParameterController = new TrafficParametersController();
//          
//          ITrafficParameter trafficParameter = trafficParameterController.obtainTrafficParameterByType(Program.trafficParameterTypeName, segmentId);
//          return trafficParameter.obtainWage();
          
          
          
          
//          //STANDARDOWE LICZENIE- BEZ PARAMETROW TRAFFIC
//          if(allTrafficParameters.isEmpty() && Program.TRAFFIC_PARAMETER_TYPE.isEmpty()){
//             return trafficParameter.obtainWage();
////            return getSegmentsCostWithoutTrafficParameters(segmentId);
//          }
//
//          
//          //AVERANGE SPEED 
//          else if(trafficParameter.getClass().getSimpleName().equals(OSMGraphModelImp.AVERAGE_SPEED_PARAMETER)){
//            return trafficParameter.obtainWage();
//            return obtainTimeDrivingByTrafficAverageSpeedParameter(segmentId, trafficParameter);
//          }
//
//          
//          //QUEUE VEHICLE 
//          else if(trafficParameter.getClass().getSimpleName().equals(OSMGraphModelImp.LENGTH_OF_VEHICLE_QUEUE_PARAMETER)){
//            return obtainTimeDrivingFromTrafficLengthofVehicleQueueParameter(segmentId, trafficParameter);
//          }
//
//          
//          //WET ROAD LICZENIE
//          else if(trafficParameter.getClass().getSimpleName().equals(OSMGraphModelImp.WEATHER_CONDITIONS_PARAMETER)){
//            return obtainTimeDrivingFromTrafficWeatherConditionsParameter(segmentId, trafficParameter);
//          }  
//
//          
//          else{
//            System.out.println("Nie ma parametrow Ruchu Drogowego ani ID odcinka w mapie wszystkich odcinkow");
//            return 1e30;
//          }
        }
        
        
        public double obtainTimeToRidingWithQueues(int queueLength){

          int acceleration = 2;// m/s^2
          int lengthToRide = queueLength * 5; //5metrow srednio ma samochod (~~osobowy, ciezarowy, tir..)

          double time = Math.sqrt(lengthToRide);// jesli acceleration jest inne niz 2, wzor nie dziala
          System.out.println("Dla kolejki1 o liczbie " + queueLength + " dlugosc ruszania to " + time + " sekund");
          return time;
        }
        
        
        public double getEdgeDistance(int edge){
            Segment e;
             if (allSegmentsMap.containsKey(edge)){
              e = allSegmentsMap.get(edge);
            }else{
              return 1e30;
            }
            
            return e.length;
        }
        
        public double getEdgeSpeed(int edge){
            Segment e;
            if (allSegmentsMap.containsKey(edge)){
              e = allSegmentsMap.get(edge);
            }else{
              return 1e-10;
            }
            return e.maxspeed;
        }


        public double getPathCost(int nodeIdFirst, int nodeIdSecond) throws Exception{
            int edgeId = obtainSegmentIdFromAllPointsSegmentsMap(nodeIdFirst, nodeIdSecond);
            int segmentWayId = obtainSegmentWayIdFromAllPointsSegmentsMap(nodeIdFirst, nodeIdSecond);
            //19270
            if (edgeId < 0) 
              return 1e10;
            
            return getSegmentsCost(edgeId, segmentWayId);
        }
        
        public int getEdgeStartNode(int edge){
            Segment e;
            if (allSegmentsMap.containsKey(edge)){
              e = allSegmentsMap.get(edge);
            }else{
              return -1;
            }
            return e.start.osmid;
        }
        
        public int getEdgesCount(){
            return allSegmentsMap.size();//bylo Count()
        }

        public int getNodeDegree(int nodeId){
            Point n;
            if (allPointsMap.containsKey(nodeId)){
              n = allPointsMap.get(nodeId);
            }else{
              return 0;
            }
            return n.degree;
        }
        
        public double estimateDistance(int nodeId1, int nodeId2){
            Point n1;
            Point n2;
            if (allPointsMap.containsKey(nodeId1)){
              n1 = allPointsMap.get(nodeId1);
            }else{
              return 1e10;
            }
            
            if (allPointsMap.containsKey(nodeId2)){
              n2 = allPointsMap.get(nodeId2);
            }else{
              return 1e10;
            }
            
            return calcuateDistance(n1, n2);
        }
        
        double calcuateDistance(Point nodeFirst, Point nodeSecond){
            // HAVERSINE FORMULA- do wyznaczania odleglosci
            double R = 6371; // [km] promien od centrum do powierzchni ziemi
            double dLat = (nodeFirst.latitude - nodeSecond.latitude) * Math.PI / 180;
            double dLon = (nodeFirst.longitude - nodeSecond.longitude) * Math.PI / 180;
            double lat1 = nodeFirst.latitude * Math.PI / 180;
            double lat2 = nodeSecond.latitude * Math.PI / 180;

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c;
            return distance;
        }

        
        //Sprawdzanie czy id1-start i id2-meta zawieraja sie w mapie punktow:
        public double estimateCost(int id1, int id2){
            Point nodeFirst; 
            Point nodeSecond;
            if (allPointsMap.containsKey(id1)){
              nodeFirst = allPointsMap.get(id1);
            }else{
              return 1e10; //1*10^10
            }
            if (allPointsMap.containsKey(id2)){
              nodeSecond = allPointsMap.get(id2);
            }else{
              return 1e10;
            }
            //wyliczanie odlegnosci pomiedzy dwoma nodami
            return calcuateDistance(nodeFirst, nodeSecond)/140;
        }
        
        public List<Integer> drawSomeRandomNodes(int count, double center_lat, double center_lon, double radius){
            Point center = new Point();
            center.latitude = center_lat;
            center.longitude = center_lon;

            List<Integer> nodesWithinRadius = new ArrayList<Integer>();
            for(Point n : allPointsMap.values()){
                if (calcuateDistance(center, n) <= radius) nodesWithinRadius.add(n.osmid);
            }

            System.out.println("There are " + nodesWithinRadius.size() + " nodes within radius of "+radius+"km from the point ("+center_lat+","+center_lon+")");

            List<Integer> result = new ArrayList<Integer>();
            Calendar rightNow = Calendar.getInstance();
            
            Random random = new Random(rightNow.getTimeInMillis());
            int limit = 0;
            
            while (result.size() < count) {
                if (limit == nodesWithinRadius.size() * nodesWithinRadius.size()) break;
                int idx = random.nextInt(nodesWithinRadius.size()); // bylo (0, nodesWithinRadius.size()) 
                int id = nodesWithinRadius.get(idx);
                if (result.contains(id) && result.size() % 2 == 1 && result.get(result.size() - 1) == id){
                  continue;
                }
                result.add(id);
                limit++;
            }

            return result;
        }
        
        public double getTurnCost(int node1, int node2, int node3){
          
            int edgeId1 = obtainSegmentIdFromAllPointsSegmentsMap(node1, node2);
            int edgeId2 = obtainSegmentIdFromAllPointsSegmentsMap(node2, node3);
            if (edgeId1 == -1 || edgeId2 == -1) return 10e10;
            Segment e1 = getEdgeById(edgeId1);
            Segment e2 = getEdgeById(edgeId2);
            double azimuth1, azimuth2;
            azimuth1 = e1.start.osmid == node1 ? e1.endAzimuth : e1.startAzimuth+180;
            azimuth2 = e2.start.osmid == node2 ? e2.startAzimuth : e2.endAzimuth+180;
            double speed1 = e1.maxspeed;
            double speed2 = e2.maxspeed;
            
            // we calculate vector product
            azimuth1 *= Math.PI / 180;
            azimuth2 *= Math.PI / 180;
            double x1 = Math.cos(azimuth1), x2 = Math.cos(azimuth2), y1 = Math.sin(azimuth1), y2 = Math.sin(azimuth2);

            double vprod = x1 * y2 - x2 * y1;
            //double refSpeed = speed2 < speed1 ? speed2 : speed1;
            double turnSpeed = Math.min(speed1, speed2) * (1 - 0.90 * Math.abs(vprod));
            if (turnSpeed < 0) turnSpeed = 0;

            double acceleration = 100 / 15 * 3600;
            // http://www.prawko-kwartnik.info/hamowanie.html
            double breaking = 70 / 6.4 * 3600;
            double cost = 0;
            if (speed1 > turnSpeed) cost += (speed1 - turnSpeed) / breaking;
            if (speed2 > turnSpeed) cost += (speed2 - turnSpeed) / acceleration;

            double mul = 1;

            if (vprod < -.8) mul = 1.3; // skręt w lewo
            if (x1 * x2 + y1 * y2 < 0 * Math.PI) mul = 2; // u-turn scalar product

            // NORTH 0
            // WEST -90
            // EAST 90
            // SOUTH -180, 180
            return cost * mul;
        }
        
        private static String readUrl(String urlString) throws Exception {
          BufferedReader reader = null;
          try
          {
              URL url = new URL(urlString);
              reader = new BufferedReader(new InputStreamReader(url.openStream()));
              StringBuffer buffer = new StringBuffer();
              int read;
    
          char[] chars = new char[1024];
              while ((read = reader.read(chars)) != -1)
                  buffer.append(chars, 0, read);

              return buffer.toString();
          } finally
          {
              if (reader != null)
                  reader.close();
          }
	}

  public void putPointToAllPointMap(String line) throws NumberFormatException {
    String[] fields = line.split(";");
    Point point = new Point();
    point.osmid = Integer.parseInt(fields[0]);
    point.latitude = Double.parseDouble(fields[1]);
    point.longitude = Double.parseDouble(fields[2]);
    point.degree = Integer.parseInt(fields[3]);
    allPointsMap.put(point.osmid, point);
  }

  public void putSegmentToAllSegmentsMap(String line, int progress) throws Exception, NumberFormatException {
    String[] fields;
    fields = line.split(";");

    Segment segment = new Segment();
    segment.id = Integer.parseInt(fields[0]);
    segment.wayId = Integer.parseInt(fields[1]);

    int osmStartPoint = Integer.parseInt(fields[2]);

    if(allPointsMap.containsKey(osmStartPoint)){
      segment.start = allPointsMap.get(osmStartPoint);
    }else{
      throw new Exception("Error in line: " + progress);
    }

    int osmEndNode = Integer.parseInt(fields[3]);
    if(allPointsMap.containsKey(osmEndNode)){
      segment.end = allPointsMap.get(osmEndNode);
    }else{
      throw new Exception("Error in line: " + progress);
    }

    segment.length = Double.parseDouble(fields[4]);
    segment.maxspeed = Integer.parseInt(fields[5]);
    segment.directed = (Integer.parseInt(fields[6]) == 0 ? false : true);
    segment.startAzimuth = Double.parseDouble(fields[7]);
    segment.endAzimuth = Double.parseDouble(fields[8]);

    allSegmentsMap.put(segment.id, segment);
  }

  public double getSegmentsCostWithoutTrafficParameters(int segmentId) {
    Segment segment;
    if (allSegmentsMap.containsKey(segmentId)){
//              Pobierane segmentu o danym ID, 
//              Pakowanie Traffic do segmentu z mapy o tym samym ID
      segment = allSegmentsMap.get(segmentId);
    }else{
      return 1e30;
    }
    
    if (segment.maxspeed < 1e-5) // 1e-5 =  1 x 10^-5 = 0.00001 
      return 1e30;
    double time = segment.length / segment.maxspeed;
    return time;
  }

//  public double getSegmentsCostRelyingTrafficParametrs(int segmentId, final int intermediateTrafficLevel, int avgTraffic) {
//    Segment segmentWithTraffic;
//    Segment segment;
////    System.out.println("Wyliczam sciezke dla odcinka na podstawie PARAMETROW RUCHU DROGOWEGO");
//    
//    //Pobierane segmentu o danym ID,
//    segmentWithTraffic = allTrafficParameters.get(segmentId);
//    segment = allSegmentsMap.get(segmentId);
//    
//    //Jesli dany odcinek/segment nie ma parametru pomiarowego to nadaj mu traffic SREDNI
//    if(segmentWithTraffic.getTraffic() == 0){
//      //pobieranie wczesniejszych trafficow i wyliczanie jego sredniej lub wpis z palca (jesi nie ma z czego wyliczac)
//      if(trafficsInPath.isEmpty()){
//        segment.setTraffic(intermediateTrafficLevel);
//      }else{
//        for(int trafficFromList : trafficsInPath){
//          avgTraffic = avgTraffic + trafficFromList;
//        }
//        segment.setTraffic(avgTraffic / trafficsInPath.size()); //Pakowanie do segmentu sredniej wszystkich trafficow z listy
//      }
//      
//    } else{
//      segment.setTraffic(segmentWithTraffic.getTraffic()); //Pakowanie Traffic do segmentu z mapy o tym samym ID
//    }
//    
//    //Pakowanie do listy trafficow - w celu wyliczania sredniej gdy nie ma parametru la segmentu/odcinka
//    trafficsInPath.add(segmentWithTraffic.getTraffic());
//    System.out.println("==========================================================================");
//    System.out.println("Dla odcinka o id: " + segmentId + " gdzie idStart: " +segment.getStart().osmid + " i idEnd: " +segment.getEnd().osmid
//            + " jest zaladowany traffic : " + segment.getTraffic());
//    
//    if (segment.maxspeed < 1e-5) // 1e-5 =  1 x 10^-5 = 0.00001 
//      return 1e30;
//    
//    
//    double time = (segment.length / segment.maxspeed ) * segment.getTraffic();
//    // czas = dlugosc / max speed time = 0.114562 / 50 = 0.00229124
//    
//    System.out.println("Time: " + time + " Length: " + segment.length + " MaxSpeed: " + segment.maxspeed);
//    
//    return time;
//
//  }
  
  public Double getStartLatitudeSegmentById(int segmentId){
    Segment segment = allSegmentsMap.get(segmentId);
    return segment.start.latitude;
  }
  
  public Double getStartLongitudeSegmentById(int segmentId){
    Segment segment = allSegmentsMap.get(segmentId);
    return segment.start.longitude;
  }
  
  public Double getEndLongitudeSegmentById(int segmentId){
    Segment segment = allSegmentsMap.get(segmentId);
    return segment.end.longitude;
  }
  
  public Double getEndLatitudeSegmentById(int segmentId){
    Segment segment = allSegmentsMap.get(segmentId);
    return segment.end.latitude;
  }
  
  public Double getAverageTraffic(int segmentId) throws Exception{
    TrafficParametersController trafficParameterController = new TrafficParametersController();
    ITrafficParameter trafficParameter = trafficParameterController.obtainTrafficParameterByType(Program.trafficParameterTypeName, segmentId);
    return (Double) trafficParameter.getTrafficParameter();
  }
  
  public int getLengthOfVehicleQueueTraffic(int segmentId) throws Exception{
    TrafficParametersController trafficParameterController = new TrafficParametersController();
    ITrafficParameter trafficParameter = trafficParameterController.obtainTrafficParameterByType(Program.trafficParameterTypeName, segmentId);
    return (Integer) trafficParameter.getTrafficParameter();
  }
  
   public boolean getWeatherConditionsTraffic(int segmentId) throws Exception{
    TrafficParametersController trafficParameterController = new TrafficParametersController();
    ITrafficParameter trafficParameter = trafficParameterController.obtainTrafficParameterByType(Program.trafficParameterTypeName, segmentId);
    return (Boolean) trafficParameter.getTrafficParameter();
  }

  public int obtainMinutesBetweenCurrentAndTrafficParameterTime(ITrafficParameter trafficParameter) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateTime trafficParameterDateTime = trafficParameter.getDate();
    //              "2014-11-13 21:26:21.117"
    DateTime currentDate = new DateTime(dateFormat.parse("2014-11-18 21:20:21"));
    
    int minutsBetweensTrafficAndCurrentTime = Minutes.minutesBetween(trafficParameterDateTime, currentDate).getMinutes();
    return minutsBetweensTrafficAndCurrentTime;
  }

//  public double obtainTimeDrivingByTrafficAverageSpeedParameter(int segmentId, ITrafficParameter trafficParameter) throws ParseException {
//    double time;
//    System.out.println("AVERAGE TRAFFIC PARAMETER");
//    Segment segment = allSegmentsMap.get(segmentId);//Pobierane segmentu o danym ID
//    int minutsBetweensTrafficAndCurrentTime = obtainMinutesBetweenCurrentAndTrafficParameterTime(trafficParameter);
//    System.out.println("minutes:  (max 15): " + minutsBetweensTrafficAndCurrentTime);
//    //jesli prametr jest sprzed nie wiecej niz 10 minut to obliczaj z parametrem
//    
//    if(minutsBetweensTrafficAndCurrentTime < offsetBetweenTrafficParameterTime){
//      double averageSpeedParameter = (double) trafficParameter.getTrafficParameter();
////                  double time = segment.length / (averageSpeedParameter / segment.maxspeed);
//      time = segment.length / averageSpeedParameter;
//      return time;
//
//    }else{
//      return getSegmentsCostWithoutTrafficParameters(segmentId);
//    }
//  }

//  public double obtainTimeDrivingFromTrafficLengthofVehicleQueueParameter(int segmentId, ITrafficParameter trafficParameter) throws ParseException {
//    double time;
//    System.out.println("LENGTH QUEUE");
//    Segment segment = allSegmentsMap.get(segmentId);//Pobierane segmentu o danym ID
//    int minutsBetweensTrafficAndCurrentTime = obtainMinutesBetweenCurrentAndTrafficParameterTime(trafficParameter);
//    System.out.println("minutes:  (max 15): " + minutsBetweensTrafficAndCurrentTime);
//    /*
//    5 samochodow w kolejce to okolo 25 metrow przed zakretem, dodaje sie przez to czas, samochod musi stanac i ruszyc
//     * Co przy zalozeniach ze przyspieszenie srednie auta wynosi 2m/s^2, wzor:
//     * s = (acceleration * time ^2 ) / 2
//     * Przy zalozeniu ze 5 samochodow to 25 metrow, czas czekania to pierwiastek z liczby samochodow -> 5;
//     * !!!!!Sprawdzic jak sie dodaje czas, metoda obtainTimeRidingInQueues zwraca double w secundach
//     * a tutaj czas jest w dziesietnych!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//     */
//    //jesli prametr jest sprzed nie wiecej niz 10 minut to obliczaj z parametrem
//    if(minutsBetweensTrafficAndCurrentTime < offsetBetweenTrafficParameterTime){
//      int lengthOfVehicleQueue = (int) trafficParameter.getTrafficParameter();
//      
//      //jesli nie ma kolejki, czas normalny
//      if(lengthOfVehicleQueue == 0){
//        time = segment.length / segment.maxspeed;
//      }
//                  time = segment.length / (segment.maxspeed * lengthOfVehicleQueue);
//      //zwykly czas dojazdu plus to co odstanie w kolejce
//      time = (segment.length / segment.maxspeed) + obtainTimeToRidingWithQueues(lengthOfVehicleQueue);
//      return time;
//
//    }else{
//      return getSegmentsCostWithoutTrafficParameters(segmentId);
//    }
//  }

//  public double obtainTimeDrivingFromTrafficWeatherConditionsParameter(int segmentId, ITrafficParameter trafficParameter) throws ParseException {
//    double time;
//    System.out.println("WEATHER CONDITIONS");
//    Segment segment = allSegmentsMap.get(segmentId);//Pobierane segmentu o danym ID
//    int minutsBetweensTrafficAndCurrentTime = obtainMinutesBetweenCurrentAndTrafficParameterTime(trafficParameter);
//    System.out.println("minutes:  (max 15): " + minutsBetweensTrafficAndCurrentTime);
//    //jesli prametr jest sprzed nie wiecej niz 10 minut to obliczaj z parametrem
//    if(minutsBetweensTrafficAndCurrentTime < offsetBetweenTrafficParameterTime){
//      boolean isWetRoad = (boolean) trafficParameter.getTrafficParameter();
////                  double time = segment.length / (averageSpeedParameter / segment.maxspeed);
//      if(isWetRoad){
//        time = 2 * (segment.length / segment.maxspeed);
//      }else{
//        time = segment.length / segment.maxspeed;
//      }
//      return time;
//
//    }else{
//      return getSegmentsCostWithoutTrafficParameters(segmentId);
//    }
//  }
  
}


