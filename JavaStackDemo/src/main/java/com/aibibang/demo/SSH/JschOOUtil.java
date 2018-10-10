package com.aibibang.demo.SSH;
/** 
* @author: Truman.P.Du 
* @since: 2016年4月6日 上午9:37:45 
* @version: v1.0
* @description:
*/
public class JschOOUtil extends JschHandler{  
	  
	  
	  
    public static JschOOUtil jschUtil = null;  
  
    /** 
     * 
     * @param host 
     * @param port 
     * @param userName 
     * @param passWord 
     * @param timeOut 
     */  
  
    public JschOOUtil(String host, int port, String userName, String passWord, int timeOut) {  
        this.jschHost = host;  
        this.jschPort = port;  
        this.jschUserName = userName;  
        this.jschPassWord = passWord;  
        this.jschTimeOut = timeOut;  
    }  
  
  
    /** 
     * 
     * @param host 
     * @param port 
     * @param userName 
     * @param passWord 
     * @param timeOut 
     * @return 
     */  
    public static JschOOUtil getJschUtilInstance(String host,int port,String userName,String passWord,int timeOut) {  
        if(jschUtil == null) {  
            synchronized (JschOOUtil.class) {  
                jschUtil = new JschOOUtil(host,port,userName,passWord,timeOut);  
            }  
        }  
       return  jschUtil;  
    }  
  
  
}  
