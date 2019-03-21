package ljp.service.impl;

import java.util.List;

import ljp.constant.Constant;
import ljp.dao.OrderDao;
import ljp.domain.Order;
import ljp.domain.OrderItem;
import ljp.domain.PageBean;
import ljp.service.OrderService;
import ljp.utils.BeanFactory;
import ljp.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	/**
	 * 提交订单
	 */
	public void save(Order order) throws Exception{
		try {
			//获取dao
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			//ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
			
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			//2.向orders表中插入一条
			od.save(order);
			
			//3.向orderitems中插入n条
			for ( OrderItem oi : order.getItems()) { 
				od.saveItem(oi);
				////更新商品库存
				//pd.updateStock(oi.getProduct().getPid(), "-", oi.getCount());
			}
			
			//4.事务控制
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
			////尚且有个问题，由于事务回滚，不能把负数量的商品设为pflag=1
		}
	}

	@Override
	/**
	 * 我的订单
	 */
	public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		
		//1.创建PageBean
		PageBean<Order> pb = new PageBean<Order>(pageNumber, pageSize);
		
		//2.查询总条数 设置总条数
		int totalRecord = od.getTotalRecordNoState(uid, Constant.order_deleted);
		pb.setTotalRecord(totalRecord);
		
		//3.查询并设置当前页数据
		List<Order> data = od.findMyOrdersByPageNoState(pb, uid, Constant.order_deleted);
		pb.setData(data);
		
		return pb;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}

	@Override
	/**
	 * 修改订单
	 */
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}
	
	@Override
	/**
	 * 取消订单
	 */
	public void del(Order order) throws Exception {
		try {
			//获取dao
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			//2.向orderitems中删除n条
			for ( OrderItem oi : order.getItems()) { 
				od.delItemsById(oi.getItemid());
			}
			
			//3.向orders表中删除一条
			od.delById(order.getOid());
			
			//4.事务控制
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	@Override
	/**
	 * （后台）查询订单
	 */
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
	}

}
