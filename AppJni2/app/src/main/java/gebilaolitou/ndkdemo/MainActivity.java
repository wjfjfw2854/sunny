package gebilaolitou.ndkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import gebilaolitou.ndkdemo.libtools.utils.DataElement;
import gebilaolitou.ndkdemo.libtools.ToolUtil;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private EditText edt1;
    private EditText edt2;
    private TextView txtShowNative;

    private EditText edt00;
    private EditText edt01;
    private EditText edt02;
    private EditText edt10;
    private EditText edt11;
    private EditText edt12;
    private Button butb;
    private TextView txtCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = NDKTools.getStringFromNDK();

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
//        String str = stringFromJNI();
        tv.setText(str);
        txtShowNative = findViewById(R.id.txtShowNative);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        findViewById(R.id.but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x1 = edt1.getText().toString().trim();
                String x2 = edt2.getText().toString().trim();
                txtShowNative.setText(ToolUtil.or(x1,x2));
            }
        });

        edt00 = findViewById(R.id.edt00);
        edt01 = findViewById(R.id.edt01);
        edt02 = findViewById(R.id.edt02);
        edt10 = findViewById(R.id.edt10);
        edt11 = findViewById(R.id.edt11);
        edt12 = findViewById(R.id.edt12);
        txtCalc = findViewById(R.id.txtCalc);
        butb = findViewById(R.id.butb);
        butb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d01 = edt00.getText().toString().trim();
                if(TextUtils.isEmpty(d01)){d01 = "2";}
                String d02 = edt01.getText().toString().trim();
                if(TextUtils.isEmpty(d02)){d02 = "2";}
                String d03 = edt02.getText().toString().trim();
                if(TextUtils.isEmpty(d03)){d03 = "2";}
                String d11 = edt10.getText().toString().trim();
                if(TextUtils.isEmpty(d11)){d11 = "2";}
                String d12 = edt11.getText().toString().trim();
                if(TextUtils.isEmpty(d12)){d12 = "2";}
                String d13 = edt12.getText().toString().trim();
                if(TextUtils.isEmpty(d13)){d13 = "2";}
                ArrayList<DataElement> arys = new ArrayList<>();
                DataElement de = new DataElement();
                de.setX(Long.parseLong(d01));
                de.setY(Long.parseLong(d02));
                de.setZ(Long.parseLong(d03));
                DataElement de1 = new DataElement();
                de1.setX(Long.parseLong(d11));
                de1.setY(Long.parseLong(d12));
                de1.setZ(Long.parseLong(d13));
                arys.add(de);
                arys.add(de1);
                txtCalc.setText(String.valueOf(ToolUtil.calcXyz(arys)));
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}
