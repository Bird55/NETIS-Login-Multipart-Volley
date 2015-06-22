package ru.netis.bird.netisloginmultipartvolley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLog";
    String url = "http://stat.netis.ru/login.pl";
    TextView mText;
    EditText mName;
    EditText mPassword;
    CookieManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mText = (TextView) findViewById(R.id.textView);
        mName = (EditText) findViewById(R.id.nameEditText);
        mPassword = (EditText) findViewById(R.id.passwordEditText);

        Button mBatton = (Button) findViewById(R.id.button);
        mBatton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = MyApp.getInstance().getRequestQueue();
                manager = MyApp.getInstance().getCookieManager();

                MyListener mLictener =new MyListener();
                final String param1 = mName.getText().toString();
                final String param2 = mPassword.getText().toString();
                if (!param1.equals("") && !param2.equals("")) {
                    StringRequest myReq = new StringRequest(Request.Method.POST,
                            url, mLictener, createMyReqErrorListener()) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("param1", param1);
                            params.put("param2", param2);
                            return params;
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Cookie", "SID=-");
                            return headers;
                        }
                    };
                    Log.d(LOG_TAG, "onClick " + myReq.toString());
                    queue.add(myReq);
                }
            }
        });
    }


    private Response.ErrorListener createMyReqErrorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mText.setText(error.getMessage());
                }
            };
        }


    private void setTvResultText(String str) {
            mText.setText(str);
    }

    private class MyListener implements Response.Listener<String> {
        public MyListener() {
        }

        @Override
        public void onResponse(String response) {
            setTvResultText(response);
            Log.d(LOG_TAG, "onResponse " + manager.getCookieStore().getCookies().toString());
            setResult(RESULT_OK);
            finish();
        }
    }
}
