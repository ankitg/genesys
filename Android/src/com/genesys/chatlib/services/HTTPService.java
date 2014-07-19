package com.genesys.chatlib.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class HTTPService extends AsyncTask<JSONObject, Void, JSONObject> {
		int callType;
		public AsyncResponse delegate=null;
		
        @Override
        protected JSONObject doInBackground(JSONObject... params) {
            return postData(params[0]);
        }

        protected void onPostExecute(JSONObject result) {
        	delegate.onWebCallFinish(result, callType);
        }

        public JSONObject postData(JSONObject params) {
            try {
            	// Get call type to determine which API call was made.
            	callType = params.getInt("type");
            	
                // Create a new HttpClient and Post Header
                HttpClient httpclient = new DefaultHttpClient();
            	HttpPost httppost = new HttpPost(params.getString("url"));
            	
                //passes the results to a string builder/entity
                StringEntity se = new StringEntity(params.getJSONObject("callParams") .toString());

                //sets the post request as the resulting string
                httppost.setEntity(se);
                
                //sets a request header so the page receving the request
                //will know what to do with it
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                //Handles what is returned from the page 
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity httpEntity = response.getEntity();
                InputStream jsonInputStream = httpEntity.getContent();  

				BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null){
					sb.append(line + "\n");
				}
				jsonInputStream.close();
				String json = sb.toString();
				
				//Create and return the JSON Object
				return new JSONObject(json);
				
			// DEBUGGING.
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
            return null;
        }	
}
