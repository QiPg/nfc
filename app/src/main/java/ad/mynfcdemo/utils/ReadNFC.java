package ad.mynfcdemo.utils;

import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;

/**
 * 读取nfc标签类
 * Created by 农启兵 on 2017/11/10.
 */

public class ReadNFC {
    private Tag tag1;
    private Crc crc16;

    /**
     * 读取IsoDep
     *
     * @param tag
     * @return
     */
    public byte[] readIsoDep(Tag tag, byte[] bytes) {
        byte[] result = null;
        //crc校验
        byte[] balance = crc16.cxc(bytes);
        if (tag1 == null) {
            tag1 = tag;
        }

        IsoDep isodep = IsoDep.get(tag1);
        Log.d("______发送指令",ArrayOperation.bytesToHexString(balance));
        Log.d("______id",ArrayOperation.bytesToHexString(tag1.getId()));
        try {
            isodep.close();
            isodep.connect();
            if (isodep.isConnected()) {
                isodep.setTimeout(2000);
                Log.d("______状态","连接成功 ");
                byte[] balanceRsp = isodep.transceive(balance);
                if (balanceRsp != null) {
                    result = ArrayOperation.byteSbustring(balanceRsp,0,0);
                }
                Log.d("______返回结果",ArrayOperation.bytesToHexString(balanceRsp));
            } else {
                Log.d("______状态","连接断开");
            }
            isodep.close();
        } catch (IOException e) {
            Log.d("______状态","不可以进行I/O操作");
            e.printStackTrace();
        } finally {
            return result;
        }

    }
    public byte[] readIsoDepTop(Tag tag, byte[] bytes){
        byte[] result = null;
        //crc校验
        byte[] balance = crc16.cxc(bytes);
        if (tag1 == null) {
            tag1 = tag;
        }

        IsoDep isodep = IsoDep.get(tag1);
        Log.d("______发送指令",ArrayOperation.bytesToHexString(balance));
        Log.d("______id",ArrayOperation.bytesToHexString(tag1.getId()));
        try {
            isodep.close();
            isodep.connect();
            if (isodep.isConnected()) {
                isodep.setTimeout(2000);
                Log.d("______状态","连接成功1 ");
                byte[] balanceRsp = isodep.transceive(balance);
                if (balanceRsp != null) {
                    result = ArrayOperation.byteSbustring(balanceRsp,1,2);
                }
                Log.d("______返回结果",ArrayOperation.bytesToHexString(balanceRsp));
            } else {
                Log.d("______状态","连接断开");
            }
            isodep.close();
        } catch (IOException e) {
            Log.d("______状态","不可以进行I/O操作");
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
