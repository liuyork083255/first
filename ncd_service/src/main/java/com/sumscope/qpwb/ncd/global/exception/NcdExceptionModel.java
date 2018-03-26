package com.sumscope.qpwb.ncd.global.exception;

/**
 * Created by liu.yang on 2018/1/8.
 */
public class NcdExceptionModel extends RuntimeException {
    private int code;
    private String msg;
    NcdExceptionModel(int code, String msg){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public static final NcdExceptionModel InitFailed = new NcdExceptionModel(10000, "系统尚未初始化");
    public static final NcdExceptionModel UserOrPwdError = new NcdExceptionModel(10001, "用户名或密码错误");
    public static final NcdExceptionModel UserNotExist = new NcdExceptionModel(10002, "用户名不存在");
    public static final NcdExceptionModel EmptyUserOrPassword = new NcdExceptionModel(10003, "用户名或密码错误");
    public static final NcdExceptionModel PasswordError = new NcdExceptionModel(10004, "密码错误");
    public static final NcdExceptionModel NotAuthorizedUser = new NcdExceptionModel(10005, "用户没有被分配权限");
    public static final NcdExceptionModel AuthorizationExpired = new NcdExceptionModel(10006, "用户权限已经失效");
    public static final NcdExceptionModel AllApiNotAuthorizedToUser = new NcdExceptionModel(10007, "用户未授权API使用权限");
    public static final NcdExceptionModel ThisApiNotAuthorizedToUser = new NcdExceptionModel(10008, "用户未授权本条API使用权限");
    public static final NcdExceptionModel NoSuchApi = new NcdExceptionModel(10009, "API不存在");
    public static final NcdExceptionModel ApiMultiAvailableVersions = new NcdExceptionModel(10010, "当前API有效版本不唯一");
    public static final NcdExceptionModel ConnectionStringInvalid = new NcdExceptionModel(10011, "数据库连接字符串不完整");
    public static final NcdExceptionModel ConnectionFailed = new NcdExceptionModel(10012, "验证数据库连接失败");
    public static final NcdExceptionModel ApiNoAvailableVersion = new NcdExceptionModel(10013, "无有效版本API");
    public static final NcdExceptionModel DuplicatedConnectionString = new NcdExceptionModel(10014, "数据库连接字符串不唯一");
    public static final NcdExceptionModel InvalidWhereCondition = new NcdExceptionModel(10015, "参数where条件无效");
    public static final NcdExceptionModel UserNotLoginYet = new NcdExceptionModel(10016, "用户尚未登录");
    public static final NcdExceptionModel NoDataSourceDb = new NcdExceptionModel(10017, "API没有指定数据源数据库ID");
    public static final NcdExceptionModel EmptyConnectionString = new NcdExceptionModel(10018, "数据库连接字符串为空");
    public static final NcdExceptionModel NoApiGroup = new NcdExceptionModel(10019, "API没有被分配组权限");
    public static final NcdExceptionModel DataQuotaOutOfRange = new NcdExceptionModel(10020, "所查询的数据量超出指定范围");
    public static final NcdExceptionModel InvalidDateFormat = new NcdExceptionModel(10021, "输入的日期格式不正确，格式：yyyyMMdd");
    public static final NcdExceptionModel StartDateShouldLessThanEnd = new NcdExceptionModel(10022, "参数startDate必须小于endDate");
    public static final NcdExceptionModel WhereConditionIsDisabled = new NcdExceptionModel(10023, "Where条件不可用");
    public static final NcdExceptionModel DataQuotaOutOfRangeToday = new NcdExceptionModel(10024, "已超出当天提取的最大数量");
    public static final NcdExceptionModel DataSourcePasswordError = new NcdExceptionModel(10025, "数据源密码错误");
    public static final NcdExceptionModel DataTypeConvertError = new NcdExceptionModel(10026, "数据格式不正确");
    public static final NcdExceptionModel StartPageMustGreaterThanZero = new NcdExceptionModel(10027, "取数页必须大于零");
    public static final NcdExceptionModel PageSizeMustGreaterThanZero = new NcdExceptionModel(10028, "每页数必须大于零");
    public static final NcdExceptionModel UnknownRequestBody = new NcdExceptionModel(10029, "未知的请求类型, 请检查Request.Body是否为空, 格式是否正确, 是否包含了无效参数。如果设置了整数类型属性, 如DataSourceId, StartPage, PageSize等, 则其值必须为整数");
    public static final NcdExceptionModel UnknownRequestBodyAsync = new NcdExceptionModel(10030, "未知的请求类型, 请检查Request.Body是否为空, 格式是否正确, 是否包含了无效参数。如果设置了ExpireInSeconds, 则其值必须为整数");
    public static final NcdExceptionModel IncorrectResultFormat = new NcdExceptionModel(10031, "ResultFormat必须是json或csv");
    public static final NcdExceptionModel InvalidQueryCondition = new NcdExceptionModel(10032, "请求中包含非法查询条件");
    public static final NcdExceptionModel InvalidJsonRequest = new NcdExceptionModel(10033, "无法解析请求中的Json字符串");
    public static final NcdExceptionModel StartPageMustNotLessThanZero = new NcdExceptionModel(10027, "取数页必须不小于零");
    public static final NcdExceptionModel PageSizeMustNotLessThanZero = new NcdExceptionModel(10028, "每页数必须不小于零");
    public static final NcdExceptionModel EmptyRequestBody = new NcdExceptionModel(10034, "请求参数为空");
    public static final NcdExceptionModel EmptyToken = new NcdExceptionModel(10035, "token为空");
    public static final NcdExceptionModel NoLogon = new NcdExceptionModel(10036, "用户未登录");
    public static final NcdExceptionModel LogonFailed = new NcdExceptionModel(10038, "登录失败");
    public static final NcdExceptionModel LogoutFailed = new NcdExceptionModel(10039, "登出失败");
    public static final NcdExceptionModel SaveDBFailed = new NcdExceptionModel(10040, "数据库保存失败");
    public static final NcdExceptionModel DeleteDBFailed = new NcdExceptionModel(10041, "数据库删除失败");
    public static final NcdExceptionModel FindDBFailed = new NcdExceptionModel(10042, "数据库查询失败");
    public static final NcdExceptionModel operateDBFailed = new NcdExceptionModel(10043, "数据库操作异常");
    public static final NcdExceptionModel UserNotMatchBrokers = new NcdExceptionModel(10044, "用户不符合5家经济商权限");




    public static final NcdExceptionModel ORDER_EXIST_WARNING = new NcdExceptionModel(10045, "订单已存在");



    public static final NcdExceptionModel InvalidStatusCondition = new NcdExceptionModel(10050, "status 参数无效,必须为:add|delete");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
