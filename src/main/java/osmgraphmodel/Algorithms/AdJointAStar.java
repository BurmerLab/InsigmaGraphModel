package osmgraphmodel.Algorithms;
//
//import java.util.List;
//
///**
// *
// * @author Michał
// */
////using System;
////using System.Collections.Generic;
////using System.Linq;
////using System.Text;
////
////namespace OSMGraphModel
////{
    public class AdJointAStar
    {
//        public class Node
//        {
//            public double g; // cummulative cost from the start to this nodde 
//            public double h; // heuristic estimate of distance to goal
//            public double f; // sum of cumulative cost of predecessors and self and heuristic
//
//            public Node parent = null;
//            public Node child = null;
//            public int edgeId;
//            public boolean directionConsistent = true;
//        }
//
//        class OpenList
//        {
//            List<Node> list = new List<Node>() {};
//            static int compareNodes(Node n1, Node n2)
//            {
//                if (n1.f > n2.f) return 1;
//                if (n1.f < n2.f) return -1;
//                return 0;
//            }
//            public int Count()
//            {
//                return list.Count();
//            }
//            public void Put(Node n)
//            {
//                list.Add(n);
//                list.Sort(compareNodes);
//            }
//            public Node Get()
//            {
//                if (list.Count() == 0) return null;
//                Node n = list.ElementAt(0);
//                list.RemoveAt(0);
//                return n;
//            }
//            public void Update()
//            {
//                list.Sort(compareNodes);
//            }
//            public Node Find(int id)
//            {
//                foreach (Node n in list)
//                {
//                    if (n.edgeId == id) return n;
//                }
//                return null;
//            }
//        }
//
//        HashSet<int> closedList = new HashSet<int>();
//        OpenList openList = new OpenList();
//        public int start;
//        public int goal;
//        public int progress = 0;
//        public int openListMaxCount = 0;
//
//        public AdjointAStar(int start, int goal)
//        {
//            this.start = start;
//            this.goal = goal;
//        }
//        //enum Result {OK,Fail};
//        public List<int> Run()
//        {
//            OSMGraphModelImp model = OSMGraphModelImp.Get();
//            if (!model.IsNode(start)) return null;
//            if (!model.IsNode(goal)) return null;
//
//            List<int> startEdges = model.GetOutcommingEdges(start);
//            foreach (int i in startEdges)
//            {
//                Node s = new Node();
//                s.edgeId = i;
//                s.g = 0;
//                Edge e = model.GetEdgeById(i);
//                s.directionConsistent = e.start.id == start ? true : false;
//                s.h = model.EstimateCost(s.directionConsistent ? e.end.id : e.start.id, goal);
//                s.f = s.g + s.h;
//                openList.Put(s);
//            }
//
//            for (progress = 1; ; progress++)
//            {
//                if (progress % 10000 == 0)
//                {
//                    Console.WriteLine("Iteration: " + progress + " Closed nodes: " + closedList.Count() + " Open nodes:" + openList.Count());
//                }
//                if (openList.Count() == 0) return null;
//                // get current node
//                Node current = openList.Get();
//                Edge currentEdge = model.GetEdgeById(current.edgeId);
//                if (currentEdge.start.id == goal || currentEdge.end.id == goal)
//                {
//                    return GetPath(current);
//                }
//                closedList.Add(current.edgeId);
//
//                int currentNodeId = current.directionConsistent ? currentEdge.end.id : currentEdge.start.id;
//                int prevNodeId = current.directionConsistent ? currentEdge.start.id : currentEdge.end.id;
//                List<Edge> outcoming = model.GetOutcomingEdges(currentNodeId);
//                if (outcoming == null)
//                {
//                    //TODO report black hole
//                    // Droga jednokierunkowa prowadząca do posesji, wjazd na stację benzynową, itp.
//                    TestDef.blackHoles.Add(currentNodeId);
//                    continue;
//                }
//                foreach (Edge outEdge in outcoming)
//                {
//                    if (closedList.Contains(outEdge.id)) continue;
//                    int nextNode = outEdge.start.id == currentNodeId ? outEdge.end.id : outEdge.start.id;
//                    double tentative_g = current.g
//                        + model.GetTurnCost(prevNodeId, currentNodeId, nextNode)
//                        + model.GetPathCost(currentNodeId, nextNode);
//
//                    Node node = openList.Find(outEdge.id);
//                    if (node == null)
//                    {
//                        node = new Node();
//                        node.edgeId = outEdge.id;
//                        node.parent = current;
//                        node.directionConsistent = currentNodeId == outEdge.start.id ? true : false;
//                        node.g = tentative_g;
//                        node.h = model.EstimateCost(nextNode, goal);
//                        node.f = node.g + node.h;
//                        openList.Put(node);
//                        if (openListMaxCount < openList.Count()) openListMaxCount = openList.Count();
//                    }
//                    else
//                    {
//                        // update values
//                        if (tentative_g < node.g)
//                        {
//                            node.parent = current;
//                            node.g = tentative_g;
//                            node.f = node.g + node.h;
//                            openList.Update();
//                        }
//                    }
//
//                }
//
//
//            }
//            return null;
//        }
//
//        List<int> GetPath(Node goal)
//        {
//            Node i = goal;
//            Node start = null;
//            while (i != null)
//            {
//                if (i.parent != null) i.parent.child = i;
//                start = i;
//                i = i.parent;
//            }
//            OSMGraphModelImp model = OSMGraphModelImp.Get();
//            List<int> list = new List<int>();
//            if (start != null)
//            {
//                Edge e = model.GetEdgeById(start.edgeId);
//                list.Add(start.directionConsistent ? e.start.id : e.end.id);
//
//            }
//            for (; start != null; start = start.child)
//            {
//                Edge e = model.GetEdgeById(start.edgeId);
//                list.Add(start.directionConsistent ? e.end.id : e.start.id);
//            }
//            return list;
//        }
//
    }
