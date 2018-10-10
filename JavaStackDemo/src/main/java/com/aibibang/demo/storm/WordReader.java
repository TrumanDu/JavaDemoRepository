package com.aibibang.demo.storm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/** 
* @author: Truman.P.Du 
* @since: 2016年4月12日 上午9:33:09 
* @version: v1.0
* @description:
*/
public class WordReader implements IRichSpout{
	private SpoutOutputCollector collector ;
    private FileReader fileReader ;
    BufferedReader reader;
    private boolean completed = false;
	public void ack(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("WordReader.ack(Object arg0):"+arg0);
	}

	public void activate() {
		// TODO Auto-generated method stub
		System.out.println("WordReader.activate");
	}

	public void close() {
		// TODO Auto-generated method stub
		System.out.println("WordReader.close");
	}

	public void deactivate() {
		// TODO Auto-generated method stub
		System.out.println("WordReader.deactivate");
	}

	public void fail(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("WordReader.fail(Object arg0):"+arg0);
	}

	public void nextTuple() {
		// TODO Auto-generated method stub
		/**
         * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
         */
         if (completed ) {
               try {
                    Thread. sleep(1000);
              } catch (InterruptedException e ) {
                     // 什么也不做
              }
               return;
        }
        String str;
         
         try {
               int i = 0;
               // 读所有文本行
               while ((str = reader.readLine()) != null) {
                    System. out.println("WordReader.nextTuple(),emits time:" + i++);
                     /**
                     * 按行发布一个新值
                     */
                     this.collector .emit(new Values( str), str );
              }
        } catch (Exception e ) {
               throw new RuntimeException("Error reading tuple", e);
        } finally {
               completed = true ;
        }
		
		
	}

	public void open(Map conf, TopologyContext arg1, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		System.out.println("WordReader.open(Map conf, TopologyContext arg1, SpoutOutputCollector collector)");
		 String fileName = conf .get("fileName").toString();
        InputStream inputStream=WordReader.class.getClassLoader().getResourceAsStream(fileName);
         reader =new BufferedReader(new InputStreamReader(inputStream ));
         this.collector = collector;
		
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		System.out.println("WordReader.declareOutputFields");
		arg0.declare(new Fields("line"));
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		System.out.println("WordReader.getComponentConfiguration");
		return null;
	}
 
}
