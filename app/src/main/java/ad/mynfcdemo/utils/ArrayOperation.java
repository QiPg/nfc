package ad.mynfcdemo.utils;

import android.nfc.Tag;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 数组操作
 * Created by 农启兵 on 2017/11/23.
 */

public class ArrayOperation {
    //数组合并
    public static byte[] mergeBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    //int 转字节数组返回两个字节
    public static byte[] intToByte(int ints) {
        int startu = 2;
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);
        try {
            doutput.writeInt(ints);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = boutput.toByteArray();
        byte[] newData;
        newData = Arrays.copyOfRange(buf, startu, buf.length);
        return newData;
    }

    //截取数字域(或某一段)
    public static byte[] byteSbustring(byte[] date, int start, int end) {
        byte[] newData;
        if (start != 0 && end != 0) {
            newData = Arrays.copyOfRange(date, start, end);
        } else {
            newData = Arrays.copyOfRange(date, 2, date.length - 2);
        }
        return newData;
    }

    //字符序列转换为16进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //根据十六进制功能码返回对应的功能码含义
    public static String bstr(String str) {
        String resutle = "未知操作";
        if (str.equals("01")) {
            resutle = "设置找网方式";
        } else if (str.equals("03")) {
            resutle = "设置CDP服务器IP地址";
        } else if (str.equals("05")) {
            resutle = "设置CDP服务器端口号";
        } else if (str.equals("07")) {
            resutle = "上位机设置频点号";
        } else if (str.equals("0b")) {
            resutle = "设置车检器上传心跳包周期";
        } else if (str.equals("0d")) {
            resutle = "设置车检器电量告警门限";
        } else if (str.equals("01")) {
            resutle = "设置找网方式";
        } else if (str.equals("0f")) {
            resutle = "设置车检器时间";
        } else if (str.equals("11")) {
            resutle = "设置车检器检测精度";
        } else if (str.equals("40")) {
            resutle = "重启地磁车检器";
        }else if (str.equals("41")) {
            resutle = "激活设备";
        }
        return resutle;
    }

    //日志
    public static ArrayList<NfcObj> setNfc(ReadNFC readNFC, Tag tag, ArrayList<NfcObj> nfcObjs) {

        byte[] d0 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x01});
        if (d0 != null && (d0.length == 9 && d0[1] != 00)) {
            String[] d00 = stringToStar(ArrayOperation.bytesToHexString(d0));
            NfcObj n = new NfcObj();
            n.setXh(d00[0]);
            n.setCz(bstr(d00[1]));
            n.setSj(tS(d0));
            nfcObjs.add(n);
        }
        byte[] d1 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x02});
        if (d1 != null && (d1.length == 9 && d1[1] != 00)) {
            String[] d01 = stringToStar(ArrayOperation.bytesToHexString(d1));
            NfcObj n1 = new NfcObj();
            n1.setXh(d01[0]);
            n1.setCz(bstr(d01[1]));
            n1.setSj(tS(d1));
            nfcObjs.add(n1);
        }
        byte[] d2 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x03});
        if (d2 != null && (d2.length == 9 && d2[1] != 00)) {
            String[] d02 = stringToStar(ArrayOperation.bytesToHexString(d2));
            NfcObj n2 = new NfcObj();
            n2.setXh(d02[0]);
            n2.setCz(bstr(d02[1]));
            n2.setSj(tS(d2));
            nfcObjs.add(n2);
        }
        byte[] d3 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x04});
        if (d3 != null && (d3.length == 9 && d3[1] != 00)) {
            String[] d03 = stringToStar(ArrayOperation.bytesToHexString(d3));
            NfcObj n3 = new NfcObj();
            n3.setXh(d03[0]);
            n3.setCz(bstr(d03[1]));
            n3.setSj(tS(d3));
            nfcObjs.add(n3);
        }
        byte[] d4 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x05});
        if (d4 != null && (d4.length == 9 && d4[1] != 00)) {
            String[] d04 = stringToStar(ArrayOperation.bytesToHexString(d4));
            NfcObj n4 = new NfcObj();
            n4.setXh(d04[0]);
            n4.setCz(bstr(d04[1]));
            n4.setSj(tS(d4));
            nfcObjs.add(n4);
        }
        byte[] d5 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x06});
        if (d5 != null && (d5.length == 9 && d5[1] != 00)) {
            String[] d05 = stringToStar(ArrayOperation.bytesToHexString(d5));
            NfcObj n5 = new NfcObj();
            n5.setXh(d05[0]);
            n5.setCz(bstr(d05[1]));
            n5.setSj(tS(d5));
            nfcObjs.add(n5);
        }
        byte[] d6 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x07});
        if (d6 != null && (d6.length == 9 && d6[1] != 00)) {
            String[] d06 = stringToStar(ArrayOperation.bytesToHexString(d6));
            NfcObj n6 = new NfcObj();
            n6.setXh(d06[0]);
            n6.setCz(bstr(d06[1]));
            n6.setSj(tS(d6));
            nfcObjs.add(n6);
        }
        byte[] d7 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x08});
        if (d7 != null && (d7.length == 9 && d7[1] != 00)) {
            String[] d07 = stringToStar(ArrayOperation.bytesToHexString(d7));
            NfcObj n7 = new NfcObj();
            n7.setXh(d07[0]);
            n7.setCz(bstr(d07[1]));
            n7.setSj(tS(d7));
            nfcObjs.add(n7);
        }
        byte[] d8 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x09});
        if (d8 != null && (d8.length == 9 && d8[1] != 00)) {
            String[] d08 = stringToStar(ArrayOperation.bytesToHexString(d8));
            NfcObj n8 = new NfcObj();
            n8.setXh(d08[0]);
            n8.setCz(bstr(d08[1]));
            n8.setSj(tS(d8));
            nfcObjs.add(n8);
        }
        byte[] d9 = readNFC.readIsoDep(tag, new byte[]{0x02, 0x33, 0x0A});
        if (d9 != null && (d9.length == 9 && d9[1] != 00)) {
            String[] d09 = stringToStar(ArrayOperation.bytesToHexString(d9));
            NfcObj n9 = new NfcObj();
            n9.setXh(d09[0]);
            n9.setCz(bstr(d09[1]));
            n9.setSj(tS(d9));
            nfcObjs.add(n9);
        }
        return nfcObjs;
    }

    //获取时间
    private static String tS(byte[] r7) {
        if (r7 == null || r7.length < 8) {
            return  " ";
        }
        String rtbyy = String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[2]}), 16));//年1
        String rtbyy2 =String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[3]}), 16));//年2
        String rtbmm =String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[4]}), 16));//月
        String rtbdd =String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[5]}), 16));//日
        String rtbhh =String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[6]}), 16));//时
        String rtbfe =String.valueOf(Integer.parseInt(ArrayOperation.bytesToHexString(new byte[]{r7[7]}), 16));//分
        String rtby = "";
        rtby+=rtbyy+initDateTime(rtbyy2)+"-";
        rtby+=initDateTime(rtbmm)+"-";
        rtby+=initDateTime(rtbdd)+" ";
        rtby+=initDateTime(rtbhh)+":";
        rtby+=initDateTime(rtbfe);
        return rtby;

    }
    //格式化时间
    private static String initDateTime(String str){
        String re=str;
        if(str.length()==1){
            re=0+str;
        }
        return re;
    }
    //截取数字域返回字符串数组
    public static String[] stringToStar(String str) {
        if (str == null) return null;
        if (str.equals("") && str.length() < 2) return null;
        String[] result = new String[str.length() / 2 == 0 ? 1 : str.length() / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = str.substring(i * 2, i * 2 + 2);
        }
        return result;
    }
}
