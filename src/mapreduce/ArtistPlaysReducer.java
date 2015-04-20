package mapreduce;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ArtistPlaysReducer extends Reducer<Artist, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Artist key, Iterable<IntWritable> values, Context output)
            throws IOException, InterruptedException {
        int plays = 0;
        for(IntWritable value: values){
            plays+= value.get();
        }
        result.set(plays);

        output.write(new Text(key.toString()),result);
    }
}