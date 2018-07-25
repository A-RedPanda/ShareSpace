<%@page import="java.util.ArrayList"%>
<%@page import="demo.sharespace.dao.GetGroupDao"%>
<%@page import="demo.sharespace.util.RequestUtils"%>
<%@page import="demo.sharespace.bean.GroupBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group Space</title>
</head>
<body>

<center>
<!-- 如果有群组则显示群组名和群组文件列表和用户列表 -->
<!-- 如果没有则显示"暂无群组"，创建 -->
<%
String userId = RequestUtils.getUserId(request);
GetGroupDao groupDao = new GetGroupDao();
List<GroupBean> groupList= new ArrayList<GroupBean>();
groupList = groupDao.getGroupList(userId);
%>
<br/>
<br/>
<br/>
<table border="1">
<tr>我的群组</tr>
<tr><th>组ID</th><th>组名</th><th>组创建者</th><th>组描述</th><th>进入群组</th><th>删除群组</th></tr>
<%
for(GroupBean group : groupList){
%>
<tr>
<td><%=group.getGroupId() %></td>
<td><%=group.getGroupName() %></td>
<td><%=group.getCreaterId() %></td>
<td><%=group.getDescription() %></td>

<td><a href="/ShareSpace/groupList.jsp?groupId=<%=group.getGroupId()%>
&groupName=<%=group.getGroupName()%>
&createrId=<%=group.getCreaterId()%>
&describe=<%=group.getDescription()%>
">进入群组</a></td>
<td><form action="/ShareSpace/GroupDeleteServlet" method="post">
<input type="hidden" name="groupId" value=<%=group.getGroupId() %>/>
<input type="submit" value="删除群组"/>
</form></td>
</tr>
<%
}
%>
</table>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<center>
<form action="/ShareSpace/CreateGroupsServlet" method="post">
  Group name: <input type="text" name="GroupName" />
  <br/><br/>
  Description: <input type="text" name="Description" />
  <br/><br/>
  <input type="submit" value="创建群组" />
  <input type="reset" value="重置">
</form>
</center>
<br/>
<br/>
<br/>

</body>
</html>