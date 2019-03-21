package ljp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.domain.PageBean;
import ljp.domain.Product;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.web.servlet.base.BaseServlet;

/**
 * 商品模块
 * @author Shinelon
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 根据商品名称查找，分页展示
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pageNumber cid 设置pagesize
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (NumberFormatException e) {
			}
			int pageSize = 12;
			String searchName = request.getParameter("searchName");
			
			//2.调用service分页查询商品，参数：3个 ， 返回值：pageBean
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> pb = ps.searchByPage(pageNumber, pageSize, searchName);
			
			//3.将pageBean放入request域中，请求转发product_list.jsp
			request.setAttribute("pb", pb);
			request.setAttribute("cname", request.getParameter("cname"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查找商品失败！");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_list.jsp";
	}
	
	/**
	 * 分类商品分页展示
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pageNumber cid 设置pagesize
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (NumberFormatException e) {
			}
			int pageSize = 12;
			String cid = request.getParameter("cid");
			
			//2.调用service分页查询商品，参数：3个 ， 返回值：pageBean
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> pb = ps.findByPage(pageNumber, pageSize, cid);
			
			//3.将pageBean放入request域中，请求转发product_list.jsp
			request.setAttribute("pb", pb);
			request.setAttribute("cname", request.getParameter("cname"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "分页查询失败！");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_list.jsp";
	}

	/**
	 * 商品详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pid
			String pid = request.getParameter("pid");
			
			//2.调用service获取单个商品，参数：pid，返回值：product
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			
			//3.将product放入request域中，请求转发到/jsp/product_info
			request.setAttribute("pBean", product);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询单个商品失败！");
			return "/jsp/msg.jsp";
		}
		
		return "/jsp/product_info.jsp";
	}
}
