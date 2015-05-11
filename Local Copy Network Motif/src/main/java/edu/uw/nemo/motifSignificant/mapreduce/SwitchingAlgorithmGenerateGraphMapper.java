package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;
import edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm.SwitchingAlgoirthmGenerateGraph;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zalak on 5/4/2015.
 */
public class SwitchingAlgorithmGenerateGraphMapper extends Mapper<LongWritable, Text, Text, IntegerTwoDArrayWritable>
{
   static Mapping inputMapping = null;
    BooleanWritable one = new BooleanWritable(true);
    BooleanWritable zero = new BooleanWritable(false);
    //IntWritable[][] adjMatrix = new IntWritable[5132][5132];
    BooleanWritable[][] adjMatrix = new BooleanWritable[3825][3825];
    //BooleanWritable[][] adjMatrixOut = new BooleanWritable[4000][4000];
    Boolean arrayCreated = false;

    protected void setup(Context context) throws IOException, InterruptedException {

        if(GraphGeneratorJob.mapping == null)
        {
            System.out.println("Input file is invalid");
            return;
        }
        else
        {
            inputMapping = GraphGeneratorJob.mapping;

        }
        super.setup(context);

    }
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String outputKey = "RandomGraph#" + value.toString();
        SwitchingAlgoirthmGenerateGraph graphGenerator = new SwitchingAlgoirthmGenerateGraph(this.inputMapping);
        Mapping randomGraph = graphGenerator.generateGraph();

        System.out.println("Mapping " + outputKey);
     //   print(randomGraph.getAdjMapping());

        System.out.println("Calling Context.write");
        BooleanWritable[][] adjMatrix1 = convertAdjMappingToMatrix(randomGraph);
       //BooleanWritable[][] adjMatrix1 = CreateTmpArray();


        IntegerTwoDArrayWritable twoDArray = new IntegerTwoDArrayWritable();
        if(twoDArray == null)
        {
            System.out.println("Two d Array is null ");
        }
        twoDArray.set(adjMatrix1);


       context.write(new Text(outputKey), twoDArray);
        System.out.println("Called Context.write");
    }


    BooleanWritable[][] convertAdjMappingToMatrix(Mapping map)
    {
        AdjacencyMapping adjMap = map.getAdjMapping();

        for(int i =0; i < map.getNodeCount(); i++)
        {
            for(int j = 0; j < map.getNodeCount(); j++)
            {
                this.adjMatrix[i][j] = this.zero;
            }

            List<AdjacentVertexWithEdge> vertexList = adjMap.getNeighbours(i);
            for(AdjacentVertexWithEdge  v : vertexList)
            {
             //   System.out.println(i + " : " + v.getNodeId());
                this.adjMatrix[i][v.getNodeId()] = this.one;
            }
        }
        return this.adjMatrix;
    }

/*    public BooleanWritable[][] CreateTmpArray()
    {
        if(arrayCreated == false) {
            for (int i = 0; i < 4000; i++) {
                for (int j = 0; j < 4000; j++) {
                    adjMatrixOut[i][j] = this.one;
                }
            }
            //arrayCreated = true;
        }


        return this.adjMatrixOut;
    }*/
    /**
     *
     * @param map
     */
    private void print(AdjacencyMapping map) {
        System.out.println("\n" + "--------------------------------------------------------------------------\n");
        for (int i = 0; i < map.size(); i++) {
            List<AdjacentVertexWithEdge> adjList = map.getNeighbours(i);
            System.out.print("\n" + i);
            for (AdjacentVertexWithEdge v : adjList) {
                System.out.print("->" + v.getNodeId());
            }
        }
    }
}
