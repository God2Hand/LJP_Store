package ljp.service.impl;

import java.util.List;

import ljp.constant.Constant;
import ljp.dao.CategoryDao;
import ljp.domain.Category;
import ljp.service.CategoryService;
import ljp.utils.BeanFactory;
import ljp.utils.JedisUtils;
import ljp.utils.JsonUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class CategoryServiceImpl implements CategoryService {
	
	@Override
	/**
	 * E:后台展示所有分类
	 */
	public List<Category> findList() throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.findAll();
	}

	@Override
	/**
	 * 查询所有分类
	 */
	public String findAll() throws Exception {
		//1.调用dao查询所有分类
		//CategoryDao cd = new CategoryDaoImpl();
//		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
//		List<Category> list = cd.findAll();
		List<Category> list = findList();
		
		//2.将list转换成json字符串
		if (list != null && list.size() > 0) {
			return JsonUtil.list2json(list);
		}
		
		return null;
	}

	@Override
	/**
	 * 从redis中获取所有的分类
	 */
	public String findALLFromRedis() throws Exception {
//		//1.获取jedis
//		Jedis jedis = JedisUtils.getJedis();
//		
//		//2.从redis中获取数据
//		String value = jedis.get(Constant.LJP_STORE_CATEGORY_LIST);
//		
//		//3.判断数据是否为空
//		if (value == null) {
//			//3.2 若为空，调用findALl()方法，将查询的结果放入redis中
//			value = findAll();
//			jedis.set(Constant.LJP_STORE_CATEGORY_LIST, value);
//			System.out.println("从mysql中获取");
//			return value;
//		}
//		//3.3 若不为空，直接return
//		System.out.println("从redis中获取");
//		return value;
		
		/*不能因为redis挂了就gg，它知识辅助缓存的*/
		Jedis j = null;
		String value = null;
		
		try {
			//1.从redis中获取数据
			try {
				j = JedisUtils.getJedis();
				value = j.get(Constant.LJP_STORE_CATEGORY_LIST);
				if (value != null) {
					System.out.println("缓存中有数据");
					return value;
				}
			} catch (Exception e) {
			}
			
			//2.redis中若无数据，则从mysql数据库中获取，别忘记把数据并放入redis中
			value = findAll();
			
			//3.将value放入redis中
			try {
				j.set(Constant.LJP_STORE_CATEGORY_LIST, value);
				System.out.println("已经将数据放入缓存中");
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//释放jedis
			JedisUtils.closeJedis(j);
		}
		
		return value;
		
	}

	@Override
	/**
	 * 添加分类（后台）
	 */
	public void save(Category c) throws Exception {
		//1.调用dao完成添加
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.save(c);
		
		//2.更新redis：删除
		updateRedis();
	}
	private void updateRedis() {
		Jedis j = null;
		try {
			j = JedisUtils.getJedis();
			j.del(Constant.LJP_STORE_CATEGORY_LIST);
		} catch(JedisConnectionException e) {
		} finally {
			JedisUtils.closeJedis(j);
		}
	}

	@Override
	/**
	 * 删除分类（后台）
	 */
	public void delById(String cid) throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.delById(cid);
		updateRedis();
//		真删除
//		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
//		cd.delById(cid);
//		
//		//2.更新redis：删除
//		updateRedis();
	}

	@Override
	/**
	 * 更新分类（后台）
	 */
	public void update(Category c) throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.update(c);
		
		//2.更新redis：删除
		updateRedis();
	}

	@Override
	/**
	 * 查询分类名称（后台）
	 */
	public String getCnameById(String cid) throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.getCnameById(cid);
	}

	@Override
	/**
	 * 查询已删除分类（后台）
	 */
	public List<Category> findDel() throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.findDel();
	}

	@Override
	/**
	 * 恢复分类（后台）
	 */
	public void recover(String cid) throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.recover(cid);
	}

}
