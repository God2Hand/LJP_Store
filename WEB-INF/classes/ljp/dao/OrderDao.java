package ljp.dao;

import java.util.List;

import ljp.domain.Order;
import ljp.domain.OrderItem;
import ljp.domain.PageBean;

public interface OrderDao {

	void save(Order order) throws Exception ;

	void saveItem(OrderItem oi) throws Exception ;

	int getTotalRecordNoState(String uid, int state) throws Exception ;
	List<Order> findMyOrdersByPageNoState(PageBean<Order> pb, String uid, int state) throws Exception ;

	Order getById(String oid) throws Exception ;

	void update(Order order) throws Exception ;

	List<Order> findAllByState(String state) throws Exception ;

	void delItemsById(String itemid) throws Exception ;
	void delById(String itemid) throws Exception ;


}
