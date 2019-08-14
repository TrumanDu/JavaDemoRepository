package com.aibibang.demo.sqoop2;

import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MDriverConfig;
import org.apache.sqoop.model.MFromConfig;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.model.MLink;
import org.apache.sqoop.model.MLinkConfig;
import org.apache.sqoop.model.MSubmission;
import org.apache.sqoop.model.MToConfig;
import org.apache.sqoop.validation.Status;

/** 
* @author: Truman.P.Du 
* @since: 2016年11月8日 下午8:32:03 
* @version: v1.0
* @description:
*/
public class SqoopDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqoopDemo sqoopDemo =new SqoopDemo();
		try {
			//
			//sqoopDemo.createJob();
			sqoopDemo.startJob(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public void createJob(){
		String url = "http://192.168.0.101:12000/sqoop/";
		SqoopClient client = new SqoopClient(url);
		//Creating dummy job object
		MJob job = client.createJob(1, 2);
		job.setName("MysqlToHDFS2");
		job.setCreationUser("Truman");
		// set the "FROM" link job config values
		MFromConfig fromJobConfig = job.getFromJobConfig();
		fromJobConfig.getStringInput("fromJobConfig.schemaName").setValue("AutoProgramming");
		fromJobConfig.getStringInput("fromJobConfig.tableName").setValue("sys_menu");
		fromJobConfig.getStringInput("fromJobConfig.partitionColumn").setValue("id");
		// set the "TO" link job config values
		MToConfig toJobConfig = job.getToJobConfig();
		toJobConfig.getStringInput("toJobConfig.outputDirectory").setValue("/user/truman/sqooptest3");
		// set the driver config values
		MDriverConfig driverConfig = job.getDriverConfig();
		//driverConfig.getStringInput("throttlingConfig.numExtractors").setValue("3");

		Status status = client.saveJob(job);
		if(status.canProceed()) {
		 System.out.println("Created Job with Job Name: "+ job.getName());
		} else {
		 System.out.println("Something went wrong creating the job");
		}
	}
	
	public void startJob(int jid) throws InterruptedException{
		String url = "http://192.168.0.101:12000/sqoop/";
		SqoopClient client = new SqoopClient(url);
		//Job start
		MSubmission submission = client.startJob(jid, null, 10);
		
		System.out.println("Job Submission Status : " + submission.getStatus());
		System.out.println("Hadoop job id :" + submission.getExternalJobId());
		System.out.println("Job link : " + submission.getExternalLink());
		System.out.println("Job execute successs......");
		//client.stopJob(1);
	}
	
	public void createLink(){
		System.setProperty("hadoop.home.dir", "D:/hadoop");
		String url = "http://192.168.0.101:12000/sqoop/";
		SqoopClient client = new SqoopClient(url);
		MLink link = client.createLink("generic-jdbc-connector");
		link.setName("AutoProgramming11");
		link.setCreationUser("Truman");
		MLinkConfig linkConfig = link.getConnectorLinkConfig();
		// fill in the link config values
		linkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://192.168.0.83/AutoProgramming");
		linkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
		linkConfig.getStringInput("linkConfig.username").setValue("root");
		linkConfig.getStringInput("linkConfig.password").setValue("123456");
		// save the link object that was filled
		Status status = client.saveLink(link);
		if(status.canProceed()) {
		 System.out.println("Created Link with Link Name : " + link.getName());
		} else {
		 System.out.println("Something went wrong creating the link");
		}
	}

}
