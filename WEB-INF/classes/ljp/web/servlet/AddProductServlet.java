package ljp.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.constant.Constant;
import ljp.domain.Category;
import ljp.domain.Product;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.utils.UUIDUtils;
import ljp.utils.UploadUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 * 保存商品
 * @author Shinelon
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//因为form表单是muti-type，所以无法从request.getParameter()方法获取参数，故只能用doGet不继承baseservlet
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//0.使用fileupload保存图片和将商品的信息放入map中
			//0.1创建map集合用来存放商品的信息
			Map<String, Object> map = new HashMap<String, Object>();
			
			//0.2创建磁盘文件项工厂（设置临时文件大小和位置）
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//0.3创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//0.4解析request
			List<FileItem> list = upload.parseRequest(req);
			
			//0.5遍历list，获取每一个文件项
			for (FileItem fi : list) {
				//0.6获取name属性值
				String key = fi.getFieldName();
				//0.7判断是否是普通的上传组件
				if (fi.isFormField()) {
					//普通
					map.put(key, fi.getString("utf-8"));
				} else {
					//文件
					//a.获取文件的名称
					String name = fi.getName();
					//b.获取文件的真实名称
					String realName = UploadUtils.getRealName(name);
					//c.获取文件的uuid名称
					String uuidName = UploadUtils.getUUIDName(realName);
					//d.获取随机的目录
					String dir = UploadUtils.getDir();
					//e.获取文件的内容（输入流）
					InputStream is = fi.getInputStream();
					//f.创建输出流
					//获取products目录的真实路径
					String pPath = getServletContext().getRealPath("/products");
					//创建随机目录
					File dirFile = new File(pPath, dir);
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}
					FileOutputStream os = new FileOutputStream(new File(dirFile, uuidName));
					//g.对拷流
					IOUtils.copy(is, os);
					//h.释放资源
					os.close();
					is.close();
					//i.删除临时文件
					fi.delete();
					//j.将商品的路径放入map中
					map.put(key, "products" + dir + "/" + uuidName);
				}
			}
			
			//1.封装product对象
			Product p = new Product();
			map.put("pid", UUIDUtils.getId());
			map.put("pdate", new Date());
			map.put("pflag", Constant.PRODUCT_IS_UP);
			BeanUtils.populate(p, map);
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			
			//2.调用service完成保存操作
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.save(p);
			
			//3.重定向
			resp.sendRedirect(req.getContextPath() + "/adminProduct?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			req.setAttribute("msg", "未知异常，保存失败！");
			req.getRequestDispatcher("/admin/welcome.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
