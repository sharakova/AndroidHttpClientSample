package jp.sharakova.android.httpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        final TextView text = (TextView)findViewById(R.id.text);
        
        new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				try {
					// AndroidHttpClientを使ってみた (Android 2.2 Froyoから使えます)
					AndroidHttpClient client = AndroidHttpClient.newInstance("Android UserAgent");
					HttpResponse res = client.execute(new HttpGet("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=fuzzy%20monkey"));
					
					// HttpResponseのEntityデータをStringへ変換
			        BufferedReader reader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			        StringBuilder builder = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			        	builder.append(line + "\n");
			        }
			        
			        return builder.toString();
				} catch (Exception e) {
					e.getStackTrace();
					return "";
				}
			}
			
			@Override
			protected void onPostExecute(String result) {
				text.setText(result);
			}
        	
        }.execute();

    }
}