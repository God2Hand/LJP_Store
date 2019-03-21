package ljp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ljp.constant.Constant;
import ljp.dao.ProductDao;
import ljp.domain.Category;
import ljp.domain.PageBean;
import ljp.domain.Product;
import ljp.utils.DataSourceUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class ProductDaoImpl implements ProductDao {

	@Override
	/**
	 * 查询热门【上架】
	 */
	public List<Product> findHot() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from " + Constant.product_is_up_table + " where is_hot=? order by pdate desc limit 9";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), Constant.PRODUCT_IS_HOT);
	}

	@Override
	/**
	 * 查询最新【上架】
	 */
	public List<Product> findNew() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from " + Constant.product_is_up_table + " order by pdate desc limit 9";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	/**
	 * 查询单个商品
	 * 重写是为了category
	 * 通过pid查询的，就不使用视图了【上架】
	 */
	public Product getById(String pid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=? limit 1";
		return queryRunner.query(sql, new ResultSetHandler<Product>() {
			@Override
			public Product handle(ResultSet rs) throws SQLException {
				Product p = null;
				if (rs.next()) {
					p = new Product();
					p.setPid(rs.getString("pid"));
					p.setPname(rs.getString("pname"));
					p.setMarket_price(rs.getDouble("market_price"));
					p.setShop_price(rs.getDouble("shop_price"));
					p.setPimage(rs.getString("pimage"));
					p.setPdate(rs.getDate("pdate"));
					p.setIs_hot(rs.getInt("is_hot"));
					p.setPdesc(rs.getString("pdesc"));
					p.setPflag(rs.getInt("pflag"));
					p.setStock(rs.getInt("stock"));
					Category c = new Category();
					c.setCid(rs.getString("cid"));
					p.setCategory(c);
				}
				return p;
			}
		}, pid);
	}

	@Override
	/**
	 * 查询当前页数据【上架】
	 */
	public List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from " + Constant.product_is_up_table + " where cid=? order by pdate desc limit ?,?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, pb.getStartIndex(), pb.getPageSize());
	}

	@Override
	/**
	 * 获取总记录数【上架】
	 */
	public int getTotalRecord(String cid) throws Exception {
		return ((Long)new QueryRunner(DataSourceUtils.getDataSource()).query("select count(*) from " + Constant.product_is_up_table + " where cid=?", new ScalarHandler(), cid)).intValue();
	}
	
	@Override
	/**
	 * 查找商品当前分页【上架】
	 */
	public List<Product> searchByPage(PageBean<Product> pb, String searchName) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from " + Constant.product_is_up_table + " where pname like ? order by pdate desc limit ?,?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), "%" + searchName + "%", pb.getStartIndex(), pb.getPageSize());
	}
	@Override
	/**
	 * 获取总查找记录数【上架】
	 */
	public int getTotalRecordByName(String searchName) throws Exception {
		return ((Long)new QueryRunner(DataSourceUtils.getDataSource()).query("select count(*) from " + Constant.product_is_up_table + " where pname like ?", new ScalarHandler(), "%" + searchName + "%")).intValue();
	}

	@Override
	/**
	 * 查询所有【已上架】商品（后台）
	 */
	public List<Product> findAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from " + Constant.product_is_up_table + " order by pdate desc";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	/**
	 * 添加上架商品（后台）
	 */
	public void save(Product p) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?,?)";
		queryRunner.update(sql,p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(), 
				p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),
				p.getCategory().getCid(),p.getStock());
	}

	@Override
	/**
	 * 删除商品（后台）
	 */
	public void delById(String pid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update product set pflag=4 where pid=?";
		queryRunner.update(sql, pid);
//		真删除
//		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
//		String sql = "delete from product where pid=?";
//		queryRunner.update(sql, pid);
	}

	@Override
	/**
	 * 修改商品（后台）
	 */
	public void update(Product p) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update product set pname=?,market_price=?,shop_price=?,"
				+ "pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,"
				+ "cid=?, stock=? where pid=?";
		queryRunner.update(sql,p.getPname(),p.getMarket_price(),p.getShop_price(), 
				p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),
				p.getCategory().getCid(),p.getStock(),p.getPid());
	}

	@Override
	/**
	 * 更新商品库存
	 */
	public void updateStock(String pid, String jorj, Integer count)
			throws Exception {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "update product set stock=stock" + jorj + count + " where pid=?";
		queryRunner.update(DataSourceUtils.getConnection(), sql, pid);
	}

	@Override
	/**
	 * 后台【下架】商品列表
	 */
	public List<Product> findPflag() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from " + Constant.product_is_down_table + " order by pdate desc";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	/**
	 * 后台分类查询商品
	 */
	public List<Product> findByC(String cid, int del) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from product where cid=? and ";
		if (del == 4) {
			sql += "pflag=4 order by pdate";
			return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid);
		} else {
			sql += "pflag!=4 order by pdate";
			return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid);
		}
	}

	@Override
	/**
	 * 后台删除一个分类所有商品
	 */
	public void delByC(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update product set pflag=4 where cid=?";
		queryRunner.update(sql, cid);
//		真删除
//		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
//		String sql = "delete from product where cid=?";
//		queryRunner.update(sql, cid);
	}

	@Override
	/**
	 * 查询已上架商品
	 */
	public List<Product> findDel() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from product where pflag=? order by pdate desc";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), Constant.PRODUCT_IS_die);
	}


}
