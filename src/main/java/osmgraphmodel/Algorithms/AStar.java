package osmgraphmodel.Algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import osmgraphmodel.OSMGraphModelImp;
import osmgraphmodel.Program;
import osmgraphmodel.TestDef;
import pl.pojo.Segment;
import pl.pojo.Statistics;
import pl.repositoryTraffic.pojo.TrafficParameter;

/**
 *
 * @author Michał
 */
    public class AStar  {

      public static Map<Integer,Statistics> allParametersToCountPath = new HashMap<Integer, Statistics>();
      public  int temporaryIterator = 1;
      
  public boolean checkPointIsNotStartOrGoal(OSMGraphModelImp model) {
    if (!model.isPoint(start)) {
      return true;
    }
    if (!model.isPoint(goal)) {
      return true;
    }
    return false;
  }

  public boolean checkOneWayRoad(List<Integer> neighbours, Node currentNode) {
    if (neighbours == null) {
      // Droga jednokierunkowa prowadząca do posesji, wjazd na stację benzynową, itp.
      TestDef.blackHoles.add(currentNode.nodeId);
      return true;
    }
    return false;
  }

  public static class Node  {
     public double g_costFromStartToThisNode; // G   cummulative cost from the start to this nodde 
     public double h_distanceToGoal; // H   heuristic estimate of distance to goal
     public double f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal; // F  sum of cumulative cost of predecessors and self and heuristic

     public Node parent = null;
     public Node child=null;
     public int nodeId;
 }

  HashSet<Integer> closedList = new HashSet<Integer>(); //Zbiór wierzchołków przejrzanych.
  OpenList openList = new OpenList(); //Zbiór wierzchołków nie odwiedzonych.

  public int start;
  public int goal;
  public int progress = 0;
  public int openListMaxCount = 0;

  public AStar(int start, int goal){
      this.start = start;
      this.goal = goal;
  }
        
  //------Glowna metoda RUN- tu sie wszystko oblicza, zwraca liste--------
  public List<Integer> Run() throws Exception{
      allParametersToCountPath.clear();

      OSMGraphModelImp model = OSMGraphModelImp.Get();

      if(model.allSegmentsMap.isEmpty()){
        System.out.println("MAPA JEST PUSTA W ASTAR");
      }else{
        System.out.println("MAPA ASTAR: " + model.allSegmentsMap.size());
      }

      if (checkPointIsNotStartOrGoal(model))
        return null;

      Node firstNode = new Node();
      firstNode.nodeId = start;
      firstNode.g_costFromStartToThisNode = 0;
      firstNode.h_distanceToGoal = model.estimateCost(start, goal);
      firstNode.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal = firstNode.g_costFromStartToThisNode + firstNode.h_distanceToGoal;
      openList.Put(firstNode);

      for (progress = 1; ; progress++ ){
        //6 % 5 = 1 (modulo- reszta z dzielenia)

        if (progress % 1000 == 0) //Sluzy do wyswietlania aktualnego stanu iteracji (ile nodow przeszlo)
          System.out.println("Iteration: " + progress + " Closed nodes: " + closedList.size() + " Open nodes:" + openList.Count());

        if (openList.Count() == 0) 
          return null;

          // get current node
          Node currentNode = openList.Get();

          if (currentNode.nodeId == goal) {
            
            OSMGraphModelImp.TRAFFIC_PARAMETER = 0;//Ustawiam na zero zeby przy kolejnym uruchomieniu nie mialo wartosci
//                  ze zbioru punktow ktore przeszedl, wybiera sciezke 
            return getFinalPath(currentNode);
          }

          closedList.add(currentNode.nodeId);

          List<Integer> neighbours = model.getNeighbourPoints(currentNode.nodeId);

          // czy ulica ma sasiada?- jesli nie to jest jednokierunkowa
          if (checkOneWayRoad(neighbours, currentNode))
            continue;


          for (int neighbourId : neighbours){
            System.out.println("Sprawdzam Punkt sasiada ["+currentNode.nodeId+"] o  ID : " + neighbourId);

            int segmentId = model.obtainSegmentIdFromAllPointsSegmentsMap(currentNode.nodeId , neighbourId);
            Segment segment = model.getEdgeById(segmentId);

            Statistics statistics = new Statistics();

            statistics.setIdStart(segment.getStart().osmid);
            statistics.setIdEnd(segment.getEnd().osmid);
            statistics.setStartLatitude(segment.getStart().latitude);
            statistics.setStartLongitude(segment.getStart().longitude);

            statistics.setEndLatitude(segment.getEnd().latitude);
            statistics.setEndLongitude(segment.getEnd().longitude);

            if (closedList.contains(neighbourId)) {
                continue;
              }

//                  g_tentativeNeighbour - koszt od startu do aktualnego sąsiada... 
            double g_tentativeNeighbour = currentNode.g_costFromStartToThisNode + model.getPathCost(currentNode.nodeId, neighbourId); 

            String g_tentativeNeighbourInFormat = new DecimalFormat("#.###############").format(g_tentativeNeighbour);
            System.out.println("g_tentativeNeighbour: " + new DecimalFormat("#.###############").format(g_tentativeNeighbour));

            if(Program.trafficParameterTypeName.equals("double")){
              statistics.setAverageParmeter(OSMGraphModelImp.TRAFFIC_PARAMETER);
              OSMGraphModelImp.TRAFFIC_PARAMETER = 0;
              statistics.setCost(g_tentativeNeighbourInFormat);


            }else if(Program.trafficParameterTypeName.equals("int")){
//                    statisticLineString.append(g_tentativeNeighbourInFormat + " // " + model.getLengthOfVehicleQueueTraffic(segmentId));

            }else if(Program.trafficParameterTypeName.equals("WeatherConditions")){
//                    statisticLineString.append(g_tentativeNeighbourInFormat + " // " + model.getWeatherConditionsTraffic(segmentId));
            }

              System.out.println("======================================================================");
//                    statistics.setLineString(statisticLineString.toString());

              allParametersToCountPath.put(temporaryIterator, statistics);
              temporaryIterator++;

              Node node = openList.Find(neighbourId);
              if (node == null){
                  node = new Node();
                  node.nodeId = neighbourId;
                  node.parent = currentNode;
                  node.g_costFromStartToThisNode = g_tentativeNeighbour;
                  node.h_distanceToGoal = model.estimateCost(node.nodeId, goal);
                  node.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal = node.g_costFromStartToThisNode + node.h_distanceToGoal;
                  openList.Put(node);

                  if (openListMaxCount < openList.Count()) {
                     openListMaxCount = openList.Count();
                  }
              }else{
                  // update values
                  if (g_tentativeNeighbour < node.g_costFromStartToThisNode){
                      node.parent = currentNode;
                      node.g_costFromStartToThisNode = g_tentativeNeighbour;
                      node.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal = node.g_costFromStartToThisNode + node.h_distanceToGoal;
                      openList.Update();
                  }
              }

          }
      }
  }

        public List<Integer> getFinalPath(Node goal){
          Node goalNode = goal;
          Node startNode = null;

          while (goalNode != null){
              if(goalNode.parent != null){
                goalNode.parent.child = goalNode;
              }
              startNode = goalNode;
              goalNode = goalNode.parent;
          }
          
          List<Integer> list = new ArrayList<Integer>();

          for ( ; startNode != null; startNode = startNode.child){
              list.add(startNode.nodeId);
          }
          
          return list;
        }

        /// <summary>
        /// //////////////////////////////
        /// </summary>
        
        public static void testPriorityQueue(){
            TreeMap<Double, String> openList = new TreeMap<Double, String>();
            openList.put(.1, "low");
            openList.put(.8, "high");
            openList.put(.5, "medium");
            openList.put(.5, "medium2");
            
            while (openList.size() > 0){
                double openListFirstKey = openList.firstKey();// Napewno FirstKey?? 
                String value = openList.get(openListFirstKey);
                System.out.println(openListFirstKey + ">>" + value);
                openList.remove(0);
            }
        }
        
        public void printAllStatistics(){
          System.out.println("Key; IdStart; startLatitude; startLongitude; endLatitude; endLongitude; idEnd; cost");
          for (Map.Entry entry : allParametersToCountPath.entrySet()) {
              Statistics statistic = (Statistics) entry.getValue();
              OSMGraphModelImp model= OSMGraphModelImp.Get();
              
              System.out.println(entry.getKey() + " ; " + statistic.getIdStart()
                      + " ; " + statistic.getStartLatitude() + " ; " + statistic.getStartLongitude()
                      + " ; " + statistic.getEndLatitude() + " ; " + statistic.getEndLongitude()
                      + " ; " + statistic.getIdEnd() + " ; " + statistic.getCost()+ " ; " + statistic.getAverageParmeter());
          }
        }
        
        public void generateCsvFile(String sFileName){
          try{
              FileWriter writer = new FileWriter(sFileName);
              writer.append("Key; IdStart; wkt; idEnd; cost \n");

              for (Map.Entry entry : allParametersToCountPath.entrySet()) {
               Statistics statistic = (Statistics) entry.getValue();
               writer.append(statistic.getLineString() + "\n");  

               }

              writer.flush();
              writer.close();
            }
            catch(IOException e)
            {
                 e.printStackTrace();
            } 
         }
        
     


    }



