package cn.tempus.dao2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface BasicDaoI2 {

	@SelectProvider(type = BasicSqlProvider2.class, method = "GetAllData")
	public List< HashMap<String,Object> > GetAllData(String tablename);
	
	@SelectProvider(type = BasicSqlProvider2.class, method = "GetAllDataCount")
	public int GetAllDataCount(String tablename);
	
	@SelectProvider(type = BasicSqlProvider2.class, method = "GetAllDataWithWhere")
	public List< HashMap<String,Object> > GetAllDataWithWhere(Map<String, Object> args);
	
	@SelectProvider(type = BasicSqlProvider2.class, method = "SelectListBySql")
	public List< HashMap<String,Object> > SelectListBySql(String sql);
	
	@SelectProvider(type = BasicSqlProvider2.class, method = "SelectCountBySql")
	public int SelectCountBySql(String sql);
	
	@SelectProvider(type = BasicSqlProvider2.class, method = "SelectListBySqlWithWhere")
	public List< HashMap<String,Object> > SelectListBySqlWithWhere(Map<String, Object> args);
	
	@SelectProvider(type = BasicSqlProvider2.class, method = "SelectCountBySqlWithWhere")
	public int SelectCountBySqlWithWhere(Map<String, Object> args);
	
	@InsertProvider(type = BasicSqlProvider2.class,method = "InsertDataBySql") 
	@Options(useGeneratedKeys=false )
	public int InsertData(Map<String, Object> args);
	
	@UpdateProvider(type = BasicSqlProvider2.class,method = "UpdateDataBySql") 
	//@Options(useGeneratedKeys=false )
	public int UpdateData(Map<String, Object> args);
	
	@DeleteProvider(type = BasicSqlProvider2.class,method = "DeleteDataBySql") 
	public int DeleteData(Map<String, Object> args);

	
}
