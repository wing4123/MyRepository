package cn.tempus.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.tempus.oracle2sqlserver.oracle2sqlserverService;

@Component
public class oracle2sqlserverTask {
	
	@Autowired
	oracle2sqlserverService oracle2sqlserverservice;
	
    /**
     * 每天23:59:00定时同步
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void timingsync(){
    	int result = oracle2sqlserverservice.SyncToday();
    }
    
    /**
     * 每天23:58:00定时连接vpn
     */
    @Scheduled(cron = "0 58 23 * * ?")
    public void vpn(){
    	BufferedReader br=null;  
        try {  
            Process p=Runtime.getRuntime().exec("rasdial vpn S216 dS5GpeKj/LA=");  
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