package demo.sharespace.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sharespace.util.DbUtils;

/**
 * Servlet implementation class GroupDeleteServlet
 */
@WebServlet("/GroupDeleteServlet")
public class GroupDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String groupId = (String) request.getParameter("groupId");
		Connection conn = DbUtils.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete Groups_Tab where id_Group='"+groupId+"'";
			stmt.execute(sql);
			sql = "delete User_Groups where id_Group ='"+groupId+"'";
			stmt.execute(sql);
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/space/GroupSpace.jsp").forward(request, response);
	}

}
