package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.esu.ESUGen;
import edu.uw.nemo.labeler.GraphLabel;
import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zalak on 5/4/2015.
 */
public class SwitchingAlgorithmGenerateGraphReducer extends Reducer<Text, BooleanTwoDArrayWritable, Text, Text>
{
    int k;
    double probability;
    public static final String LabelGFile = "labelg.exe";
    @Override
    protected void setup(Context context) throws IOException, InterruptedException
    {
        k = GraphGeneratorJob.size;
        probability = GraphGeneratorJob.probability;
        super.setup(context);
    }

    protected void reduce(Text key, Iterable<BooleanTwoDArrayWritable> adjMatrices, Context context) {
       System.out.println("Entering reducer");

        System.out.println(key.toString());
        Mapping randomMapping = null;
        //System.out.print("Printing adj matrices: " + adjMatrices.toString());

        for (BooleanTwoDArrayWritable adjMatrix : adjMatrices)
        {
          //  System.out.println("Entering reduce iterable");
            randomMapping = convertAdjMatrixToMapping(adjMatrix.get());
            break;
        }

       // ArrayList<RandomGraphCanonicalLabelling> canonicalSubgraphList = new ArrayList<RandomGraphCanonicalLabelling>();
        //RandomGraphCanonicalLabelling canonicalSubgraphList = new RandomGraphCanonicalLabelling();
      /*  System.out.println("Enumerating Subgraphs");
        GraphLabel label = new GraphLabel(false);
        this.enumerateSubGraphs(randomMapping, label, k, probability);
        System.out.println("Number of subgraphs enumerated: " + label.getSubgraphCount());

        // get canonical labels with GraphLabel
        String labelgPath = "./" + LabelGFile;
        Map<String, List<Map.Entry<String, Long>>> canonicalSubgraphs = label.getCanonicalLabels(labelgPath);
        System.out.println("Number of canonical labels for all enumerated subgraphs: " + canonicalSubgraphs.size());
        //canonicalSubgraphList.add(new RandomGraphCanonicalLabelling(randomMapping, canonicalSubgraphs));
        RandomGraphCanonicalLabelling canonicalLabel = new RandomGraphCanonicalLabelling(randomMapping, canonicalSubgraphs);


        for (Map.Entry<String, List<Map.Entry<String, Long>>> e : canonicalSubgraphs.entrySet())
        {
            System.out.println("Cannonical Label (g6) \"" + e.getKey() + "\" has following Sub Graphs:");
            Map<String, Long> subGraphCounts = GraphFormat.countDistinctGraphs(e.getValue());
            for (Map.Entry<String, Long> c : subGraphCounts.entrySet())
            {
                System.out.println("\tSubGraph (g6) \"" + c.getKey() + "\" has count: " + c.getValue());
            }
        }*/
        print(randomMapping.getAdjMapping());



    }

    Mapping convertAdjMatrixToMapping(Writable[][] adjMatrix)
    {
        System.out.println("Converting mattric to mapping in reducer" + adjMatrix.length);
        AdjacencyMapping adjMap = new AdjacencyMapping(adjMatrix.length);
        BooleanWritable one = new BooleanWritable(true);
        List<String[]> randomEdgeList = new ArrayList<String[]>();

        for(int i=0; i< adjMatrix.length; i++)
        {
          //  System.out.println("\n" + adjMatrix[i].length);
            for(int j =0; j < adjMatrix[i].length; j++)
            {
            //    System.out.println(adjMatrix[i][j] + " " +  i + " : " + j  + " ");
                if(adjMatrix[i][j].equals(one))
                {
                  //  System.out.println(adjMatrix[i][j] + "== " +  i + " : " + j  + " ");
                    if (adjMap.size() == 0)
                    {
                        adjMap.addAdjacentVertices(i, j);
                        continue;
                    }
                    if (adjMap.size()-1 >= i)
                    {
                        if (adjMap.getNeighbour(i, j) == null)
                        {
                             adjMap.addAdjacentVertices(i, j);
                            continue;
                        }
                    }
                    if (adjMap.size()-1 >= j)
                    {
                        if (adjMap.getNeighbour(j, i) == null)
                        {
                            adjMap.addAdjacentVertices(j, i);
                            continue;
                        }
                    }

                    if (!(adjMap.size()-1 >= i) || !(adjMap.size()-1 >= j))
                    {
                        adjMap.addAdjacentVertices(i, j);
                    }
                }

            }
        }

        return (new Mapping(randomEdgeList, adjMap));
    }

    private void print(AdjacencyMapping map) {
        System.out.println("\n" + "-----------------------------------------Reducer---------------------------------\n");

        for (int i = 0; i < map.size(); i++) {
            List<AdjacentVertexWithEdge> adjList = map.getNeighbours(i);
            System.out.print("\n" + i);
            for (AdjacentVertexWithEdge v : adjList) {
                System.out.print("->" + v.getNodeId());
            }
        }
    }

    private void enumerateSubGraphs(Mapping mapping, GraphLabel label, int size, double probability)
    {
        ESUGen generator = new ESUGen(true);
        generator.enumerateSubgraphs(mapping, label, size, probability);
    }

}
