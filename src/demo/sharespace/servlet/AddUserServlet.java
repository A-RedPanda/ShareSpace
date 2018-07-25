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

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String groupId = (String) request.getParameter("groupId").trim();
		
		String userId = (String) request.getParameter("userId").trim();
		
		Connection conn = DbUtils.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into User_Groups values('"+groupId+"','"+userId+"')";
			stmt.execute(sql);
			System.out.println("Successful!");
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/groupList.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userId = (String) request.getParameter("userId").trim();
		Connection conn = DbUtils.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete User_Groups where id_User ='"+userId+"'";
			stmt.execute(sql);
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/groupList.jsp").forward(request, response);
	}
}
