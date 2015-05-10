package edu.uw.nemo.motifSignificant.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.TwoDArrayWritable;

/**
 * Created by Zalak on 5/9/2015.
 */
public class IntegerTwoDArrayWritable extends TwoDArrayWritable
{
    public IntegerTwoDArrayWritable() {
        super(IntWritable.class);
    }
}
