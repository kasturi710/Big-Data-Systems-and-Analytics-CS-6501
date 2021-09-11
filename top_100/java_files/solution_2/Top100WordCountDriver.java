import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
public class Top100WordCountDriver extends Configured implements Tool
{

@Override
public int run(String[] args)
{
int result = -1;
try
{
Configuration configuration = new Configuration();
Job job = new Job(configuration, "Word Frequency Count Job");
job.setJarByClass(Top100WordCountDriver.class);
job.setMapperClass(Top100WordCountMapper.class);
job.setReducerClass(Top100WordCountReducer.class);
job.setOutputKeyClass(LongWritable.class);
job.setOutputValueClass(Text.class);
FileInputFormat.setInputPaths(job, new Path(args[0]));
FileOutputFormat.setOutputPath(job, new Path(args[1]));
System.exit(job.waitForCompletion(true) ? 0 : 1);
if (job.isSuccessful())
{
System.out.println("Job is Completed Successfully");
} else
{
System.out.println("Error in job...");
}
} catch (Exception exception)
{
exception.printStackTrace();
}
return result;
}
public static void main(String[] args) throws Exception
{
int response = ToolRunner.run(new Configuration(), new Top100WordCountDriver(), args);
System.out.println("Result = " + response);
}
}