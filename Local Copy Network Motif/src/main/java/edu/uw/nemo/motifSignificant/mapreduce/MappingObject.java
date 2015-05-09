package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.model.Mapping;
import org.apache.hadoop.io.ObjectWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Zalak on 5/9/2015.
 */
public class MappingObject extends ObjectWritable
{
    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

  private Mapping mapping;
    public void write(DataOutput out) throws IOException {

    }

    public void readFields(DataInput in) throws IOException {



    }

}
