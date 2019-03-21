package ljp.dao.impl;

import java.util.List;

import ljp.constant.Constant;
import ljp.dao.AdminDao;
import ljp.domain.Admin;
import ljp.utils.DataSourceUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class AdminDaoImpl implements AdminDao {

	@Override
	/**
	 * 管理原登录，根据用户名密码获得管理员
	 */
	public Admin mlogin(String mname, String mpassword) throws Exception {
		//权限控制：这里的get连接要用root
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from manager where mname=? and mpassword=md5(?) limit 1";
		return queryRunner.query(sql, new BeanHandler<Admin>(Admin.class), mname, mpassword);
	}

	@Override
	/**
	 * 获取单个管理员
	 */
	public Admin getById(String mid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from manager where mid=?";
		return queryRunner.query(sql, new BeanHandler<Admin>(Admin.class), mid);
	}

	@Override
	/**
	 * 更新用户（修改密码）
	 */
	public void update(Admin admin) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update manager set mname=?,mpassword=? where mid=?";
		queryRunner.update(sql, admin.getMname(), admin.getMpassword(), admin.getMid());
	}

	@Override
	/**
	 * 普通管理员列表
	 */
	public List<Admin> findAllNormal() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from manager where mid!=?";
		return queryRunner.query(sql, new BeanListHandler<Admin>(Admin.class), Constant.SUPER);
	}

	@Override
	/**
	 * 根据mid删除管理员
	 */
	public void deleteById(String mid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource3());
		String sql = "delete from manager where mid=?";
		queryRunner.update(sql, mid);
	}

	@Override
	/**
	 * 添加管理员
	 */
	public void add(Admin admin) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource3());
		String sql = "insert into manager values(?,?,?)";
		queryRunner.update(sql, admin.getMid(), admin.getMname(), admin.getMpassword());
	}

	@Override
	/**
	 * 根据mname查询管理员
	 */
	public Admin findByMname(String value) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from manager where mname=?";
		return queryRunner.query(sql, new BeanHandler<Admin>(Admin.class), value);
	}

}
