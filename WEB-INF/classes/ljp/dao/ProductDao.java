package ljp.dao;

import java.util.List;

import ljp.domain.PageBean;
import ljp.domain.Product;

public interface ProductDao {

	List<Product> findHot() throws Exception ;

	List<Product> findNew() throws Exception ;

	Product getById(String pid) throws Exception ;

	List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception ;
	List<Product> searchByPage(PageBean<Product> pb, String searchName) throws Exception ;

	int getTotalRecord(String cid) throws Exception ;
	int getTotalRecordByName(String searchName) throws Exception ;

	List<Product> findAll() throws Exception ;

	void save(Product p) throws Exception ;

	void delById(String pid) throws Exception ;

	void update(Product p) throws Exception ;

	void updateStock(String pid, String jorj, Integer count) throws Exception ;

	List<Product> findPflag() throws Exception ;

	List<Product> findByC(String cid, int del) throws Exception ;

	void delByC(String cid) throws Exception ;

	List<Product> findDel() throws Exception ;



}
