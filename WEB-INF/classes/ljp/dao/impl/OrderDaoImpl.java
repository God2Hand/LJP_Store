package ljp.dao.impl;

import java.util.List;
import java.util.Map;

import ljp.dao.OrderDao;
import ljp.domain.Order;
import ljp.domain.OrderItem;
import ljp.domain.PageBean;
import ljp.domain.Product;
import ljp.utils.DataSourceUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class OrderDaoImpl implements OrderDao {

	@Override
	/**
	 * 保存订单
	 */
	public void save(Order o) throws Exception {
		//有事务要用无参的
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		runner.update(DataSourceUtils.getConnection(), sql, o.getOid(), o.getOrdertime(), o.getTotal(), 
				o.getState(), o.getAddress(), o.getName(), o.getTelephone(), o.getUser().getUid());
	}
	@Override
	/**
	 * 保存订单项
	 */
	public void saveItem(OrderItem oi) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		runner.update(DataSourceUtils.getConnection(), sql, oi.getItemid(), oi.getCount(), oi.getSubtotal(), 
				oi.getProduct().getPid(), oi.getOrder().getOid());
	}

	@Override
	/**
	 * 获取我的订单的总条数
	 */
	public int getTotalRecordNoState(String uid, int state) throws Exception {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid=? and state!=?";
		return ((Long)runner.query(sql, new ScalarHandler(), uid, state)).intValue();
	}

	@Override
	/**
	 * 获取我的订单的当前页数据
	 */
	public List<Order> findMyOrdersByPageNoState(PageBean<Order> pb, String uid, int state) throws Exception {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//查询所有订单（基本信息）
		String sql = "select * from orders where uid=? and state!=? order by ordertime desc limit ?,?";
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), uid, state, pb.getStartIndex(), pb.getPageSize());
		
		//查询订单列表 获取每一个订单，查询每个订单订单项
		for (Order order : list) {
			sql = "select * from orderitem oi, product p where oi.pid=p.pid and oi.oid=?";
			List<Map<String, Object>> maplist = runner.query(sql, new MapListHandler(), order.getOid());
			
			//遍历maplist，获取每一个订单详情，封装成orderitem，将其加入当前订单项列表中
			for (Map<String, Object> map : maplist) {
				//1.封装成orderitem
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				Product p = new Product();
				BeanUtils.populate(p, map);
				oi.setProduct(p);
				
				//2.将orderitem放入order的订单项列表中
				order.getItems().add(oi);
			}
		}
		return list;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//1.查询订单基本信息
		String sql = "select * from orders where oid=?";
		Order order = runner.query(sql, new BeanHandler<Order>(Order.class), oid);
		
		//2.查询订单项
		sql = "select * from orderitem oi, product p where oi.pid=p.pid and oi.oid=?";
		//所有的订单项详情
		List<Map<String, Object>> maplist = runner.query(sql, new MapListHandler(), oid);
		//获取每一个订单详情 封装成orderitem 加入到当前订单的items中
		for (Map<String, Object> map : maplist) {
			//1.封装成orderitem
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map);
			Product p = new Product();
			BeanUtils.populate(p, map);
			oi.setProduct(p);
			
			//2.将orderitem放入order的订单项列表中
			order.getItems().add(oi);
		}
		return order;
	}

	@Override
	/**
	 * 修改订单
	 */
	public void update(Order order) throws Exception {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state=?, address=?, name=?, telephone=? where oid=?";
		runner.update(sql, order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}
	
	@Override
	/**
	 * 删除订单项
	 */
	public void delItemsById(String itemid) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql = "delete from orderitem where itemid=?";
		runner.update(DataSourceUtils.getConnection(), sql, itemid);
	}
	@Override
	/**
	 * 删除订单
	 */
	public void delById(String oid) throws Exception {
		//有事务要用无参的
		QueryRunner runner = new QueryRunner();
		String sql = "delete from orders where oid=?";
		runner.update(DataSourceUtils.getConnection(), sql, oid);
	}

	@Override
	/**
	 * 查询订单（后台）
	 */
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from orders ";
		//判断state是否为空
		if (null == state || state.trim().length() == 0) {
			sql += " order by ordertime desc";
			return runner.query(sql, new BeanListHandler<Order>(Order.class));
		} else if ("3and4".endsWith(state.trim())) {
			sql += " where state=? or state=? order by ordertime desc";
			return runner.query(sql, new BeanListHandler<Order>(Order.class), 3, 4);
		} else {
			sql += " where state=? order by ordertime desc";
			return runner.query(sql, new BeanListHandler<Order>(Order.class), state);			
		}
	}

}
