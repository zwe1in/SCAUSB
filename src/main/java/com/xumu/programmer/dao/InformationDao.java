package com.xumu.programmer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xumu.programmer.util.MysqlClient;


public class InformationDao {
    private String userName = "zw";
    private String password = "lovxzy";
    private int port = 3306;
    private String host = "112.74.181.170";
    private String dbName = "information_schema";
    
    private static final Logger logger = LoggerFactory.getLogger(InformationDao.class);
    private MysqlClient mysqlClient;
    
    public InformationDao() throws Exception {
        mysqlClient = new MysqlClient(host, port, dbName, userName, password);
    }
    
        
    public Map<String, String> getColumnAndCommentForTableName(String tableName, String tableSchema) {
        try {
            String sql = "select column_name, COLUMN_COMMENT from information_schema.columns where table_name = '" + tableName + "' and table_schema = '" + tableSchema + "'";
            List<Map<String, String>> columns = mysqlClient.query(sql);
            Map<String, String> columnNameAndComment = new HashMap<String, String>();
            for (Map<String, String> column : columns) {
            	if (!column.get("COLUMN_COMMENT").equals("")) {
            		columnNameAndComment.put(column.get("COLUMN_NAME"), column.get("COLUMN_COMMENT"));
            	}
            }
            return columnNameAndComment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
