package demo.sharespace.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sharespace.bean.FileBean;
import demo.sharespace.util.DbUtils;
import demo.sharespace.util.RequestUtils;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//获取要查找的内容 为一个文件id的子串或者文件名的子串
		String content = request.getParameter("content");
		
		//获取用户id
		String userId = RequestUtils.getUserId(request);
		
		String fileId = "";
		
		List<FileBean> fileList = new ArrayList<FileBean>();
		
		//在用户id的文件夹下  先按文件id查询，再按文件名查询 ，返回文件id和文件和文件路径
		Connection conn = DbUtils.getConnection();
		
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();
			//先在用户id条件下按id_File查找文件
			String sql = "select id_File from User_File where id_User = '" + userId + "'"+"and id_File like '%"+content+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			
			//使用另一个对象检查 因为.next会使指针下移
			ResultSet rs2 = rs;
			
			//如果按id_File找不到文件（结果集为空） 则按文件名查找 
			if(!rs2.next())sql="select id_File from File_Tab where name_File like '%"+content+"%' and filepath like '%"+userId+"%'";
			rs = stmt.executeQuery(sql);

			
			// 展开结果集数据库
			while (rs.next()) {
				//获取符合条件的id_File，并找到相应的id_File,name_File,filepath
				fileId = rs.getString("id_File").trim();
				String sql2 = "select id_File,name_File,filepath from File_Tab where id_File='"+fileId+"'";
				Statement stmt2 = conn.createStatement();
				ResultSet rs3= stmt2.executeQuery(sql2);
				while(rs3.next()) {
					//将结果装到fileList中
					FileBean file = new FileBean();
					file.setFileId(rs3.getString("id_File").trim());
					file.setFileName(rs3.getString("name_File").trim());
					file.setFilePath(rs3.getString("filepath").trim());
					fileList.add(file);
				}
				stmt2.close();
				rs3.close();
			}
			// 完成后关闭
			rs.close();
			rs2.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//传递和跳转
		request.setAttribute("filelist",fileList);
		request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
	}

}
