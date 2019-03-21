package ljp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import ljp.dao.UserDao;
import ljp.domain.User;
import ljp.utils.DataSourceUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class UserDaoImpl implements UserDao {

	@Override
	/**
	 * 用户注册
	 */
	public void save(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		queryRunner.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), user.getState(), user.getCode());
	}

	@Override
	/**
	 * 通过激活码获取用户
	 */
	public User getByCode(String code) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code=? limit 1";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), code);
	}

	@Override
	/**
	 * 更新用户
	 */
	public void update(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set password=?,sex=?, state=?, code=? where uid=? ";
		queryRunner.update(sql, user.getPassword(), user.getSex(), user.getState(), user.getCode(), user.getUid());
	}

	@Override
	/**
	 * 用户登录
	 */
	public User getByUsernameAndPassword(String username, String password) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=? limit 1";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
	}

	@Override
	/**
	 * 用户列表（后台）
	 */
	public List<User> findAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "select * from user";
		return queryRunner.query(sql, new BeanListHandler<User>(User.class));
	}

	@Override
	/**
	 * 查找单个用户（后台）（前台）
	 */
	public User getById(String uid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid=?";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), uid);
	}

	@Override
	/**
	 * 更新用户（后台）
	 */
	public void update_p_n_t_s(User user) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource2());
		String sql = "update user set password=?,name=?,telephone=?,state=? where uid=?";
		queryRunner.update(sql, user.getPassword(),user.getName(),
				user.getTelephone(),user.getState(),user.getUid());
	}

	@Override
	/**
	 * 根据用户名查找用户
	 */
	public User findByUsername(String value) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? limit 1";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), value);
	}

	@Override
	/**
	 * 注册失败则删除用户！
	 */
	public void deleteById(String uid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from user where uid=?";
		queryRunner.update(sql, uid);
	}

	@Override
	/**
	 * 根据邮箱查询用户
	 */
	public User findByEmail(String value) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where email=? limit 1";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), value);
	}

}
