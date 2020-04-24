package com.simba.themestore.launch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.dialog.picker.SingleChooseManager;
import com.simba.base.utils.ResourceUtils;
import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.utils.img.GlideImageLoader;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/24
 * @Desc :
 */
public class WallPaperSettingActivity extends MyBaseActivity implements View.OnClickListener {
    private TextView tvTitle;
    private ImageView ivWallPaper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallpaper_setting;
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivWallPaper = findViewById(R.id.iv_wallpaper);

        tvTitle.setText("推荐");
        findViewById(R.id.btn_edit).setVisibility(View.GONE);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        GlideImageLoader.getInstance().loadResImage(this, ivWallPaper, R.mipmap.about_bg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.btn_submit: {
                String[] values = ResourceUtils.getStringArray(R.array.wallpaper_setting);
                SingleChooseManager singleChooseManager = new SingleChooseManager(WallPaperSettingActivity.this, values);
                singleChooseManager.show();
                singleChooseManager.setOnItemClickListener(new SingleChooseManager.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
            }
            break;
        }
    }
}
