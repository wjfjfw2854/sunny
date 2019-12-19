package gebilaolitou.ndkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import gebilaolitou.ndkdemo.libtools.ToolUtil;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private EditText edt1;
    private EditText edt2;
    private TextView txtShowNative;

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
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}
