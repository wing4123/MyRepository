package cn.tempus.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {

	public static void main(String[] args) throws IOException {
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
	
    public static void exeCmd(String commandStr) {  
        BufferedReader br = null;  
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
            	System.out.println(new String(sb.toString().getBytes("GBK"),"UTF-8"));
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }

}
