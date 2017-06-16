package cn.tempus.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.tempus.dao1.EasyDao1;

@Service
public class testService {
	
	@Autowired
	protected EasyDao1 basicService;
	
	public List<HashMap<String, Object>> getUserlist(){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select fid,fname,fage,fgender ,date_format(fbirthday,'%Y-%m-%d') fbirthday, fcreatetime from test");
		List<HashMap<String, Object>> result=basicService.SelectListBySqlWithWhere(args);
		
		return result;
	}
	
	public List<HashMap<String, Object>> getUserById(String userid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select fid,fname,fage,fgender, date_format(fbirthday,'%Y-%m-%d') fbirthday,date_format(fcreatetime,'%Y-%m-%d %H:%i:%s') fcreatetime from test where fid=#{userid}");
		args.put("userid", userid);
		
		List<HashMap<String, Object>> result=basicService.SelectListBySqlWithWhere(args);
		
		return result;
	}
	
	@Transactional(value="transactionManager1", rollbackFor={Exception.class, RuntimeException.class})
	public int testinsert(HttpServletRequest request){
		Map<String,Object> args = new HashMap<String,Object>();
//		args.put("#SQL", "delete from tb_test");
//		int result = basicService.DeleteData(args);
//		args.put("#SQL", "ABC");
//		basicService.SelectListBySqlWithWhere(args);
		
		
		args.put("#SQL", "insert into tb_test values (#{fid},#{fname},#{fage})");
		args.put("fid", request.getParameter("fid"));
		args.put("fname", request.getParameter("fname"));
		args.put("fage", request.getParameter("fage"));
		int result = basicService.InsertData(args);
		String s=null;
		s.equals("");
		
		return result;
	}
}
