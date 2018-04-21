package osmgraphmodel.Algorithms;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import osmgraphmodel.OSMGraphModelImp;
import osmgraphmodel.TestDef;

/**
 *
 * @author Michał
 */
//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using System.IO;
//
//namespace OSMGraphModel
//{
    public class AStarOrginal  {

         public static class Node  {
            public double g_costFromStartToThisNode; // G   cummulative cost from the start to this nodde 
            public double h_distanceToGoal; // H   heuristic estimate of distance to goal
            public double f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal; // F  sum of cumulative cost of predecessors and self and heuristic

            public Node parent = null;
            public Node child=null;
            public int nodeId;
        }

//        public static class OpenList implements Comparator<Node>  {
////            List<Node> nodeList = new List<Node>();
//            List<Node> nodeList = new ArrayList<Node>();
////            static int compareNodes(Node nodeFirst, Node nodeSecond){
////                if (nodeFirst.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal > nodeSecond.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal)
////                  return 1;
////                if (nodeFirst.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal < nodeSecond.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal)
////                  return -1;
////                return 0;
////            }
//          @Override
//            public int compare(Node nodeFirst, Node nodeSecond) {
//                if (nodeFirst.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal > nodeSecond.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal)
//                  return 1;
//                if (nodeFirst.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal < nodeSecond.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal)
//                  return -1;
//                return 0;
//            }
//            
//            public int Count(){
////                return nodeList.Count();
//                return nodeList.size();
//            }
//            
//            //dodaje i po dodaniu przesortowuje wg. compareNodes
//            public void Put(Node node){
////                nodeList.Add(node);
//                nodeList.add(node);
////                nodeList.Sort(compareNodes);
//                Collections.sort(nodeList, new OpenList());
//            }
//            
//            public Node Get(){
////                if(nodeList.Count() == 0)return null;
//                if(nodeList.size() == 0)return null;
////                Node node = nodeList.ElementAt(0);//sprawdzane elementu?
//                Node node = nodeList.get(0);//sprawdzane elementu?
////                nodeList.RemoveAt(0);//usuwanie?
//                nodeList.remove(0);//usuwanie?
//                
//                return node;
//            }
//            
//            public void Update(){
////                nodeList.Sort(compareNodes);
//                Collections.sort(nodeList, new OpenList());
//            }
//            
//            public Node Find(int id){
////                foreach (Node n in nodeList){
//                for (Node n : nodeList){
//                    if (n.nodeId == id) return n;
//                }
//                return null;
//            }
//
//        }
//        
//        
//        HashSet<Integer> closedList = new HashSet<Integer>();
//        OpenList openList = new OpenList();
//        
//        public int start;
//        public int goal;
//        public int progress = 0;
//        public int openListMaxCount = 0;
//        
//        public AStarOrginal(int start, int goal){
//            this.start = start;
//            this.goal = goal;
//        }
//        
//        //enum Result {OK,Fail};
//        public List<Integer> Run(){
//            OSMGraphModelImp model = OSMGraphModelImp.Get();
//            
//            if (!model.isPoint(start)) {
//              return null;
//            }
//            
//            if (!model.isPoint(goal)) {
//              return null;
//            }
//
//            Node startNode = new Node();
//            startNode.nodeId = start;
//            startNode.g_costFromStartToThisNode = 0;
//            startNode.h_distanceToGoal = model.estimateCost(start, goal);
//            startNode.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal = startNode.g_costFromStartToThisNode + startNode.h_distanceToGoal;
//            openList.Put(startNode);
//
//            
//            for (progress = 1; ; progress++ ){
//                
//              if (progress % 10000 == 0){
////                    Console.WriteLine("Iteration: " + progress + " Closed nodes: " + closedList.size() + " Open nodes:" + openList.Count());
//                    System.out.println("Iteration: " + progress + " Closed nodes: " + closedList.size() + " Open nodes:" + openList.Count());
//              }
//              
//              if (openList.Count() == 0){
//                  return null;
//              }
//                
//                // get current node
//                Node currentNode = openList.Get();
//                if (currentNode.nodeId == goal){
//                    return GetPath(currentNode);
//                }
//                
//                closedList.add(currentNode.nodeId);
//                
//                List<Integer> neighbours = model.getNeighbourPoints(currentNode.nodeId);
//                
//                if (neighbours == null){
//                    //TODO report black hole
//                    // Droga jednokierunkowa prowadząca do posesji, wjazd na stację benzynową, itp.
//                    TestDef.blackHoles.add(currentNode.nodeId);
//                    continue;
//                }
//                for (int n : neighbours){
//                    if (closedList.contains(n)) {
//                      continue;
//                    }
//                    
//                    double tentative_g = currentNode.g_costFromStartToThisNode + model.getPathCost(currentNode.nodeId, n);
//                    Node node = openList.Find(n);
//                    if (node == null){
//                        node = new Node();
//                        node.nodeId = n;
//                        node.parent = currentNode;
//                        node.g_costFromStartToThisNode = tentative_g;
//                        node.h_distanceToGoal = model.estimateCost(node.nodeId, goal);
//                        node.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal = node.g_costFromStartToThisNode + node.h_distanceToGoal;
//                        openList.Put(node);
//                        
//                        if (openListMaxCount < openList.Count()) {
//                           openListMaxCount = openList.Count();
//                        }
//                    }else{
//                        // update values
//                        if (tentative_g < node.g_costFromStartToThisNode){
//                            node.parent = currentNode;
//                            node.g_costFromStartToThisNode = tentative_g;
//                            node.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal = node.g_costFromStartToThisNode + node.h_distanceToGoal;
//                            openList.Update();
//                        }
//                    }
//
//                }
//            }
////            return null;
//        }
//
//        public List<Integer> GetPath(Node goal){
//            Node goalNode = goal;
//            Node startNode = null;
//            
//            while (goalNode != null){
//                if(goalNode.parent != null){
//                  goalNode.parent.child = goalNode;
//                }
//                startNode = goalNode;
//                goalNode = goalNode.parent;
//            }
//            List<Integer> list = new ArrayList<Integer>();
//            
//            for ( ; startNode != null; startNode = startNode.child){
//                list.add(startNode.nodeId);
//            }
//            return list;
//        }
//
//        /// <summary>
//        /// //////////////////////////////
//        /// </summary>
//        
//        public static void testPriorityQueue(){
////            SortedList<double, String> openList = new SortedList<double, String>();
//            TreeMap<Double, String> openList = new TreeMap<Double, String>();
////            openList.Add(.1, "low");
////            openList.Add(.8, "high");
////            openList.Add(.5, "medium");
////            openList.Add(.5, "medium2");
//            openList.put(.1, "low");
//            openList.put(.8, "high");
//            openList.put(.5, "medium");
//            openList.put(.5, "medium2");
//
////            while (openList.Count() > 0){
////                double d = openList.Keys[0];
////                String val = openList[d];
////                Console.WriteLine(d + ">>" + val);
////                openList.RemoveAt(0);
////            }
//            while (openList.size() > 0){
////              double d = openList.Keys[0];  
//                double openListFirstKey = openList.firstKey();// Napewno FirstKey?? 
////                String oldValue = myMap.put(1, "Jan Kowalski");
////                String val = openList[d];
//                String value = openList.get(openListFirstKey);
//                System.out.println(openListFirstKey + ">>" + value);
////                Console.WriteLine(d + ">>" + val);
//                openList.remove(0);
//            }
//        }


    }



