package demo.sharespace.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import demo.sharespace.util.DbUtils;

/**
 * Servlet implementation class FileDelete
 */

@WebServlet("/FileDelete")
public class FileDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 获取要删除文件ID
		String fileId = (String) request.getParameter("fileId");
		String fileName = "";
		String filePath = "";
		Connection conn = DbUtils.getConnection();
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();
			String sql = "select name_File, filepath from File_Tab where id_File = '" + fileId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索,获得文件名和地址
				fileName = rs.getString("name_File").trim();
				filePath = rs.getString("filepath").trim();
				break;
			}
			// 完成后关闭
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 设置编码格式
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		//文件所在路径
        File file = new File(filePath);
       
        //删除确认
        int res=JOptionPane.showConfirmDialog(null, "是否继续删除文件", "是否继续删除", JOptionPane.YES_NO_OPTION);
                       
        if(res==JOptionPane.YES_OPTION)  //点击“是”后 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        {                  
        	if (file.exists() && file.isFile()) 
            {
        		try {
        			// 执行 SQL 查询
        			Statement stmt2 = conn.createStatement();
        			String sql2 = "delete from File_Tab where id_File = '" + fileId + "'";
        			String sql3 = "delete from User_File where id_File = '" + fileId + "'";
        			
        			stmt2.addBatch(sql2);
        			stmt2.addBatch(sql3);
        			
        			stmt2.executeBatch();
        			stmt2.clearBatch();
        		
        			// 完成后关闭
        			//rs2.close();
        			stmt2.close();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        		
                if (file.delete()) 
                {
                    System.out.println("删除单个文件" + fileName + "成功！");
                } 
                else 
                {
                    System.out.println("删除单个文件" + fileName + "失败！");  
                }
            } 
            else 
            {
                System.out.println("删除单个文件失败：" + fileName + "不存在或不是文件！");
            }    
        }

        
		 //跳转到download.jsp
		 request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

}
