package ljp.service;

import java.util.List;

import ljp.domain.PageBean;
import ljp.domain.Product;

public interface ProductService {

	List<Product> findHot() throws Exception ;

	List<Product> findNew() throws Exception ;

	Product getById(String pid) throws Exception ;

	PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception ;
	PageBean<Product> searchByPage(int pageNumber, int pageSize, String searchName) throws Exception ;

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
