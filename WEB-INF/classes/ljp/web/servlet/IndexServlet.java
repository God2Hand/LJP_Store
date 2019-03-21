package ljp.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.domain.Product;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.web.servlet.base.BaseServlet;

/**
 * 首页模块
 * @author Shinelon
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用productService查询最新商品和热门商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> hotList = ps.findHot();
			List<Product> newList = ps.findNew();
			
			//2.将两个list都放入request域中
			request.setAttribute("hList", hotList);
			request.setAttribute("nList", newList);
		} catch (Exception e) {
		}
		
		return "/jsp/index.jsp";
	}
}
