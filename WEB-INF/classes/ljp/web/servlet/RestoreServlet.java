package ljp.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.constant.Constant;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class RestoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*这里的超管过滤放过滤器了*/
		
		response.setContentType("text/html;charset=utf-8");

		//1.创建DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//2.创建ServletFileUpload
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置上传文件中文名称乱码
		upload.setHeaderEncoding("utf-8");
		//3.得到所有的FileItem
		try {
			List<FileItem> items = upload.parseRequest(request);
			//遍历items，得到所有的上传信息
			for (FileItem item : items) {
				if (item.isFormField()) {
				} else {
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(Constant.restore_dir + Constant.restore_databaseName);
					OutputStream outputStream = process.getOutputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(item.getInputStream(), "utf-8"));
					String str = null;
					StringBuffer sb = new StringBuffer();
					while ((str = br.readLine()) != null) {
						sb.append(str + "\r\n");
					}
					str = sb.toString();
					// System.out.println(str);
					OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
					writer.write(str);
					writer.flush();
					outputStream.close();
					br.close();
					writer.close();
					item.delete();
				}
			}
			response.getWriter().write("<font color='red' >还原成功！</font>");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("还原失败！");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
