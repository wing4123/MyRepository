package cn.tempus.dao1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EasyDao1 {
	
	public HashMap<String,Object> GetSinglerData(String sql){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public HashMap<String,Object> GetSinglerData(Map<String, Object> args){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public HashMap<String,Object> SelectKVMap(String sql,String keyName,String valueName){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		HashMap<String,Object> map=new HashMap<String, Object>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item.get(valueName));
		}
		return map;
	}
	
	public HashMap<String,Object> SelectKVMap(Map<String, Object> args,String keyName,String valueName){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		HashMap<String,Object> map=new HashMap<String, Object>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item.get(valueName));
		}
		return map;
	}
	
	public HashMap<String,HashMap<String,Object>>  SelectKVMapTable(String sql,String keyName){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		HashMap<String,HashMap<String,Object>> map=new HashMap<String, HashMap<String,Object>>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item);
		}
		return map;
	}
	
	public HashMap<String,HashMap<String,Object>>  SelectKVMapTable(Map<String, Object> args,String keyName){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		HashMap<String,HashMap<String,Object>> map=new HashMap<String, HashMap<String,Object>>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item);
		}
		return map;
	}
	
	
	public List< HashMap<String,Object> > GetAllData(String tablename){
		return basicDao.GetAllData(tablename);
	}
	public int GetAllDataCount(String tablename){
		return basicDao.GetAllDataCount(tablename);
	}
	public List< HashMap<String,Object> > GetAllDataWithWhere(Map<String, Object> args){
		return basicDao.GetAllDataWithWhere(args);
	}
	public List< HashMap<String,Object> > SelectListBySql(String sql){
		return basicDao.SelectListBySql(sql);
	}
	public int SelectCountBySql(String sql){
		return basicDao.SelectCountBySql(sql);
	}
	public List< HashMap<String,Object> > SelectListBySqlWithWhere(Map<String, Object> args){
		return basicDao.SelectListBySqlWithWhere(args);
	}
	public int SelectCountBySqlWithWhere(Map<String, Object> args){	
		return basicDao.SelectCountBySqlWithWhere(args);
	}
	public int InsertData(Map<String, Object> args){
		return basicDao.InsertData(args);
	}
	public int UpdateData(Map<String, Object> args){
		return basicDao.UpdateData(args);
	}
	public int DeleteData(Map<String, Object> args){
		return basicDao.DeleteData(args);
	}
	
	public Object GetFirstValueBySql(String sql){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		if(list.size()>0){
			if(list.get(0)==null)return null;
			return list.get(0).values().toArray()[0];
		}else{
			return null;
		}
		 
	}
	
	public Object GetFirstValueBySqlWithWhere(Map<String, Object> args){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		if(list.size()>0){
			if(list.get(0)==null)return null;
			return list.get(0).values().toArray()[0];
		}else{
			return null;
		}
	}
	
	public String BuildPageSql(String sqlandorder,int ipageStart,int ipagesize){
		return "select * from ( select rownum r,t_.* from ("+sqlandorder+" ) t_ ) where r>"+ipageStart+" and r<="+(ipageStart+ipagesize);
	}
	
	@Autowired
	private BasicDaoI1 basicDao; 
}
