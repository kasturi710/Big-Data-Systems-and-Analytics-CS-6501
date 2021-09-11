import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TopKWordCountMapper extends Mapper<Object, Text, Text, IntWritable>
{
private static final IntWritable one = new IntWritable(1);
private Text word = new Text();
public void map(Object key, Text value, Context context) throws IOException, InterruptedException
{

/*Retrieving tokens from string input*/
StringTokenizer tokenizer = new StringTokenizer(value.toString());
while (tokenizer.hasMoreTokens())
{

/*While tokens found put initial count as 1*/
word.set(tokenizer.nextToken());
context.write(word, one);
}
}
}