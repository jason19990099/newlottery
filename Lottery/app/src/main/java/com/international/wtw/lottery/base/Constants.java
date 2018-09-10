package com.international.wtw.lottery.base;


public class Constants {
    public static final String BASE_URL = "http://www.7557b.com/";//此行链接不允许改动
    public static final String LOTTERY_WEBSITE = "https://pk10tv.com/";//开奖网
    public static final String GAME_WEBSITE = "http://wap.alcp66.com/#/todayRule";//游戏规则
    public static final String USER_MSGBYID = "user/getMsgById";//消息详情
    public static final String GET_VERSION_ANDROID = "systems/getVersionAndroid";
    public static final String VERIFICATIONCODE = "sms/getVerificationCode";
    public static final String SMSAMOUNT = "sms/bindMobilePhone";
    public static final String BINDMOBILEPHONE = "sms/unBindMobilePhone";
    public static final String CHANGEMOBILEPHONE = "sms/changeMobilePhone";
    public static final String CHANGELOGINPSD = "sms/changeLoginPsd";
    public static final String CHANGEPAYPSD = "sms/changePayPsd";
    public static final String SEND_VERIFICATIONCODE = "Systems/sendVerificationCode";//忘记密码 验证码
    public static final String MAKESURE_VERIFICATIONCODE = "Systems/makeSureVerificationCode";//验证验证码
    public static final String CHANGEPASSWD = "Systems/changepasswd";//忘记密码
    public static final int TIME_OUT = 10;//请求超时时间
    public static final String GET_BETTING_HISTORY = "getinfo/getBettingHistory";

    public class LOTTERY_TYPE {
        public static final int PJ_PK_10 = 51;//北京赛车
        public static final int CJ_LOTTERY = 2;//重庆时时彩
        public static final int LUCKY_FLY_LOTTERY = 171;//幸运飞艇
        public static final int GD_HAPPY_LOTTERY = 3;//广东快乐十分
        public static final int CJ_LUCKY_LOTTERY = 47;//重庆幸运农场
        public static final int ROME_LOTTERY = 46;//罗马时时彩(自己开奖的)
        public static final int HK_MARK_SIX_LOTTERY = 69;//香港六合彩
        public static final int LUCKY_28_LOTTERY = 160;  //PC 蛋蛋
        public static final int JS_QUICK_3 = 172;  //江苏快3
        public static final int VENICE_SPEEDBOAT = 210;  //威尼斯赛艇
        public static final int ONLINE_SERVICE = 0;//真人视讯
        public static final int REAL_VIDEO = 1;//在线客服
        public static final int REAL_MORE = 121212;
        public static final int SPEED_CAR = 240;//极速赛车
        public static final int SPEED_SSC = 250;//极速时时彩
        public static final int HORSE_88 = 260;//88赛马
        public static final int SPEED_MARK_SIX = 270;//极速六合彩
    }


    public class PK_10_PLAY_TYPE_SERVER {
        public static final int DOUBLE_SIDE = 0;
        public static final int GOLD_SILVER_COMBINE = 1;
        public static final int ONE_TO_FIVE = 2;
        public static final int SIX_TO_TEN = 3;
    }

    public class LUCKY_28_PLAY_TYPE_SERVER {
        public static final int MIX_TYPE = 0;
        public static final int SPECIAL_CODE = 1;
    }

    public class CJ_LOTTERY_PLAY_TYPE {
        public static final int DOUBLE_SIDE = 0;
        public static final int NUMBER_SIDE = 1;
        public static final int FRONT_MINDDLE_BACK = 2;
    }

    public class GD_HAPPY_PLAY_TYPE {
        public static final int DOUBLE_SIDE = 0;
        public static final int FIRST_POINT = 1;
        public static final int SECOND_POINT = 2;
        public static final int THIRD_POINT = 3;
        public static final int FORTH_POINT = 4;
        public static final int FIFTH_POINT = 5;
        public static final int SIXTH_POINT = 6;
        public static final int SEVENTH_POINT = 7;
        public static final int EIGHTH_POINT = 8;
        public static final int JOINT_MARK = 9;
        public static final int ONE_TO_FOUR = 10;
        public static final int FIVE_TO_EIGHT = 11;
    }

    public static String getErrorCodeInfo(String errorCode) {
        if (null == errorCode) {
            return "未知错误";
        }
        String info = "";
        boolean needErrorCode = false;
        switch (errorCode) {
            case "2001":
                info = "您的密码输入有误，请重试！";
                break;
            case "2002":
                info = "该用户不存在，请检查后重试！";
                break;
            case "2003":
                needErrorCode = true;
                info = "因网络原因，您的注册未成功，请重试！";
                break;
            case "2004":
                info = "该用户名已被占用，请重新输入！";
                break;
            case "2005":
                info = "该账户已被停用，如有疑问，请联系客服！";
                break;
            case "2006":
                info = "恭喜您，操作成功！";
                break;
            case "2007":
                info = "您的支付密码输入有误，请重新输入！";
                break;
            case "2008":
                info = "用户名必须为字母开头的6~15位字母和数字组合，请重新输入！";
                break;
            case "2009":
                info = "密码必须为6~15位字母和数字组合，请重新输入！";
                break;
            case "2010":
                info = "您的支付密码过于简单，请重新输入！";
                break;
            case "2011":
                info = "禁止使用同名注册，请重新输入！";
                break;
            case "2012":
                info = "请检查您的手机号码格式是否正确，谢谢！";
                break;
            case "2013":
                info = "该账户已被冻结，如有疑问，请联系客服！";
                break;
            case "2014":
                info = "此IP已被注册";
                break;
            case "2018":
                info = "您已很长时间未修改密码，为了您的账号安全，请修改密码";
                break;
            case "3001":
                needErrorCode = true;
                info = "下注内容未开放，请尝试其他游戏！";
                break;
            case "3002":
                needErrorCode = true;
                info = "下注内容未开放，请尝试该游戏的其他玩法！";
                break;
            case "3003":
                needErrorCode = true;
                info = "抱歉，网络原因导致下注未成功，请重新尝试！";
                break;
            case "3004":
                info = "单双玩法一期仅能下注一次，请下期再试！";
                break;
            case "4001":
                info = "登录信息已失效，请重新登录";
                break;
            case "4003":
                info = "系统维护中，请稍后重试";
                break;
            case "5001":
                info = "请检查您的投注内容和金额是否正确！";
                break;
            case "5002":
                needErrorCode = true;
                info = "该游戏正在封盘，可前往其他游戏！";
                break;
            case "5003":
                needErrorCode = true;
                info = "因网络原因，本次投注未成功，请稍后重试！";
                break;
            case "5004":
                info = "您当前余额不足，可立即前往充值！";
                break;
            case "5005":
                info = "请检查您的金额输入是否正确，谢谢！";
                break;
            case "5006":
                info = "您的操作过于频繁，请稍后再试！";
                break;
            case "5007":
                info = "请您在上一笔交易完成后再试，谢谢！";
                break;
            case "5008":
                info = "网络繁忙，请稍后再试！";
                break;
            case "5009":
                info = "您的操作过于频繁，请稍后再试！";
                break;
            case "5010":
                info = "请不要重复提交，谢谢！";
                break;
            case "6001":
                info = "您选择的号码过少，请确认后再进行下注！";
                break;
            case "6002":
                info = "您选择的号码过多，请确认后再进行下注！";
                break;
            case "6003":
                info = "您的下注金额低于最低投注额，请重新输入！";
                break;
            case "6004":
                info = "您的今日提款次数已达上限，请明天再来！";
                break;
            case "60041":
                info = "请您在上一笔交易完成后再试，谢谢！";
                break;
            case "7001":
                needErrorCode = true;
                info = "操作失败，请检查您的网络，并稍后再试！";
                break;
            case "7002":
                needErrorCode = true;
                info = "您的订单查询操作不成功，请刷新后再试！";
                break;
            case "7003":
                needErrorCode = true;
                info = "您的额度转换操作不成功，请刷新后再试！";
                break;
            case "7004":
                info = "您的订单查询不成功，请刷新后再试！";
                break;
            case "7005":
                info = "输入的验证码有误，请重新输入";
                break;
            case "8001":
                info = "输入的手机号码格式有误，请重新输入";
                break;
            case "8002":
                info = "该用户已经绑定过手机号";
                break;
            case "8003":
                needErrorCode = true;
                info = "更新手机号码失败";
                break;
            case "8004":
                info = "手机号未绑定,无法进行此操作";
                break;
            case "8005":
                info = "手机号与当前绑定的不一致";
                break;
            case "8007":
                needErrorCode = true;
                info = "领取彩金失败，数据库错误";
                break;
            case "8008":
                needErrorCode = true;
                info = "您的支付密码修改不成功，请重试！";
                break;
            case "8009":
                needErrorCode = true;
                info = "您的登录密码修改不成功，请重试！";
                break;
            case "8010":
                info = "该网站未开通手机短信功能";
                break;
            case "8011":
                info = "输入的手机号码格式有误，请重新输入";
                break;
            case "8012":
                info = "网络连接有误，请检查您的网络！";
                break;
            case "8013":
                info = "验证码输入错误，请重新输入";
                break;
            case "8014":
                info = "验证码已失效，请重新获取验证码";
                break;
            case "8015":
                needErrorCode = true;
                info = "解绑失败";
                break;
            case "8016":
                info = "该手机号已被绑定过";
                break;
            case "9001":
                needErrorCode = true;
                info = "该手机号已被绑定过";
                break;
            case "9002":
                info = "签到不成功，请稍后重试！";
                break;
            case "9003":
                info = "您的剩余抽奖次数为0，请下次再来！";
                break;
            case "9004":
                info = "您的剩余抽奖次数为0，请下次再来！";
                break;
            case "9005":
                info = "您的账户暂未达到抽奖要求，请留意抽奖规则，谢谢！";
                break;
            case "10011":
            case "10012":
            case "10013":
            case "10014":
            case "10015":
            case "10016":
                needErrorCode = true;
                info = "您的网络连接超时，请稍后再试！";
                break;
            default:
                needErrorCode = false;
        }
        if (needErrorCode) {
            return String.format("%s\n(错误码：%s)", info, errorCode);
        }
        return info;
    }

}
