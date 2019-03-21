package ljp.service;

import java.util.List;

import ljp.domain.Admin;

public interface AdminService {

	Admin mlogin(String mname, String mpassword) throws Exception;

	Admin pwd(String mid, String mpassword) throws Exception;

	List<Admin> findAllNormal() throws Exception;

	void delete(String mid) throws Exception;

	void mAdd(Admin admin) throws Exception;

	Admin findByMname(String value) throws Exception;

}
