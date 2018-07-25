package demo.sharespace.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo.sharespace.bean.GroupBean;
import demo.sharespace.bean.UserBean;
import demo.sharespace.util.DbUtils;

//按组id查找组内用户
public class GetUserDao {
	public List<UserBean> getUserList(String groupId) {
		List<UserBean> userBeans = new ArrayList<UserBean>();
		List<String> user = new ArrayList<String>();
		Connection conn = DbUtils.getConnection();
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();
			
				String sql = "select id_User from User_Groups where id_Group = '" + groupId + "'";
				ResultSet rs = stmt.executeQuery(sql);

				// 展开结果集数据库
				while (rs.next()) {
					// 通过字段检索
					if (!user.contains(rs.getString("id_User").trim()))
						user.add(rs.getString("id_User").trim());
					;
				}
				// 完成后关闭
				rs.close();
			
			
			for(int i=0;i<user.size();i++) 
			{
				UserBean userbean = new UserBean();
				sql = "select id_User,name_User from User_Tab where id_User='"+user.get(i)+"'";
				ResultSet rs2 = stmt.executeQuery(sql);
				while(rs2.next()) {
					userbean.setUserId(rs2.getString("id_User").trim());
					userbean.setUserName(rs2.getString("name_User").trim());
					userBeans.add(userbean);
				}
				rs2.close();
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userBeans;
}
	}
