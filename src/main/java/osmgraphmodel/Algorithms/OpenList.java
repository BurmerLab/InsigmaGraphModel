/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmgraphmodel.Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Michał
 */
public class OpenList implements Comparator<AStar.Node>  {
            List<AStar.Node> nodeList = new ArrayList<AStar.Node>();

            @Override
            public int compare(AStar.Node nodeFirst, AStar.Node nodeSecond) {
                if (nodeFirst.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal > nodeSecond.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal)
                  return 1;
                if (nodeFirst.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal < nodeSecond.f_sumCostOfAccumulatedPredecessorsPlusSelfAndDistanceToGoal)
                  return -1;
                return 0;
            }
            
            public int Count(){
                return nodeList.size();
            }
            
            public void Put(AStar.Node node){
                nodeList.add(node);
                Collections.sort(nodeList, new OpenList());
            }
            
            public AStar.Node Get(){
                if(nodeList.size() == 0)return null;
                
                AStar.Node node = nodeList.get(0);//sprawdzane elementu?
                nodeList.remove(0);//usuwanie?
                
                return node;
            }
            
            public void Update(){
                Collections.sort(nodeList, new OpenList());
            }
            
            public AStar.Node Find(int neighbourId){
                for (AStar.Node node : nodeList){
                    if (node.nodeId == neighbourId)
                      return node;
                }
                return null;
            }
        }
