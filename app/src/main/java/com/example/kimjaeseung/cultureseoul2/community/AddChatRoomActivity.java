package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class AddChatRoomActivity extends Activity {
    @Bind(R.id.community_et_chatroomname)
    EditText chatRoomName;
    @Bind(R.id.community_btn_chatrommcreate)
    Button chatRoomCreateButton;

    private DatabaseReference reference= FirebaseDatabase.getInstance()
            .getReference().getRoot();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_addchatroom);
        ButterKnife.bind(this);

        chatRoomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(chatRoomName.getText().toString(), "");
                reference.updateChildren(map);
                Intent intent=new Intent(AddChatRoomActivity.this,MainActivity.class);
                intent.putExtra("select_page",CommunityFragment.class.getSimpleName());
                startActivity(intent);
            }
        });
    }
}
