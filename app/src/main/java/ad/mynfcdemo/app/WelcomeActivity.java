package ad.mynfcdemo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ad.mynfcdemo.R;

/**
 * 欢迎页
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);
        //两秒进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(WelcomeActivity.this,ActivePageActivity.class));
                finish();
            }
        },0);
    }
}
