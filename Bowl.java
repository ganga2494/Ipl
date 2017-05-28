import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Reducer;




public class Bowl {
    public static class ba extends Mapper<LongWritable,Text,Text,Text>{
        public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
            String arr[]=value.toString().split(",");
           
            if(arr[6].equals(arr[10])){
                if(arr[7].contains("field")){
            context.write(new Text(arr[14]), new Text(arr[14]));
        }
    }
    }
    }
public static class at extends Reducer<Text,Text,Text,IntWritable>{
   Text maxword=new Text();
    private int max = 0;
   
   

    public void reduce(Text key,Iterable<Text> value,Context context) throws IOException, InterruptedException{
       
          int count= 0;
            for (Text values : value)
            {
             
            count++;
           
            if(count> max)
            {
                max = count;
                maxword.set(key);
               
            }
          
        }}

        // only display the character which has the largest value
   
        protected void cleanup(Context context) throws IOException, InterruptedException {
            context.write(maxword,new IntWritable(max) );
        }
       
      }
   

public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
    Configuration obj=new Configuration();
    Job job=Job.getInstance(obj,"country");
    job.setJarByClass(Bowl.class);
    job.setMapperClass(ba.class);
job.setReducerClass(at.class);
job.setMapOutputKeyClass(Text.class);
job.setMapOutputValueClass(Text.class);
    //job.setNumReduceTasks(1);
     job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
       
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileSystem.get(obj).delete(new Path(args[1]), true);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
            
   
}}