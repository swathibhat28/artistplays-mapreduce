package mapreduce;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ArtistPlaysApplication extends Configured implements Tool{

    public static void main(String[] args) throws Exception {
        
        int res = ToolRunner.run(new Configuration(), new ArtistPlaysApplication(), args);
        System.exit(res);       
    }

	@Override
    public int run(String[] args) throws Exception {
		
        if (args.length != 4) {
            System.out.println("usage: [input] [output] -artistNames [artistNamesFile]");
            System.exit(-1);
        }

        Job job = Job.getInstance(new Configuration());
        
        job.setMapOutputKeyClass(Artist.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        	
        job.setMapperClass(ArtistPlaysMapper.class);
        job.setReducerClass(ArtistPlaysReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

    	List<String> other_args = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			if("-artistNames".equals(args[i])) {
				
				// Logic to read the location of artist names file from the command line
				// The argument after -artistNames option will be taken as the location of artist
				// name file
		 
				job.addCacheFile(new Path(args[++i]).toUri());
				if (i+1 < args.length)
				{
					i++;
				}
				else
				{
					break;
				}
			}
			
			other_args.add(args[i]);
		}
 
		FileInputFormat.setInputPaths(job, new Path(other_args.get(0)));
		FileOutputFormat.setOutputPath(job, new Path(other_args.get(1)));

        job.setJarByClass(ArtistPlaysApplication.class);

        job.submit();
        return 0;
    }
}