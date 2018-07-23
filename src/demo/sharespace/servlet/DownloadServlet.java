package demo.sharespace.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.FileInfo;

import demo.sharespace.bean.FileBean;
import demo.sharespace.util.DbUtils;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
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
				// 通过字段检索
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

		File file = new File(filePath);
		if (file.exists()) {
			// 以流的形式读入文件
			InputStream in = new FileInputStream(file);

			// 下载转码
			String userAgent = request.getHeader("user-agent").toLowerCase();
			if (userAgent.contains("msie") || userAgent.contains("edge") || userAgent.contains("trident")) {
				// win10 ie edge 浏览器 和其他系统的ie
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}

			// 使用浏览器进行下载
			response.addHeader("Content-type", "appllication/octet-stream");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

			// 定义流输出
			OutputStream out = response.getOutputStream();

			// 定义临时缓冲区
			byte[] buffer = new byte[1024];
			int len = -1;

			// 将流中的数据读出
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			// 关闭流
			out.close();
			in.close();
		} else {
			System.out.println("文件不存在：" + filePath);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
