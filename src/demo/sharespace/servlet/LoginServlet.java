package demo.sharespace.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import demo.sharespace.util.DbUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Connection conn = DbUtils.getConnection();
		boolean checkSuccess = false;
		String userid = "";
		try {
			// 执行 SQL 查询
			Statement stmt = conn.createStatement();
			String sql = "select id_User,name_User from User_Tab where name_User = '" + username + "' and PassCode = '" + password + "'";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				userid = rs.getString("id_User");
				String name = rs.getString("name_User");
				checkSuccess = username.equals(name);
				break;
			}

			// 完成后关闭
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (checkSuccess) {
			// request.setAttribute("login_flag", "1");
			// request.setAttribute("username", username);
			// request.getRequestDispatcher("/home.jsp").forward(request,
			// response);

			session.setAttribute("login_flag", "1");
			session.setAttribute("username", username);
			session.setAttribute("userid", userid);
			response.sendRedirect("/ShareSpace/home.jsp");
		} else {
			session.setAttribute("login_flag", "-1");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
