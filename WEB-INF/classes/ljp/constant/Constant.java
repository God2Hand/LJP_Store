package ljp.constant;

public interface Constant {
	
	/**
	 * 商城网址（供邮件使用，方便修改）
	 */
	String INDEX = "http://localhost/LJP_store/";
	
	/**
	 * 【内置】超级管理员的id
	 */
	String SUPER = "god_and_evil_ljp_3116004823";

	/**
	 * 用户未激活
	 */
	int USER_IS_NOT_ACTIVE = 0;
	/**
	 * 用户已激活
	 */
	int USER_IS_ACTIVE = 1;
	
	/**
	 * 记住用户名
	 */
	String SAVE_NAME = "yes";
	
	/**
	 * redis中存储分类列表的key
	 */
	String LJP_STORE_CATEGORY_LIST = "category_list";
	/**
	 * redis的服务器地址
	 */
	String REDIS_HOST = "192.168.170.132";
	/**
	 * redis的服务器端口号
	 */
	int REDIS_PORT = 6379;
	
	//////////
	
	/**
	 * 热门商品
	 */
	int PRODUCT_IS_HOT = 1;
	
	/**
	 * 商品未下架
	 */
	int PRODUCT_IS_UP = 0;
	/**
	 * 商品已下架
	 */
	int PRODUCT_IS_DOWN = 1;
	/**
	 * 商品已删除
	 */
	int PRODUCT_IS_die = 4;
	
	
	//这里就不大写了不然怕我自己都不懂
	/**
	 * 订单状态：未付款
	 */
	int order_unpay = 0;
	/**
	 * 订单状态：已付款
	 */
	int order_payed = 1;
	/**
	 * 订单状态：已发货
	 */
	int order_sended = 2;
	/**
	 * 订单状态：已完成
	 */
	int order_finished = 3;
	/**
	 * 订单状态：已删除
	 */
	int order_deleted = 4;
	
	//////////视图名
	//上架商品
	String product_is_up_table = "product_is_up";
	//下架商品
	String product_is_down_table = "product_is_down";
	//未删除分类
	String category_no_del_table = "category_no_del";
	
	/**
	 * 已删除分类
	 */
	int CATEGORY_DEL = 4;
	/**
	 * 未删除分类（默认值）
	 */
	int CATEGORY_DEFAULT = 0;
	
	/**
	 * mysql安装目录 + 账户登录某个数据库
	 */
	String backup_mysqldumpDir = "F:\\mysql-5.7.11-winx64\\bin\\mysqldump -h localhost -uadmin -p123456 ljp_store";
	/**
	 * 输出目录
	 */
	String backup_outSqlFile = "f:\\test.sql";
	/**
	 * mysql安装目录还原命令
	 */
	String restore_dir = "F:\\mysql-5.7.11-winx64\\bin\\mysql.exe -hlocalhost -uadmin -p123456 --default-character-set=utf8 ";
	/**
	 * 
	 */
	String restore_databaseName = "ljp_store";
	/**
	 * 装入的sql文件
	 */
//	String restore_inSqlFile = "f:\\test.sql";
}
