package demo.sharespace.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo.sharespace.bean.FileBean;
import demo.sharespace.util.DbUtils;

public class MySpaceDao {

	public List<FileBean> getMyFileList( String userId) {
		List<FileBean> fileBeans = new ArrayList<FileBean>();
		Connection conn = DbUtils.getConnection();
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();
			String sql = "select t1.id_File, t1.name_File, t1.filepath from File_Tab t1 inner join User_File t2 on t1.id_File = t2.id_File where t2.id_User = '" + userId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			FileBean fileBean = null;
			// 展开结果集数据库
			while (rs.next()) {
				 fileBean = new FileBean();
				 // 通过字段检索
				 fileBean.setFileId(rs.getString("id_File").trim());
				 fileBean.setFileName(rs.getString("name_File").trim());
				 fileBean.setFilePath(rs.getString("filepath").trim());
				 fileBeans.add(fileBean);
			}

			// 完成后关闭
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileBeans;
	}
	
}
