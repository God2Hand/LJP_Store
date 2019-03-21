package ljp.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.constant.Constant;
import ljp.domain.Cart;
import ljp.domain.CartItem;
import ljp.domain.Order;
import ljp.domain.OrderItem;
import ljp.domain.PageBean;
import ljp.domain.User;
import ljp.service.OrderService;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;
import ljp.utils.PaymentUtil;
import ljp.utils.UUIDUtils;
import ljp.utils.ZZUtils;
import ljp.web.servlet.base.BaseServlet;

public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service，删除订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			os.del(order);
			
			//4.重定向
			response.sendRedirect(request.getContextPath() + "/order?method=findMyOrdersByPage&pageNumber=1");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;
	}
	
	/**
	 * 删除已完成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service，获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			if (order == null) {
				request.setAttribute("msg", "删除记录失败");
				return "/jsp/msg.jsp";
			}
			//3.设置订单状态
			order.setState(Constant.order_deleted);
			os.update(order);
			
			//4.重定向
			response.sendRedirect(request.getContextPath() + "/order?method=findMyOrdersByPage&pageNumber=1");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;
	}
	
	/**
	 * 确认收货：已发货 -> 已完成
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirmReceive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service，获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			if (order == null) {
				request.setAttribute("msg", "哎呀呀~收货失败！亲，麻烦你重新确认！");
				return "/jsp/msg.jsp";
			}
			//3.设置订单状态
			order.setState(Constant.order_finished);
			os.update(order);
			
			//4.转发or重定向
			request.setAttribute("msg", "确认收货成功！欢迎下次再来！ O(∩_∩)O~~");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 获取订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service 查询当歌订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			//请求转发
			request.setAttribute("oBean", order);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询订单详情失败~");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 分页查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pageNumber 设置pageSize 获取userid
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize=3;
			
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				request.setAttribute("msg", "请先登录");
				return "/jsp/msg.jsp";
			}
			
			//2.调用service获取当前页所有数据pageBean
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> bean = os.findMyOrdersByPage(pageNumber, pageSize, user.getUid());
			
			//3.将pageBean放入request域中，请求转发 order_list.jsp
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取我的订单失败...");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_list.jsp";
	}

	/**
	 * 保存订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//-1.从session中获取user
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			//未登录，提示
			request.setAttribute("msg", "请先登录！");
			return "/jsp/msg.jsp";
		}
		
		//0.获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		try {
			
			//99.修正：提交空订单。不判断的话后面会有空指针异常的。
			if (cart == null || cart.getItemMap() == null || cart.getItemMap().isEmpty()) {
				request.setAttribute("msg", "亲，你没有下单哟~请先去逛逛吧~");
				return "/jsp/msg.jsp";
			}
			
			//1.封装订单对象
			//1.1 创建对象
			Order order = new Order();
			
			//1.2 设置oid,ordertime,total,state,user,items(订单项列表)
			order.setOid(UUIDUtils.getId());
			order.setOrdertime(new Date());
			order.setTotal(cart.getTotal());//从购物车中获得
			order.setState(Constant.order_unpay);
			//未付款全为一个空格 ---------------------------------------
			order.setAddress(" ");
			order.setName(" ");
			order.setTelephone(" ");
			
			//设置user
			order.setUser(user);
			//设置item（订单项列表），遍历购物项列表
			for (CartItem ci : cart.getCartItems()) {
				//判断库存数量
				if (ci.getCount() > ci.getProduct().getStock()) {
					request.setAttribute("msg", "亲，您手慢了，订单中有商品【已售空下架】或【数量不足】！");
					cart.clearCart();
					return "/jsp/msg.jsp";
				}
				//封装成orderitem
				OrderItem orderItem = new OrderItem();
				orderItem.setItemid(UUIDUtils.getId());
				orderItem.setCount(ci.getCount());
				orderItem.setSubtotal(ci.getSubtotal());
				orderItem.setProduct(ci.getProduct());
				orderItem.setOrder(order);
				
				//将orderitem加入order的items中
				order.getItems().add(orderItem);
			}
			
			//2.调用orderService完成保存操作
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			os.save(order);
			
			//3.请求转发到order_info.jsp
			request.setAttribute("oBean", order);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "哎呀~订单提交出错了~");
			cart.clearCart();
			return "/jsp/msg.jsp";
		} finally {
			//99.修正：提交订单后应从购物车清除该商品
			cart.clearCart();
			//订单出错也是要清除的
		}
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 在线支付
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		try {
//			//1.获取收货人信息 获取cid 获取银行
//			/*测试*/Order order = new Order();
//			//2.调用service获取订单 修改收货人信息 更新订单
//			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
//			os.update(order);
//			//3.拼接给第三方的url
//			//4.重定向
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "支付失败！！");
//			return "/jsp/msg.jsp";
//		}
//		return null;
		
			try {
				//接受参数
				String address=request.getParameter("address");
				String name=request.getParameter("name");
				String telephone=request.getParameter("telephone");
				String oid=request.getParameter("oid");
				
				//前端校验
				String regex = "^.{3,30}$";
				boolean c1 = ZZUtils.check(regex, address);
				regex = "^.{1,20}$";
				boolean c2 = ZZUtils.check(regex, name);
				regex = "^1[34578]\\d{9}$";
				boolean c3 = ZZUtils.check(regex, telephone);
				if (!(c1 && c2 && c3)) {
					request.setAttribute("msg", "请不要尝试越过检查！");
					return "/jsp/msg.jsp";
				}
				
				//通过id获取order
				OrderService s=(OrderService) BeanFactory.getBean("OrderService");
				Order order = s.getById(oid);
				
				order.setAddress(address);
				order.setName(name);
				order.setTelephone(telephone);
				
				//99999999999999999999999999999999999999999999这里不用网银了哼，截断它，直接不用付钱，爽吧~
				//先获取订单中订单项，遍历，减去库存，异常则跳转删除订单并页面
				try {
					for (OrderItem item : order.getItems()) {
						ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
						ps.updateStock(item.getProduct().getPid(), "-", item.getCount());
					}
				} catch (Exception e) {
					e.printStackTrace();
					s.del(order);
					request.setAttribute("msg", "订单付款失败！"
							+ "<br />亲，您可能手慢了，订单中有商品【已售空下架】或【数量不足】！"
							+ "<br />请重新下单吧mua~");
					return "/jsp/msg.jsp";
				}
				//库存end
				order.setState(Constant.order_payed);
				s.update(order);
				request.setAttribute("msg", "您的订单号为:"+oid+",金额为:"+order.getTotal()+"已经支付成功,等待发货~~");
				if (1 == 1) {
					return "/jsp/msg.jsp";					
				}
				//99999999999999999999999999999999999999999999：结束
				
				//更新order
				s.update(order);
				

				// 组织发送支付公司需要哪些数据
				String pd_FrpId = request.getParameter("pd_FrpId");
				String p0_Cmd = "Buy";
				String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
				String p2_Order = oid;
				String p3_Amt = "0.01";
				String p4_Cur = "CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
				// 第三方支付可以访问网址
				String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				// 加密hmac 需要密钥
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
						p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
						pd_FrpId, pr_NeedResponse, keyValue);

				
				//发送给第三方
				StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
				sb.append("p0_Cmd=").append(p0_Cmd).append("&");
				sb.append("p1_MerId=").append(p1_MerId).append("&");
				sb.append("p2_Order=").append(p2_Order).append("&");
				sb.append("p3_Amt=").append(p3_Amt).append("&");
				sb.append("p4_Cur=").append(p4_Cur).append("&");
				sb.append("p5_Pid=").append(p5_Pid).append("&");
				sb.append("p6_Pcat=").append(p6_Pcat).append("&");
				sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
				sb.append("p8_Url=").append(p8_Url).append("&");
				sb.append("p9_SAF=").append(p9_SAF).append("&");
				sb.append("pa_MP=").append(pa_MP).append("&");
				sb.append("pd_FrpId=").append(pd_FrpId).append("&");
				sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
				sb.append("hmac=").append(hmac);
				
				response.sendRedirect(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "支付失败！！");
				return "/jsp/msg.jsp";
			}
			
			return null;
		
	}
	/**
	 * 支付成功后回调
	 */
	public String rollback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取第三方发送过来的数据
		//2.获取订单 修改订单状态
		//3.更新订单
		//...
//		return null;
		
			try {
				String p1_MerId = request.getParameter("p1_MerId");
				String r0_Cmd = request.getParameter("r0_Cmd");
				String r1_Code = request.getParameter("r1_Code");
				String r2_TrxId = request.getParameter("r2_TrxId");
				String r3_Amt = request.getParameter("r3_Amt");
				String r4_Cur = request.getParameter("r4_Cur");
				String r5_Pid = request.getParameter("r5_Pid");
				String r6_Order = request.getParameter("r6_Order");
				String r7_Uid = request.getParameter("r7_Uid");
				String r8_MP = request.getParameter("r8_MP");
				String r9_BType = request.getParameter("r9_BType");
				String rb_BankId = request.getParameter("rb_BankId");
				String ro_BankOrderId = request.getParameter("ro_BankOrderId");
				String rp_PayDate = request.getParameter("rp_PayDate");
				String rq_CardNo = request.getParameter("rq_CardNo");
				String ru_Trxtime = request.getParameter("ru_Trxtime");
				// 身份校验 --- 判断是不是支付公司通知你
				String hmac = request.getParameter("hmac");
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");

				// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
				boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
						r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
						r8_MP, r9_BType, keyValue);
				if (isValid) {
					// 响应数据有效
					if (r9_BType.equals("1")) {
						// 浏览器重定向
						System.out.println("111");
						request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
						
					} else if (r9_BType.equals("2")) {
						// 服务器点对点 --- 支付公司通知你
						System.out.println("付款成功！222");
						// 修改订单状态 为已付款
						// 回复支付公司
						response.getWriter().print("success");
					}
					
					//=========这里因为是内网，所以只好统一更新
					//修改订单状态
					OrderService s=(OrderService) BeanFactory.getBean("OrderService");
					Order order = s.getById(r6_Order);
					order.setState(Constant.order_payed);
					
					s.update(order);
					
				} else {
					// 数据无效
					System.out.println("数据被篡改！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "支付失败！！");
				return "/jsp/msg.jsp";
			}
			
			
			return "/jsp/msg.jsp";
	}

}
