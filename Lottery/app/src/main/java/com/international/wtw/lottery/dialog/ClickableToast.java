package com.international.wtw.lottery.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.listener.DefaultListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * Created by lin on 2018/1/9.
 * https://github.com/bboylin/UniversalToast
 */

public class ClickableToast implements View.OnClickListener {
    @NonNull
    private final Toast toast;
    @NonNull
    private final Context context;
    @Type
    private final int type;
    private static final String TAG = ClickableToast.class.getSimpleName();

    //通用和强调toast，用原生toast实现
    public static final int NORMAL = 0;
    //可点击toast，用自定义window实现
    public static final int CLICKABLE = 1;

    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    private static final int TIME_LONG = 3500;
    private static final int TIME_SHORT = 2000;

    @IntDef({NORMAL, CLICKABLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @IntDef({LENGTH_LONG, LENGTH_SHORT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private View view;
    private int duration;
    private Handler handler;

    private OnClickListener listener = new DefaultListener();

    private ClickableToast(@NonNull Context context, @NonNull Toast toast, @Type int type, View view) {
        this.context = context;
        this.toast = toast;
        this.type = type;

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        FrameLayout rootView = (FrameLayout) view.findViewById(R.id.fl_toast_root);
        rootView.getLayoutParams().width = windowManager.getDefaultDisplay().getWidth();
        rootView.getLayoutParams().height = windowManager.getDefaultDisplay().getHeight();
        rootView.setFitsSystemWindows(true);

        TextView layout = (TextView) view.findViewById(R.id.btn_ok);
        layout.setVisibility(View.VISIBLE);
        layout.setOnClickListener(this);

        if (type == CLICKABLE) {
            this.view = view;
            duration = toast.getDuration() == ClickableToast.LENGTH_LONG ? TIME_LONG : TIME_SHORT;
            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = android.R.style.Animation_Toast;
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
            params.setTitle("Toast");
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    | WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
            handler = new Handler();
        }

        toast.setView(view);
    }

    public static ClickableToast makeText(@NonNull Context context, @NonNull String text, @Duration int duration) {
        return makeText(context, text, duration, CLICKABLE);
    }

    public static ClickableToast makeText(@NonNull Context context, @NonNull String text, @Duration int duration, @Type int type) {
        Toast toast = Toast.makeText(context, text, duration);
        int layoutId = R.layout.layout_dialog_common;
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        ((TextView) view.findViewById(R.id.tv_message)).setText(text);

        if (Build.VERSION.SDK_INT < 26 && type != CLICKABLE) {
            setContext(view, new SafeToastContext(context));
        }
        return new ClickableToast(context, toast, type, view);
    }

    private static void setContext(@NonNull View view, @NonNull Context context) {
        try {
            Field field = View.class.getDeclaredField("mContext");
            field.setAccessible(true);
            field.set(view, context);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param duration it's not recommanded for you to
     *                 set duration as values except for 2000L,3500L.
     */
    @Deprecated
    public ClickableToast setDuration(int duration) {
        if (type != CLICKABLE) {
            toast.setDuration(duration);
        } else {
            this.duration = duration;
        }
        return this;
    }

    public ClickableToast setIcon(@DrawableRes int resId) {
        ImageView imageView;
        if (type == CLICKABLE) {
            imageView = (ImageView) view.findViewById(R.id.iv_icon);
        } else {
            imageView = (ImageView) toast.getView().findViewById(R.id.iv_icon);
        }
        imageView.setBackgroundResource(resId);
        imageView.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * @param animations A style resource defining the animations to use for this window.
     *                   This must be a system resource; it can not be an application resource
     *                   because the window manager does not have access to applications.
     */
    @Deprecated
    public ClickableToast setAnimations(@StyleRes int animations) {
        Log.d(TAG, "method:setAnimations is Deprecated , animations must be a system resource " +
                ", considering the window manager does not have access to applications.");
        if (type == CLICKABLE) {
            params.windowAnimations = animations;
        } else {
            try {
                Field tnField = toast.getClass().getDeclaredField("mTN");
                tnField.setAccessible(true);
                Object mTN = tnField.get(toast);
                Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
                tnParamsField.setAccessible(true);
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) tnParamsField.get(mTN);
                params.windowAnimations = animations;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    //设置toast背景颜色
    public ClickableToast setColor(@ColorRes int colorRes) {
        GradientDrawable drawable;
        if (type == CLICKABLE) {
            drawable = (GradientDrawable) view.getBackground();
        } else {
            drawable = (GradientDrawable) toast.getView().getBackground();
        }
        drawable.setColor(context.getResources().getColor(colorRes));
        return this;
    }

    public ClickableToast setBackground(Drawable drawable) {
        if (type == CLICKABLE) {
            view.setBackground(drawable);
        } else {
            toast.getView().setBackground(drawable);
        }
        return this;
    }

    public ClickableToast setGravity(int gravity, int xOffset, int yOffset) {
        if (type == CLICKABLE) {
            final Configuration config = view.getContext().getResources().getConfiguration();
            final int g = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
            params.gravity = g;
            if ((g & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                params.horizontalWeight = 1.0f;
            }
            if ((g & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                params.verticalWeight = 1.0f;
            }
            params.x = xOffset;
            params.y = yOffset;
        } else {
            toast.setGravity(gravity, xOffset, yOffset);
        }
        return this;
    }

    public ClickableToast setMargin(float horizontalMargin, float verticalMargin) {
        if (type == CLICKABLE) {
            params.verticalMargin = verticalMargin;
            params.horizontalMargin = horizontalMargin;
        } else {
            toast.setMargin(horizontalMargin, verticalMargin);
        }
        return this;
    }

    public ClickableToast setText(@StringRes int resId) {
        if (type == CLICKABLE) {
            ((TextView) view.findViewById(R.id.tv_message)).setText(resId);
        } else {
            toast.setText(resId);
        }
        return this;
    }

    public ClickableToast setText(@NonNull CharSequence s) {
        if (type == CLICKABLE) {
            ((TextView) view.findViewById(R.id.tv_message)).setText(s);
        } else {
            toast.setText(s);
        }
        return this;
    }

    public void show() {
        if (type != CLICKABLE) {
            toast.show();
        } else {
            if (listener == null) {
                Log.e(TAG, "the listener of clickable toast is null,have you called method:setClickCallBack?");
                return;
            }
            if (view.getParent() != null) {
                windowManager.removeView(view);
            }
            windowManager.addView(view, params);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            }, duration);
        }
    }

    public void cancel() {
        if (type != CLICKABLE || windowManager == null) {
            return;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        windowManager.removeView(view);
        params = null;
        windowManager = null;
        view = null;
        handler = null;
        listener = null;
    }

    public void showSuccess() {
        setIcon(R.drawable.ic_dialog_warning);
        show();
    }

    public void showError() {
        setIcon(R.drawable.ic_dialog_warning);
        show();
    }

    public void showWarning() {
        setIcon(R.drawable.ic_dialog_warning);
        show();
    }

    public ClickableToast setClickCallBack(@NonNull OnClickListener listener) {
        if (type != CLICKABLE) {
            Log.d(TAG, "only clickable toast has click callback!!!");
            return this;
        }
        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(this);
        }
        cancel();
    }

    public interface OnClickListener {

        void onClick(ClickableToast toast);
    }
}