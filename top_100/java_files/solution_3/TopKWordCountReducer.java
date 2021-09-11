import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;
public class TopKWordCountReducer extends Reducer<Text, IntWritable, IntWritable, Text>{

private TreeMap<Integer, String> sortedMap;
@Override
public void setup(Context context) throws IOException,
                                 InterruptedException
{
    sortedMap = new TreeMap<Integer, String>();
}
public void reduce(Text key, Iterable <IntWritable> values, Context context) throws
IOException, InterruptedException
{

/*Initial count will be 0 for a keyword*/
	Integer total = 0;
	for (IntWritable value: values)
	{

	/*Getting previous value and add new value in count*/
	total += value.get();
	}
	String wordStr=key.toString();
	sortedMap.put(total,wordStr);
	if (sortedMap.size() > 100)
	{
	    sortedMap.remove(sortedMap.firstKey());
	}

}

@Override
public void cleanup(Context context) throws IOException,
                                   InterruptedException
{
	int ct=1;
	for(Map.Entry<Integer,String> entry: sortedMap.entrySet()){
		Integer t=entry.getKey();
		context.write(new IntWritable(t.intValue()),new Text(entry.getValue()));
	}
}

}