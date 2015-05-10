package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;
import edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm.SwitchingAlgoirthmGenerateGraph;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.TwoDArrayWritable;
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
        IntWritable[][] adjMatrix = convertAdjMappingToMatrix(randomGraph);
        //myarray[0][0] = new IntWritable(0);
        //myarray[1][0] = new IntWritable(1);

        IntegerTwoDArrayWritable twoDArray = new IntegerTwoDArrayWritable();
        twoDArray.set(adjMatrix);


       context.write(new Text(outputKey), twoDArray);
      //  System.out.println("Called Context.write");
    }


    IntWritable[][] convertAdjMappingToMatrix(Mapping map)
    {
        AdjacencyMapping adjMap = map.getAdjMapping();
        IntWritable[][] adjMatrix= new IntWritable[map.getNodeCount()][map.getNodeCount()];

        for(int i =0; i < map.getNodeCount(); i++)
        {
            for(int j = 0; j < map.getNodeCount(); j++)
            {
                adjMatrix[i][j] = new IntWritable(0);
            }
        }

        for(int i =0; i < adjMap.size(); i++)
        {
            List<AdjacentVertexWithEdge> vertexList = adjMap.getNeighbours(i);
            for(AdjacentVertexWithEdge  v : vertexList)
            {
                adjMatrix[i][v.getNodeId()] = new IntWritable(1);
            }
        }

        return adjMatrix;
    }

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
