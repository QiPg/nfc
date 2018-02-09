package ad.mynfcdemo.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ad.mynfcdemo.R;

/**
 * Created by 农启兵 on 2017/11/21.
 */

public class ToastUtil{
    //自定义toast
    public static void show(String message, Context context) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
        Toast toast = new Toast(context);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.toast_notice);
        tv.setText(message);
        toast.show();
    }
}
