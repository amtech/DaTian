<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车队信息</title>
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
	<a href="myinfo" hidefocus="true" class="a_text_main_title1">我的信息</a>&nbsp;&gt;&nbsp;我的资源
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="230" class="td_leftnav_top">
                <div id="main_frame_left">
                    <%@ include  file="mysource_leftnav_mytrade.jsp"%>
                    <hr class="hr_2" />
                    <span class="text_mgmt_leftnav1"><span
							id="mgmt_nav_switch2a" class="span_mgmt_nav1" title="收起"
							onclick="mgmt_nav_switch2a();"></span><span
							id="mgmt_nav_switch2b" class="span_mgmt_nav2" title="展开"
							onclick="mgmt_nav_switch2b();"></span>我的资源</span>
						<div id="mgmt_nav2">
                       <% if((Integer)session.getAttribute("userKind") ==3) {%><!-- 企业用户 -->
                        <a href="linetransport?flag=1&Display=10&PageNow=1" class="a_mgmt_leftnav" hidefocus="true">干线运输线路信息</a>
                        <a href="cityline?flag=1" class="a_mgmt_leftnav" hidefocus="true">城市配送网络信息</a>
                        <a href="car?flag=1" class="a_mgmt_leftnav1" hidefocus="true">车辆信息</a>
                        <a href="warehouse?flag=1" class="a_mgmt_leftnav" hidefocus="true">仓库信息</a>
						<a href="driver?flag=1" class="a_mgmt_leftnav" hidefocus="true">司机信息</a>
						<%} %>
						 <% if((Integer)session.getAttribute("userKind") ==2) {%><!-- 个人用户 -->
                        <a href="client" class="a_mgmt_leftnav" hidefocus="true">客户信息</a>
                        <a href="goodsform?flag=1" class="a_mgmt_leftnav" hidefocus="true">货物信息</a>
                         <%} %>
                        <% if((Integer)session.getAttribute("userKind") ==3) {%><!-- 企业用户 -->
                        <a href="contract2" class="a_mgmt_leftnav" hidefocus="true">合同信息</a>
                        <%} %>
                        <% if((Integer)session.getAttribute("userKind") ==2) {%><!-- 个人用户 -->
                        <a href="contract" class="a_mgmt_leftnav1" hidefocus="true">合同信息</a>
                        <%} %>
                    </div>
                    <%@ include  file="mysource_leftnav_myplan.jsp"%>
                    <%@ include  file="mysource_leftnav_myanalysis.jsp"%>
                    <%@ include  file="mysource_leftnav_myaccount.jsp"%>
</div>
			</td>
			<td class="td_leftnav_top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_mgmt_right2a">
                    <tr>
                        <td>
                            <span class="span_mgmt_right2_text1">更新车队信息</span> <span class="span_mgmt_right2_text2"><a href="javascript:history.go(-1);" hidefocus="true"><img src="images/btn_back1.png" class="span_mgmt_right2_pic1" title="返回" /></a></span>
                        </td>
			        </tr>
			    </table>
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_mgmt_right3">
			        <tr>
			            <td class="td_mgmt_right3_td1a"><br />
			            <form action="updatecarteam?id=${carteaminfo.id }" method="post">
			                <table width="90%" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                        <td width="120" height="40" class="td_mgmt_right3_td1b">车队名称：</td>
			                        <td><input type="text" class="input_mgmt1" style="width:300px;" value="${carteaminfo.teamName }" name="teamName" required/></td>
		                        </tr>
		                        <tr>
			                        <td width="120" height="40" class="td_mgmt_right3_td1b">数量(辆)：</td>
			                        <td><input type="text" class="input_mgmt1" style="width:300px;" value="${carteaminfo.carCount }" name="carCount" required/></td>
		                        </tr>
			                    <tr>
			                        <td height="40" class="td_mgmt_right3_td1b">车队负责人：</td>
			                        <td><input type="text" class="input_mgmt1" style="width:300px;" value="${carteaminfo.chief }" name="chief" required/></td>
		                        </tr>
			                    <tr>
			                        <td height="40" class="td_mgmt_right3_td1b">联系电话：</td>
			                        <td><input type="text" class="input_mgmt1" style="width:300px;" value="${carteaminfo.phone }" name="phone" required/></td>
		                        </tr>
			                    <tr>
			                        <td height="40" class="td_mgmt_right3_td1b">说明：</td>
			                        <td><textarea class="textarea_rating" placeholder="请输入内容..." name=explaination required>${carteaminfo.explaination }</textarea></td>
		                        </tr>
			                    <tr>
			                        <td height="40" class="td_mgmt_right3_td1b">&nbsp;</td>
			                        <td><input type="submit" id="btn1" value="提交" class="btn_mgmt1" hidefocus="true" />
			                            <!-- <input type="reset" id="btn1" value="重填" class="btn_mgmt2" hidefocus="true" /> -->
			                        </td>
		                        </tr>
		                    </table>
		                    </form>
                        </td>
		            </tr>
		        </table></td>
        </tr>
    </table>
</div>

<%@ include  file="popup1.jsp"%>

<div id="footer_frame">
	<iframe allowtransparency="true" width="100%" frameborder="0" hspace="0" marginheight="0" marginwidth="0" scrolling="no" vspace="0" src="footer.jsp"></iframe>
</div>

</body>
<script type="text/javascript">
	function OnLoad() {
		loadFocus();
		
	}
	
</script>
</html>