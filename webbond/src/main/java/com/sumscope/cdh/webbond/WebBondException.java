package com.sumscope.cdh.webbond;

public class WebBondException extends RuntimeException
{

    private static final long serialVersionUID = 8037822434079016951L;
    private int code;

    public WebBondException(int code, String message)
    {
        super(message);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public static class PredefinedExceptions
    {
        public static int OtherRuntimeErrorId = 90000;

        public static final WebBondException InitFailed = new WebBondException(10000, "系统尚未初始化");
        public static final WebBondException UserOrPwdError = new WebBondException(10001, "用户名或密码错误");
        public static final WebBondException UserNotExist = new WebBondException(10002, "用户名不存在");
        public static final WebBondException EmptyUserOrPassword = new WebBondException(10003, "用户名或密码错误");
        public static final WebBondException PasswordError = new WebBondException(10004, "密码错误");
        public static final WebBondException NotAuthorizedUser = new WebBondException(10005, "用户没有被分配权限");
        public static final WebBondException AuthorizationExpired = new WebBondException(10006, "用户权限已经失效");
        public static final WebBondException AllApiNotAuthorizedToUser = new WebBondException(10007, "用户未授权API使用权限");
        public static final WebBondException ThisApiNotAuthorizedToUser = new WebBondException(10008, "用户未授权本条API使用权限");
        public static final WebBondException NoSuchApi = new WebBondException(10009, "API不存在");
        public static final WebBondException ApiMultiAvailableVersions = new WebBondException(10010, "当前API有效版本不唯一");
        public static final WebBondException ConnectionStringInvalid = new WebBondException(10011, "数据库连接字符串不完整");
        public static final WebBondException ConnectionFailed = new WebBondException(10012, "验证数据库连接失败");
        public static final WebBondException ApiNoAvailableVersion = new WebBondException(10013, "无有效版本API");
        public static final WebBondException DuplicatedConnectionString = new WebBondException(10014, "数据库连接字符串不唯一");
        public static final WebBondException InvalidWhereCondition = new WebBondException(10015, "参数where条件无效");
        public static final WebBondException UserNotLoginYet = new WebBondException(10016, "用户尚未登录");
        public static final WebBondException NoDataSourceDb = new WebBondException(10017, "API没有指定数据源数据库ID");
        public static final WebBondException EmptyConnectionString = new WebBondException(10018, "数据库连接字符串为空");
        public static final WebBondException NoApiGroup = new WebBondException(10019, "API没有被分配组权限");
        public static final WebBondException DataQuotaOutOfRange = new WebBondException(10020, "所查询的数据量超出指定范围");
        public static final WebBondException InvalidDateFormat = new WebBondException(10021, "输入的日期格式不正确，格式：yyyyMMdd");
        public static final WebBondException StartDateShouldLessThanEnd = new WebBondException(10022, "参数startDate必须小于endDate");
        public static final WebBondException WhereConditionIsDisabled = new WebBondException(10023, "Where条件不可用");
        public static final WebBondException DataQuotaOutOfRangeToday = new WebBondException(10024, "已超出当天提取的最大数量");
        public static final WebBondException DataSourcePasswordError = new WebBondException(10025, "数据源密码错误");
        public static final WebBondException DataTypeConvertError = new WebBondException(10026, "数据格式不正确");
        public static final WebBondException StartPageMustGreaterThanZero = new WebBondException(10027, "取数页必须大于零");
        public static final WebBondException PageSizeMustGreaterThanZero = new WebBondException(10028, "每页数必须大于零");
        public static final WebBondException UnknownRequestBody = new WebBondException(10029, "未知的请求类型, 请检查Request.Body是否为空, 格式是否正确, 是否包含了无效参数。如果设置了整数类型属性, 如DataSourceId, StartPage, PageSize等, 则其值必须为整数");
        public static final WebBondException UnknownRequestBodyAsync = new WebBondException(10030, "未知的请求类型, 请检查Request.Body是否为空, 格式是否正确, 是否包含了无效参数。如果设置了ExpireInSeconds, 则其值必须为整数");
        public static final WebBondException IncorrectResultFormat = new WebBondException(10031, "ResultFormat必须是json或csv");
        public static final WebBondException InvalidQueryCondition = new WebBondException(10032, "请求中包含非法查询条件");
        public static final WebBondException InvalidJsonRequest = new WebBondException(10033, "无法解析请求中的Json字符串");
        public static final WebBondException StartPageMustNotLessThanZero = new WebBondException(10027, "取数页必须不小于零");
        public static final WebBondException PageSizeMustNotLessThanZero = new WebBondException(10028, "每页数必须不小于零");

        public static final WebBondException EmptyRequestBody = new WebBondException(10034, "请求参数为空");
        public static final WebBondException EmptyToken = new WebBondException(10035, "token为空");
        public static final WebBondException NoLogon = new WebBondException(10036, "用户未登录");
        public static final WebBondException LogonFailed = new WebBondException(10038, "登录失败");
        public static final WebBondException LogoutFailed = new WebBondException(10039, "登出失败");
    }

}
