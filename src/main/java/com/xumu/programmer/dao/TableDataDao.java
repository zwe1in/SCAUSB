package com.xumu.programmer.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xumu.programmer.util.MysqlClient;

@Repository
@Transactional
public class TableDataDao {
	private String userName = "zw";
    private String password = "lovxzy";
    private int port = 3306;
    private String host = "112.74.181.170";
    private String dbName = "db_environment"; //该类查询的ku
    private MysqlClient mysqlClient;
    private InformationDao informationDao;
    
    public TableDataDao() throws Exception {
        mysqlClient = new MysqlClient(host, port, dbName, userName, password);
        informationDao = new InformationDao();
    }
    
    /**
     * 
     * @param tableName
     * @return 返回一个map中内容格式为      字段注释：value 
     */
    public List<Map<String, String>> getDisinfectionData(String tableName) {
    	Map<String, String> columnAndComment = informationDao.getColumnAndCommentForTableName(tableName, dbName);
    	String sql = querySqlForMap(columnAndComment, tableName);
    	List<Map<String, String>> rows = mysqlClient.queryForLabel(sql);
    	return rows;
    }
    
    public List<Map<String,Object>> getTableTitle(String table_name){
    	Map<String, String> columnAndComment = informationDao.getColumnAndCommentForTableName(table_name, dbName);
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	for(Map.Entry<String, String> entry : columnAndComment.entrySet()) {
    		if(entry.getKey().equals("timestamp") || entry.getKey().equals("id")) {
    			continue;
    		}
    		Map<String,Object> temp = new HashMap<String, Object>();
    		temp.put("field", entry.getKey());
    		temp.put("title", entry.getValue());
    		temp.put("width", 120);
    		temp.put("sort", true);
    		result.add(temp);
    	}
    	//加入时间列
//    	Map<String,Object> time = new HashMap<String, Object>();
//    	time.put("field","timestamp");
//    	time.put("title", "时间");
//    	time.put("width", 350);
//    	time.put("sort", true);
//    	result.add(time);
//    	System.out.println(result);
    	return result;
    }
    
    public Map<String,String> getDataType(String table_name){
    	Map<String, String> columnAndComment = informationDao.getColumnAndCommentForTableName(table_name, dbName);
    	return columnAndComment;
    }
    
    /**
     * 
     * @param columnAndComment
     * @param tableName
     * @return 一个用map内容拼凑成的查询语句sql
     */
    private String querySqlForMap(Map<String, String> columnAndComment, String tableName) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select ");
    	
    	for (String column : columnAndComment.keySet()) {
    		sql.append("`"+column+"`" + " as " +"'"+ columnAndComment.get(column) + "', ");
    	}
    	sql = sql.deleteCharAt(sql.length()-2);
    	sql.append("from " + tableName+" order by timestamp desc");
    	return sql.toString();
    	
    }

	public List<Map<String, String>> getPaintData(String table_name, String type, String date) throws ParseException {
		String sql = "select "+ type +",timestamp from "+table_name+ " where date_format(timestamp,'%Y-%m-%d') = '"+date+"' order by timestamp asc";
		List<Map<String, String>> result = mysqlClient.query(sql);
		System.out.println("result:"+result);
		return result;
	}
	
	/**
	 * 分页查询数据
	 * @param pageNum	页码
	 * @param pageSize	该页容量
	 * @param table_name	表名
	 * @return
	 */
	public List<Map<String, String>> queryDataForPage(String table_name, int pageNum, int pageSize){
    	Map<String, String> columnAndComment = informationDao.getColumnAndCommentForTableName(table_name, dbName);
    	String sql = querySqlForMap(columnAndComment, table_name);
    	sql += " limit "+(pageNum-1)*pageSize+","+pageSize;
    	List<Map<String, String>> rows = mysqlClient.queryForLabel(sql);
    	return rows;
	}
	
	/**
	 * 获取页数
	 * @param table_name
	 * @return
	 */
	public int getTotalPage(String table_name) {
		String sql = "select count(*) from "+table_name;
		int total = mysqlClient.getTotalCount(sql);
		return total/10+1;
	}
	
	/**
	 * 获取设备状态
	 * @param table_name
	 * @return
	 */
	public Map<String,String> getEquipStatus(String table_name){
		String sql = "select * from equipment_status where table_name = '"+table_name+"'";
		Map<String,String> equipment = new HashMap<String, String>();
		equipment = mysqlClient.query(sql).get(0);
		return equipment;
	}
	
	public boolean equipmentUpdate(String table_name, String type, int status) {
		String sql = "update equipment_status set "+type+" = "+status+" where table_name = '"+table_name+"'";
		try {			
			mysqlClient.execute(sql);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
