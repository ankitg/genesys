package com.genesys.chatlib.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.genesys.chatlib.enums.APICalls;

public class Polling extends AsyncTask<String, Void, JSONObject> {
	int callType = APICalls.GETTRANSCRIPT.getTypeCode();
	public AsyncResponse delegate=null;
	
	@Override
	protected JSONObject doInBackground(String... params) {
		return recieveMessage(params[0], params[1]);
	}
	
	 protected void onPostExecute(JSONObject result) {
		 Log.d("TEST","onPostExecute()");
     	delegate.onWebCallFinish(result, callType);
     }
	
	private JSONObject recieveMessage(String baseURL, String chatId) {
		try {
			Log.d("TEST","recieveMessage()");
			HttpClient httpclient = new DefaultHttpClient();

			HttpGet request = new HttpGet();
			URI website = new URI(baseURL + "/" + chatId + "/messages");
			request.setURI(website);
			request.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(request);
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = in.readLine()) != null){
				sb.append(line + "\n");
			}
			in.close();
			String json = sb.toString();
			
			Log.d("TEST",json);
			
			//Create and return the JSON Object
			return new JSONObject(json);
		} catch (ClientProtocolException e) {
        	Log.d("TEST","Client Protocol Exception");
        } 
        catch(UnsupportedEncodingException e){
			Log.d("TEST","Unsupported Encoding Exception");
		}
        catch(UnknownHostException e){
			Log.d("TEST","Unknown Host Exception");
		}
        catch(IOException e){
			Log.d("TEST","IO Exception");
		}
        catch(JSONException e){
			Log.d("TEST","JSON Parse Exception");
		}
        catch(Exception e){
			Log.d("TEST","JSON Fetch Exception");
		}
		return new JSONObject();
	}
}