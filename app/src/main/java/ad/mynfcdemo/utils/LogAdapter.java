package ad.mynfcdemo.utils;

import android.content.Context;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
 import android.util.Log;
import android.widget.TextView;

import java.util.List;

import ad.mynfcdemo.R;

/**
 * 日志适配器
 * Created by 农启兵 on 2017/11/24.
 */

public class LogAdapter extends ArrayAdapter<LogNfc>{
    private int resourceId;
    public LogAdapter(Context context, int textViewResourceId, List<LogNfc> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogNfc log=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.xihao=(TextView)view.findViewById(R.id.f_xuhao);
            viewHolder.zaozuo=(TextView)view.findViewById(R.id.f_caozuo);
            viewHolder.timef=(TextView)view.findViewById(R.id.f_time);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.xihao.setText(log.getXihao());
        viewHolder.zaozuo.setText(log.getCaozuo());
        viewHolder.timef.setText(log.getTime());
        return  view;
    }
    class ViewHolder{
        TextView xihao;
        TextView zaozuo;
        TextView timef;
    }
}
