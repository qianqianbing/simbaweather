package com.simba.violationenquiry.ui.view;

import android.app.Activity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.base.utils.Tuple;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.ui.itemdecoration.SpaceItemDecoration;
import com.simba.violationenquiry.ui.view.adapter.ProvicesAdapter;
import com.simba.violationenquiry.utils.PopupHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc : 键盘控件
 */
public class ProvincesKeyBoardView extends LinearLayout {
    private RecyclerView recyclerView;
    private TextView tvKey;
    private List<String> mData;
    private String provinceStr = "京沪津渝鲁冀晋蒙辽吉黑苏浙皖闽赣豫湘鄂粤桂琼川贵云藏陕甘青宁新港澳台";
    private Activity activity;
    private ProvicesAdapter adapter;

    public ProvincesKeyBoardView(Activity context, TextView tvKey) {
        this(context, null, 0, tvKey);

    }

    public ProvincesKeyBoardView(final Activity activity, @Nullable AttributeSet attrs, int defStyleAttr, TextView tvKey) {
        super(activity, attrs, defStyleAttr);
        this.tvKey = tvKey;
        this.activity = activity;
        LayoutInflater.from(activity).inflate(R.layout.view_keyboard, this);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 12);
        recyclerView.setLayoutManager(gridLayoutManager);
        Tuple<List<String>, Integer> tuple = mkEntitiesOf(provinceStr);
        mData = tuple._1;
        adapter = new ProvicesAdapter(mData, onItemClickListener, tuple._2);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(activity, 30));

    }

    private ProvicesAdapter.OnItemClickListener onItemClickListener = new ProvicesAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            tvKey.setText(mData.get(position));
            PopupHelper.dismiss(activity);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private Tuple<List<String>, Integer> mkEntitiesOf(String keysStr) {
        List<String> mList = new ArrayList<>();
        String sP = tvKey.getText().toString();
        int pos = -1;
        boolean flag = !TextUtils.isEmpty(sP);
        for (int i = 0; i < keysStr.length(); i++) {
            String keyChar = String.valueOf(keysStr.charAt(i));
            if (flag && sP.equals(keyChar)) {
                pos = i;
            }

            mList.add(keyChar);
        }

        return new Tuple<>(mList, pos);
    }
}
