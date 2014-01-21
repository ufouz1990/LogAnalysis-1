package com.visualpath.loganalysis.client;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.visualpath.loganalysis.mapper.LogProcessMapper;
/**
 * ProcessLogsClient: client to run the LogAnalysis job
 * @author Murthy
 *
 */
public class ProcessLogsClient {

  public static void main(String[] args) throws Exception  {
	  System.out.println("Started............");
	  
	String ouputPath = "/user/hduser/LogAnalysisOutput";
	
	//Deleting existing path -- starts
	Path p = new Path(ouputPath);
	Configuration config = new Configuration();
	FileSystem fs = FileSystem.get(URI.create(ouputPath), config);
	fs.delete(p); 
    // Deleting existing path -- ends
	
    Job job = new Job();
    job.setJarByClass(ProcessLogsClient.class);
    job.setJobName("Log Process");
    FileInputFormat.addInputPath(job, new Path("/user/hduser/LogAnalysis"));
	FileOutputFormat.setOutputPath(job, new Path(ouputPath));
  
    job.setMapperClass(LogProcessMapper.class);
    job.setNumReduceTasks(0);
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    job.waitForCompletion(true);
    System.out.println("Completed............");
  }
}
