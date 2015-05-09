package edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;

import java.util.*;

/**
 * Created by Zalak on 4/8/2015.
 */
public class SwitchingAlgoirthmGenerateGraph
{
    Mapping inputGraph;
    public static final int Q = 1;

    /**
     *
     * @param inputGraph
     */
    public SwitchingAlgoirthmGenerateGraph(Mapping inputGraph)
    {
        this.inputGraph = inputGraph;
    }

    public SwitchingAlgoirthmGenerateGraph()
    {

    }

    /**
     *
     * @param n
     * @return
     */
    public List<Mapping> generateGraph(int n)
    {
        List<Mapping> randomGraphList = new ArrayList<Mapping>();
        System.out.println("Input Graph");

        for(int i = 0; i<n; i++)
        {
            System.out.println("\n Graph " + (i+1) + "\n");
            Mapping map = generateGraph();
            randomGraphList.add(map);
        }

        return randomGraphList;
    }

    /**
     *
     * @return
     */
    public Mapping generateGraph()
    {
        ConvertDataStructure convert = new ConvertDataStructure();

        Hashtable<Integer, HashSet<Integer>> randomGraphAdjacenyList = convert.convertToAdjacencyHashtable(this.inputGraph);
       // convert.print(randomGraphAdjacenyList);


        List<String[]> randomEdgeList = new ArrayList<String[]>();

        int []vertex_edge1;
        int [] vertex_edge2;
        long startTime = System.currentTimeMillis();
        for(long i= 0; i< getQE(); i++)
        {
            vertex_edge1 = getToFromVertex();
            vertex_edge2 = getToFromVertex();

            if(!checkEdgeExistence(vertex_edge1, randomGraphAdjacenyList))
            {
                vertex_edge1 = getNextEdge(vertex_edge1, randomGraphAdjacenyList);
                if(vertex_edge1[0] < 0 || vertex_edge1[1] < 0)
                {
                    continue;
                }
            }
            if(!checkEdgeExistence(vertex_edge2, randomGraphAdjacenyList))
            {
                vertex_edge2 = getNextEdge(vertex_edge2, randomGraphAdjacenyList);
                if(vertex_edge2[0] < 0 || vertex_edge2[1] < 0)
                {
                    continue;
                }
            }

            if(!isEdgeUnique(vertex_edge1, vertex_edge2))
            {
                i--;
                continue;
            }
            boolean flag = swap(vertex_edge1, vertex_edge2, randomGraphAdjacenyList, i);
            if(!flag)
            {
                i--;
                continue;
            }

            /*if(i % 10000 == 0)
            {
                long endTime = System.currentTimeMillis();
                System.out.println("\n" + i + " : " + (endTime - startTime));
            }*/
        }

        System.out.print("\n *****************************************************************************************************\n");
      //  convert.print(randomGraphAdjacenyList);

        AdjacencyMapping randomAdjMap = convert.convertToAdjacencyMapping(randomGraphAdjacenyList);
       // convert.print(randomAdjMap);
        return (new Mapping(randomEdgeList, randomAdjMap));
    }


    public long getQE()
    {
       // System.out.println(Q * inputGraph.getLinkCount());
        return (Q * inputGraph.getLinkCount());
    }

    /**
     *
     * @param vertex_edge
     * @param adjList
     * @return
     */
    int[] getNextEdge(int []vertex_edge, Hashtable<Integer, HashSet<Integer>> adjList)
    {
        int to = vertex_edge[0];
        int from = vertex_edge[1];

        boolean flag = false;

        for(Iterator iter = adjList.get(to).iterator();iter.hasNext();)
        {
            int nextVertex = (Integer)iter.next();
            if(nextVertex == from)
            {
                  if(iter.hasNext())
                  {
                      vertex_edge[1] = (Integer) iter.next();
                      flag =true;
                      break;
                  }

            }
            else
            {
                vertex_edge[1] = nextVertex;
                flag = true;
                break;
            }
        }

        if(!flag)
        {
            for(Iterator iter = adjList.get(from).iterator(); iter.hasNext();)
            {
                int nextVertex = (Integer)iter.next();
                vertex_edge[0] = from;
                if(nextVertex == to)
                {
                    if (iter.hasNext())
                    {
                        vertex_edge[1] = (Integer) iter.next();
                        flag = true;
                        break;
                    }

                }
                else
                {
                    vertex_edge[1] = nextVertex;
                    flag= true;
                    break;
                }
            }
        }
        if(!flag)
        {
            vertex_edge[0] = -1;
            vertex_edge[1] = -1;
        }
        return vertex_edge;
    }

    /**
     *
     * @param vertex_edge1
     * @param vertex_edge2
     * @return
     */
    public boolean isEdgeUnique(int []vertex_edge1, int[]vertex_edge2)
    {
        if(vertex_edge1[0] == vertex_edge2[1] || vertex_edge1[0] == vertex_edge2[0])
        {
            return false;
        }
        if(vertex_edge1[1] == vertex_edge2[1] || vertex_edge1[1] == vertex_edge2[0])
        {
            return false;
        }

        return true;
    }

    /**
     *
     * @param vertex
     * @param adjList
     * @return
     */
    public boolean checkEdgeExistence(int[]vertex,Hashtable<Integer, HashSet<Integer>> adjList)
    {
        return (adjList.containsKey(vertex[0])?(adjList.get(vertex[0]).contains(vertex[1])? true : false): false);
    }

    /**
     *
     * @return
     */
    public int[] getToFromVertex()
    {
        int[]vertex = new int[2];
        while(true)
        {
            vertex[0] = randomNumberGenerator(inputGraph.getNodeCount());
            vertex[1] = randomNumberGenerator(inputGraph.getNodeCount());

            if(vertex[0] != vertex[1])
                break;
        }

        return vertex;
    }

    /**
     *
     * @param n
     * @return
     */
    public int randomNumberGenerator(int n)
    {
       long seed =  System.currentTimeMillis();
        Random rand = new Random(seed);
        return rand.nextInt(n);
    }

    /**
     *
     * @param vertex_edge1
     * @param vertex_edge2
     * @param adjList
     * @param i
     * @return
     */
    boolean swap(int[] vertex_edge1, int[] vertex_edge2,Hashtable<Integer, HashSet<Integer>> adjList, long i)
    {
        boolean flag = true;
        if(flag)
        {
            if(checkEdgeExistence(new int[] {vertex_edge1[0],  vertex_edge2[0]}, adjList ))
            {
                flag = false;
                //return false;
            }
            if(checkEdgeExistence(new int[] {vertex_edge1[1],  vertex_edge2[1]}, adjList ))
            {
                flag = false;
                //return false;
            }
            if(flag)
            {
                addEdges(adjList, vertex_edge1[0], vertex_edge2[0]);
                addEdges(adjList, vertex_edge1[1], vertex_edge2[1]);
            }
        }
        if(!flag)
        {
            if(checkEdgeExistence(new int[] {vertex_edge1[0],  vertex_edge2[1]}, adjList))
            {
                return false;
            }
            if (checkEdgeExistence(new int[] {vertex_edge1[1],  vertex_edge2[0]}, adjList ))
            {
                return false;
            }
            flag = true;
            if(flag)
            {
                addEdges(adjList, vertex_edge1[0], vertex_edge2[1]);
                addEdges(adjList, vertex_edge1[1], vertex_edge2[0]);
            }
        }
        if(flag)
        {
            deleteEdges(adjList, vertex_edge1[0], vertex_edge1[1]);
            deleteEdges(adjList, vertex_edge2[0], vertex_edge2[1]);
        }

        return true;
    }

    /**
     *
     * @param adjList
     * @param to
     * @param from
     */
    public void addEdges(Hashtable<Integer, HashSet<Integer>> adjList, int to, int from)
    {
        HashSet<Integer> set1 = null;
        HashSet<Integer> set2 = null;

        if(adjList.containsKey(to))
        {
           set1 = adjList.get(to);
           set1.add(from);
        }
        else
        {
            set1 = new HashSet<Integer>();
            set1.add(from);

        }
        if(adjList.containsKey(from))
        {
           set2 = adjList.get(from);
           set2.add(to);
        }
        else
        {
            set2 = new HashSet<Integer>();
            set2.add(to);
        }
        adjList.put(to, set1);
        adjList.put(from, set2);

    }

    public void deleteEdges(Hashtable<Integer, HashSet<Integer>> adjList, int to, int from)
    {
        if(adjList.containsKey(to))
        {
            adjList.get(to).remove(from);
        }

        if(adjList.containsKey(from))
        {
            adjList.get(from).remove(to);
        }
    }
}


