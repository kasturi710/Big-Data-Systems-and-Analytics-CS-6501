import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class Top100WordCountReducer extends Reducer<LongWritable, Text, LongWritable, Text>{
	private Map<String,Integer>wordMap=new HashMap();
	private Map<Integer,String>sortedMap=new TreeMap<>(Collections.reverseOrder());
public void reduce(LongWritable one, Iterable <Text> it, Context context) throws
IOException, InterruptedException
{

/*Initial count will be 0 for a keyword*/
int count = 0;
for (Text word: it){
	count=1;
	String wordStr=word.toString();
	if(wordMap.containsKey(wordStr)){
		count=wordMap.get(wordStr);
		count++;
	}
	wordMap.put(wordStr,count);
}
for(Map.Entry<String,Integer> entry: wordMap.entrySet()){
	sortedMap.put(entry.getValue(),entry.getKey());
}
int ct=1;
for(Map.Entry<Integer,String> entry: sortedMap.entrySet()){
	context.write(new LongWritable(entry.getKey()),new Text(entry.getValue()));
	if(ct==100)
		break;
	ct++;
}

}
}