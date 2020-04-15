package com.simba.base.accountManager;

/*账号登陆管理类，实现在MemberCenter中使用content provider实现，各个app使用AccountManager调用便可
    AccountManager.getInstance().getLoginedAccount()
 */
public class AccountManager {

    static private AccountManager accountManager;

    public AccountManager getInstance (){
        if(accountManager == null ){
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    private AccountManager() {

    }

    //获取账号登陆状态
    public static boolean getLoginState(){
        return true;
    }

    //获取已登陆的账号
    public static String getLoginedAccount(){
        return "simba";
    }

    //获取已登陆账号的token
    public static String getAccountToken(){
        return "1234566778";
    }

    public static boolean isRealNameAuthentication(){
        return false;
    }
}
