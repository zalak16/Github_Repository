package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;
import edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm.SwitchingAlgoirthmGenerateGraph;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Mapper;




import java.io.IOException;
import java.util.List;


/**
 * Created by Zalak on 5/4/2015.
 */
public class SwitchingAlgorithmGenerateGraphMapper extends Mapper<LongWritable, Text, Text, Text>
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

//        MappingObject map = new MappingObject();
  //      map.setMapping(randomGraph);
        context.write(new Text(outputKey), new Text(randomGraph.toString()));


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
