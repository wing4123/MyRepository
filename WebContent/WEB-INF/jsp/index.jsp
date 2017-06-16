<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	Calendar c1 = Calendar.getInstance();
	c1.add(Calendar.DAY_OF_MONTH, -1);
	String yesterday = new SimpleDateFormat("yyyy/MM/dd").format(c1.getTime());
	String today = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Oracle2SQLServer</title>
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../bootstrap/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="row">
			<div id="successAlert" class="alert alert-info text-center" style="font-size: 18px;font-weight: bold;">
				数据同步控制台
			</div>
			<form class="form-horizontal" role="form">
				<div class="form-group">
    				<label class="col-md-2 control-label">同步今天</label>
    				<div class="col-md-4">
      					<input type="text" class="form-control" readonly="readonly" value='<%=today %>' />
   					</div>
   					<button type="button" class="btn btn-success col-md-1" onclick="synctoday()">同步</button>
  				</div>
  				<div class="form-group">
    				<label class="col-md-2 control-label">同步昨天</label>
    				<div class="col-md-4">
      					<input type="text" class="form-control" readonly="readonly" value='<%=yesterday %>' />
   					</div>
   					<button type="button" class="btn btn-success col-md-1" onclick="syncyesterday()">同步</button>
  				</div>
  				<div id="specifieddate" class="form-group">
    				<label class="col-md-2 control-label">同步指定日期</label>
    				<div class="col-md-4">
	    				<div class="input-group date form_date" data-date-format="yyyy/mm/dd">
						    <input class="form-control" onchange="$('#specifieddate').removeClass('has-error')" />
						    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
   					<button type="button" class="btn btn-success col-md-1" onclick="syncspecifieddate()">同步</button>
  				</div>
			</form>
		</div> 
	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						同步结果
					</h4>
				</div>
				<div class="modal-body">
					<div id="successAlert" class="alert alert-success">
						<strong>同步成功。</strong>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
				</div>
			</div><!-- /.modal-content -->
		</div>
	</div>
	
	<script src="../js/jquery-3.2.1.js"></script>
	<script src="../bootstrap/js/bootstrap.js"></script>
	<script src="../bootstrap/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script src="../bootstrap/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script>
		$(function(){
			$("#specifieddate .input-group").datetimepicker({
				autoclose: true,
				language: "zh-CN",
				minView: "month",
				todayBtn: true,
				todayHighlight: true,
				pickerPosition: "bottom-left",
				startDate: "2015/07/06",
				endDate: new Date(),
				weekStart: 1
			});
		});
	
		var synctoday = function(){
			$.get("./SyncToday",{},function(data){
				if(data>0){
					
				}else{
					
				}
				$("#myModal").modal("show");
			});
		}
		
		var syncyesterday = function(){
			$.get("./SyncYesterday",{},function(data){
				if(data>0){
					
				}else{
					
				}
				$("#myModal").modal("show");
			});
		}
		
		var syncspecifieddate = function(){
			var specifieddate = $("#specifieddate input").val();
			if(specifieddate==""){
				$("#specifieddate").addClass("has-error");
			}else{
				$.get("./SyncSpecifiedDate",{"specifieddate":specifieddate},function(data){
					if(data>0){
						
					}else{
						
					}
					$("#myModal").modal("show");
				});
			}
		}

	</script>
</body>
</html>