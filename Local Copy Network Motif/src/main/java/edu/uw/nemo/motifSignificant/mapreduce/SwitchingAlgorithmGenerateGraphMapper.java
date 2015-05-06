package edu.uw.nemo.motifSignificant.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Mapper;




import java.io.IOException;


/**
 * Created by Zalak on 5/4/2015.
 */
public class SwitchingAlgorithmGenerateGraphMapper extends Mapper<LongWritable, Text, Text, Text>
{
    protected void setup(Context context)
    {

    }
    public void map(LongWritable key, Text value, Context context) throws IOException
    {

    }

    public void close() throws IOException
    {

    }

    public void configure(JobConf jobConf)
    {

    }
}
