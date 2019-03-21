package ljp.utils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	//超管是否登录
	private static boolean admin = false;
	public static boolean isAdmin() {
		return admin;
	}
	public static void setAdmin(boolean admin) {
		DataSourceUtils.admin = admin;
	}
	
	private static ComboPooledDataSource ds=new ComboPooledDataSource();
	//仿
	private static ComboPooledDataSource ds2 = new ComboPooledDataSource();
	static {
		Properties p = new Properties();
		try {
			p.load(DataSourceUtils.class.getClassLoader().getResourceAsStream("c3p0_2.properties"));
			ds2.setDriverClass(p.getProperty("c3p0.driverClass"));
			ds2.setJdbcUrl(p.getProperty("c3p0.jdbcUrl"));
			ds2.setUser(p.getProperty("c3p0.user"));
			ds2.setPassword(p.getProperty("c3p0.password"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	private static ComboPooledDataSource ds3 = new ComboPooledDataSource();
	static {
		Properties p = new Properties();
		try {
			p.load(DataSourceUtils.class.getClassLoader().getResourceAsStream("c3p0_3.properties"));
			ds3.setDriverClass(p.getProperty("c3p0.driverClass"));
			ds3.setJdbcUrl(p.getProperty("c3p0.jdbcUrl"));
			ds3.setUser(p.getProperty("c3p0.user"));
			ds3.setPassword(p.getProperty("c3p0.password"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	private static ThreadLocal<Connection> tl=new ThreadLocal<>();
	private static ThreadLocal<Connection> t2=new ThreadLocal<>();
	
	/**
	 * 获取数据源
	 * @return 连接池
	 */
	public static DataSource getDataSource(){
		return ds;
	}
	public static DataSource getDataSource2(){
			return ds2;
	}
	public static DataSource getDataSource3(){
		if (admin) {
			return ds3;
		} else {
			return ds2;
		}
	}
	
	/**
	 * 从线程中获取连接
	 * @return 连接
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		//若是第一次获取 需要从池中获取一个连接,将这个连接和当前线程绑定
		if(conn==null){
			conn=ds.getConnection();
			
			//将这个连接和当前线程绑定
			tl.set(conn);
		}
		
		return conn;
	}
	public static Connection getConnection2() throws SQLException{
		Connection conn = t2.get();
		//若是第一次获取 需要从池中获取一个连接,将这个连接和当前线程绑定
		if(conn==null){
			conn=ds2.getConnection();
			
			//将这个连接和当前线程绑定
			t2.set(conn);
		}
		
		return conn;
	}
	
	
	
	/**
	 * 释放资源
	 * 
	 * @param conn
	 *            连接
	 * @param st
	 *            语句执行者
	 * @param rs
	 *            结果集
	 */
	public static void closeResource(Connection conn, Statement st, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(st);
		closeConn(conn);
	}

	/**
	 * 释放连接
	 * 
	 * @param conn
	 *            连接
	 */
	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				//和当前线程解绑
				tl.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}

	}

	/**
	 * 释放语句执行者
	 * 
	 * @param st
	 *            语句执行者
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			st = null;
		}

	}

	/**
	 * 释放结果集
	 * 
	 * @param rs
	 *            结果集
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}

	}
	
	/**
	 * 开始事务
	 * @throws SQLException 
	 */
	public static void startTransaction() throws SQLException{
		//1.获取连接
		Connection conn=getConnection();
		
		//2.开始
		conn.setAutoCommit(false);
	}
	public static void startTransaction2() throws SQLException{
		//1.获取连接
		Connection conn=getConnection2();
		
		//2.开始
		conn.setAutoCommit(false);
	}
	
	/**
	 * 事务提交
	 */
	public static void commitAndClose(){
		try {
			//0.获取连接
			Connection conn = getConnection();
			
			//1.提交事务
			conn.commit();
			
			//2.关闭且移除
			closeConn(conn);
		} catch (SQLException e) {
		}
		
	}
	public static void commitAndClose2(){
		try {
			//0.获取连接
			Connection conn = getConnection2();
			
			//1.提交事务
			conn.commit();
			
			//2.关闭且移除
			closeConn(conn);
		} catch (SQLException e) {
		}
		
	}
	
	/**
	 * 提交回滚
	 */
	public static void rollbackAndClose(){
		try {
			//0.获取连接
			Connection conn = getConnection();
			
			//1.事务回滚
			conn.rollback();
			
			//2.关闭且移除
			closeConn(conn);
		} catch (SQLException e) {
		}
		
	}
	public static void rollbackAndClose2(){
		try {
			//0.获取连接
			Connection conn = getConnection2();
			
			//1.事务回滚
			conn.rollback();
			
			//2.关闭且移除
			closeConn(conn);
		} catch (SQLException e) {
		}
		
	}
}
