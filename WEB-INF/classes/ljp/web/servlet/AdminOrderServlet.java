package ljp.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import ljp.constant.Constant;
import ljp.domain.Order;
import ljp.domain.OrderItem;
import ljp.service.OrderService;
import ljp.utils.BeanFactory;
import ljp.utils.JsonUtil;
import ljp.web.servlet.base.BaseServlet;

/**
 * 后台订单模块
 * @author Shinelon
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 修改订单状态：已付款 -> 已发货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service，获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			//后面有空再弄个错误页面吧
			if (order == null) {
				response.getWriter().write("error...");
			}
			//3.设置订单状态
			order.setState(Constant.order_sended);
			os.update(order);
			
			//4.重定向
			response.sendRedirect(request.getContextPath() + "/adminOrder?method=findAllByState&state=" + Constant.order_payed);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，发货失败！");
			return "/admin/welcome.jsp";
		}
		return null;
	}
	
	/**
	 * （后台）展示订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.设置编码
			response.setContentType("text/html;charset=utf-8");
			
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service，获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			//3.获取订单的订单项列表，转成json，写会浏览器
			if (order != null) {
				List<OrderItem> list = order.getItems();
				if (list != null && list.size() > 0) {
					//response.getWriter().println(JsonUtil.list2json(list));
					JsonConfig config = JsonUtil.configJson(new String[]{"order", "pdate","pdesc","itemid"});
					response.getWriter().println(JSONArray.fromObject(list, config));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，查看订单详情失败！");
			return "/admin/welcome.jsp";
		}
		return null;
	}
	
	/**
	 * 后台按状态查询订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取state
			String state = request.getParameter("state");
			
			//2.调用service，获取不同列表
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
//			if ("3and4".equals(state)) {
//				List<Order> list = os.findAllByState("3");
//				
//			}
			List<Order> list = os.findAllByState(state);
			
			//3.将list放入request域中，请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，查询订单列表失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/order/list.jsp";
	}
}
