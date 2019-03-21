package ljp.service;

import java.util.List;

import ljp.domain.User;

public interface UserService {

	void regist(User user) throws Exception ;

	User active(String code) throws Exception ;

	User login(String username, String password) throws Exception;
	
	User changePwd(String uid,String password,String repassword) throws Exception ;
	
	void forgetPwd(String uid) throws Exception ;

	List<User> findAll() throws Exception ;

	User getById(String uid) throws Exception ;

	void update_p_n_t_s(User user) throws Exception ;

	User findByUsername(String value) throws Exception ;

	User findByEmail(String value) throws Exception ;

}
