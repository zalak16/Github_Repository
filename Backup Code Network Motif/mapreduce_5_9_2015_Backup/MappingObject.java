package edu.uw.nemo.motifSignificant.mapreduce;

import edu.uw.nemo.model.Mapping;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.TwoDArrayWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Zalak on 5/9/2015.
 */
public class MappingObject extends ObjectWritable
{
//    public Mapping getMapping() {
//        return mapping;
//    }
//
//    public void setMapping(Mapping mapping) {
//        this.mapping = mapping;
//    }
//
//    public MappingObject (Mapping mapping)
//    {
//        this.mapping = mapping;
//    }

    public MappingObject ()
    {

    }
    public void setValue(IntWritable number)
    {
        this.number = number;
    }

    public IntWritable getValue()
    {
        return this.number;
    }

    public void setArray(IntWritable[][] array)
    {
        this.array.set(array);
    }

    public TwoDArrayWritable getArray()
    {
        return array;
    }

  //private Mapping mapping;
    private IntWritable number = new IntWritable();

    private TwoDArrayWritable array = new TwoDArrayWritable(IntWritable.class);

    public void write(DataOutput out) throws IOException {
//        System.out.println("  Write function from ObjectWritable called, zalak");
//        ObjectWritable ow = new ObjectWritable(this.number);
//        ow.write(out);
//    }
//
//    public void readFields(DataInput in) throws IOException {
//
//        System.out.println("Read function from ObjectWritable called, zalak");
//        ObjectWritable ow = new ObjectWritable();
//        Configuration conf = new Configuration();
//        ow.setConf(conf);
//        ow.readFields(in);
//
//        this.number = this.getValue();
//
//    }


        System.out.println("  Write function from ObjectWritable called, zalak");
        ObjectWritable ow = new ObjectWritable(this.array);
        ow.write(out);
    }

    public void readFields(DataInput in) throws IOException {

        System.out.println("Read function from ObjectWritable called, zalak");
        ObjectWritable ow = new ObjectWritable();
        Configuration conf = new Configuration();
        ow.setConf(conf);
        ow.readFields(in);

        this.array = this.getArray();

    }

//    public void write(DataOutput out) throws IOException {
//        System.out.println("  Write function from ObjectWritable called, zalak");
//        ObjectWritable map = new ObjectWritable(this.mapping);
//        map.write(out);
//    }
//
//    public void readFields(DataInput in) throws IOException {
//
//        System.out.println("Read function from ObjectWritable called, zalak");
//        ObjectWritable ow = new ObjectWritable();
//        Configuration conf = new Configuration();
//        ow.setConf(conf);
//        ow.readFields(in);
//
//
//        this.mapping = (Mapping)this.getMapping();
//
//    }


}
