package ljp.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 购物车
 * @author Shinelon
 */
public class Cart {

	private Map<String, CartItem> itemMap = new HashMap<String, CartItem>();
	private Double total = 0.0;
	
	public Collection<CartItem> getCartItems() {
		return itemMap.values();
	}
	
	public Map<String, CartItem> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, CartItem> itemMap) {
		this.itemMap = itemMap;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	/**
	 * 加入购物车
	 * @param item
	 */
	public void add2cart(CartItem item) {
		//获取商品的pid
		String pid = item.getProduct().getPid();
		int stock = item.getProduct().getStock();
		//判断购物车中是否有
		if (itemMap.containsKey(pid)) {
			//有，修改数量 = 原来数量 + 新加数量
			//原来的购物项
			//超过数量等于最大数量
			CartItem oItem = itemMap.get(pid);
			int totalCount = oItem.getCount() + item.getCount();
			if (totalCount > stock) {
				totalCount = stock;
			}
			oItem.setCount(totalCount);
		} else {
			//没有
			itemMap.put(pid, item);
		}		
		//修改总金额
		total += item.getSubtotal();
	}
	
	/**
	 * 从购物车移除一个购物项
	 * @param item
	 */
	public void removeFromCart(String pid) {
		//1.从购物车(map)中移除购物项
		CartItem item = itemMap.remove(pid);
		
		//2.修改总金额
		total -= item.getSubtotal();
	}
	
	/**
	 * 清空购物车
	 * @param item
	 */
	public void clearCart() {
		//1.清空map
		itemMap.clear();
		
		//2.修改总金额为零
		total = 0.0;
	}
}
