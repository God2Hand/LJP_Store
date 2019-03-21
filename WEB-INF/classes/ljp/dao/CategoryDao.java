package ljp.dao;

import java.util.List;

import ljp.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void save(Category c) throws Exception;

	void delById(String cid) throws Exception;

	void update(Category c) throws Exception;

	String getCnameById(String cid) throws Exception;

	List<Category> findDel() throws Exception;

	Object recover(String cid) throws Exception;

}
