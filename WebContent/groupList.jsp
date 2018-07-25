<%@page import="java.util.ArrayList"%>
<%@page import="demo.sharespace.dao.GetUserDao"%>
<%@page import="demo.sharespace.dao.GetFileDao"%>
<%@page import="demo.sharespace.bean.GroupBean"%>
<%@page import="java.util.List"%>
<%@page import="demo.sharespace.bean.FileBean"%>
<%@page import="demo.sharespace.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<%
GetFileDao fileDao = new GetFileDao();
GetUserDao userDao = new GetUserDao();
List<FileBean> filelist= new ArrayList<FileBean>();
List<UserBean> userlist= new ArrayList<UserBean>();
String groupId = request.getParameter("groupId").trim();

//获取组内所有所有用户
userlist = userDao.getUserList(groupId);

// 获取所有用户所拥有的文件，即群组共享的文件
filelist = fileDao.getUserList(userlist);


%>
<br/>
<br/>
<br/>
<table border="1">
<tr>组信息</tr>
<tr><th>组ID</th><th>组名</th><th>组创建者</th><th>组描述</th></tr>
<tr>
<td><%=request.getParameter("groupId").trim() %></td>
<td><%=request.getParameter("groupName").trim() %></td>
<td><%=request.getParameter("createrId").trim() %></td>
<td><%=request.getParameter("describe").trim() %></td>
</tr>
</table>
<br/>
<br/>
<br/>

<table border="1">
<tr>组内用户和成员</tr>
<tr><th>用户ID</th><th>用户名</th><th>删除</th></tr>
<%
for(UserBean user : userlist){
%>
<tr>
<td><%=user.getUserId().trim()%></td>
<td><%=user.getUserName().trim() %></td>
<td><form action="AddUserServlet" method="post">
<input type="hidden" name="userId" value="<%=user.getUserId().trim()%>"/>
<input type="hidden" name="groupId" value="<%=request.getParameter("groupId").trim()%>"/>
<input type="hidden" name="groupName" value="<%=request.getParameter("groupName").trim()%>"/>
<input type="hidden" name="createrId" value="<%=request.getParameter("createrId").trim()%>"/>
<input type="hidden" name="describe" value="<%=request.getParameter("describe").trim()%>"/>
<input type="submit" value="删除用户"/>
</form></td>
</tr>
<%
}
%>
</table>

<br/>
<br/>
<br/>
<table border="1">
<tr>组内共享文件</tr>
<tr><th>文件ID</th><th>文件名</th><th>文件路径</th><th>下载</th><th>删除</th></tr>
<%
for(FileBean file : filelist){
%>
<tr>
<td><%=file.getFileId().trim() %></td>
<td><%=file.getFileName().trim()%></td>
<td><%=file.getFilePath().trim()%></td>
<% out.println("<td><a href=\"/ShareSpace/DownloadServlet?fileId=" + file.getFileId() + "\">下载</a></td>"); %>
<% out.println("<td><a href=\"/ShareSpace/FileDelete?fileId=" + file.getFileId() + "\">删除</a></td>"); %>
</tr>
<%
}
%>
</table>
</center>
<br/>
<br/>
<br/>
<br/>
<center>
<form action="AddUserServlet" method="get">
<input type="hidden" name="groupId" value="<%=request.getParameter("groupId").trim()%>"/>
<input type="hidden" name="groupName" value="<%=request.getParameter("groupName").trim()%>"/>
<input type="hidden" name="createrId" value="<%=request.getParameter("createrId").trim()%>"/>
<input type="hidden" name="describe" value="<%=request.getParameter("describe").trim()%>"/>
  <br/>
  User Id: <input type="text" name="userId" />
  <br/><br/>
  <input type="submit" value="加入" />
  <input type="reset" value="重置">
</form>
</center>
</body>
</html>