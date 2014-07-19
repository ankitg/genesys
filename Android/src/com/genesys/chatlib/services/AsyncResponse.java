package com.genesys.chatlib.services;

import org.json.JSONObject;
import com.genesys.chatlib.enums.APICalls;

public interface AsyncResponse {
    void onWebCallFinish(JSONObject result, int type);
}