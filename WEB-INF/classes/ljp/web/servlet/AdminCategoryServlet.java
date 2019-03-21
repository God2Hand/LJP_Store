package ljp.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.domain.Category;
import ljp.service.CategoryService;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.utils.UUIDUtils;
import ljp.utils.ZZUtils;
import ljp.web.servlet.base.BaseServlet;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 后台分类管理模块
 * @author Shinelon
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 恢复分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String recover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.recover(cid);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，恢复分类失败！");
			return "/admin/welcome.jsp";
		}
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		return null;
	}
	
	/**
	 * 已删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = cs.findDel();
			
			//2.将返回值放入request域中
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，查找已删除分类失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/category/binList.jsp";
	}
	
	/**
	 * 修改分类名称
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//前端校验
			String regex = "^.{1,20}$";
			boolean c1 = ZZUtils.check(regex, request.getParameter("cname"));
			if (!c1) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/admin/category/edit.jsp";
			}
			
			Category c = new Category();
			BeanUtils.populate(c, request.getParameterMap());
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.update(c);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，修改分类名失败！");
			return "/admin/welcome.jsp";
		}
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		return null;
		//可恶啊，用了下面这个会有bug，弄懂花了接近一天
		//return "/adminCategory?method=findAll";
	}
	
	/**
	 * 跳转编辑分类名称页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("cid", request.getParameter("cid"));
		return "/admin/category/edit.jsp";
	}
	
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.delById(cid);
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.delByC(cid);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，删除分类失败！");
			return "/admin/welcome.jsp";
		}
		return "/adminCategory?method=findAll";
//		真删除
//		String cid = request.getParameter("cid");
//		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
//		try {
//			cs.delById(cid);
//		} catch (Exception e) {
//			//throw new RuntimeException(e);
//			try {
//				String cname = cs.getCnameById(cid);
//				request.setAttribute("delMsg", "<font color='red'>错误信息：</font>"
//						+ "<br />该分类还有商品喔~若要删除，请先清空所有商品！！！"
//						+ "<br />您也可以一个个修改商品分类，该分类下无商品时即可删除~");
//				return "/adminProduct?method=findByC&cid=" + cid;
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//		return "/adminCategory?method=findAll";
	}
	
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			//前端校验
			String regex = "^.{1,20}$";
			boolean c1 = ZZUtils.check(regex, request.getParameter("cname"));
			if (!c1) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/admin/category/add.jsp";
			}
			
			//1.封装category对象
			Category c = new Category();
			c.setCid(UUIDUtils.getId());
			c.setCname(request.getParameter("cname"));
			//2.调用service完成添加操作
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.save(c);
			
			//3.重定向
			response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		} catch (Exception e) {
			//e.printStackTrace();
			//throw new RuntimeException(e);
			response.getWriter().write("该分类已经存在！不能添加同名分类！"
					+ "<br />如果在【分类列表】找不到该分类，请去【已删除列表】找到并恢复。");
		}
		return null;
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}

	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service，获取所欲分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = cs.findList();
			
			//2.将返回值放入request域中
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			//后续做一个信息提示页面
			request.setAttribute("msg", "未知异常，展示所有分类失败！");
			return "/admin/welcome.jsp";
		}
		
		//3.请求转发
		return "/admin/category/list.jsp";
	}
}
