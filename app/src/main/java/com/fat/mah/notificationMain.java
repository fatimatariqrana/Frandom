package com.fat.mah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;
public class notificationMain extends AppCompatActivity {

        EditText title,body,appid;
        Button send;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notification_main);
            title=findViewById(R.id.title);
            body=findViewById(R.id.body);
            appid=findViewById(R.id.appid);
            send=findViewById(R.id.send);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'" +
                                body.getText().toString() + "'}, 'headings':{'en':'" +
                                title.getText().toString() + "'} , 'include_player_ids': ['" +
                                appid.getText().toString() + "']}"), new OneSignal.PostNotificationResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                Log.d("response",response.toString());
                            }

                            @Override
                            public void onFailure(JSONObject response) {
                                Log.d("response",response.toString());

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
