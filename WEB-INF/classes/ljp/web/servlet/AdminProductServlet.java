package ljp.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.domain.Category;
import ljp.domain.Product;
import ljp.service.CategoryService;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.web.servlet.base.BaseServlet;

public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 已删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service查询已上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findDel();
			
			//2.将返回值放入request域中，请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，查询列表失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/category/csList.jsp";
	}
	
	/**
	 * 清空分类商品并且删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.delByC(cid);
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.delById(cid);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，清空分类失败！");
			return "/admin/welcome.jsp";
		}
		return "adminCategory?method=findAll";
	}
	
	/**
	 * （后台）分类查询商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			int del = Integer.parseInt(request.getParameter("del"));
			Category c = new Category();
			c.setCid(cid);
			c.setCname(((CategoryService)BeanFactory.getBean("CategoryService")).getCnameById(cid));
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findByC(cid, del);
			request.setAttribute("list", list);
			request.setAttribute("c", c);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，分类查询商品失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/category/csList.jsp";
	}
	
	/**
	 * 转到编辑商品名称页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product p = ps.getById(pid);
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			String cname = cs.getCnameById(p.getCategory().getCid());
			p.getCategory().setCname(cname);
			request.setAttribute("cList", cs.findList());
			request.setAttribute("p", p);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，编辑商品失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/product/edit.jsp";
	}
	
	/**
	 * 删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.delById(pid);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "未知异常，删除失败！");
			return "/admin/welcome.jsp";
		}
		return "/adminProduct?method=findAll";
//		
//		真删除
//		try {
//			String pid = request.getParameter("pid");
//			String pimage = request.getParameter("pimage");
//			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
//			ps.delById(pid);
//			//删除图片
//			String pPath = getServletContext().getRealPath("/");
//			File f = new File(pPath,pimage);
//			if (f.exists() && f.isFile()) {
//				System.out.println(f.getAbsolutePath());
//				System.out.println(f.delete());
//					
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		return "/adminProduct?method=findAll";
	}
	
	/**
	 * 跳转到添加的页面上
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//嗲用category查询所有分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			request.setAttribute("list", cs.findList());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "未知异常，添加商品失败！");
			return "/admin/welcome.jsp";
		}
		
		return "/admin/product/add.jsp";
	}

	/**
	 * 展示已上架商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service查询已上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAll();
			
			//2.将返回值放入request域中，请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			// new RuntimeException(e);
			request.setAttribute("msg", "未知异常，展示列表失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/product/list.jsp";
	}
	
	/**
	 * 下架商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findPflag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service查询已上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findPflag();
			
			//2.将返回值放入request域中，请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，打开列表失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/product/list.jsp";
	}
}
