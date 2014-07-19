package com.genesys.chatlib;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.genesys.chatlib.enums.APICalls;
import com.genesys.chatlib.models.Session;
import com.genesys.chatlib.services.AsyncResponse;
import com.genesys.chatlib.services.HTTPService;

public class ChatActivity extends Activity implements AsyncResponse {

	HTTPService sendMessageCall = new HTTPService();
	Session chatSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		chatSession = (Session)getIntent().getParcelableExtra("session"); 			
		sendMessageCall.delegate = this;
		sendMessage("This is a triumph.");
	}

	private void sendMessage(String message) {
		
		JSONObject callParams = new JSONObject();
		JSONObject params = new JSONObject();
		try {
			callParams.put("operationName", "SendMessage");
			callParams.put("text", message);
			
			params.put("url", "http://192.168.43.94:8888/api/v2/chats/" + chatSession.getId());
			params.put("type", APICalls.MESSAGE.getTypeCode());
			params.put("callParams", callParams);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMessageCall.execute(params);
		
	}
	
	@Override
	public void onWebCallFinish(JSONObject result, int type) {
		Log.d("TEST", "Message sent response = " + result);
	}
}
