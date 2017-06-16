package cn.tempus.oracle2sqlserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("session")
@RequestMapping("/control")
public class oracle2sqlserverAction {
	
	@Autowired
    private oracle2sqlserverService oracle2sqlserverservice;
	
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
	
	public void exec(){
		BufferedReader br=null;  
        try {  
            Process p=Runtime.getRuntime().exec("ping lol.qq.com -t");  
            br=new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));  
            String line=null;  
            while((line=br.readLine())!=null){  
                System.out.println(line);
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(br!=null){  
                try{  
                    br.close();  
                }catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        } 
	}
	
}
