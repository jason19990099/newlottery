package com.international.wtw.lottery.activity.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.OnlinePayChannel;
import com.international.wtw.lottery.json.QRModel;
import com.international.wtw.lottery.utils.QRCodeUtil;
import com.international.wtw.lottery.utils.SizeUtils;
import com.international.wtw.lottery.utils.SpannableBuilder;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class QRCodeActivity extends NewBaseActivity {

    public static final String RECHARGE_AMOUNT = "recharge_amount";
    public static final String PAY_CHANNEL = "pay_channel";
    public static final String PAY_URL = "pay_url";
    public static final String USER_ID = "user_id";
    public static final String PAY_ID = "pay_id";
    public static final String PAY_TYPE = "pay_type";
    public static final String GAME_NAME = "game_name";

    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.iv_top_logo)
    ImageView mIvTopLogo;
    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_bottom_tips)
    TextView mTvBottomTips;

    private Bitmap qrBitmap;
    private String mGameName;
    private String mAmount;
    private String mPayUrl;
    private String mUserId;
    private String mPayId;
    private String mPayType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        OnlinePayChannel payChannel = (OnlinePayChannel) getIntent().getSerializableExtra(PAY_CHANNEL);
        mAmount = getIntent().getStringExtra(RECHARGE_AMOUNT);
        mPayUrl = getIntent().getStringExtra(PAY_URL);
        mUserId = getIntent().getStringExtra(USER_ID);
        mPayId = getIntent().getStringExtra(PAY_ID);
        mPayType = getIntent().getStringExtra(PAY_TYPE);
        mGameName = getIntent().getStringExtra(GAME_NAME);

        mTvPayType.setText(payChannel.getTypeName() + "在线充值");
        mIvTopLogo.setImageResource(payChannel.largeLogoRes);
        mTvBottomTips.setText(getString(R.string.qr_tips1, payChannel.getTypeName()));

        showProgressDialog(getString(R.string.loading));
        onlinePay(mPayUrl, mUserId, mPayId, mAmount, mGameName);
    }

    private void onlinePay(String payUrl, String userId, String payId, String amount, String gameName) {
        HttpRequest.getInstance().onlinePay(this, payUrl, userId, payId, amount, gameName,
                null, new HttpCallback<QRModel>() {
                    @Override
                    public void onSuccess(QRModel data) {
                        dismissProgressDialog();
                        setSpannableText(data.getOrder(), amount);
                        if (!TextUtils.isEmpty(data.getCode_url())) {
                            //如果 二维码的有效链接 不为空
                            qrBitmap = QRCodeUtil.createQRCodeBitmap(data.getCode_url(), 400);
                            mIvQrCode.setImageBitmap(qrBitmap);
                        } else if (!TextUtils.isEmpty(data.getJump_url())) {
                            //如果没有二维码有效链接, 则取二维码的图片地址, 加载出来
                            loadImage(data.getJump_url());
                        }
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        Logger.d("code=" + msgCode + ", errorMsg=" + errorMsg);
                        dismissProgressDialog();
                        gotoWebOnlinePay();
                    }
                });
    }

    private void setSpannableText(String orderNo, String money) {
        Spannable orderNoSpan = SpannableBuilder.create(this)
                .append("订单号：", R.dimen.sp_14, R.color.text_light)
                .append(orderNo, R.dimen.sp_14, R.color.text_normal)
                .build();
        Spannable moneySpan = SpannableBuilder.create(this)
                .append("应付金额：", R.dimen.sp_15, R.color.text_light)
                .append(money, R.dimen.sp_16, R.color.color_primary)
                .append(" 元", R.dimen.sp_16, R.color.text_normal)
                .build();
        mTvOrderNo.setText(orderNoSpan);
        mTvMoney.setText(moneySpan);
    }

    private void loadImage(String url) {
        Picasso.with(this)
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .resize(SizeUtils.dp2px(180), SizeUtils.dp2px(180))
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Logger.d("onBitmapLoaded");
                        qrBitmap = bitmap;
                        mIvQrCode.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Logger.d("onBitmapFailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Logger.d("onPrepareLoad");
                    }
                });
    }

    private void gotoWebOnlinePay() {
        String bankName = getIntent().getStringExtra(WebOnlinePayActivity.BANK_NAME);
        boolean isShowBank = getIntent().getBooleanExtra(WebOnlinePayActivity.IsShow_Bank, false);
        Intent intent = new Intent(this, WebOnlinePayActivity.class);
        intent.putExtra(WebOnlinePayActivity.PAY_URL, mPayUrl);
        intent.putExtra(WebOnlinePayActivity.USER_ID, mUserId);
        intent.putExtra(WebOnlinePayActivity.PAY_ID, mPayId);
        intent.putExtra(WebOnlinePayActivity.PAY_TYPE, mPayType);
        intent.putExtra(WebOnlinePayActivity.IsShow_Bank, isShowBank);
        intent.putExtra(WebOnlinePayActivity.BANK_NAME, bankName);
        intent.putExtra(WebOnlinePayActivity.PAY_MONEY, mAmount);
        intent.putExtra(WebOnlinePayActivity.GAME_NAME, mGameName);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.ll_save_image, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_save_image:
                if (qrBitmap != null) {
                    if (saveImageToGallery(this, qrBitmap)) {
                        ToastDialog.success("保存成功").show(getSupportFragmentManager());
                    } else {
                        ToastDialog.error("保存失败").show(getSupportFragmentManager());
                    }
                }
                break;
        }
    }

    private boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "qr" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
