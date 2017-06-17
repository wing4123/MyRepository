package cn.tempus.oracle2sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tempus.dao1.EasyDao1;
import cn.tempus.dao2.EasyDao2;

@Service
public class oracle2sqlserverService {
	
	@Autowired
	protected EasyDao1 basicService1;
	
	@Autowired
	protected EasyDao2 basicService2;
	
	/**
	 * 同步今天
	 * @return
	 */
	public int SyncToday(){
		int result = 0;
		String sql1 = "select NVL(to_char(delivery_date,'yyyy/mm/dd'),to_char(sysdate,'yyyy/mm/dd')) sale_datetime,'closed' sale_status,NVL(b.sub_total,0) sub_total,NVL(b.sub_total,0) discount_percent,0 discount_amount,0 tax_percent,NVL(b.sub_total-b.sub_total/1.06,0) tax_amount,NVL(b.sub_total/1.06,0) grand_total,sysdate received_datetime from dual a left join ("
						+"select "
						+"   sum(actual_amount) sub_total,trunc(delivery_date) delivery_date "
						+"from "
						+"  tb_order_headers t "
						+"where"
						+"  tb_store_id=542 AND trunc(delivery_date) = trunc(sysdate) and status_code='RECEIPTED' "
						+"group by "
						+"  trunc(delivery_date) "
						+") b on b.delivery_date=trunc(sysdate) ";
		List<HashMap<String, Object>> olist = basicService1.SelectListBySql(sql1);
		
		if(olist.size()>0){
			Map<String,Object> args = new HashMap<String,Object>();
			StringBuffer sql2 = new StringBuffer("insert into dbo.posmaster (sale_id, sale_datetime, sale_status, sub_total, discount_percent, discount_amount, tax_percent, tax_amount, grand_total, received_datetime) values ");
			for(int i=0;i<olist.size();i++){
				HashMap<String,Object> map = olist.get(i);
				args.put("SALE_ID"+i, UUID.randomUUID().toString());
				args.put("SALE_DATETIME"+i, map.get("SALE_DATETIME"));
				args.put("SALE_STATUS"+i, map.get("SALE_STATUS"));
				args.put("SUB_TOTAL"+i, map.get("SUB_TOTAL"));
				args.put("DISCOUNT_PERCENT"+i, map.get("DISCOUNT_PERCENT"));
				args.put("DISCOUNT_AMOUNT"+i, map.get("DISCOUNT_AMOUNT"));
				args.put("TAX_PERCENT"+i, map.get("TAX_PERCENT"));
				args.put("TAX_AMOUNT"+i, map.get("TAX_AMOUNT"));
				args.put("GRAND_TOTAL"+i, map.get("GRAND_TOTAL"));
				args.put("RECEIVED_DATETIME"+i, map.get("RECEIVED_DATETIME"));
				
				sql2.append("(#{SALE_ID"+i+"},#{SALE_DATETIME"+i+"},#{SALE_STATUS"+i+"},#{SUB_TOTAL"+i+"},#{DISCOUNT_PERCENT"+i+"},#{DISCOUNT_AMOUNT"+i+"},#{TAX_PERCENT"+i+"},#{TAX_AMOUNT"+i+"},#{GRAND_TOTAL"+i+"},#{RECEIVED_DATETIME"+i+"})");
			}
			args.put("#SQL", "delete from dbo.posmaster where sale_datetime=CONVERT(varchar,getdate(),112)");
			basicService2.DeleteData(args);
			
			args.put("#SQL", StringUtils.removeEnd(sql2.toString(), ","));
			result = basicService2.InsertData(args);
		}
		
		return result;
	}
	
	/**
	 * 同步昨天
	 * @return
	 */
	public int SyncYesterday(){//CAST(sys_guid() as varchar2(50))
		int result = 0;
		String sql1 = "select CAST(sys_guid() as varchar2(50)) sale_id,to_char(sysdate-1,'yyyy/mm/dd') sale_datetime,'closed' sale_status,NVL(b.sub_total,0) sub_total,NVL(b.sub_total,0) discount_percent,0 discount_amount,0 tax_percent,NVL(b.sub_total-b.sub_total/1.06,0) tax_amount,NVL(b.sub_total/1.06,0) grand_total,sysdate received_datetime from dual a left join ("
						+"select "
						+"   sum(actual_amount) sub_total,trunc(delivery_date) delivery_date "
						+"from "
						+"  tb_order_headers t "
						+"where"
						+"  tb_store_id=542 AND trunc(delivery_date) = trunc(sysdate-1) and status_code='RECEIPTED' "
						+"group by "
						+"  trunc(delivery_date) "
						+") b on b.delivery_date=trunc(sysdate-1) ";
		
		List<HashMap<String, Object>> olist = basicService1.SelectListBySql(sql1);
		if(olist.size()>0){
			Map<String,Object> args = new HashMap<String,Object>();
			StringBuffer sql2 = new StringBuffer("insert into dbo.posmaster (sale_id, sale_datetime, sale_status, sub_total, discount_percent, discount_amount, tax_percent, tax_amount, grand_total, received_datetime) values ");
			for(int i=0;i<olist.size();i++){
				HashMap<String,Object> map = olist.get(i);
				args.put("SALE_ID"+i, map.get("SALE_ID"));
				args.put("SALE_DATETIME"+i, map.get("SALE_DATETIME"));
				args.put("SALE_STATUS"+i, map.get("SALE_STATUS"));
				args.put("SUB_TOTAL"+i, map.get("SUB_TOTAL"));
				args.put("DISCOUNT_PERCENT"+i, map.get("DISCOUNT_PERCENT"));
				args.put("DISCOUNT_AMOUNT"+i, map.get("DISCOUNT_AMOUNT"));
				args.put("TAX_PERCENT"+i, map.get("TAX_PERCENT"));
				args.put("TAX_AMOUNT"+i, map.get("TAX_AMOUNT"));
				args.put("GRAND_TOTAL"+i, map.get("GRAND_TOTAL"));
				args.put("RECEIVED_DATETIME"+i, map.get("RECEIVED_DATETIME"));
				
				sql2.append("(#{SALE_ID"+i+"},#{SALE_DATETIME"+i+"},#{SALE_STATUS"+i+"},#{SUB_TOTAL"+i+"},#{DISCOUNT_PERCENT"+i+"},#{DISCOUNT_AMOUNT"+i+"},#{TAX_PERCENT"+i+"},#{TAX_AMOUNT"+i+"},#{GRAND_TOTAL"+i+"},#{RECEIVED_DATETIME"+i+"})");
			}
			args.put("#SQL", "delete from dbo.posmaster where CONVERT(varchar,sale_datetime,112)=CONVERT(varchar,getdate()-1,112)");
			basicService2.DeleteData(args);
			
			args.put("#SQL", StringUtils.removeEnd(sql2.toString(), ","));
			result = basicService2.InsertData(args);
		}
		return result;
	}
	
	/**
	 * 同步指定日期
	 * @return
	 */
	@Transactional
	public int SyncSpecifiedDate(String specifieddate){
		int result = 0;
		String sql1 = "select '"+specifieddate+"' sale_datetime,'closed' sale_status,NVL(b.sub_total,0) sub_total,NVL(b.sub_total,0) discount_percent,0 discount_amount,0 tax_percent,NVL(round(b.sub_total-b.sub_total/1.06,5),0) tax_amount,NVL(round(b.sub_total/1.06,5),0) grand_total,sysdate received_datetime from dual a left join ("
						+"select "
						+"   sum(actual_amount) sub_total,trunc(delivery_date) delivery_date "
						+"from "
						+"  tb_order_headers t "
						+"where"
						+"  tb_store_id=542 AND trunc(delivery_date) = trunc(to_date('"+specifieddate+"','yyyy/mm/dd')) and status_code='RECEIPTED' "
						+"group by "
						+"  trunc(delivery_date) "
						+") b on b.delivery_date=trunc(to_date('"+specifieddate+"','yyyy/mm/dd')) ";
		
		List<HashMap<String, Object>> olist = basicService1.SelectListBySql(sql1);
		if(olist.size()>0){
			Map<String,Object> args = new HashMap<String,Object>();
			StringBuffer sql2 = new StringBuffer("insert into dbo.posmaster (sale_id, sale_datetime, sale_status, sub_total, discount_percent, discount_amount, tax_percent, tax_amount, grand_total, received_datetime) values ");
			for(int i=0;i<olist.size();i++){
				HashMap<String,Object> map = olist.get(i);
				args.put("SALE_ID"+i, UUID.randomUUID().toString());
				args.put("SALE_DATETIME"+i, map.get("SALE_DATETIME"));
				args.put("SALE_STATUS"+i, map.get("SALE_STATUS"));
				args.put("SUB_TOTAL"+i, map.get("SUB_TOTAL"));
				args.put("DISCOUNT_PERCENT"+i, map.get("DISCOUNT_PERCENT"));
				args.put("DISCOUNT_AMOUNT"+i, map.get("DISCOUNT_AMOUNT"));
				args.put("TAX_PERCENT"+i, map.get("TAX_PERCENT"));
				args.put("TAX_AMOUNT"+i, map.get("TAX_AMOUNT"));
				args.put("GRAND_TOTAL"+i, map.get("GRAND_TOTAL"));
				args.put("RECEIVED_DATETIME"+i, map.get("RECEIVED_DATETIME"));
				
				sql2.append("(#{SALE_ID"+i+"},#{SALE_DATETIME"+i+"},#{SALE_STATUS"+i+"},#{SUB_TOTAL"+i+"},#{DISCOUNT_PERCENT"+i+"},#{DISCOUNT_AMOUNT"+i+"},#{TAX_PERCENT"+i+"},#{TAX_AMOUNT"+i+"},#{GRAND_TOTAL"+i+"},#{RECEIVED_DATETIME"+i+"})");
			}
			args.put("#SQL", "delete from dbo.posmaster where CONVERT(varchar,sale_datetime,111)='"+specifieddate+"'");
			basicService2.DeleteData(args);
			
			args.put("#SQL", StringUtils.removeEnd(sql2.toString(), ","));
			result = basicService2.InsertData(args);
		}
		return result;
	}
	
	public Map<String,Object> getTableData(int pagenum){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select "+(pagenum*10-10)+"+rownum seq,sale_id,CONVERT(varchar(10),sale_datetime, 23) sale_datetime,sale_status,sub_total,discount_percent,discount_amount,tax_percent,tax_amount,grand_total,CONVERT(varchar(10),received_datetime, 20) received_datetime from (select ROW_NUMBER() OVER (ORDER BY sale_datetime desc) as rownum,* from posmaster) t  where t.rownum between "+(pagenum*10-10)+" and "+pagenum*10);
		List<HashMap<String, Object>> list = basicService2.SelectListBySqlWithWhere(args);
		int total=basicService2.SelectCountBySql("select count(*) from posmaster");
		total=(int)Math.ceil((double)total/10);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sb.append("<li><a href='#'>"+(pagenum*10+i+1)+"</a></li>");
		}
		map.put("list", list);
		map.put("total", total);
		map.put("page", sb.toString());
		
		return map;
	}
	
}
