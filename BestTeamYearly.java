

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BestTeamYearly {

	/**
	 * 3. which is the best team in each year
	 */
	public static class MyMapper extends Mapper<LongWritable,Text,IntWritable,Text>{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
			String arr[]=value.toString().split(",");
			context.write( new IntWritable(Integer.parseInt(arr[1])),new Text(arr[10]));
		}
	}
	
	
	public static class MyPartitioner extends Partitioner<IntWritable,Text>{

		/*public void configure(JobConf arg0) {
			// TODO Auto-generated method stub
			
		}*/

		public int getPartition(IntWritable key, Text value, int repo) {
			if(key.get()==2008){
				return 0;
			}
			if(key.get()==2009){
				return 1;
			}
			if(key.get()==2010){
				return 2;
			}
			if(key.get()==2011){
				return 3;
			}
			if(key.get()==2012){
				return 4;
			}
			if(key.get()==2013){
				return 5;
			}
			if(key.get()==2014){
				return 6;
			}
			if(key.get()==2015){
				return 7;
			}
			if(key.get()==2016){
				return 8;
			}
			else{
				return 9;
			}
			
		}
		
	}
	public static class MyReducer extends Reducer<IntWritable,Text,IntWritable,Text>{
		public void reduce(IntWritable key,Iterable<Text> value,Context context) throws IOException, InterruptedException{
			int c=0;int max=0;

			List ls=new ArrayList();
			for(Text x:value){
				c++;
				ls.add(c);
			}
			
			
			//context.write(key, c);
		
		
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration c=new Configuration();
		Job job=Job.getInstance(c,"BestTeam");
		job.setJarByClass(BestTeamYearly.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setPartitionerClass(MyPartitioner.class);
		job.setNumReduceTasks(3);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileSystem.get(c).delete(new Path(args[1]),true);
		FileInputFormat.addInputPath(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true)?0:1);
	}

}
