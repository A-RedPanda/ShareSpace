<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="demo.sharespace.bean.FileBean" %>
<%@ page import="demo.sharespace.dao.MySpaceDao" %>  
<%@ page import="demo.sharespace.util.RequestUtils" %>    
<%@ page import="java.util.List" %>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SearchResult</title>
<style type="text/css">
body {
	background-color: grey;
}

#nav {
	background-color: beige;
	padding: 15px;
}

#nav a {
	background: gainsboro;
	margin: 10px;
	padding: 10px;
	text-decoration: none;
}

td {
	padding: 10px;
}
</style>
</head>
<body>
<center>
<%
if("1".equals(session.getAttribute("login_flag"))){
%>
<div id="nav">
<a id="ss" href="/ShareSpace/space/ShareSpace.jsp">Share Space</a>
<a id="ms" href="/ShareSpace/space/MySpace.jsp">My Space</a>
<a id="gs" href="/ShareSpace/space/GroupSpace.jsp">Group Space</a>
<a id="upload" href="/ShareSpace/upload.jsp">上传</a>
欢迎,<%=session.getAttribute("username") %>
<a id="logout" href="/ShareSpace/LogoutServlet">登出</a>

<!-- 新增查询 -->
<form method="get"   action="SearchServlet" >
<input type="text" name="content" value="输入文件id或文件名"/>
<input type="submit" value="查询" />
</form>

</div>
<%
List<FileBean> fileList = (List<FileBean>)request.getAttribute("filelist");
if(fileList.size()==0)
{
%>
<script type="text/javascript">
alert("查询结果为空");
window.location.href='home.jsp';
</script>
<%	
}else{
%>
<br/>
<br/>
<br/>
<table border="1" style="border-color: blue;background-color: bisque;">
<tr><th>文件ID</th><th>文件名</th><th>文件路径</th><th>操作</th></tr>
<%
for(FileBean file : fileList){
	
%>
<tr>
<td><%=file.getFileId() %></td>
<td><%=file.getFileName() %></td>
<td><%=file.getFilePath() %></td>
<% out.println("<td><a href=\"/ShareSpace/DownloadServlet?fileId=" + file.getFileId() + "\">下载</a></td>"); %>
<% out.println("<td><a href=\"/ShareSpace/FileDelete?fileId=" + file.getFileId() + "\">删除</a></td>"); %>
</tr>
<%
}
%>
</table>
<%
}}
else{
	response.sendRedirect("/ShareSpace/home.jsp");
}
%>
</center>
</body>
</html>