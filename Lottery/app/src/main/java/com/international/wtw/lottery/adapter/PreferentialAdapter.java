package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.json.PreferentialProBean;
import com.international.wtw.lottery.utils.ImageUtil;
import com.international.wtw.lottery.utils.RoundedCornersTransformation;
import com.international.wtw.lottery.utils.SizeUtils;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by XIAOYAN on 2017/10/4.
 */

public class PreferentialAdapter extends BaseAdapter {

    private Context context;
    private List<PreferentialProBean> list;

    public PreferentialAdapter(Context context, List<PreferentialProBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_preferentia, null);
            viewHolder.img_prefer_more = (ImageView) view.findViewById(R.id.img_prefer_more);
            viewHolder.tv_prefer_more = (TextView) view.findViewById(R.id.tv_prefer_more);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String url = Constants.BASE_URL + list.get(position).getImage();
        Picasso.with(context).load(url)
                .transform(new RoundedCornersTransformation(SizeUtils.dp2px(5), 0, RoundedCornersTransformation.CornerType.TOP))
                .into(viewHolder.img_prefer_more);
        //        asyncloadImage(viewHolder.img_prefer_more, url);
        viewHolder.tv_prefer_more.setText(list.get(position).getText());

        return view;
    }

    class ViewHolder {
        ImageView img_prefer_more;
        TextView tv_prefer_more;
    }

    private void asyncloadImage(final ImageView imageView, final String uri) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (imageView != null && uri != null) {
                        Bitmap roundedCornerBitmap = ImageUtil.getRoundedCornerBitmap(bitmap, 5);
                        imageView.setImageBitmap(roundedCornerBitmap);
                    }
                }
            }
        };
        // 子线程，开启子线程去下载或者去缓存目录找图片，并且返回图片在缓存目录的地址
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //这个URI是图片下载到本地后的缓存目录中的URI
                    if (uri != null) {
                        Bitmap bitmap = getBitmap(uri);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    public Bitmap getBitmap(String url) {
        //        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap = null;
        if (url != null) {
            InputStream in = null;
            BufferedOutputStream out = null;
            try {
                //读取图片输入流
                in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
                //准备输出流
                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                out = new BufferedOutputStream(dataStream, 2 * 1024);
                byte[] b = new byte[1024];
                int read;
                //将输入流变为输出流
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }
                out.flush();
                //将输出流转换为bitmap
                byte[] data = dataStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                data = null;
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                //                return bitmap;
            }
        }
        return bitmap;
    }

}
