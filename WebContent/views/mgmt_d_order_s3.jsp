<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我提交的订单</title>
<META HTTP-EQUIV="imagetoolbar" CONTENT="no">
<link rel="shortcut icon" href="/images/fav.ico" type="image/x-icon" />
<link rel="icon" href="/images/fav.ico" type="image/x-icon" />
<link rel="bookmark" href="/images/fav.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="css/index.css">
<script type="text/javascript" src="js/jquery.min.1.7.2.js"></script>
<script type="text/javascript" src="js/top_search.js"></script>
<script type="text/javascript" src="js/main_nav.js"></script>
<script type="text/javascript" src="js/mgmt.js"></script>
<script type="text/javascript" src="js/backtop.js"></script>
<script type="text/javascript" src="js/popup.js"></script>
<script type="text/javascript" src="js/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="js/focus_load.js"></script>
<%@ include file="jsTool.jsp" %>
<script type="text/javascript"> 
	$(function() {
		$('input, textarea').placeholder(); 
		
	});
</script>
</head>

<body onload="OnLoad()">

<%@ include file="qq.jsp"%>

<%@ include  file="topFrame.jsp"%>

<div id="main_frame">
	<a href="myinfo" hidefocus="true" class="a_text_main_title1">我的信息</a>&nbsp;&gt;&nbsp;我的交易
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="230" class="td_leftnav_top">
                <div id="main_frame_left">
                    <span class="text_mgmt_leftnav1"><span id="mgmt_nav_switch1a" class="span_mgmt_nav1" title="收起" onclick="mgmt_nav_switch1a();"></span><span id="mgmt_nav_switch1b" class="span_mgmt_nav2" title="展开" onclick="mgmt_nav_switch1b();"></span>我的交易</span>
                    <div id="mgmt_nav1">
                        <a href="getallfocus" class="a_mgmt_leftnav" hidefocus="true">我的关注</a>
                       	<% if((Integer)session.getAttribute("userKind") ==3) {%><!-- 企业用户 -->
                        <a href="getallresponse" class="a_mgmt_leftnav" hidefocus="true">我的反馈</a>
                         <%} %>
                      <% if((Integer)session.getAttribute("userKind") ==2) {%> <!-- 普通用户 -->
                        <a href="sendorderinfo" class="a_mgmt_leftnav1" hidefocus="true">我提交的订单</a>
                      <%} %>
                      <% if((Integer)session.getAttribute("userKind") ==3) {%><!-- 企业用户 -->
                        <a href="recieveorderinfo" class="a_mgmt_leftnav" hidefocus="true">我收到的订单</a>
                       <%} %>
                        <a href="mysettlement" class="a_mgmt_leftnav" hidefocus="true">我的结算</a>
                        <% if((Integer)session.getAttribute("userKind") ==2) {%>  <!-- 普通用户 -->
                        <a href="mycomplaint" class="a_mgmt_leftnav" hidefocus="true">我的投诉</a>
                       <%} %>
						</div>
                   <%@ include  file="mysource_leftnav_myresource.jsp"%>
                    <%@ include  file="mysource_leftnav_myplan.jsp"%>
                    <%@ include  file="mysource_leftnav_myanalysis.jsp"%>
                    <%@ include  file="mysource_leftnav_myaccount.jsp"%>
</div>
			</td>
			<td class="td_leftnav_top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_mgmt_right2a">
                    <tr>
                        <td>
                            <span class="span_mgmt_right2_text1">更新订单</span>
                            <span class="span_mgmt_right2_text2"><a href="javascript:history.go(-1);" hidefocus="true"><img src="images/btn_back1.png" class="span_mgmt_right2_pic1" title="返回" /></a></span>
                        </td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_mgmt_right3">
                    <tr>
                        <td class="td_mgmt_right3_td1a">
                           <form action="doUpdate" method="post" id="new_order">	          
                       
                            <div class="span_mgmt_right3_text4">基本信息</div>  
                               <table width="90%" border="0" cellspacing="0" cellpadding="0">
                               	          
                               <tr>
                                    <td width="120" height="40" class="td_mgmt_right3_td1b">订单编号：</td>
                                    <td>${orderInfo.orderNum }</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">所属客户：</td>
									<td>
										<select style="width:120px;" name="clientName" required id="clientName">
											<option value="" selected="selected">请选择</option>
                                        </select>
                                        	<!-- 	订单id	 -->
                                        <input name="id" type="hidden" value="${orderInfo.id }"/>
									</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">关联客户运单：</td>
                                    <td>
                                        <select style="width:120px;" onchange="change2();" id="isLinkToClientWayBill" name="isLinkToClientWayBill" required>
                                            <option value="" >请选择</option>
                                            <option value="有">有</option>
                                            <option value="无" >无</option>
                                        </select>
                                        <div id="p_detail" style="display:none;">
                                            <input type="text" value="${orderInfo.clientWayBillNum }" name="clientWayBillNum" class="input_mgmt1" style="width:176px;" placeholder="请输入客户运单号..."/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">承运方：</td>
                                    <td>${orderInfo.carrierName }</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">承运方合同：</td>
                                    <td>
                                        <select style="width:110px;" onchange="change_cert();" id="hasCarrierContract" name="hasCarrierContract" required>
                                            <option value="空" >请选择</option>
                                            <option value="有">有</option>
                                            <option value="无">无</option>
                                        </select>
                                        <div id="c_detail" style="display:none;">
                                            <select style="width:93px;" name="contractId" id="contractId">
                                                <option value="无" selected="selected">请选择</option>
                                            </select>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">资源分类：</td>
									<td>${orderInfo.resourceType }</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">资源名称：</td>
									<td>${orderInfo.resourceName }</td>
                                </tr>
                            </table>
                            <div class="span_mgmt_right3_text4">货物信息</div>      	          
                            <table width="90%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="120" height="40" class="td_mgmt_right3_td1b">货物名称：</td>
                                    <td><input type="text" name="goodsName" class="input_mgmt1" style="width:300px;" value="${orderInfo.goodsName }" required/></td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">货物重量：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:300px;" value="${orderInfo.goodsWeight }" name="goodsWeight" required/>
                                        (公斤)</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">货物体积：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:300px;" value="${orderInfo.goodsVolume }" name="goodsVolume" required/>
                                    (立方米)</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">货物声明价值：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:300px;" value="${orderInfo.declaredPrice }" name="declaredPrice" required/>
                                    (元)</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">保险费：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:300px;" value="${orderInfo.insurance }" name="insurance" required/>                                    (元)</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">运费：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:300px;" value="${orderInfo.expectedPrice } " name="expectedPrice" required/>
                                      (元)</td>
                                </tr>
                            </table>
                            <div class="span_mgmt_right3_text4" style="float:none;">地址信息</div>      	          
                            <table width="90%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="120" height="40" class="td_mgmt_right3_td1b">
                                    	发货人信息
                                        <a href="javascript:;" onclick="showid('popup2');" hidefocus="true"><img src="images/btn_address.png" title="查询" /></a>
                                    </td>
                                    <td width="250">&nbsp;</td>
                                    <td width="100" class="td_mgmt_right3_td1b">
                                    	收货人信息
                                        <a href="javascript:;" onclick="showid('popup3');" hidefocus="true"><img src="images/btn_address.png" title="查询" /></a>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">姓名：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:200px;" value="${orderInfo.deliveryName }" name="deliveryName" id="deliveryName" required/></td>
                                    <td class="td_mgmt_right3_td1b">姓名：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:200px;" value="${orderInfo.recieverName }" name="recieverName" id="recieverName" required/></td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">地址：</td>
                                    <td><textarea class="textarea_rating1" name="deliveryAddr" id="deliveryAddr" required>${orderInfo.deliveryAddr }</textarea></td>
                                    <td class="td_mgmt_right3_td1b">地址：</td>
                                    <td><textarea class="textarea_rating1" name="recieverAddr" id="recieverAddr" required>${orderInfo.recieverAddr }</textarea></td>
                                </tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">电话：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:200px;" value="${orderInfo.deliveryPhone }" name="deliveryPhone" id="deliveryPhone" required/></td>
                                    <td class="td_mgmt_right3_td1b">电话：</td>
                                    <td><input type="text" class="input_mgmt1" style="width:200px;" value="${orderInfo.recieverPhone }" name="recieverPhone" id="recieverPhone" required/></td>
                                </tr>
                                <tr>
                                    <td height="20" class="td_mgmt_right3_td1b">&nbsp;</td>
                                    <td><input type="checkbox" id="sender_info" name="sender_info"/>&nbsp;加入常用发货地址</td>
                                    <td class="td_mgmt_right3_td1b">&nbsp;</td>
                                    <td><input type="checkbox" id="reciever_info" name="reciever_info"/>&nbsp;加入常用收货地址</td>
                                </tr>
                            </table>
                            <div class="span_mgmt_right3_text4" style="float:none;">备注信息</div>      	          
                            <table width="90%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="120" height="40" class="td_mgmt_right3_td1b">备注：</td>
									<td>
                                    	<textarea name="remarks" class="textarea_rating" placeholder="请输入内容……" required>${orderInfo.remarks }</textarea>
                                    </td>
								</tr>
                                <tr>
                                    <td height="40" class="td_mgmt_right3_td1b">&nbsp;</td>
                                    <td><input type="submit" id="btn1" value="提交" class="btn_mgmt1" hidefocus="true" />
                                    <!-- <input type="reset" id="btn1" value="重填" class="btn_mgmt2" hidefocus="true" /> --></td>
                                </tr>
                            
                            </table>
                        </td>
                    </tr>
                </table>
             </td>
		</tr>
    </table>
</div>

<%-- <%@ include  file="popup1.jsp"%> --%>

<div id="popup1" style="display:none;">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="510"><div class="div_popup_title1">留言</div></td>
            <td>
                <div id="close" style="cursor:pointer;"><img src="images/btn_cancel1.png" title="关闭本窗口" /></div>
            </td>
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="540">
            	<textarea class="textarea_popup1" placeholder="请输入内容..." id="message"></textarea>
            </td>
        </tr>
        <tr>
            <td class="td_popup1">
                <input type="button" id="btn1" value="提交" class="btn_mgmt1" hidefocus="true" onclick="insertMessage()"/><input type="button" id="btn2" value="重填" class="btn_mgmt2" hidefocus="true" />
            </td>
        </tr>
    </table>
</div>

<div id="popup2" style="display:none;">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="610"><div class="div_popup_title1">常用发货地址</div></td>
            <td>
                <div id="close2" style="cursor:pointer; margin-right:10px;"><img src="images/btn_cancel1.png" title="关闭本窗口" /></div>
            </td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_popup_address1">
        <tr>
            <td width="100" class="td_popup_address1">姓名</td>
            <td width="120" class="td_popup_address1">电话</td>
            <td class="td_popup_address1">地址</td>
        </tr>
    </table>
	<div class="div_popup_address">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_popup_address2" >
            <tbody id="send_info">
            
            </tbody>
        </table>
    </div>
</div>

<div id="popup3" style="display:none;">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="610"><div class="div_popup_title1">常用收货地址</div></td>
            <td>
                <div id="close3" style="cursor:pointer; margin-right:10px;"><img src="images/btn_cancel1.png" title="关闭本窗口" /></div>
            </td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_popup_address1">
        <tr>
            <td width="100" class="td_popup_address1">姓名</td>
            <td width="120" class="td_popup_address1">电话</td>
            <td class="td_popup_address1">地址</td>
        </tr>
    </table>
	<div class="div_popup_address">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_popup_address2" >
            <tbody id="recieve_info">
            
            </tbody>
        </table>
    </div>
</div>

<div id="footer_frame">
	<iframe allowtransparency="true" width="100%" frameborder="0" hspace="0" marginheight="0" marginwidth="0" scrolling="no" vspace="0" src="footer.jsp"></iframe>
</div>

</body>
<script type="text/javascript">
	function OnLoad() {
		loadFocus();
		//获取用户合同编号
		getUserContract();
		//获取用户客户信息
		getUserClientName();
		//设置订单的原始数据,主要是select标签的内容
		setOrderValue();
		
		//获取常用发货地址
		getFrequentAddress(1);
		//获取常用收货地址
		getFrequentAddress(2);
	}
	//设置订单的原始数据
	function setOrderValue(){
		//设置关联客户运单
		if("${orderInfo.isLinkToClientWayBill}" == '有'){
			$("#p_detail").attr("style","display:inline");
		}
		$("#isLinkToClientWayBill").val("${orderInfo.isLinkToClientWayBill}");
		//是否有承运方合同
		if("${orderInfo.hasCarrierContract}" == '有'){
			$("#c_detail").attr("style","display:inline");
		}
			$("#hasCarrierContract").val("${orderInfo.hasCarrierContract}");
			//$("#clientName").val("${orderInfo.clientName}");//向后台请求到结果后才能设置
		
	}
	//获取用户合同编号
	function getUserContract(){
		var url="getUserContractIdAjax";
		$.post(url,{currentUserId:$('#currentUserId').val()},function(data,status){
			var CONTRACTID=$('#contractId');
			 for(var i=0;i<data.length;i++) {
		         option = $("<option>").text(data[i].id).val(data[i].id);
		         CONTRACTID.append(option);
		      }     
			 //如果有则需要显示当前的订单号
			 if("${orderInfo.hasCarrierContract}" == '有'){
				 $("#contractId").val("${orderInfo.contractId}");
			 }
		},"json");
	}
	//获取用户客户信息
	function getUserClientName(){
		var url="getUserBusinessClientAjax";
		$.post(url,{currentUserId:$('#currentUserId').val()},function(data,status){
			var client_name=$('#clientName');
			 for(var i=0;i<data.length;i++) {
		         var option = $("<option>").text(data[i].clientName).val(data[i].clientName);
		         client_name.append(option);
		      }
			 
			 $("#clientName").val("${orderInfo.clientName}");//向后台请求到结果后才能设置
		},"json");
	}
	
	//获取常用地址]
	function getFrequentAddress(kind) {
		var url = "getUserAddressAjax";
		$
				.ajax({
					url : url,
					cache : false,
					dataType : "json",
					data : {
						kind : kind
					},
					success : function(data, status) {
						var f;
						if (kind == 1) {
							f = $("#send_info");
						} else {
							f = $("#recieve_info");
						}
						f.empty();
						for (var i = 0; i < data.length; i++) {
							var str="<tr>";
							str+="<td width=\"100\" class=\"td_popup_address2a\">"+data[i].name+"</td>";
							str+="<td width=\"120\" class=\"td_popup_address2\">"+data[i].phone+"</td>";
							str+="<td class=\"td_popup_address2\">"+data[i].address+"</td>";
							
							//str+="<td class=\"td_popup_address2a\"><input type=\"radio\" name=\"address_choose\" id=\"address_choose\" /></td>"
							if(kind ==1){
								str+="<td class=\"td_popup_address2a\"><input type=\"button\" value=\"选择\" onclick=\"chooseSendAddress('"+data[i].name+"','"+data[i].phone+"','"+data[i].address+"')\" name=\"address_choose\" id=\"address_choose\" /></td>"
							}else{
								str+="<td class=\"td_popup_address2a\"><input type=\"button\" value=\"选择\" onclick=\"chooseRecieveAddress('"+data[i].name+"','"+data[i].phone+"','"+data[i].address+"')\" name=\"address_choose\" id=\"address_choose\" /></td>"
							}
							str+="</tr>";
							f.append(str);
						}
					}
				})
	}
	
	//选择发货人
	function chooseSendAddress(name,phone,address){
		//关闭窗口
		$("#close2").click();
		$("#deliveryAddr").val(address);
		$("#deliveryName").val(name);
		$("#deliveryPhone").val(phone);
		
	}
	
	//选择收货人
	function chooseRecieveAddress(name,phone,address){
		//关闭窗口
		$("#close3").click();
		$("#recieverAddr").val(address);
		$("#recieverName").val(name);
		$("#recieverPhone").val(phone);
		
	}
</script>
</html>