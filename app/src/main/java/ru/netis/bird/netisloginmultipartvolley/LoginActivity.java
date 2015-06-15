package ru.netis.bird.netisloginmultipartvolley;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends Activity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextView = (TextView) findViewById(R.id.textView);

        String url = "http://person.com/personals.phtml?user_id=7795853";
        Request request = new JsonRequest<Person>(Request.Method.GET, url, null, new Response.Listener<Person>() {

            @Override
            public void onResponse(Person response) {
                // Do something with our person object
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                // error.networkResponse.statusCode
                // error.networkResponse.data
            }
        }) {

            @Override
            protected Response<Person> parseNetworkResponse(NetworkResponse response) {
                String jsonString = null;
                try {
                    jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Person person = new GsonBuilder().create().fromJson(jsonString, Person.class);
                Response<Person> result = Response.success(person, HttpHeaderParser.parseCacheHeaders(response));
                return result;
            }
        };
        // Access the RequestQueue through your singleton class.
        MainActivity.MySingleton.getInstance(this).addToRequestQueue(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Person {
        long id;
        String firstName;
        String lastName;
        String address;

        @Override
        public String toString() {
            return id + "-" + firstName + "-" + lastName + "-" + address;
        }
    }

}
