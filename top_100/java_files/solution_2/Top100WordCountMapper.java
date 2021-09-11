import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Top100WordCountMapper extends Mapper<LongWritable, Text,LongWritable,Text>
{
private static final LongWritable one = new LongWritable(1);
private Text word = new Text();
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
{

/*Retrieving tokens from string input*/
StringTokenizer tokenizer = new StringTokenizer(value.toString());
while (tokenizer.hasMoreTokens())
{

/*While tokens found put initial count as 1*/
word.set(tokenizer.nextToken());
context.write(one,word);
}
}
}