package com.xumu.programmer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlClient {
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	    Connection conn = null;
	    Statement stmt = null;
	    String host;
	    String dbName;
	    String userName;
	    String password;
	    Integer port;
	    
	    public MysqlClient() {
	        
	    }
	    
	    public MysqlClient(String host, Integer port, String dbName,
	            String userName, String password) throws Exception {
	        if (!this.initialize(host, port, dbName, userName, password)) {
	            throw new Exception("Failed to init MysqlClient");
	        }
	    }

	    public boolean initialize(String host, Integer port, String dbName, String userName, String password) {
	        this.host = host;
	        this.dbName = dbName;
	        this.userName = userName;
	        this.password = password;
	        this.port = port;
	        try {
	            Class.forName(JDBC_DRIVER);
	            // Create the connection
	            String db_url = "jdbc:mysql://" + host + ":" + port.toString() + "/" + dbName+"?useUnicode=true&characterEncoding=UTF-8";
	            conn = DriverManager.getConnection(db_url, userName, password);
	            stmt = conn.createStatement();
	            return true;
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public boolean isValid() {
	        try {
	            if (this.stmt.isClosed()) {
	                return false;
	            }
	            if (this.conn.isClosed()) {
	                return false;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	        
	        return true;
	    }
	    
	    public boolean execute(String sql) {
	        System.out.println(sql);
	        try {
	            if (!isValid()) {
	                if (initialize(host, port, dbName, userName, password)) {
	                    this.stmt.executeUpdate(sql);
	                    return true;
	                } else {
	                    return false;
	                }
	            } else {
	                this.stmt.executeUpdate(sql);
	                return true;
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            if ("08S01".equals(e.getSQLState()) || "40001".equals(e.getSQLState())) {
	                if (initialize(host, port, dbName, userName, password)) {
	                    return execute(sql);
	                } else {
	                    return false;
	                }
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public List<Map<String, String>> query(String sql) {
	        System.out.println(sql);
	        try {
	            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	            ResultSet res = this.stmt.executeQuery(sql);
	            ResultSetMetaData metaData = res.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (res.next()) {
	                Map<String, String> rowData = new HashMap<String, String>();
	                for (int i = 1; i <= columnCount; i++) {
//	                  System.out.println(metaData.getColumnName(i) + " : " + res.getString(i));
	                    rowData.put(metaData.getColumnName(i), res.getString(i));
	                }
	                result.add(rowData);
	            }
	            res.close();
//	            stmt.close();
//	            conn.close();
	            return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            if ("08S01".equals(e.getSQLState()) || "40001".equals(e.getSQLState())) {
	                if (initialize(host, port, dbName, userName, password)) {
	                    return query(sql);
	                } else {
	                    return null;
	                }
	            } else {
	                return null;
	            }
	        }
	    }
	    
	    public List<Map<String, String>> queryForLabel(String sql) {
	        System.out.println(sql);
	        try {
	            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	            ResultSet res = this.stmt.executeQuery(sql);
	            ResultSetMetaData metaData = res.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (res.next()) {
	                Map<String, String> rowData = new HashMap<String, String>();
	                for (int i = 1; i <= columnCount; i++) {
//	                  System.out.println(metaData.getColumnName(i) + " : " + res.getString(i));
	                    rowData.put(metaData.getColumnLabel(i), res.getString(i));
	                }
	                result.add(rowData);
	            }
	            res.close();
//	            stmt.close();
//	            conn.close();
	            return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            if ("08S01".equals(e.getSQLState()) || "40001".equals(e.getSQLState())) {
	                if (initialize(host, port, dbName, userName, password)) {
	                    return query(sql);
	                } else {
	                    return null;
	                }
	            } else {
	                return null;
	            }
	        }
	    }
	    
	    public boolean packData(String sql) {
	        System.out.println(sql);
	        try {
	            ResultSet res = this.stmt.executeQuery(sql);
	            res.close();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            if ("08S01".equals(e.getSQLState()) || "40001".equals(e.getSQLState())) {
	                if (initialize(host, port, dbName, userName, password)) {
	                    return packData(sql);
	                } else {
	                    return false;
	                }
	            } else {
	                return false;
	            }
	        }
	    }
	    
	    //获取总数据数量
	    public int getTotalCount(String sql) {
	    	int total = 0;
	    	try {
	    		ResultSet res = this.stmt.executeQuery(sql);
	    		if(res.next()) {
	    			total = res.getInt(1);
	    		}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	return total;
	    }
}
