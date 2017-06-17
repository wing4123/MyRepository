package cn.tempus.oracle2sqlserver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tempus.task.oracle2sqlserverTask;
import cn.tempus.utils.cmdUtil;
import net.sf.json.JSONObject;

@Controller
@Scope("session")
@RequestMapping("/control")
public class oracle2sqlserverAction {
	
	@Autowired
    private oracle2sqlserverService oracle2sqlserverservice;
	@Autowired
	oracle2sqlserverTask task;
	
	@RequestMapping("/home")
	public String home(){
		return "index";
	}
	
	@RequestMapping(value="/SyncToday")
	@ResponseBody
	public int SyncToday(){
		String today = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
		int result = oracle2sqlserverservice.SyncSpecifiedDate(today);
		return result;
	}
	
	@RequestMapping(value="/SyncYesterday")
	@ResponseBody
	public int SyncYesterday(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
		int result = oracle2sqlserverservice.SyncSpecifiedDate(yesterday);
		return result;
	}
	
	@RequestMapping(value="/SyncSpecifiedDate")
	@ResponseBody
	public int SyncSpecifiedDate(@RequestParam("specifieddate")String specifieddate){
		int result = oracle2sqlserverservice.SyncSpecifiedDate(specifieddate);
		return result;
	}
	
	@RequestMapping(value="/getTableData")
	@ResponseBody
	public Map<String,Object> getTableData(@RequestParam("pagenum")int pagenum){
		Map<String,Object> result = oracle2sqlserverservice.getTableData(pagenum);
		return result;
	}
	
	@RequestMapping(value="/connectVPN")
	@ResponseBody
	public JSONObject connectVPN(){
		String result = task.connectVPN();
		JSONObject jo = new JSONObject();
		if(result.contains("已连接")||result.contains("已经连接")){
			jo.put("success", true);
			jo.put("msg", "连接成功！");
		}else{
			jo.put("success", false);
			jo.put("msg", "连接成功！");
		}
		System.out.println(jo);
		return jo;
	}
	
}
