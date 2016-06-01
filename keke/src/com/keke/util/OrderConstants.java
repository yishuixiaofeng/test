package com.keke.util;

public class OrderConstants {
	
	/** 用户商品类型：1-购物车*/
	public static final int TYPE_USER_GOOD_SHOP_CART = 1;
	/** 用户商品类型：2-我的订单*/
	public static final int TYPE_USER_GOOD_MY_ORDER = 2;
	/** 用户商品类型：3-我的收藏*/
	public static final int TYPE_USER_GOOD_MY_STORE = 3;
	
	
	/** session保存的用户登录key值 */
	public static final String SESSION_LOGON_USER = "session_logon_user";
	
	/** order系统默认系统管理员id */
	public static final int SYS_ADMIN_USER_ID = 1;
	
	/** 标示controller调用哪个不同的service底层方法。 当一个controller需要调用不同的service方法时使用 */
	public static final int SYS_SERVICE_METHOD = 1;
	
	/** order调用tyfo接口时验证管理员身份的f_id */
	public static final int SYS_ADMIN_F_ID = 1;
	/** order调用tyfo接口时验证管理员身份的token_key */
	public static final String SYS_ADMIN_TOKEN_KEY = "e50defeb586e4cea9951067a5fe7d032";
	
	/** tyfo中系统角色常量*/
	/** TYFO_ROLE_SYSTEM_ADMIN_ID: 系统管理员id*/
	public static final int TYFO_ROLE_SYSTEM_ADMIN_ID = 1073676289;
	/** TYFO_ROLE_INFO_ADMIN_ID: 信息筛选员 */
	public static final int TYFO_ROLE_INFO_ADMIN_ID = 1073676290;
	/** TYFO_ROLE_USER_ID: 普通用户 */
	public static final int TYFO_ROLE_USER_ID = 1073676288;
	
	
	
	/** 状态-正常*/
	public static final int STATUS_NORMAL = 0;
	/** 状态-不可用*/
	public static final int STATUS_UNAVAILABLE = -1;
	
	
	
	/** KEY: tyfo的userId f_ty_user_id*/
	public static final String KEY_F_TY_USER_ID = "f_ty_user_id";
	/** KEY: 主键id */
	public static final String KEY_ID = "id";
	/** KEY: 外键f_user_id */
	public static final String KEY_F_USER_ID = "f_user_id";
	/** KEY: 外键f_role_id */
	public static final String KEY_F_ROLE_ID = "f_role_id";
	/** KEY: 外键 f_server_id*/
	public static final String KEY_F_SERVER_ID = "f_server_id";
	/** KEY: 关键字 */
	public static final String KEY_KEY_WORD = "keyword";
	/** KEY: 偏移量 */
	public static final String KEY_OFFSET = "offset";
	/** KEY: 每页的记录数 */
	public static final String KEY_PAGE_COUNT = "pageCount";
	/** KEY: 用户名 */
	public static final String KEY_USERNAME = "name";
	/** KEY: 用户密码 */
	public static final String KEY_PWDHASH = "pwd";
	/** KEY: 状态 */
	public static final String KEY_STATUS = "status";
	/** KEY: 操作人id*/
	public static final String KEY_OPERATOR = "operator_id";
	/** KEY: 操作时间 */
	public static final String KEY_OPERATE_TIME = "operate_time";
	/** KEY: in还是not in 的标志 */
	public static final String KEY_IN_FLAG = "inFlag";
	
	
	/** SQL的in查询  标志*/
	public static final int SQL_IN_FLAG = 0;
	/** SQL的not in查询 标志*/
	public static final int SQL_IN_NOT_FLAG = 1;
	
	
	/** 分页查询的默认每页条目数*/
	/** PAGE_COUNT_LARGER: 较大值20条 */
	public static final int PAGE_COUNT_LARGER = 20;
	/** PAGE_COUNT_MEDIUM: 中间值10条 */
	public static final int PAGE_COUNT_MEDIUM = 10;
	/** PAGE_COUNT_LESSER: 较小值5条 */
	public static final int PAGE_COUNT_LESSER = 5;
	/** PAGE_COUNT_TINY: 极小值3条 */
	public static final int PAGE_COUNT_TINY = 3;
	
	/** 系统角色常量 */
	/** ROLE_SYSTEM_ADMIN_ID: 系统管理员id*/
	public static final int ROLE_SYSTEM_ADMIN_ID = 1;
	/** ROLE_CS_ADMIN_ID: 客服管理员 */
	public static final int ROLE_CS_ADMIN_ID = 2;
	/** ROLE_CUSTOMER_SERVICE_ID: 客服专员id */
	public static final int ROLE_CUSTOMER_SERVICE_ID = 3;
	
	
	/** 连续错误登录3次的限制时间*/
	public static final long LOGON_ERROR_LIMIT_TIME = 1000*60;
}
