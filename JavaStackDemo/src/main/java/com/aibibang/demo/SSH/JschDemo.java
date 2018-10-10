package com.aibibang.demo.SSH;

import org.junit.Test;

/** 
* @author: Truman.P.Du 
* @since: 2016年4月6日 上午9:05:17 
* @version: v1.0
* @description:jsch使用demo
*/
public class JschDemo {

	JschOOUtil jschUtil =  JschOOUtil.getJschUtilInstance(JschConstantsOO.JSCH_REQ_HOST, JschConstantsOO.JSCH_REQ_PORT, JschConstantsOO.JSCH_REQ_USERNAME, JschConstantsOO.JSCH_REQ_PASSWORD, JschConstantsOO.JSCH_REQ_TIMEOUT);  
	  
    @Test  
    public void testExecCmd() throws Exception {  
        jschUtil.execCmd();  
    }  
  
    @Test  
    public void testUpload() throws Exception {  
        jschUtil.upload("/data/truman/jschtest/java/","e:/jsch/java/JavaLearn.java");  
    }  
  
  
    @Test  
    public void testDownload() throws Exception {  
        jschUtil.download("e:/jsch/","/data/truman/jschtest/jsch.txt");  
    }  
  
  
    @Test  
    public void testDelete() throws Exception {  
        jschUtil.delete("/data/truman/jschtest/jsch.txt");  
    }  

}
