package ljp.service;

import java.util.List;

import ljp.domain.Category;

public interface CategoryService {

	String findAll() throws Exception;

	String findALLFromRedis() throws Exception;

	List<Category> findList() throws Exception;

	void save(Category c) throws Exception;

	void delById(String cid) throws Exception;

	void update(Category c) throws Exception;

	String getCnameById(String cid) throws Exception;

	List<Category> findDel() throws Exception;

	void recover(String cid) throws Exception;

}
