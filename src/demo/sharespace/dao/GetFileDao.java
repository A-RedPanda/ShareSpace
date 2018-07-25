package demo.sharespace.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo.sharespace.bean.FileBean;
import demo.sharespace.bean.UserBean;
import demo.sharespace.util.DbUtils;

public class GetFileDao {
	public List<FileBean> getUserList(List<UserBean> user) {
		List<FileBean> fileBeans = new ArrayList<FileBean>();
		
		List<String> file = new ArrayList<String>();
		Connection conn = DbUtils.getConnection();
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();
			for (int i = 0; i < user.size(); i++) {
				String sql = "select id_File from User_File where id_User = '" + user.get(i).getUserId() + "'";
				ResultSet rs = stmt.executeQuery(sql);

				// 展开结果集数据库
				while (rs.next()) {
					// 通过字段检索
					if (!file.contains(rs.getString("id_File").trim()))
						file.add(rs.getString("id_File").trim());
					;
				}
				// 完成后关闭
				rs.close();
			}
			
			for(int i=0;i<file.size();i++) 
			{
				FileBean filebean = new FileBean();
				String sql = "select id_File,name_File,filepath from File_Tab where id_File='"+file.get(i)+"'";
				ResultSet rs2 = stmt.executeQuery(sql);
				while(rs2.next()) {
					filebean.setFileId(rs2.getString("id_File").trim());
					filebean.setFileName(rs2.getString("name_File").trim());
					filebean.setFilePath(rs2.getString("filepath").trim());
					fileBeans.add(filebean);
				}
				rs2.close();
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileBeans;
}}
