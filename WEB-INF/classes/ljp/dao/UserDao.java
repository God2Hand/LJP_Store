package ljp.dao;

import java.util.List;

import ljp.domain.User;

public interface UserDao{

	void save(User user) throws Exception ;

	User getByCode(String code) throws Exception;

	void update(User user) throws Exception;

	User getByUsernameAndPassword(String username, String password) throws Exception;

	List<User> findAll() throws Exception;

	User getById(String uid) throws Exception;

	void update_p_n_t_s(User user) throws Exception;

	User findByUsername(String value) throws Exception;

	void deleteById(String uid) throws Exception;

	User findByEmail(String value) throws Exception;


}
