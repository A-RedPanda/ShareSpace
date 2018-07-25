package demo.sharespace.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sharespace.util.DbUtils;
import demo.sharespace.util.RequestUtils;

/**
 * Servlet implementation class CreateGroups
 */
@WebServlet("/CreateGroupsServlet")
public class CreateGroupsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGroupsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String GroupName = request.getParameter("GroupName");
		String Description = request.getParameter("Description");
		String userId = RequestUtils.getUserId(request);
		
		String groupId = String.format("%010d", Math.abs(UUID.randomUUID().toString().hashCode()));

		Connection conn = DbUtils.getConnection();
		Statement stmt = null;
		try {
		stmt = conn.createStatement();
		String sql = "insert into Groups_Tab(id_Group,name_Group,createrId,describe) values('"+groupId+"','"+GroupName+"','"
		+userId+"','"+Description+"')";
		stmt.execute(sql);
		
		sql = "insert into User_Groups(id_Group,id_User) values('"+groupId+"','"+userId+"')";
		stmt.execute(sql);
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/space/GroupSpace.jsp").forward(request, response);
	}
	
}
