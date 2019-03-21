package ljp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.domain.Cart;
import ljp.domain.CartItem;
import ljp.domain.Product;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.utils.ZZUtils;
import ljp.web.servlet.base.BaseServlet;

/**
 * 购物车模块
 * @author Shinelon
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.后去购物车，执行清空
		getCart(request).clearCart();
		
		//2.重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * 移出购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取商品的pid
		String pid = request.getParameter("pid");
		
		//2.获取购物车，执行移除
		getCart(request).removeFromCart(pid);
		
		//3.重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}

	/**
	 * 加入购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add2cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pid和count
			String pid = request.getParameter("pid");
			String countStr = request.getParameter("count");
			
			String regex = "^\\+?[1-9][0-9]*$";
			boolean c1 = ZZUtils.check(regex, countStr);
			if (!c1) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/jsp/msg.jsp";
			}
			
			int count = Integer.parseInt(countStr);
			
			//2.封装cartitem
			//调用service获取product
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			//创建CartItem
			CartItem cartItem = new CartItem(product, count);
			
			//3.将cartitem加入购物车
			//从session中获取购物车
			Cart cart = getCart(request);
			cart.add2cart(cartItem);
			
			//4.重定向
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "加入购物车失败！");
			return "/jsp/msg.jsp";
		}
		return null;
	}

	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	private Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			//将cart放入当前的session中
			request.getSession().setAttribute("cart", cart);
			//以后就不用放入session中了，因为是引用，存放的是地址值
		}
		return cart;
	}

}
