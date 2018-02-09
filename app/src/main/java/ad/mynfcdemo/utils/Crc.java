package ad.mynfcdemo.utils;

import android.util.Log;

/**CRC校验
 * Created by 农启兵 on 2017/11/16.
 */

public class Crc {

    public static byte[] cxc(byte[] setnettype) {
        byte[] nfccrc = new byte[2];
        ComputeCrc(setnettype, setnettype.length, nfccrc);
        Log.d("______CRC校验结果",String.valueOf((int)nfccrc[0] & 0x000000ff)+"  "+String.valueOf((int)nfccrc[1] & 0x000000ff));
        return addBytes(setnettype, nfccrc);
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    /*********************************************************************************************************
     ** Function name:       UpdataCrc
     ** Descriptions:        计算CRC值
     ** input parameters:    需要计算的CRC字符计算结果
     ** output parameters:   CRC结果
     ** Returned value:      无
     *********************************************************************************************************/
    private static int UpdateCrc(int ch, int lpwCrc) {
        ch = ((ch & 0x00ff) ^ (int) ((lpwCrc) & 0x00FF));
        ch = ((ch ^ (ch << 4)) & 0x00ff);
        lpwCrc = (lpwCrc >> 8) ^ (((int) ch << 8) & 0xffff) ^ (((int) ch << 3) & 0xffff) ^ (((int) ch >> 4) & 0xffff);
        return (lpwCrc);
    }

    /*********************************************************************************************************
     ** Function name:       ComputeCrc
     ** Descriptions:        CRC计算
     ** input parameters:    CRC计算字符指针 字符字节数量 CRC首先传输和最后传输结果
     ** output parameters:
     ** Returned value:      无
     *********************************************************************************************************/
    public static boolean ComputeCrc(byte[] Data, int Length, byte[] pcrc) {
        boolean ret = false;
        short chBlock = 0;
        int wCrc;
        int i = 0;
        wCrc = 0x6363; // ITU-V.41
        if (Length == 0) {
            return ret;
        }

        do {
            chBlock = Data[i++];
            wCrc = UpdateCrc(chBlock, wCrc);
        } while ((--Length) > 0);
        pcrc[0] = (byte) (wCrc & 0xFF);
        pcrc[1] = (byte) ((wCrc >> 8) & 0xFF);
        ret = true;
        return ret;
    }

}
