package edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Zalak on 4/8/2015.
 */
public class ConvertDataStructure
{
    public Hashtable<Integer, HashSet<Integer>> convertToAdjacencyHashtable(Mapping inputGraph)
    {
        Hashtable<Integer, HashSet<Integer>> adjacentListTable = new Hashtable<Integer, HashSet<Integer>>();
        HashSet<Integer> adjSet = null;
        for(int i= 0; i< inputGraph.getNodeCount(); i++)
        {
            if(adjacentListTable.containsKey(i))
            {
                adjSet = adjacentListTable.get(i);
            }
            else
            {
                adjSet = new HashSet<Integer>();
            }

            List<AdjacentVertexWithEdge> adjVertexList = inputGraph.getNeighbours(i);
            for(AdjacentVertexWithEdge e: adjVertexList )
            {
                if(adjSet != null)
                {
                    if(!adjSet.contains(e.getNodeId()))
                    {
                        adjSet.add(e.getNodeId());
                    }
                }
            }
            adjacentListTable.put(i, adjSet);
        }

        return adjacentListTable;
    }

    public AdjacencyMapping convertToAdjacencyMapping(Hashtable<Integer, HashSet<Integer>> adjHashTable)
    {
        AdjacencyMapping map = new AdjacencyMapping(adjHashTable.size());
        for(Integer key: adjHashTable.keySet())
        {
            HashSet<Integer> set = adjHashTable.get(key);
            for(Iterator iter = set.iterator(); iter.hasNext();)
            {
                int adjNode = (Integer)iter.next();
                if(map.size() == 0)
                {
                    map.addAdjacentVertices(key, adjNode);
                    continue;
                }
                if(map.size() >= key)
                {
                    if(map.getNeighbour(key, adjNode) == null)
                    {
                        map.addAdjacentVertices(key, adjNode);
                        continue;
                    }
                }
                if(map.size()>= adjNode)
                {
                    if(map.getNeighbour(adjNode, key) == null)
                    {
                        map.addAdjacentVertices(adjNode, key);
                        continue;
                    }
                }

                if(!(map.size() >=key) || !(map.size()>= adjNode))
                    map.addAdjacentVertices(key, adjNode);

            }
        }

        return map;
    }

    public void print(Hashtable<Integer, HashSet<Integer>> adjList )
    {
        for(Integer key: adjList.keySet())
        {
            HashSet<Integer> set = adjList.get(key);
            System.out.print("\n" + key);
            for(Iterator iter = set.iterator(); iter.hasNext();)
            {
                System.out.print("- >" + iter.next());
            }
        }


    }

    public void print(AdjacencyMapping map)
    {
        System.out.println("\n" + "--------------------------------------------------------------------------\n");
        for(int i= 0; i< map.size(); i++)
        {
            List<AdjacentVertexWithEdge> adjList = map.getNeighbours(i);
            System.out.print("\n" + i);
            for(AdjacentVertexWithEdge v : adjList)
            {
                System.out.print("->" + v.getNodeId());
            }
        }
    }

}
