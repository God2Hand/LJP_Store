package ljp.service.impl;

import java.util.List;

import ljp.dao.ProductDao;
import ljp.domain.PageBean;
import ljp.domain.Product;
import ljp.service.ProductService;
import ljp.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	@Override
	/**
	 * 查询热门商品
	 */
	public List<Product> findHot() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findHot();
	}

	@Override
	/**
	 * 查询最新商品
	 */
	public List<Product> findNew() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findNew();
	}

	@Override
	/**
	 * 查询单个商品
	 */
	public Product getById(String pid) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.getById(pid);
	}

	@Override
	/**
	 * 分页展示商品
	 */
	public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//1.创建pageBean
		PageBean<Product> pb = new PageBean<Product>(pageNumber, pageSize);
		
		//2.设置当前页数据
		List<Product> data = pd.findByPage(pb,cid);
		pb.setData(data);
		
		//3.设置总记录数
		int totalRecord = pd.getTotalRecord(cid);
		pb.setTotalRecord(totalRecord);
		
		return pb;
	}
	
	@Override
	/**
	 * 分页查询商品
	 */
	public PageBean<Product> searchByPage(int pageNumber, int pageSize, String searchName) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//1.创建pageBean
		PageBean<Product> pb = new PageBean<Product>(pageNumber, pageSize);
		//2.设置当前页数据
		List<Product> data = pd.searchByPage(pb,searchName);
		pb.setData(data);
		
		//3.设置总记录数
		int totalRecord = pd.getTotalRecordByName(searchName);
		pb.setTotalRecord(totalRecord);
		
		return pb;
	}
	
	@Override
	/**
	 * 修改商品库存
	 */
	public void updateStock(String pid, String jorj, Integer count) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.updateStock(pid, jorj, count);
	}

	@Override
	/**
	 * 后台展示已上架商品
	 */
	public List<Product> findAll() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findAll();
	}

	@Override
	/**
	 * 后台保存商品
	 */
	public void save(Product p) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.save(p);
	}

	@Override
	/**
	 * 后台删除商品
	 */
	public void delById(String pid) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.delById(pid);
//		真删除
//		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
//		pd.delById(pid);
	}

	@Override
	/**
	 * 后台修改商品
	 */
	public void update(Product p) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.update(p);
	}

	@Override
	/**
	 * 后台查询下架商品列表
	 */
	public List<Product> findPflag() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findPflag();
	}

	@Override
	/**
	 * 后台按照分类查询商品
	 */
	public List<Product> findByC(String cid, int del) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findByC(cid, del);
	}

	@Override
	/**
	 * 后台删除一个分类所有商品
	 */
	public void delByC(String cid) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.delByC(cid);
//		真删除
//		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
//		pd.delByC(cid);
	}

	@Override
	/**
	 * 后台查询已删除商品
	 */
	public List<Product> findDel() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findDel();
	}

}
