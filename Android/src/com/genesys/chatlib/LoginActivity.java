package com.genesys.chatlib;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genesys.chatlib.enums.APICalls;
import com.genesys.chatlib.models.Chat;
import com.genesys.chatlib.models.Session;
import com.genesys.chatlib.services.AsyncResponse;
import com.genesys.chatlib.services.HTTPService;

public class LoginActivity extends Activity implements AsyncResponse {

	private Chat chat = new Chat();
	private Session chatSession = new Session();

	private EditText et_firstname_login;
	private EditText et_lastname_login;
	private EditText et_nickname_login;
	private EditText et_subject_login;
	private EditText et_email_login;
	private Boolean isValid;
	
	HTTPService chatRequestCall = new HTTPService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
		chatRequestCall.delegate = this;
	}
	
	private void initViews() {
		et_firstname_login = (EditText) findViewById(R.id.et_firstname_login);
		et_lastname_login = (EditText) findViewById(R.id.et_lastname_login);
		et_nickname_login = (EditText) findViewById(R.id.et_nickname_login);
		et_subject_login = (EditText) findViewById(R.id.et_subject_login);
		et_email_login = (EditText) findViewById(R.id.et_email_login);
	}
	
	public void doChat (View view) {
		isValid = true;
		chat.setFirstName(checkNull(et_firstname_login));
		chat.setLastName(checkNull(et_lastname_login));
		chat.setNickname(checkNull(et_nickname_login));
		chat.setSubject(checkNull(et_subject_login));
		chat.setEmail(checkNull(et_email_login));
		
		if(isValid) {
			// Do network call.			
			JSONObject callParams = new JSONObject();
			JSONObject params = new JSONObject();
			try {
				callParams.put("operationName", "RequestChat");
				callParams.put("nickname", chat.getNickname());
				callParams.put("subject", chat.getSubject());
				
				params.put("url", getResources().getString(R.string.chatlib_baseurl));
				params.put("type", APICalls.REQUESTCHAT.getTypeCode());
				params.put("callParams", callParams);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			chatRequestCall.execute(params);
		}
		else {
			// MakeToast.show().
			 Toast.makeText(this, "Please provide all the info", Toast.LENGTH_LONG).show();
		}
	}
	
	private String checkNull (EditText editText) {
		if(editText != null && editText.getText() != null && editText.getText().length() > 0) {
			return editText.getText().toString();
		}
		else {
			isValid = false;
			return "";
		}
	}
	
	@Override
	public void onWebCallFinish(JSONObject result, int type) {
		if(type == APICalls.REQUESTCHAT.getTypeCode()) {
			chatSession.initSession(result);
			
			Intent chatIntent = new Intent(LoginActivity.this, ChatActivity.class);
			Bundle mBundle = new Bundle(); 
			mBundle.putParcelable("session", chatSession); 
			chatIntent.putExtras(mBundle);			
			startActivity(chatIntent);
		}
	}
	
	public void doCancel (View view) {
		finish();
	}	
}
