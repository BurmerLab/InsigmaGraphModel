package pl.webservice.rest;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import osmgraphmodel.Algorithms.AStar;
import osmgraphmodel.OSMGraphModelImp;
import osmgraphmodel.Point;
import osmgraphmodel.Program;
import osmgraphmodel.TestDef;
import pl.pojo.GeneralPathWithStatisticsResult;
import pl.pojo.Segment;
import pl.pojo.Statistics;
 
@Path("/obtain")
public class InsigmaREST {
  
    //1 AverageSpeed
    //2 LengthOfVehicleQueue
    //3 WeatherConditions
  
//http://localhost:8084/InsigmaGraphModel/rest/obtain/path/from/261732023/to/207426792/traffic/2
	@GET
	@Path("/path/from/{idStart}/to/{idEnd}/traffic/{trafficParameter}")
	@Produces(MediaType.APPLICATION_JSON)
	public String obtainPathWithGeneralStatistics(@PathParam("idStart") int idStart,
                                                  @PathParam("idEnd") int idEnd,
                                                  @PathParam("trafficParameter") int trafficParameter) throws Exception{
      Program program = new Program();
      GeneralPathWithStatisticsResult generalStatisticsOfPath = program.obtainFinalPath(idStart, idEnd, trafficParameter);
      
      Gson gson = new Gson();
      System.out.println(gson.toJson(generalStatisticsOfPath));
      return gson.toJson(generalStatisticsOfPath);
	}
 
    
//    http://localhost:8084/InsigmaGraphModel/rest/obtain/loadVectorDataFromFile
    @GET
	@Path("/loadVectorDataFromFile")
    @Produces("application/json")
	public String loadVectorDataFromFile(){
      Boolean isCorrectlyEnd;
      
      try {
        Program program = new Program();
        program.loadVectorDataFromFile();
        program.makeIndexForPointSegment();
//        program.loadTrafficParametersFromDatabase();
        isCorrectlyEnd = true;
        
      } catch (Exception ex) {
        Logger.getLogger(InsigmaREST.class.getName()).log(Level.SEVERE, null, ex);
        isCorrectlyEnd = false;
      }
	   return isCorrectlyEnd.toString();
	}
    
//    http://localhost:8084/InsigmaGraphModel/rest/obtain/checkIsMapLoaded
    @GET
	@Path("/checkIsMapLoaded")
    @Produces("application/json")
	public String checkIsMapLoaded(){
      Boolean isCorrectlyEnd = null;
      
      try {
      if(OSMGraphModelImp.Get().equals(null)){
        System.out.println("JEST NULLEM");
        isCorrectlyEnd = true;
      }else{
       System.out.println("KLUCZY JEST: " + OSMGraphModelImp.allSegmentsMap.size());
       System.out.println("NIE JEST NULLEM");
       isCorrectlyEnd = false;
      }
        
      } catch (Exception ex) {
        Logger.getLogger(InsigmaREST.class.getName()).log(Level.SEVERE, null, ex);
      }
	   return isCorrectlyEnd.toString();
	}
    
    //    http://localhost:8084/InsigmaGraphModel/rest/obtain/checkIsTrafficParametersLoaded
    @GET
	@Path("/checkIsTrafficParametersLoaded")
    @Produces("application/json")
	public String checkIsTrafficParametersLoaded(){
      Boolean isCorrectlyEnd = null;
      
      try {
      if(OSMGraphModelImp.Get().equals(null)){
        System.out.println("JEST NULLEM");
        isCorrectlyEnd = true;
      }else{
       System.out.println("KLUCZY JEST: " + OSMGraphModelImp.allTrafficParametersNew.size());
       System.out.println("NIE JEST NULLEM");
       isCorrectlyEnd = false;
      }
        
      } catch (Exception ex) {
        Logger.getLogger(InsigmaREST.class.getName()).log(Level.SEVERE, null, ex);
      }
	   return isCorrectlyEnd.toString();
	}
    
    
    
    
    
    
    
//    
    //http://localhost:8084/InsigmaGraphModel/rest/obtain/loadPointsMapFromAplication
    @GET
	@Path("/loadPointsMapFromAplication")
    @Produces("application/json")
	public String loadPointsMapFromAplication(){
      
      Map<Integer,Statistics> allParametersToCountPath = new HashMap<Integer, Statistics>();
      allParametersToCountPath = AStar.allParametersToCountPath;
        
      Gson gson = new Gson();
      System.out.println(gson.toJson(allParametersToCountPath));
      return gson.toJson(allParametersToCountPath);
	}
    
//    //http://localhost:8084/InsigmaGraphModel/rest/obtain/path/from/261732023/to/207426792/traffic/2
//	@GET
//	@Path("/statistics/from/{idStart}/to/{idEnd}/traffic/{trafficParameter}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String obtainStatisticsOfPath(@PathParam("idStart") int idStart,
//                                                  @PathParam("idEnd") int idEnd,
//                                                  @PathParam("trafficParameter") int trafficParameter) throws Exception{
//      Program program = new Program();
//      GeneralPathWithStatisticsResult generalStatisticsOfPath = program.obtainFinalPath(idStart, idEnd, trafficParameter);
//      
//      Gson gson = new Gson();
//      System.out.println(gson.toJson(generalStatisticsOfPath));
//      return gson.toJson(generalStatisticsOfPath);
//	}
    
    
    
    
    
}