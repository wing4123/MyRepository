package cn.tempus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cmdUtil {

	public void excutecmd(String cmd){
		BufferedReader br=null;  
        try {  
            Process p=Runtime.getRuntime().exec(cmd);  
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
