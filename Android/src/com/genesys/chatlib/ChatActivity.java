package com.genesys.chatlib;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.color.speechbubble.AwesomeAdapter;
import com.color.speechbubble.Message;
import com.genesys.chatlib.enums.APICalls;
import com.genesys.chatlib.models.Session;
import com.genesys.chatlib.services.AsyncResponse;
import com.genesys.chatlib.services.HTTPService;
import com.genesys.chatlib.services.Polling;

public class ChatActivity extends ListActivity implements AsyncResponse {

	HTTPService sendMessageCall = new HTTPService();
	Session chatSession;
	Thread pollingTimer;
	Polling recieveMessageCall;
	Boolean interrupted = false;
	
	EditText et_chat;
	
	ArrayList<Message> messages;
	AwesomeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		initViews();
		
		chatSession = (Session)getIntent().getParcelableExtra("session"); 			
		sendMessageCall.delegate = this;

		startPolling();
		
		messages = new ArrayList<Message>();
		adapter = new AwesomeAdapter(this, messages);
		setListAdapter(adapter);
	}

	private void initViews() {
		et_chat = (EditText)findViewById(R.id.et_chat);
	}
	
	private void sendMessage(String message) {
		JSONObject callParams = new JSONObject();
		JSONObject params = new JSONObject();
		try {
			callParams.put("operationName", "SendMessage");
			callParams.put("text", message);
			
			params.put("url", getResources().getString(R.string.chatlib_baseurl) +"/"+ chatSession.getId());
			params.put("type", APICalls.MESSAGE.getTypeCode());
			params.put("callParams", callParams);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendMessageCall.execute(params);
	}
	
	public void sendMessage(View view) {
		sendMessage(et_chat.getText().toString());
	}
	
	@Override
	public void onWebCallFinish(JSONObject result, int type) {
		if(type == APICalls.MESSAGE.getTypeCode()) {
			messages.add(new Message(et_chat.getText().toString(), true));
			adapter.notifyDataSetChanged();
			
			et_chat.setText("");
			sendMessageCall = new HTTPService();
			sendMessageCall.delegate = this;
		}
		else if(type == APICalls.GETTRANSCRIPT.getTypeCode()) {
			
			try {
			JSONArray jsonMessages = result.getJSONArray("messages");
			for (int i=0; i<jsonMessages.length(); i++){
				JSONObject message = jsonMessages.getJSONObject(i);
				if(message.getString("type").compareToIgnoreCase("Text") == 0 && message.getJSONObject("from").getString("type").compareToIgnoreCase("Agent") == 0) {
					messages.add(new Message(message.getString("text"), false));
					adapter.notifyDataSetChanged();
				}
			}
			}
			catch (Exception e) {}
			startPolling();
		}
	}
	
	private void startPolling() {
		Log.d("TEST","startPolling()");
		pollingTimer = new Thread()
		{
        	public void run()
        	{
        		try
        		{
        			sleep(3000);
        		}
        		catch (InterruptedException e)
        		{
        			Log.e("Error","polling interrupted, " + e);
        			interrupted = true;
        			return;
        		}
        		finally
        		{
        			if(!interrupted) {
        				pollForResponse();
        			}
        		}
       		}
		};
		pollingTimer.start();
	}
	
	public void pollForResponse() {
		Log.d("TEST","pollForResponse()");
		recieveMessageCall = new Polling();
		recieveMessageCall.delegate = this;
		recieveMessageCall.execute(getResources().getString(R.string.chatlib_baseurl), chatSession.getId());
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if(recieveMessageCall != null && recieveMessageCall.delegate!=null) {
			recieveMessageCall.delegate = null;
			recieveMessageCall.cancel(true);
		}
		pollingTimer.interrupt();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		if(recieveMessageCall != null && recieveMessageCall.delegate!=null) {
//			recieveMessageCall.delegate = null;
//			recieveMessageCall.cancel(true);
//		}
	}
}

