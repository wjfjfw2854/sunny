package cn.wjf.apphan.frags;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import cn.wjf.apphan.R;
import cn.wjf.apphan.base.BaseFrag;
import cn.wjf.apphan.databinding.FragHandBinding;
import cn.wjf.apphan.frags.vm.HandVm;

public class HandFrag extends BaseFrag {
    private FragHandBinding db;
    private HandVm vm;
    private final static int MSG1 = 1;
    private final static int MSG2 = 2;
    private Thread thread1;
    private Thread thread2;
    private Handler handler1;
    private Handler handler2;
    private int index1;
    private int index2;
    @Override
    public void init(LayoutInflater inflater) {
        db = (FragHandBinding) setContentView(R.layout.frag_hand);
        vm = ViewModelProviders.of(this).get(HandVm.class);
        db.setVm(vm);
        initThread1();
        initThread2();
        initV();
        int x = 0;
        int size = ++x;
        int d = 0;
        int midSize = d++;
        Log.e("wjf>>>>","size: " + size + "--x: " + x + "--midSize: " + midSize + "--d: " + d);
    }

    private void initV() {
        db.but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thread1.isAlive())
                {
                    click1();
                }
            }
        });
        db.but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thread2.isAlive())
                {
                    click2();
                }
            }
        });
    }

    public void initThread1()
    {
        if(thread1 == null) {
            thread1 = new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    if (handler1 == null) {
                        handler1 = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == MSG1) {
                                    db.ed1.setText((String)msg.obj);
                                }
                            }
                        };
                    }
                    click1();
                    Looper.loop();
                }
            };
        }
        thread1.setName("Thread1");
        thread1.start();
    }

    private void click1() {
//        String str1 = db.ed1.getText().toString().trim();
        if(handler2 != null /*&& !TextUtils.isEmpty(str1)*/)
        {
            index1 ++;
            Message msg = handler2.obtainMessage(MSG2);
//            msg.obj = str1 + "::告之2--" + index1 + "次";
            msg.obj = "::告之2--" + index1 + "次";
            handler2.sendMessage(msg);
        }
    }

    public void initThread2()
    {
        if(thread2 == null) {
            thread2 = new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    if (handler2 == null) {
                        handler2 = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == MSG2) {
                                    db.ed2.setText((String)msg.obj);
                                }
                            }
                        };
                    }
                    click2();
                    Looper.loop();
                }
            };
        }
        thread2.setName("Threader2");
        thread2.start();
    }

    private void click2() {
//        String str2 = db.ed2.getText().toString().trim();
        if(handler1 != null /*&& !TextUtils.isEmpty(str2)*/)
        {
            index2 ++;
            Message msg = handler1.obtainMessage(MSG1);
//            msg.obj = str2 + "::回答1--" + index2 + "次";
            msg.obj = "::回答1--" + index2 + "次";
            handler1.sendMessage(msg);
        }
    }

    private void closeThread1()
    {
        if(thread1 != null && thread1.isAlive())
        {
            thread1.interrupt();
            thread1 = null;
        }
    }

    private void closeThread2()
    {
        if(thread2 != null && thread2.isAlive())
        {
            thread2.interrupt();
            thread2 = null;
        }
    }
}
