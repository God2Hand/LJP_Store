package ljp.dao;

import java.util.List;

import ljp.domain.Admin;

public interface AdminDao {

	Admin mlogin(String mname, String mpassword) throws Exception;

	Admin getById(String mid) throws Exception;

	void update(Admin admin) throws Exception;

	List<Admin> findAllNormal() throws Exception;

	void deleteById(String mid) throws Exception;

	void add(Admin admin) throws Exception;

	Admin findByMname(String value) throws Exception;

}
