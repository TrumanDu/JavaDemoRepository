package com.aibibang.demo.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/** 
* @author: Truman.P.Du 
* @since: 2016年6月29日 下午2:34:34 
* @version: v1.0
* @description:
*/
public class SQLiteJDBC {
	 public static void main( String args[] )
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:deploy.db");
	      System.out.println("Opened database successfully");
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE soft "+
	                   "(ID INT PRIMARY KEY     NOT NULL,"+
	                   " name           CHAR(200)    NOT NULL,"+ 
	                   " hostUserName   CHAR(50)      NOT NULL, "+
	                   " hostPassword   CHAR(50), "+
	                    "path   CHAR(100), "+
	                   " startScript   text, "+
	                   " stopScript   text, "+
	                    "filePath   CHAR(100), "+
	                   "hostIPs         text)"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	  }
}
