package ad.mynfcdemo.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import ad.mynfcdemo.R;
import ad.mynfcdemo.utils.ArrayOperation;
import ad.mynfcdemo.utils.Command;
import ad.mynfcdemo.utils.ReadNFC;
import ad.mynfcdemo.utils.ToastUtil;

/**
 * Created by 农启兵 on 2017/11/27.
 */

public class ActivePageActivity extends BaseNfcActivity {
    private TextView version;
    private Button kfa;
    private Button apaBut;
    private TextView apaText;
    private ImageView apaImg;
    private Tag tag;
    private IsoDep isoDep;
    private ReadNFC readNFC;
    private Context context;
    private boolean dierci = true;
    int MIN_CLICK_DELAY_TIME = 500;//延迟
    long lastClickTime = 0;//延迟

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_page);
        readNFC = new ReadNFC();
        context = getApplicationContext();
        apaBut = (Button) findViewById(R.id.apa_but);
        kfa = (Button) findViewById(R.id.kfa);
        apaText = (TextView) findViewById(R.id.apa_text);
        apaImg = (ImageView) findViewById(R.id.apa_img);
        version = (TextView) findViewById(R.id.app_version);
        version.setText(getVersionName());
        apaBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
                    ToastUtil.show("请求过于频繁", context);
                    return;
                }
                lastClickTime = currentTime;
                if (!conncte()) {
                    return;
                }
                //已激活
                if (initNFC()) {
                    openMainActvity();
                    return;
                }
                byte[] isor = readNFC.readIsoDepTop(tag, Command.set_activation);
                if (isor == null || isor.length < 1) {
                    apaText.setText("请重新请求");
                    return;
                  }
                String isor1 = ArrayOperation.stringToStar(ArrayOperation.bytesToHexString(isor))[0];
                Log.d("______激活返回功能码",isor1);
                if (!isor1.equals("41")) {
                    apaText.setText("激活设备失败");
                    return;
                }
                dierci = false;
                apaText.setText("设备激活中...");
            }
        });
        kfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActvity();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        isoDep = IsoDep.get(tag);
        if (!conncte()) return;
        initNFC();
    }

    //查看是否与设备连接
    private boolean conncte() {
        if (tag == null) {
            ToastUtil.show("已断开连接", context);
            return false;
        }
        if (isoDep == null) {
            ToastUtil.show("不支持该类型卡", context);
            return false;
        }
        try {
            isoDep.connect();
            isoDep.close();
        } catch (IOException e) {
            ToastUtil.show("已断开连接", context);
            return false;
        }
        return true;
    }

    //查看是否设备是否已激活
    private boolean initNFC() {
        ToastUtil.show("已检测到设备", ActivePageActivity.this);
        byte[] isor = readNFC.readIsoDep(tag, Command.set_isoractivation);
        if (isor == null || isor.length < 1) {
            apaText.setText("设备无响应");
            return false;
        }
        String isor1 = ArrayOperation.stringToStar(ArrayOperation.bytesToHexString(isor))[0];
        if (isor1.equals("00")) {
            if (dierci) {
                apaText.setText("设备未激活");
            } else {
                apaText.setText("设备激活中...");
            }
            return false;
        } else if (isor1.equals("01")) {
            openMainActvity();
            return true;
        } else {
            apaText.setText("设备返回数据不正确");
            return false;
        }
    }

    //进到主页面
    private void openMainActvity() {
        apaText.setTextColor(Color.parseColor("#1296db"));
        apaText.setText("设备已激活");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ActivePageActivity.this,MainActivity.class);
                intent.putExtra("Tago",tag);
                startActivity(intent);
                finish();
            }
        }, 500);
    }

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return "版本：" + version;
    }
}
