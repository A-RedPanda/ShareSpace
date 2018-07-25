package demo.sharespace.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo.sharespace.bean.GroupBean;
import demo.sharespace.bean.UserBean;
import demo.sharespace.util.DbUtils;

public class GetGroupDao {
	public List<GroupBean> getGroupList(String userId) {
		List<GroupBean> groupBeans = new ArrayList<GroupBean>();

		List<String> group = new ArrayList<String>();
		Connection conn = DbUtils.getConnection();
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();

			String sql = "select id_Group from User_Groups where id_User = '" + userId + "'";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				if (!group.contains(rs.getString("id_Group").trim()))
					group.add(rs.getString("id_Group").trim());
				;
			}
			// 完成后关闭
			rs.close();

			for (int i = 0; i < group.size(); i++) {
				GroupBean groupbean = new GroupBean();
				sql = "select id_Group,name_Group,createrId,describe from Groups_Tab where id_Group='" + group.get(i)
						+ "'";
				ResultSet rs2 = stmt.executeQuery(sql);
				while (rs2.next()) {
					groupbean.setGroupId(rs2.getString("id_Group"));
					groupbean.setGroupName(rs2.getString("name_Group"));
					groupbean.setCreaterId(rs2.getString("createrId"));
					groupbean.setDescription(rs2.getString("describe"));
					groupBeans.add(groupbean);
				}
				rs2.close();
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return groupBeans;
	}
}
