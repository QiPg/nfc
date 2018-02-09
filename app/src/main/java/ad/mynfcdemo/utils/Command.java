package ad.mynfcdemo.utils;


/**
 * 指令类
 * Created by 农启兵 on 2017/11/18.
 *
 */

public class Command {
    //上位机读取找网方式
    public static byte[] read_network_mode={(byte) 0x02,(byte) 0x02,(byte) 0x00};
    //上位机读取CDP服务器IP地址
    public static byte[] read_server_address_ip={(byte) 0x02,(byte) 0X04,(byte) 0x00};
    //上位机读取CDP服务器端口号
    public static byte[] read_server_port={(byte) 0x02,(byte) 0X06,(byte) 0x00};
    //上位机读取CDP服务器频点号
    public static byte[] read_access_number={(byte) 0x02,(byte) 0X08,(byte) 0x00};
    //上位机读取NB心跳包周期
    public static byte[] read_heartbeat={(byte) 0x02,(byte) 0X0C,(byte) 0x00};
    //上位机读取车检器电量告警门限
    public static byte[] read_power_notice={(byte) 0x02,(byte) 0X0E,(byte) 0x00};
    //上位机读取车检器时间
    public static byte[] read_time={(byte) 0x02,(byte) 0X10,(byte) 0x00};
    //上位机读取车检器检测精度(有车阈)
    public static byte[] read_detection_y={(byte) 0x02,(byte) 0X12,(byte) 0x00};
    //上位机读取车检器检测精度(无车阈)
    public static byte[] read_detection_w={(byte) 0x02,(byte) 0X12,(byte) 0x00};
    //上位机读取车检器当前电量
    public static byte[] read_power={(byte) 0x02,(byte) 0X31,(byte) 0x00};
    //上位机读取上报数据成功率
    public static byte[] read_data_reporting={(byte) 0x02,(byte) 0X32,(byte) 0x00};
    //上位机读取车检器日志
    public static byte[] read_log={(byte) 0x02,(byte) 0X33,(byte) 0x01};
    //重启地磁车检器
    public static byte[] restart={(byte) 0x03,(byte) 0X40,(byte) 0x5A,(byte) 0X6B};

    //上位机设置找网方式手动
    public static byte[] set_network_mode_s={(byte) 0x02,(byte) 0x01,(byte) 0x01};
    //上位机设置找网方式自动
    public static byte[] set_network_mode_z={(byte) 0x02,(byte) 0x01,(byte) 0x02};

    //上机设置频点号850
    public static byte[] sset_access_number_5={(byte) 0x02,(byte) 0x07,(byte) 0x05};
    //上机设置频点号800
    public static byte[] sset_access_number_8={(byte) 0x02,(byte) 0x07,(byte) 0x08};
    //上机设置频点号900
    public static byte[] sset_access_number_9={(byte) 0x02,(byte) 0x07,(byte) 0x09};
   //上机设置服务器地址
    public static byte[] set_server_address_ip(int r1,int r2,int r3,int r4){
        return new byte[]{(byte) 0x05,(byte) 0x03,ArrayOperation.intToByte(r1)[1],ArrayOperation.intToByte(r2)[1],ArrayOperation.intToByte(r3)[1],ArrayOperation.intToByte(r4)[1]};
    }
    //设置服务端口号
    public static byte[] set_server_port(int r1){
        byte[] ssp1={(byte) 0x03,(byte) 0x05};
        return ArrayOperation.mergeBytes(ssp1,ArrayOperation.intToByte(r1));
    }
    //设置心跳包周期
    public static byte[] set_heartbeat(int r1){
        byte[] ssp1={(byte) 0x03,(byte) 0x0B};
        return ArrayOperation.mergeBytes(ssp1,ArrayOperation.intToByte(r1));
    }
    //设置电量警告阈值
    public  static byte[] set_power_notice(int r1){
        byte[] spn={(byte)0X02 ,(byte) 0X0D,ArrayOperation.intToByte(r1)[1]};
        return spn;
    }
    //上位机设置车检器检测精度
    public static byte[] set_detection(int r,int r1){
        byte[] sd={(byte) 0x05,(byte) 0x11};
        byte[] sd1=ArrayOperation.intToByte(r);
        byte[] sd2=ArrayOperation.intToByte(r1);
        return ArrayOperation.mergeBytes(sd,ArrayOperation.mergeBytes(sd1,sd2));
    }
    //设置时间
    public static  byte[] set_time(int y,int y1,int m,int d,int h,int f){
        byte[] st1=ArrayOperation.intToByte(y),st11=ArrayOperation.intToByte(y1),st2=ArrayOperation.intToByte(m),st3=ArrayOperation.intToByte(d),st4=ArrayOperation.intToByte(h),st5=ArrayOperation.intToByte(f);
        byte[] st={(byte) 0x08,(byte) 0x0F,st1[1],st11[1],st2[1],st3[1],st4[1],st5[1],(byte)0x00};
        return st;
    }
    //查询设备是否已激活
    public static byte[] set_isoractivation={(byte)0x02,(byte)0x42,(byte)0x00};
    //上位机配置激活设备
    public static byte[] set_activation={(byte)0x02,(byte)0x41,(byte)0x00};
}
