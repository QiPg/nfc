package ad.mynfcdemo.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
/**
 * ip 地址控件
 * Created by 农启兵 on 2017/11/21.
 */

public class IPEditText{
    private static EditText mFirstIP;
    private static EditText mSecondIP;
    private static EditText mThirdIP;
    private static EditText mFourthIP;
    private static String mText1 = "";
    private static String mText2 = "";
    private static String mText3 = "";
    private static String mText4 = "";

    public static void IPEditText(Context context,EditText mFirstip,EditText mSecondip,EditText mThirdip,EditText mFourthip) {

        /**
         * 初始化控件
         */
        mFirstIP = mFirstip;
        mSecondIP = mSecondip;
        mThirdIP = mThirdip;
        mFourthIP = mFourthip;
        OperatingEditText(context);
    }

    /**
     * 获得EditText中的内容,当每个Edittext的字符达到三位时,自动跳转到下一个EditText,当用户点击.时,
     * 下一个EditText获得焦点
     */
    private static void OperatingEditText(final Context context) {
        mFirstIP.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                /**
                 * 获得EditTe输入内容,做判断,如果大于255,提示不合法,当数字为合法的三位数下一个EditText获得焦点,
                 * 用户点击啊.时,下一个EditText获得焦点
                 */
                if (s != null && s.length() > 0) {
                    if (s.length() > 2 || s.toString().trim().contains(".")) {
                        if (s.toString().trim().contains(".")) {
                            mText1 = s.toString().substring(0, s.length() - 1);
                            mFirstIP.setText(mText1);
                        } else {
                            mText1 = s.toString().trim();
                        }

                        if (mText1.length() == 0) {
                            return;
                        }
                        if (Integer.parseInt(mText1) > 255) {
                            ToastUtil.show("请输入合法的ip地址",context);
                            mFirstIP.setText("");
                            return;
                        }


                        mSecondIP.setFocusable(true);
                        mSecondIP.requestFocus();
                    } else {
                        if (s.toString().trim().contains(".")) {
                            mText1 = s.toString().substring(0, s.length() - 1);
                            mFirstIP.setText(mText1);
                        } else {
                            mText1 = s.toString().trim();
                        }
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSecondIP.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                /**
                 * 获得EditTe输入内容,做判断,如果大于255,提示不合法,当数字为合法的三位数下一个EditText获得焦点,
                 * 用户点击啊.时,下一个EditText获得焦点
                 */
                if (s != null && s.length() > 0) {
                    if (s.length() > 2 || s.toString().trim().contains(".")) {
                        if (s.toString().trim().contains(".")) {
                            mText2 = s.toString().substring(0, s.length() - 1);
                            mSecondIP.setText(mText2);
                        } else {
                            mText2 = s.toString().trim();
                        }
                        if (mText2.length() == 0) {
                            return;
                        }
                        if (Integer.parseInt(mText2) > 255) {
                            ToastUtil.show("请输入合法的ip地址",context);
                            mSecondIP.setText("");
                            return;
                        }


                        mThirdIP.setFocusable(true);
                        mThirdIP.requestFocus();
                    } else {
                        if (s.toString().trim().contains(".")) {
                            mText2 = s.toString().substring(0, s.length() - 1);
                            mSecondIP.setText(mText2);
                        } else {
                            mText2 = s.toString().trim();
                        }
                    }
                }

                /**
                 * 当用户需要删除时,此时的EditText为空时,上一个EditText获得焦点
                 */
                if (start == 0 && s.length() == 0) {
                    mFirstIP.setFocusable(true);
                    mFirstIP.requestFocus();
                    //mFirstIP.setSelection(mPreferences.getInt("IP_FIRST", 0));
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mThirdIP.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                /**
                 * 获得EditTe输入内容,做判断,如果大于255,提示不合法,当数字为合法的三位数下一个EditText获得焦点,
                 * 用户点击啊.时,下一个EditText获得焦点
                 */
                if (s != null && s.length() > 0) {
                    if (s.length() > 2 || s.toString().trim().contains(".")) {
                        if (s.toString().trim().contains(".")) {
                            mText3 = s.toString().substring(0, s.length() - 1);
                            mThirdIP.setText(mText3);
                        } else {
                            mText3 = s.toString().trim();
                        }
                        if (mText3.length() == 0) {
                            return;
                        }
                        if (Integer.parseInt(mText3) > 255) {
                            ToastUtil.show("请输入合法的ip地址",context);
                            mThirdIP.setText("");
                            return;
                        }
                        mFourthIP.setFocusable(true);
                        mFourthIP.requestFocus();
                    } else {
                        if (s.toString().trim().contains(".")) {
                            mText3 = s.toString().substring(0, s.length() - 1);
                            mThirdIP.setText(mText3);
                        } else {
                            mText3 = s.toString().trim();
                        }
                    }

                }

                /**
                 * 当用户需要删除时,此时的EditText为空时,上一个EditText获得焦点
                 */
                if (start == 0 && s.length() == 0) {
                    mSecondIP.setFocusable(true);
                    mSecondIP.requestFocus();
                    //mSecondIP.setSelection(mPreferences.getInt("IP_SECOND", 0));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFourthIP.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                /**
                 * 获得EditTe输入内容,做判断,如果大于255,提示不合法,当数字为合法的三位数下一个EditText获得焦点,
                 * 用户点击啊.时,下一个EditText获得焦点
                 */
                if (s != null && s.length() > 0) {
                    if (s.toString().trim().contains(".")) {
                        mText4 = s.toString().substring(0, s.length() - 1);
                        mFourthIP.setText(mText4);
                    } else {
                        mText4 = s.toString().trim();
                    }
                    if (mText4.length() == 0) {
                        return;
                    }
                    if (Integer.parseInt(mText4) > 255) {
                        ToastUtil.show("请输入合法的ip地址",context);
                        mFourthIP.setText("");
                        return;
                    }
                }

                /**
                 * 当用户需要删除时,此时的EditText为空时,上一个EditText获得焦点
                 */
                if (start == 0 && s.length() == 0) {
                    mThirdIP.setFocusable(true);
                    mThirdIP.requestFocus();
                    //mThirdIP.setSelection(mPreferences.getInt("IP_THIRD", 0));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void setText(String ip) {
        String[] ips = ip.split("\\.");
        if (ips.length == 4) {
            mFirstIP.setText(ips[0]);
            mSecondIP.setText(ips[1]);
            mThirdIP.setText(ips[2]);
            mFourthIP.setText(ips[3]);
        }
    }

}
