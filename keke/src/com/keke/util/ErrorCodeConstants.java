package com.keke.util;

public class ErrorCodeConstants {
	
	//系统模块
	/** ERROR: sys.0000-成功 */
	public static final String ERROR_SYS_SUCCESS = "sys.0000";
	/** ERROR: sys.0001-系统错误 */
	public static final String ERROR_SYS_ERROR = "sys.0001";
	/** ERROR: sys.0002-没有权限 */
	public static final String ERROR_SYS_NO_PERMISSION = "sys.0002";
	/** ERROR: sys.0003-用户未登录 */
	public static final String ERROR_SYS_USER_NOT_LOGON = "sys.0003";
	/** ERROR: sys.0004-参数id为空 */
	public static final String ERROR_SYS_ID_IS_NULL = "sys.0004";
	/** ERROR: sys.0005-参数name为空 */
	public static final String ERROR_SYS_NAME_IS_NULL = "sys.0005";
	/** ERROR: sys.0006-数据已经被使用 */
	public static final String ERROR_SYS_DATA_IS_USED = "sys.0006";
	/** ERROR: sys.0007-tyfo调用失败 */
	public static final String ERROR_SYS_TYFO_API_FAIL = "sys.0007";
	/** ERROR: sys.0008-数据已存在 */
	public static final String ERROR_SYS_DATA_IS_EXIT = "sys.0008";
	
	//user模块
	/** ERROR: user.1001-用户id为空 */
	public static final String ERROR_USER_ID_IS_NULL = "user.1001";
	/** ERROR: user.1002-用户未找到 */
	public static final String ERROR_USER_IS_NOT_FOUND = "user.1002";
	/** ERROR: user.1003-用户名或密码错误 */
	public static final String ERROR_USER_NAME_PWD_WRONG = "user.1003";
	
	//role模块
	/** ERROR: role.1004-角色未找到 */
	public static final String ERROR_ROLE_IS_NOT_FOUND = "user.1004";
	/** ERROR: role.1005-角色id为空 */
	public static final String ERROR_ROLE_ID_I_NULL = "user.1005";
	/** ERROR: role.1005-角色name为空 */
	public static final String ERROR_ROLE_NAME_I_NULL = "user.1006";
	
	//userRole
	/**ERROR: userRoles.1007-用户角色表未找到*/
	public static final String ERROR_USERROLES_IS_NOT_FOUND = "user.1007";
	/** ERROR: userRoles.1008-用户角色id为空 */
	public static final String ERROR_USERROLES_ID_IS_NULL = "user.1008";
	/** ERROR: userRoles.1009-用户角色name为空 */
	public static final String ERROR_USERROLES_NAME_IS_NULL = "user.1009";
	
	//roleMenus
	/**ERROR: roleMenus.1010-角色模块表未找到*/
	public static final String ERROR_ROLEMENUS_IS_NOT_FOUND = "user.1010";
	/** ERROR: roleMenus.1011-角色模块id为空 */
	public static final String ERROR_ROLEMENUS_ID_IS_NULL = "user.1011";
	/** ERROR: roleMenus.1012-角色模块name为空 */
	public static final String ERROR_ROLEMENUS_NAME_IS_NULL = "user.1012";
	
	//server
	/**ERROR: server.1013-服务器表未找到*/
	public static final String ERROR_SERVER_IS_NOT_FOUND = "user.1013";
	/** ERROR: server.1014-服务器id为空 */
	public static final String ERROR_SERVER_ID_IS_NULL = "user.1014";
	/** ERROR: server.1015-服务器name为空 */
	public static final String ERROR_SERVER_NAME_IS_NULL = "user.1015";
	
	//UserServer
	/**ERROR: userServer.1016-用户授权服务器表未找到*/
	public static final String ERROR_USERSERVER_IS_NOT_FOUND = "user.1016";
	/** ERROR: userServer.1017-用户授权服务器id为空 */
	public static final String ERROR_USERSERVER_ID_IS_NULL = "user.1017";
	/** ERROR: userServer.1018-用户授权服务器name为空 */
	public static final String ERROR_USERSERVER_NAME_IS_NULL = "user.1018";
	
	//UserGroups
	/**ERROR: userGroups.1019-用户分组表未找到*/
	public static final String ERROR_USERGROUPS_IS_NOT_FOUND = "user.1019";
	/** ERROR: userGroups.1020-用户分组表id为空 */
	public static final String ERROR_USERGROUPS_ID_IS_NULL = "user.10120";
	/** ERROR: userGroups.1021-用户分组name为空 */
	public static final String ERROR_USERGROUPS_NAME_IS_NULL = "user.1021";
		
	//UserIdentify
	/**ERROR: userIdentify.1022-用户认证表未找到*/
	public static final String ERROR_USERIDENTIFY_IS_NOT_FOUND = "user.1022";
	/** ERROR: userIdentify.1023-用户认证id为空 */
	public static final String ERROR_USERIDENTIFY_ID_IS_NULL = "user.1023";
	/** ERROR: userIdentify.1024-用户认证name为空 */
	public static final String ERROR_USERIDENTIFY_NAME_IS_NULL = "user.1024";
	/** ERROR: user.1025-用户原始密码错误 */
	public static final String ERROR_USER_OLD_PWD_WRONG = "user.1025";
    //SysMenu
	/** ERROR: sysMenu.0101-模块管理表未找到*/
	public static final String ERROR_SYSMENU_IS_NOT_FOUND = "sys.0101";
	/** ERROR: sysMenu.0102-模块管理id为空*/
	public static final String ERROR_SYSMENU_ID_IS_NULL = "sys.0102";
	/** ERROR: sysMenu.0103-模块管理name为空*/
	public static final String ERROR_SYSMENU_NAME_IS_NULL = "sys.0103";
	//SysParam
	/** ERROR: sysParam.0104-系统配置表表未找到*/
	public static final String ERROR_SYSPARAM_IS_NOT_FOUND = "sys.0104";
	/** ERROR: sysParam.0105-系统配置id为空*/
	public static final String ERROR_SYSPARAM_ID_IS_NULL = "sys.0105";
	/** ERROR: sysParam.0106-系统配置name为空*/
	public static final String ERROR_SYSPARAM_NAME_IS_NULL = "sys.0106";
}
