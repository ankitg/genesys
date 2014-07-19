package com.genesys.chatlib.services;

import org.json.JSONObject;

public interface AsyncResponse {
    void onWebCallFinish(JSONObject result, int type);
}