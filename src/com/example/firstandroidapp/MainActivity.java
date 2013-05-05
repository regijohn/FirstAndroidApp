package com.example.firstandroidapp;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	TextView txtView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void sendMessage(View view) {
    	
    	txtView = (TextView)findViewById(R.id.textView1);
    	txtView.setText("Sending...");
    	new DataServer().execute();
    	
    }
    
    private class DataServer extends AsyncTask<Void, Void, String>{

    	@Override
    	protected String doInBackground(Void... arg0) {
    		String stringResponse = null;
    		
    		try {
    			//Create the JSON payload
    	    	JSONObject jsonObject = new JSONObject();
    	    	jsonObject.put("data1", 1);
    	    	jsonObject.put("data2", 2);
    	    	StringEntity entity = new StringEntity(jsonObject.toString());
    	    	
    	    	//10.0.2.2 is the loopback address, not localhost or 127.0.0.1
    	    	HttpPost request = new HttpPost("http://10.0.2.2:8080/perfdata");
    	    	
    	    	//Set the HTTP headers
    	    	request.addHeader("Content-Type", "application/json");
    	    	request.addHeader("charset","utf-8");

    	    	//Load the JSON payload
    	    	request.setEntity(entity);
    	    	
    	    	//Create the HTTP client and send the request
    	    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	        HttpResponse httpResponse = httpClient.execute(request);
    	        
    	        //Extract the response
    	        stringResponse = EntityUtils.toString(httpResponse.getEntity());
    	        
    	        Log.d("FirstAndroidApp.DataServer", stringResponse);
        	
    		    }
    		    catch(Exception e) {
    		    		Log.d("FirstAndroidApp-EXCEPTION", e.toString());
    		    }
    		
    			return stringResponse;
    	}
    	
    	protected void onPostExecute(String httpResponse) {
    		
    		txtView.setText(httpResponse);
    	}
    	
    }
}
