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
import java.util.List;


/**
 * Created by Zalak on 5/4/2015.
 */
public class SwitchingAlgorithmGenerateGraphMapper extends Mapper<LongWritable, Text, Text, MappingObject>
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
        print(randomGraph.getAdjMapping());

        MappingObject map = new MappingObject();
        //map.setMapping(randomGraph);

        IntWritable[][] array = new IntWritable[2][1];
        //array[0] = new IntWritable[1];
        //array[1] = new IntWritable[1];
        array[0][0] = new IntWritable(45);
        array[1][0] = new IntWritable(55);

        map.setArray(array);

//        map.setValue(new IntWritable(45));
        context.write(new Text(outputKey), map);


//        TwoDArrayWritable array = new TwoDArrayWritable(IntWritable.class);
//        IntWritable[][] adjMatrix = new IntWritable[2][1];
//        for(int i =0; i <adjMatrix.length; i++)
//        {
//            adjMatrix[i][0] = new IntWritable();
//        }
//        adjMatrix[0][0] = new IntWritable(0);
//        adjMatrix[1][0] = new IntWritable(1);
//        array.set(adjMatrix);
        System.out.println("Calling Context.write");

        //context.write(new Text(outputKey), randomGraph);
        //context.write(new Text(outputKey), array);
        System.out.println("Called Context.write");


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
