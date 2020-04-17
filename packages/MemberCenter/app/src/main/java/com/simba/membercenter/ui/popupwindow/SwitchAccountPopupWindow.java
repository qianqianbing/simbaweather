package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.simba.membercenter.R;
import com.simba.membercenter.DB.AccountBean;

import java.util.List;

public class SwitchAccountPopupWindow extends PopupWindow implements View.OnClickListener {
    private static String TAG = "SwitchAccountPopupWindow";
    private TextView tv_cancel, tv_switchaccount;
    private ListView lv_account;
    private List<AccountBean> accountBeanList;
    private LayoutInflater mInflater;
    private AccountListViewAdapter accountListViewAdapter ;
    AccountSwitchHander accountSwitchHander;

    public void setAccountSwitchHander(AccountSwitchHander accountSwitchHander) {
        this.accountSwitchHander = accountSwitchHander;
    }

    public SwitchAccountPopupWindow(Context context, List<AccountBean> accountBeans) {
        super(context);
        this.accountBeanList = accountBeans;
        init(context);
    }

    private void init(Context context) {
         mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mContentView = mInflater.inflate(R.layout.popup_switchaccount,null);
        //设置View
        setContentView(mContentView);
        //设置宽与高
        setWidth(600);
        setHeight(430);
        tv_cancel = mContentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_switchaccount = mContentView.findViewById(R.id.tv_switchaccount);
        tv_switchaccount.setOnClickListener(this);
        lv_account = mContentView.findViewById(R.id.lv_account);
        accountListViewAdapter = new AccountListViewAdapter();
        lv_account.setAdapter(accountListViewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_switchaccount:
                accountListViewAdapter.getSelectIndex();
                dismiss();
                if(accountSwitchHander!=null){
                    accountSwitchHander.accountSwitch(accountBeanList.get(accountListViewAdapter.getSelectIndex()));
                }
                break;
        }
    }

    static int NoSelected = -1;
    class AccountListViewAdapter extends BaseAdapter implements View.OnClickListener{

        private int selectIndex = NoSelected;

        public int getSelectIndex() {
            return selectIndex;
        }

        @Override
        public int getCount() {
            return accountBeanList == null? 0:accountBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return accountBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.listview_item_account,null);
            TextView tv_account = view.findViewById(R.id.tv_account);
            tv_account.setText(accountBeanList.get(position).getNickName());

            Button bt_account_select = view.findViewById(R.id.bt_account_select);
            bt_account_select.setOnClickListener(this);
            bt_account_select.setTag(position);
            if(selectIndex == position){
                bt_account_select.setSelected(true);
            }
            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_account_select:
                    int clickIndex = (int)v.getTag();

                    Log.e(TAG, "v.getTag " + clickIndex);
                    if(selectIndex == clickIndex){
                        selectIndex = NoSelected;
                    }else {
                        selectIndex = clickIndex;
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    public interface AccountSwitchHander{
        void accountSwitch(AccountBean accountBean);
    }
}
