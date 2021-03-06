package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.model.AdjacencyMapping;
import edu.uw.nemo.model.AdjacentVertexWithEdge;
import edu.uw.nemo.model.Mapping;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.TwoDArrayWritable;
import org.apache.hadoop.mapreduce.Reducer;


import java.io.IOException;
import java.util.List;


/**
 * Created by Zalak on 5/4/2015.
 */
public class SwitchingAlgorithmGenerateGraphReducer extends Reducer<Text, MappingObject, Text, Text>
{

    @Override
    protected  void  setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    protected void reduce(Text key, Iterable<TwoDArrayWritable> maps, Context context)
    {
        System.out.println("Entering reducer");
        System.out.println(key.toString());
//
        for(TwoDArrayWritable array : maps)
        {
            IntWritable[][] arr = (IntWritable[][]) array.get();
            System.out.print(arr.length);
        }

        /*for(Mapping map : maps)
        {
            //Mapping randomMapping = map.getMapping();
            //System.out.println(map.getValue());
            print(map.getAdjMapping());
            break;
        }*/

    }

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
