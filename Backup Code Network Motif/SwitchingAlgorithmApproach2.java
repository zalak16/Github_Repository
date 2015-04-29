package edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.Mapping;
import org.apache.hadoop.util.hash.Hash;

import java.util.*;

/**
 * Created by Zalak on 4/19/2015.
 */
//public class SwitchingAlgorithmApproach2
//{
//    Mapping inputGraph;
//    public static final int Q = 1;
//
//    /**
//     *
//     * @param inputGraph
//     */
//    public SwitchingAlgorithmApproach2(Mapping inputGraph)
//    {
//        this.inputGraph = inputGraph;
//    }
//
//    /**
//     *
//     * @param n
//     * @return
//     */
//    public List<Mapping> generateGraph(int n)
//    {
//        List<Mapping> randomGraphList = new ArrayList<Mapping>();
//        System.out.println("Input Graph");
//
//        for(int i = 0; i<n; i++)
//        {
//            System.out.println("\n Graph " + (i+1) + "\n");
//            Mapping map = generateGraph();
//            randomGraphList.add(map);
//        }
//
//        return randomGraphList;
//    }

    /**
     *
     * @return
     */
//    public Mapping generateGraph()
//    {
//      ConvertDataStructure convert = new ConvertDataStructure();
//
//        HashMap<Integer, ArrayList<Integer>> randomGraphAdjacenyMap = convert.convertToAdjacencyHashMap(this.inputGraph);
//        convert.print(randomGraphAdjacenyMap);
//
//
//        List<String[]> randomEdgeList = new ArrayList<String[]>();
//
//        int []vertex_edge1;
//        int [] vertex_edge2;
//        long startTime = System.currentTimeMillis();
//        for(long i= 0; i< getQE(); i++)
//        {
//            vertex_edge1 = getToFromVertex(randomGraphAdjacenyMap);
//            vertex_edge2 = getToFromVertex(randomGraphAdjacenyMap);

          /*  if(!checkEdgeExistence(vertex_edge1, randomGraphAdjacenyMap))
            {
                vertex_edge1 = getNextEdge(vertex_edge1, randomGraphAdjacenyMap);
                if(vertex_edge1[0] < 0 || vertex_edge1[1] < 0)
                {
                    continue;
                }
            }
            if(!checkEdgeExistence(vertex_edge2, randomGraphAdjacenyMap))
            {
                vertex_edge2 = getNextEdge(vertex_edge2, randomGraphAdjacenyMap);
                if(vertex_edge2[0] < 0 || vertex_edge2[1] < 0)
                {
                    continue;
                }
            }*/

//            if(!isEdgeUnique(vertex_edge1, vertex_edge2))
//            {
//                i--;
//                continue;
//            }
//            boolean flag = swap(vertex_edge1, vertex_edge2, randomGraphAdjacenyMap, i);
//            if(!flag)
//            {
//                i--;
//                continue;
//            }
//
//            if(i % 10000 == 0)
//            {
//                long endTime = System.currentTimeMillis();
//                System.out.println("\n" + i + " : " + (endTime - startTime));
//            }
//        }
//
//        System.out.print("\n *****************************************************************************************************\n");
//        convert.print(randomGraphAdjacenyMap);
//
//        AdjacencyMapping randomAdjMap = convert.convertToAdjacencyMapping(randomGraphAdjacenyMap);
//        convert.print(randomAdjMap);
//        return (new Mapping(randomEdgeList, randomAdjMap));
//    }
//
//
//    public long getQE()
//    {
//        // System.out.println(Q * inputGraph.getLinkCount());
//        return (Q * inputGraph.getLinkCount());
//    }

//    /**
//     *
//     * @param vertex_edge
//     * @param adjList
//     * @return
//     */
//    int[] getNextEdge(int []vertex_edge, Hashtable<Integer, HashSet<Integer>> adjList)
//    {
//        int to = vertex_edge[0];
//        int from = vertex_edge[1];
//
//        boolean flag = false;
//
//        for(Iterator iter = adjList.get(to).iterator();iter.hasNext();)
//        {
//            int nextVertex = (Integer)iter.next();
//            if(nextVertex == from)
//            {
//                if(iter.hasNext())
//                {
//                    vertex_edge[1] = (Integer) iter.next();
//                    flag =true;
//                    break;
//                }
//
//            }
//            else
//            {
//                vertex_edge[1] = nextVertex;
//                flag = true;
//                break;
//            }
//        }
//
//        if(!flag)
//        {
//            for(Iterator iter = adjList.get(from).iterator(); iter.hasNext();)
//            {
//                int nextVertex = (Integer)iter.next();
//                vertex_edge[0] = from;
//                if(nextVertex == to)
//                {
//                    if (iter.hasNext())
//                    {
//                        vertex_edge[1] = (Integer) iter.next();
//                        flag = true;
//                        break;
//                    }
//
//                }
//                else
//                {
//                    vertex_edge[1] = nextVertex;
//                    flag= true;
//                    break;
//                }
//            }
//        }
//        if(!flag)
//        {
//            vertex_edge[0] = -1;
//            vertex_edge[1] = -1;
//        }
//        return vertex_edge;
//    }
//
//    /**
//     *
//     * @param vertex_edge1
//     * @param vertex_edge2
//     * @return
//     */
//    public boolean isEdgeUnique(int []vertex_edge1, int[]vertex_edge2)
//    {
//        if(vertex_edge1[0] == vertex_edge2[1] || vertex_edge1[0] == vertex_edge2[0])
//        {
//            return false;
//        }
//        if(vertex_edge1[1] == vertex_edge2[1] || vertex_edge1[1] == vertex_edge2[0])
//        {
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     *
//     * @param vertex
//     * @param adjList
//     * @return
//     */
//    public boolean checkEdgeExistence(int[]vertex,HashMap<Integer, ArrayList<Integer>> adjList)
//    {
//        return (adjList.containsKey(vertex[0])?(adjList.get(vertex[0]).contains(vertex[1])? true : false): false);
//    }
//
//    /**
//     *
//     * @return
//     */
//    public int[] getToFromVertex(HashMap<Integer, ArrayList<Integer>> adjList)
//    {
//        int[]vertex = new int[3];
//        while(true)
//        {
//            vertex[0] = randomNumberGenerator(inputGraph.getNodeCount());
//            int rand = randomNumberGenerator(adjList.get(vertex[0]).size());
//            vertex[1] = adjList.get(vertex[0]).get(rand);
//            vertex[2] = rand;
//            if(vertex[0] != vertex[1])
//                break;
//        }
//
//        return vertex;
//    }
//
//    /**
//     *
//     * @param n
//     * @return
//     */
//    public int randomNumberGenerator(int n)
//    {
//        long seed =  System.currentTimeMillis();
//        Random rand = new Random(seed);
//        return rand.nextInt(n);
//    }
//
//    /**
//     *
//     * @param vertex_edge1
//     * @param vertex_edge2
//     * @param adjList
//     * @param i
//     * @return
//     */
//    boolean swap(int[] vertex_edge1, int[] vertex_edge2,HashMap<Integer, ArrayList<Integer>> adjList, long i)
//    {
//
//        if(i % 2 == 0)
//        {
//            if(checkEdgeExistence(new int[] {vertex_edge1[0],  vertex_edge2[0]}, adjList ))
//            {
//                return false;
//            }
//            if(checkEdgeExistence(new int[] {vertex_edge1[1],  vertex_edge2[1]}, adjList ))
//            {
//                return false;
//            }
//            deleteEdges(adjList, vertex_edge1[0], vertex_edge1[1], vertex_edge1[2]);
//            deleteEdges(adjList, vertex_edge2[0], vertex_edge2[1], vertex_edge2[2]);
//            addEdges(adjList, vertex_edge1[0], vertex_edge2[0], vertex_edge1[2], vertex_edge2[2]);
//            addEdges(adjList, vertex_edge1[1], vertex_edge2[1], vertex_edge1[2], vertex_edge2[2]);
//        }
//        else
//        {
//            if(checkEdgeExistence(new int[] {vertex_edge1[0],  vertex_edge2[1]}, adjList))
//            {
//                return false;
//            }
//            if (checkEdgeExistence(new int[] {vertex_edge1[1],  vertex_edge2[0]}, adjList ))
//            {
//                return false;
//            }
//            addEdges(adjList, vertex_edge1[0], vertex_edge2[1], vertex_edge1[2], vertex_edge2[2]);
//            addEdges(adjList, vertex_edge1[1], vertex_edge2[0], vertex_edge1[2], vertex_edge2[2]);
//        }
//       // deleteEdges(adjList, vertex_edge1[0], vertex_edge1[1]);
////        deleteEdges(adjList, vertex_edge2[0], vertex_edge2[1]);
//
//        return true;
//    }
//
//    /**
//     *
//     * @param adjList
//     * @param to
//     * @param from
//     */
//    public void addEdges(HashMap<Integer, ArrayList<Integer>> adjList, int to, int from, int pos1, int pos2)
//    {
//      // ArrayList<Integer> list1 = null;
//       // ArrayList<Integer> list2 = null;
////      if(adjList.containsKey(to))
//  //    {
//            //st1 = adjList.get(to);
////            list1.add(from);
//////      }
////        else
////        {
////            list1 = new ArrayList<Integer>();
////            list1.add(from);
////
////        }
//        adjList.get(to).add(pos1, from);
//        adjList.get(from).add(pos2, to);
////        if(adjList.containsKey(from))
////        {
////            list2 = adjList.get(from);
////            list2.add(to);
////        }
////        else
////        {
////            list2 = new HashSet<Integer>();
////            list2.add(to);
////        }
////        adjList.put(to, list1);
////        adjList.put(from, list2);
//
//    }
//
//    public void deleteEdges(HashMap<Integer, ArrayList<Integer>> adjList, int to, int from, int pos)
//    {
//     /*   if(adjList.containsKey(to))
//
//        {
//            adjList.get(to).remove(from);
//        }
//
//        if(adjList.containsKey(from))
//        {
//            adjList.get(from).remove(to);
//        }*/
//    }
//
//}
