package ljp.dao.impl;

import java.util.List;

import ljp.constant.Constant;
import ljp.dao.CategoryDao;
import ljp.domain.Category;
import ljp.utils.DataSourceUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	/**
	 * 查询所有【未删除】分类
	 */
	public List<Category> findAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from " + Constant.category_no_del_table;
		return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	/**
	 * 保存分类
	 */
	public void save(Category c) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "insert into category(cid,cname) values(?,?)";
		queryRunner.update(sql, c.getCid(), c.getCname());
	}

	@Override
	/**
	 * 删除分类
	 */
	public void delById(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update category set del=4 where cid=?";
		queryRunner.update(sql, cid);
//		真删除
//		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
//		String sql = "delete from category where cid=?";
//		queryRunner.update(sql, cid);
	}

	@Override
	/**
	 * 更新分类
	 */
	public void update(Category c) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update category set cname=? where cid=?";
		queryRunner.update(sql, c.getCname(), c.getCid());
	}

	@Override
	/**
	 * 查询分类名称
	 * 这个查询就不用视图了
	 */
	public String getCnameById(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select cname from category where cid=?";
		Object query = queryRunner.query(sql, new ScalarHandler() ,cid);
		return String.valueOf(query);
	}

	@Override
	/**
	 * 查询所有已删除分类
	 */
	public List<Category> findDel() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from category where del=?";
		return queryRunner.query(sql, new BeanListHandler<Category>(Category.class), Constant.CATEGORY_DEL);
	}

	@Override
	/**
	 * 恢复分类
	 */
	public Object recover(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update category set del=? where cid=?";
		return queryRunner.update(sql, Constant.CATEGORY_DEFAULT, cid);
	}

}
