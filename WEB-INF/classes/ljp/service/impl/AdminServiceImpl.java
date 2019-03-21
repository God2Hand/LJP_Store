package ljp.service.impl;

import java.util.List;

import ljp.dao.AdminDao;
import ljp.domain.Admin;
import ljp.service.AdminService;
import ljp.utils.BeanFactory;

public class AdminServiceImpl implements AdminService {

	@Override
	/**
	 * 管理员登录
	 */
	public Admin mlogin(String mname, String mpassword) throws Exception {
		AdminDao ad = (AdminDao) BeanFactory.getBean("AdminDao");
		return ad.mlogin(mname, mpassword);
	}

	@Override
	/**
	 * 修改密码
	 */
	public Admin pwd(String mid, String mpassword) throws Exception {
		AdminDao ad = (AdminDao) BeanFactory.getBean("AdminDao");
		Admin admin = ad.getById(mid);
		if (admin == null) {
			return null;
		}
		admin.setMpassword(mpassword);
		ad.update(admin);
		return admin;
	}

	@Override
	/**
	 * 普通管理员列表
	 */
	public List<Admin> findAllNormal() throws Exception {
		AdminDao ad = (AdminDao) BeanFactory.getBean("AdminDao");
		return ad.findAllNormal();
	}

	@Override
	/**
	 * 删除普通管理员
	 */
	public void delete(String mid) throws Exception {
		AdminDao ad = (AdminDao) BeanFactory.getBean("AdminDao");
		ad.deleteById(mid);
	}

	@Override
	/**
	 * 添加管理员
	 */
	public void mAdd(Admin admin) throws Exception {
		AdminDao ad = (AdminDao) BeanFactory.getBean("AdminDao");
		ad.add(admin);
	}

	@Override
	/**
	 * 根据mname查询管理员
	 */
	public Admin findByMname(String value) throws Exception {
		AdminDao ad = (AdminDao) BeanFactory.getBean("AdminDao");
		return ad.findByMname(value);
	}

}
