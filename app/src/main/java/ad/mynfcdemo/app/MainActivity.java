package ad.mynfcdemo.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ad.mynfcdemo.R;
import ad.mynfcdemo.utils.ArrayOperation;
import ad.mynfcdemo.utils.Command;
import ad.mynfcdemo.utils.CustomDatePicker;
import ad.mynfcdemo.utils.IPEditText;
import ad.mynfcdemo.utils.LogAdapter;
import ad.mynfcdemo.utils.LogNfc;
import ad.mynfcdemo.utils.NfcObj;
import ad.mynfcdemo.utils.ReadNFC;
import ad.mynfcdemo.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseNfcActivity {
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.read_network_mode_result)
    TextView readNetworkModeResult;
    @BindView(R.id.read_network_mode_but)
    Button readNetworkModeBut;
    @BindView(R.id.read_network_mode_but_qh)
    Button readNetworkModeButQh;
    @BindView(R.id.set_network_mode_but)
    Button setNetworkModeBut;
    @BindView(R.id.read_access_number_but)
    Button readAccessNumberBut;
    @BindView(R.id.set_access_number_but)
    Button setAccessNumberBut;
    @BindView(R.id.read_server_port_result)
    EditText readServerPortResult;
    @BindView(R.id.read_server_port_but)
    Button readServerPortBut;
    @BindView(R.id.set_server_port_but)
    Button setServerPortBut;
    @BindView(R.id.ip_first)
    EditText ipFirst;
    @BindView(R.id.ip_second)
    EditText ipSecond;
    @BindView(R.id.ip_third)
    EditText ipThird;
    @BindView(R.id.ip_fourth)
    EditText ipFourth;
    @BindView(R.id.read_server_address_but)
    Button readServerAddressBut;
    @BindView(R.id.set_server_address_but)
    Button setServerAddressBut;
    @BindView(R.id.restart_but)
    Button restartBut;
    @BindView(R.id.network_tab_soll)
    ScrollView networkTabSoll;
    @BindView(R.id.read_heartbeat_result)
    EditText readHeartbeatResult;
    @BindView(R.id.read_heartbeat_but)
    Button readHeartbeatBut;
    @BindView(R.id.set_heartbeat_but)
    Button setHeartbeatBut;
    @BindView(R.id.read_detection_but_y)
    Button readDetectionButY;
    @BindView(R.id.set_detection_but_y)
    Button setDetectionBut;
    @BindView(R.id.read_power_notice_but)
    Button readPowerNoticeBut;
    @BindView(R.id.set_power_notice_but)
    Button setPowerNoticeBut;
    @BindView(R.id.read_time_result)
    TextView readTimeResult;
    @BindView(R.id.read_time_but)
    Button readTimeBut;
    @BindView(R.id.set_time_but)
    Button setTimeBut;
    @BindView(R.id.device_tab_soll)
    ScrollView deviceTabSoll;
    @BindView(R.id.read_log_but)
    Button readLogBut;
    @BindView(R.id.satus_tab_soll)
    ScrollView satusTabSoll;
    @BindView(R.id.read_power_result)
    TextView readPowerResult;
    @BindView(R.id.read_power_but)
    Button readPowerBut;
    @BindView(R.id.read_data_reporting_result)
    TextView readDataReportingResult;
    @BindView(R.id.read_data_reporting_but)
    Button readDataReportingBut;
    @BindView(R.id.read_access_number_result)
    Spinner readAccessNumberResult;
    @BindView(R.id.read_power_notice_result)
    Spinner readPowerNoticeResult;
    @BindView(R.id.read_power_notice_result_end)
    Spinner readPowerNoticeResultEnd;
    @BindView(R.id.read_detection_result_w)
    EditText readDetectionResultW;
    @BindView(R.id.read_detection_but_w)
    Button readDetectionButW;
    @BindView(R.id.set_detection_but_w)
    Button setDetectionButW;
    @BindView(R.id.read_detection_result_y)
    EditText readDetectionResultY;
    IsoDep isoDep;
    @BindView(R.id.log_tobar)
    TextView logTobar;
    private Tag tag;
    ReadNFC readNFC;//nfc读取类
    Command command;//指令类
    boolean zwfsqh = false;//切换找网方式
    private CustomDatePicker customDatePicker2;//时间选择器类
    IPEditText ipEditText;//ip地址类
    private static Context context;//上下文
    private List<LogNfc> fruitList = new ArrayList<>();//日志集合（配合适配器使用）
    ArrayList<NfcObj> nfcObjslist = new ArrayList<NfcObj>();//日志集合
    LogAdapter adapter;//日志适配器
    int MIN_CLICK_DELAY_TIME = 500;//延迟
    long lastClickTime = 0;//延迟

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rgMain.check(R.id.network_tab_but);
        tag = getIntent().getParcelableExtra("Tago");
        if(tag!=null){
            isoDep = IsoDep.get(tag);
        }
        adapter = new LogAdapter(MainActivity.this, R.layout.log_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.log_list_view);
        listView.setAdapter(adapter);
        context = getApplicationContext();
        init();
    }

    //初始化
    private void init() {
        initReadAccessNumberResult();
        initReadPowerNoticeResult();
        initReadPowerNoticeResultEnd();
        intiReadNetworkModeButQh();
        ipEditText.IPEditText(context, ipFirst, ipSecond, ipThird, ipFourth);
        readNFC = new ReadNFC();
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.network_tab_but:
                        deviceTabSoll.setVisibility(View.GONE);
                        satusTabSoll.setVisibility(View.GONE);
                        networkTabSoll.setVisibility(View.VISIBLE);
                        break;
                    case R.id.device_tab_but:
                        networkTabSoll.setVisibility(View.GONE);
                        satusTabSoll.setVisibility(View.GONE);
                        deviceTabSoll.setVisibility(View.VISIBLE);
                        break;
                    case R.id.satus_tab_but:
                        networkTabSoll.setVisibility(View.GONE);
                        deviceTabSoll.setVisibility(View.GONE);
                        satusTabSoll.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        initDatePicker();
        readTimeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker2.show(readTimeResult.getText().toString());
            }
        });
    }


    //探测到nfc都会执行这个方法
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        isoDep = IsoDep.get(tag);
        conncte();
    }

    //切换(找网方式)
    private void intiReadNetworkModeButQh() {
        readNetworkModeButQh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zwfsqh = !zwfsqh;
                readNetworkModeResult.setText(zwfsqh ? "自动找网" : "手动找网");
            }
        });
    }

    //入网频点号
    private void initReadAccessNumberResult() {
        String[] mItems = getResources().getStringArray(R.array.spingarr_listdate);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        readAccessNumberResult.setAdapter(adapter);
    }

    //电量警告阈值
    private void initReadPowerNoticeResult() {
        String[] mItems = getResources().getStringArray(R.array.power_notice_listdate);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        readPowerNoticeResult.setAdapter(adapter);
    }

    //电量警告阈值end
    private void initReadPowerNoticeResultEnd() {
        String[] mItems = getResources().getStringArray(R.array.power_notice_listdate_end);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        readPowerNoticeResultEnd.setAdapter(adapter);
    }

    //根据值,设置spinner默认选中
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();//得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }

    //时间选择控件
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
//        currentDate.setText(now.split(" ")[0]);
        readTimeResult.setText(now);
//        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                currentDate.setText(time.split(" ")[0]);
//            }
//        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker1.showSpecificTime(false); // 不显示时和分
//        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                readTimeResult.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }


    //自定义toast
    protected void toast(String message) {
        View toastRoot = LayoutInflater.from(this).inflate(R.layout.toast, null);
        Toast toast = new Toast(this);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.toast_notice);
        tv.setText(message);
        toast.show();
    }

    //页面点击事件
    @OnClick({R.id.read_network_mode_but, R.id.set_network_mode_but, R.id.read_access_number_but, R.id.set_access_number_but, R.id.read_server_port_but, R.id.set_server_port_but,
            R.id.read_server_address_but, R.id.set_server_address_but, R.id.restart_but, R.id.set_heartbeat_but, R.id.read_heartbeat_but, R.id.read_detection_but_y,
            R.id.set_detection_but_y, R.id.read_power_notice_but, R.id.set_power_notice_but, R.id.read_time_but, R.id.set_time_but, R.id.read_power_but,
            R.id.read_data_reporting_but, R.id.read_log_but, R.id.read_detection_but_w, R.id.set_detection_but_w})
    public void onViewClicked(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
            ToastUtil.show("请求过于频繁", context);
            return;
        }
        lastClickTime = currentTime;
        if (tag == null) {
            ToastUtil.show("已断开连接", context);
            return;
        }
        try {
            if (isoDep == null) {
                ToastUtil.show("不支持该类型卡", context);
                return;
            }
            isoDep.connect();
            isoDep.close();
        } catch (IOException e) {
            ToastUtil.show("已断开连接", context);
            return;
        }
        switch (view.getId()) {
            case R.id.read_network_mode_but://读取找网方式
                byte[] rnmb = readNFC.readIsoDep(tag, command.read_network_mode);
                if (rnmb == null || rnmb.length < 1) {
                    resultsToast(false, 0);
                    return;
                }
                String rnmb1 = stringToStar(ArrayOperation.bytesToHexString(rnmb))[0];
                String rnmb2 = "";
                if (rnmb1.equals("01")) {
                    zwfsqh = false;
                    rnmb2 = "手动找网";
                } else if (rnmb1.equals("02")) {
                    zwfsqh = true;
                    rnmb2 = "自动找网";
                } else {
                    resultsToast(false, 0);
                }
                readNetworkModeResult.setText(rnmb2);
                break;
            case R.id.set_network_mode_but://设置找网方式
                byte[] rz = readNFC.readIsoDep(tag, zwfsqh ? command.set_network_mode_z : command.set_network_mode_s);
                if (rz == null || rz.length < 1) {
                    resultsToast(false, 1);
                    return;
                }
                String rdfd = stringToStar(ArrayOperation.bytesToHexString(rz))[0];
                if (rdfd.equals("00")) {
                    resultsToast(true, 1);
                } else resultsToast(false, 1);
                break;
            case R.id.read_access_number_but://读取入网频点号
                byte[] rsnub = readNFC.readIsoDep(tag, command.read_access_number);
                if (rsnub == null || rsnub.length < 1) {
                    resultsToast(false, 0);
                    return;
                }
                String rsnub1 = stringToStar(ArrayOperation.bytesToHexString(rsnub))[0];
                String rsnub2 = "";
                if (rsnub1.equals("05")) {
                    rsnub2 = "850MHZ";
                } else if (rsnub1.equals("08")) {
                    rsnub2 = "800MHZ";
                } else if (rsnub1.equals("09")) {
                    rsnub2 = "900MHZ";
                } else {
                    resultsToast(false, 0);
                }
                setSpinnerItemSelectedByValue(readAccessNumberResult, rsnub2);
                break;
            case R.id.set_access_number_but://设置入网频点号
                String sanb = readAccessNumberResult.getSelectedItem().toString();
                byte[] sanb1;
                if (sanb.equals("800MHZ")) {
                    sanb1 = command.sset_access_number_8;
                } else if (sanb.equals("850MHZ")) {
                    sanb1 = command.sset_access_number_5;
                } else {
                    sanb1 = command.sset_access_number_9;
                }
                byte[] sanbr = readNFC.readIsoDep(tag, sanb1);
                String sanbr1 = stringToStar(ArrayOperation.bytesToHexString(sanbr))[0];
                if (sanbr1.equals("00")) {
                    resultsToast(true, 1);
                } else resultsToast(false, 1);
                break;
            case R.id.read_server_port_but://读取服务器端口号
                byte[] r4 = readNFC.readIsoDep(tag, command.read_server_port);
                if (r4 == null || r4.length < 1) {
                    resultsToast(false, 0);
                    return;
                }
                readServerPortResult.setText(String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(r4), 16)));
                break;
            case R.id.set_server_port_but://设置服务器端口号
                if (readServerPortResult.getText().toString().equals("")) {
                    ToastUtil.show("值不能为空", context);
                    return;
                }
                int ssspb = Integer.parseInt(readServerPortResult.getText().toString());
                if (ssspb > 65536) {
                    ToastUtil.show("只能设置小于65535的数", context);
                    return;
                }
                byte[] ssspb1 = command.set_server_port(ssspb);
                byte[] ssspb12 = readNFC.readIsoDep(tag, ssspb1);
                if (ssspb12 == null || ssspb12.length < 1) {
                    resultsToast(false, 1);
                    return;
                }
                String ssspb13 = ArrayOperation.bytesToHexString(ssspb12);
                if (ssspb13.equals("00")) {
                    resultsToast(true, 1);
                } else resultsToast(false, 1);
                break;
            case R.id.read_server_address_but://读取服务器地址
                byte[] rsab = readNFC.readIsoDep(tag, command.read_server_address_ip);
                String[] rsab1 = stringToStar(ArrayOperation.bytesToHexString(rsab));
                if (rsab1 == null || rsab1.length < 4) {
                    resultsToast(false, 0);
                    return;
                }
                ipFirst.setText(String.valueOf(Integer.parseInt(rsab1[0], 16)));
                ipSecond.setText(String.valueOf(Integer.parseInt(rsab1[1], 16)));
                ipThird.setText(String.valueOf(Integer.parseInt(rsab1[2], 16)));
                ipFourth.setText(String.valueOf(Integer.parseInt(rsab1[3], 16)));
                break;
            case R.id.set_server_address_but://设置服务器地址
                setServerAddress();
                break;
            case R.id.restart_but://重启设备
                onBackPressed1(true);
                break;
            case R.id.set_heartbeat_but://设置心跳周期
                if (readHeartbeatResult.getText().toString().equals("")) {
                    ToastUtil.show("值不能设置为空", context);
                    return;
                }
                int shb3 = Integer.parseInt(readHeartbeatResult.getText().toString());
                if (shb3 > 32767) {
                    ToastUtil.show("不能设置大于32767分钟", context);
                    return;
                }
                byte[] shb1 = readNFC.readIsoDep(tag, Command.set_heartbeat(shb3));
                if (shb1 == null || shb1.length < 1) {
                    resultsToast(false, 1);
                    return;
                }
                String[] shb2 = stringToStar(ArrayOperation.bytesToHexString(shb1));
                if (shb2[0].equals("00")) {
                    resultsToast(true, 1);
                } else resultsToast(false, 1);
                break;
            case R.id.read_heartbeat_but://读取心跳周期
                byte[] rr = readNFC.readIsoDep(tag, command.read_heartbeat);
                if (rr == null || rr.length < 1) {
                    resultsToast(false, 0);
                    return;
                }
                readHeartbeatResult.setText(String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(rr), 16)));
                break;
            case R.id.read_power_notice_but://读取电量警告阈值
                byte[] rpnb = readNFC.readIsoDep(tag, command.read_power_notice);
                if (rpnb == null) {
                    resultsToast(false, 1);
                    return;
                }
                String rpnb1 = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(rpnb), 16));
                if (rpnb1.length() < 2) {
                    resultsToast(false, 1);
                    return;
                }
                setSpinnerItemSelectedByValue(readPowerNoticeResult, rpnb1.substring(0, 1));
                setSpinnerItemSelectedByValue(readPowerNoticeResultEnd, rpnb1.substring(1, 2));
                break;
            case R.id.set_power_notice_but://设置电量警告阈值
                String indsds = readPowerNoticeResult.getSelectedItem().toString();
                String indsds1 = readPowerNoticeResultEnd.getSelectedItem().toString();
                byte[] indsds2 = command.set_power_notice(Integer.parseInt(indsds + indsds1));
                byte[] indsds3 = readNFC.readIsoDep(tag, indsds2);
                if (indsds3 == null || indsds3.length < 1) {
                    resultsToast(false, 1);
                    return;
                }
                String[] indsds4 = stringToStar(ArrayOperation.bytesToHexString(indsds3));
                if (indsds4[0].equals("00")) {
                    resultsToast(true, 1);
                } else resultsToast(false, 1);
                break;
            case R.id.read_time_but://读取时间
                readTime();
                break;
            case R.id.set_time_but://设置时间
                setTime();
                break;
            case R.id.read_power_but://读取电量
                byte[] r9 = readNFC.readIsoDep(tag, command.read_power);
                if (r9 == null || r9.length < 1) {
                    resultsToast(false, 0);
                    return;
                }
                String ds = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(r9), 16));
                if (Integer.parseInt(ds) > 100) {
                    ds = "100";
                }
                readPowerResult.setText(ds + " %");
                break;
            case R.id.read_data_reporting_but://上报成功率
                byte[] r10 = readNFC.readIsoDep(tag, command.read_data_reporting);
                String[] rdrb = stringToStar(ArrayOperation.bytesToHexString(r10));
                if (r10 == null || r10.length < 3) {
                    resultsToast(false, 0);
                    return;
                }
                String rdrb1 = String.valueOf(Integer.parseInt(rdrb[2], 16));
                if (Integer.parseInt(rdrb[2], 16) > 100) {
                    rdrb1 = "100";
                }
                readDataReportingResult.setText(rdrb1 + " %");
                break;
            case R.id.read_log_but://读取日志
                intiLogNfc();
                break;
            case R.id.read_detection_but_y://读取有车阈值
            case R.id.read_detection_but_w://读取无车阈值
                readDetectionRe();
                break;
            case R.id.set_detection_but_y://设置有车阈值
            case R.id.set_detection_but_w://设置无车阈值
                detectionReIsOr();
                break;
        }
    }

    //格式化时间
    private static String initDateTime(String str) {
        String re = str;
        if (str.length() == 1) {
            re = 0 + str;
        }
        return re;
    }

    //读取时间
    private void readTime() {
        byte[] r7 = readNFC.readIsoDep(tag, command.read_time);
        if (r7 == null || r7.length < 5) {
            resultsToast(false, 0);
            return;
        }
        String rtbyy = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[0]}), 16));//年1
        String rtbyy2 = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[1]}), 16));//年2
        String rtbmm = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[2]}), 16));//月
        String rtbdd = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[3]}), 16));//日
        String rtbhh = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[4]}), 16));//时
        String rtbfe = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[5]}), 16));//分
        String rtby = "";
        rtby += rtbyy + initDateTime(rtbyy2) + "-";
        rtby += initDateTime(rtbmm) + "-";
        rtby += initDateTime(rtbdd) + " ";
        rtby += initDateTime(rtbhh) + ":";
        rtby += initDateTime(rtbfe);
        readTimeResult.setText(rtby);
    }

    //延迟执行
    class RunnableM implements Runnable {
        public String str;

        public RunnableM(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            setSpinnerItemSelectedByValue(readPowerNoticeResultEnd, str.length() <= 1 ? "0" : str.substring(1, 2));
        }
    }

    //设置服务器地址
    private void setServerAddress() {
        String p1 = ipFirst.getText().toString();
        String p2 = ipSecond.getText().toString();
        String p3 = ipThird.getText().toString();
        String p4 = ipFourth.getText().toString();
        if (p1.equals("") || p2.equals("") || p3.equals("") || p4.equals("")) {
            ToastUtil.show("请输入合法的ip地址", context);
            return;
        }
        byte[] strArr1 = command.set_server_address_ip(Integer.parseInt(p1), Integer.parseInt(p2), Integer.parseInt(p3), Integer.parseInt(p4));
        byte[] ssab = readNFC.readIsoDep(tag, strArr1);
        if (ssab == null || ssab.length < 1) {
            resultsToast(false, 1);
            return;
        }
        String[] ssab1 = stringToStar(ArrayOperation.bytesToHexString(ssab));
        if (ssab1[0].equals("00")) {
            resultsToast(true, 1);
        } else resultsToast(false, 1);
    }

    //设置设备时间
    private void setTime() {
        String st = readTimeResult.getText().toString();
        String[] st1 = st.split(" ");
        String[] ymd = st1[0].split("-");
        String st2 = ymd[0].substring(0, 2);
        String st3 = ymd[0].substring(2, 4);
        String[] sf = st1[1].split(":");
        byte[] stb = command.set_time(Integer.parseInt(st2), Integer.parseInt(st3), Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]), Integer.parseInt(sf[0]), Integer.parseInt(sf[1]));
        byte[] stb4 = readNFC.readIsoDep(tag, stb);
        if (stb4 == null || stb4.length < 1) {
            resultsToast(false, 1);
            return;
        }
        String stbd = stringToStar(ArrayOperation.bytesToHexString(stb4))[0];
        if (stbd.equals("00")) {
            resultsToast(true, 1);
        } else resultsToast(false, 1);
    }

    //读取有无阈值
    private void readDetectionRe() {
        byte[] r8 = readNFC.readIsoDep(tag, command.read_detection_y);
        if (r8 == null || r8.length < 4) {
            resultsToast(false, 0);
            return;
        }
        byte[] rw = {r8[0], r8[1]};
        byte[] ry = {r8[2], r8[3]};
        readDetectionResultW.setText(String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(rw), 16)));
        readDetectionResultY.setText(String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(ry), 16)));
    }

    //设置有无车阈值
    private void detectionReIsOr() {
        if (readDetectionResultW.getText().toString().equals("") || readDetectionResultY.getText().toString().equals("")) {
            ToastUtil.show("值不能为空", context);
            return;
        }
        int rew = Integer.valueOf(readDetectionResultW.getText().toString());
        int rey = Integer.valueOf(readDetectionResultY.getText().toString());
        if (rew > 65536 || rey > 65536) {
            ToastUtil.show("只能设置小于65535的数", context);
            return;
        }
        if (rew > rey) {
            ToastUtil.show("无车阈值必须要小于有阈值", context);
            return;
        }
        byte[] b = command.set_detection(rew, rey);
        byte[] r8 = readNFC.readIsoDep(tag, b);
        if (r8 == null || r8.length < 1) {
            resultsToast(false, 1);
            return;
        }
        String b2 = stringToStar(ArrayOperation.bytesToHexString(r8))[0];
        if (b2.equals("00")) {
            resultsToast(true, 1);
        } else resultsToast(false, 1);
    }

    //截取数字域返回字符串数组
    private String[] stringToStar(String str) {
        if (str == null) return null;
        if (str.equals("") && str.length() < 2) return null;
        String[] result = new String[str.length() / 2 == 0 ? 1 : str.length() / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = str.substring(i * 2, i * 2 + 2);
        }
        return result;
    }

    //提示toast
    private void resultsToast(Boolean b, int i) {
        String reb = "";
        if (b) {
            if (i == 0) {
                reb = "读取成功";
            } else {
                reb = "保存成功";
            }

        } else {
            if (i == 0) {
                reb = "读取指令有误";
            } else {
                reb = "保存指令有误";
            }

        }
        ToastUtil.show(reb, context);

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

    //日志
    private void intiLogNfc() {
        nfcObjslist.clear();
        fruitList.clear();
        nfcObjslist = ArrayOperation.setNfc(readNFC, tag, nfcObjslist);
        if (nfcObjslist.size() == 0) {
            adapter.notifyDataSetChanged();
            logTobar.setVisibility(View.VISIBLE);
            return;
        }
        logTobar.setVisibility(View.GONE);
        for (NfcObj d : nfcObjslist) {
            LogNfc ad = new LogNfc(d.getXh(), d.getCz(), d.getSj());
            fruitList.add(ad);
        }
        adapter.notifyDataSetChanged();
    }

    public void onBackPressed1(boolean d) {

        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("确定要重启吗");
        localBuilder.setIcon(android.R.drawable.ic_dialog_info);
        localBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        byte[] r5 = readNFC.readIsoDep(tag, command.restart);
                        if (r5 == null || r5.length < 1) {
                            ToastUtil.show("重启设备指令有误", context);
                            return;
                        }
                        String[] rb = stringToStar(ArrayOperation.bytesToHexString(r5));
                        if (rb[0].equals("00")) {
                            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("设备重启中...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.show("重启设备成功", context);
                                    startActivity(new Intent(MainActivity.this, ActivePageActivity.class));
                                    finish();
                                }
                            }, 3000);
                        } else ToastUtil.show("重启设备失败", context);
                    }
                });
        localBuilder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        if (d) {
            localBuilder.setCancelable(false);
            localBuilder.show();
            d = false;
        }

    }

}
