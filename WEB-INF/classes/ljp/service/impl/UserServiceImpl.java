package ljp.service.impl;

import java.util.List;

import ljp.constant.Constant;
import ljp.dao.UserDao;
import ljp.domain.User;
import ljp.service.UserService;
import ljp.utils.BeanFactory;
import ljp.utils.MailUtils;

public class UserServiceImpl implements UserService {

	@Override
	/**
	 * 用户注册
	 */
	public void regist(User user) throws Exception {
		//1.调用dao完成注册
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		ud.save(user);
		
		//2.发送激活邮件
		try {
			String subject = "ljp网上商城：用户激活!";
			String emailMsg = "恭喜" + user.getName() + "成为我们商城的一员！<a href='" + Constant.INDEX + "user?method=active&code=" + user.getCode() + "'>点此激活</a>";
			MailUtils.sendMail(user.getEmail(), subject, emailMsg);
		} catch (Exception e) {
			e.printStackTrace();
			ud.deleteById(user.getUid());
		}
	}

	@Override
	/**
	 * 用户激活
	 */
	public User active(String code) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		
		//1.通过code获取用户
		User user = ud.getByCode(code);
		
		//1.2 通过激活码没有找到用户
		if (user == null) {
			return null;
		}
		
		//2.若获取到了，修改用户
		user.setState(Constant.USER_IS_ACTIVE);
		user.setCode(null);
		ud.update(user);
		
		return user;
		
	}
	
	@Override
	/**
	 * 修改用户密码
	 */
	public User changePwd(String uid,String password,String repassword) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User user = ud.getById(uid);
		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}
		user.setPassword(repassword);
		ud.update(user);
		return user;
	}

	@Override
	/**
	 * 用户登录
	 */
	public User login(String username, String password) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.getByUsernameAndPassword(username, password);
	}
	
	@Override
	/**
	 * 忘记密码
	 */
	public void forgetPwd(String uid) throws Exception {
		//1.调用dao完成注册
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User user = ud.getById(uid);
		
		//2.发送激活邮件
		String subject = "ljp网上商城：忘记密码";
		String emailMsg = "用户" + user.getName() + "，您好！有人登录了账户并点击忘记密码..."
				+ "<br />如果不是您所为，请马上登录并修改密码。"
				+ "<br />如果是您操作的，建议您收到密码后登录并修改密码！！"
				+ "密码为：<font color='grey' >" + user.getPassword() + "</font>"
				+ "<a href='" + Constant.INDEX + "'>点此前去商城</a>";
		MailUtils.sendMail(user.getEmail(), subject, emailMsg);
	}

	@Override
	/**
	 * 用户列表（后台）
	 */
	public List<User> findAll() throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.findAll();
	}

	@Override
	/**
	 * 查找单个用户（后台）
	 */
	public User getById(String uid) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.getById(uid);
	}

	@Override
	/**
	 * 更新用户（后台）
	 */
	public void update_p_n_t_s(User user) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		ud.update_p_n_t_s(user);
	}

	@Override
	/**
	 * 根据用户名查找用户
	 */
	public User findByUsername(String value) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.findByUsername(value);
	}

	@Override
	/**
	 * 根据邮箱查找用户
	 */
	public User findByEmail(String value) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.findByEmail(value);
	}

}
