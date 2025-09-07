package com.fat.mah;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fat.mah.main.JustChatModel;
import com.fat.mah.main.MessageChatAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private static final int RC_PHOTO_PICKER =  2;
    public static final String ANONYMOUS = "anonymous";

    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private TextView user_name;
    private String mUsername;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mChatsDarabaseRefrence;
    private DatabaseReference reference;

    private DatabaseReference mUsersDarabaseRefrence;
    private ChildEventListener mChildEventListner;
    private  FirebaseAuth.AuthStateListener mAuthStateListner;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    String m1;
    String m2;
    ImageView pimage;

    private MessageChatAdapter mMessageAdapter;//////////
    List<JustChatModel> mchat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        user_name = (TextView) findViewById(R.id.user_name);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        pimage=(ImageView) findViewById(R.id.pimage);

        recyclerView=findViewById(R.id.messageListView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        m1 = intent.getStringExtra("sender");  // sender
        m2 = intent.getStringExtra("reciever");  // reciever

        byte[] byteArray = intent.getByteArrayExtra("profileImage");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        pimage.setImageBitmap(bmp);

        Log.d("sender", m1);
        Log.d("reciever", m2);

        user_name.setText(m2);
        //user_name.setText(m2);

        FirebaseApp.initializeApp(chatActivity.this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("chat_photos");

        mChatsDarabaseRefrence = mFirebaseDatabase.getReference().child("chats");
        //mUsersDarabaseRefrence = mFirebaseDatabase.getReference().child("users");
        readMessage(m1,m2);

        //mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chatActivity.this, "Select Picture", Toast.LENGTH_LONG).show();

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(getIntent().ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "select picture"), RC_PHOTO_PICKER);
            }
        });

        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });  //make changes hereee

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                JustChatModel messageToSend = new JustChatModel(mMessageEditText.getText().toString(),m1, m2, null);
                mChatsDarabaseRefrence.push().setValue(messageToSend);
                // Clear input box
                mMessageEditText.setText("");
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER)
        {

            if(requestCode == RC_PHOTO_PICKER && resultCode ==RESULT_OK)
            {
                Uri selectedPhoto = data.getData();

                //the photo name will be the last part of the uri segment
                final StorageReference photoRefrence = mStorageReference.child(selectedPhoto.getLastPathSegment());
                photoRefrence.putFile(selectedPhoto).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Task<Uri> photoDownloadedUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        //Toast.makeText(MainActivity.this, photoDownloadedUrl.toString(),Toast.LENGTH_LONG).show();
                        photoRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                JustChatModel newMessage = new JustChatModel(mMessageEditText.getText().toString(),
                                        m1,m2,
                                        uri.toString());
                                mChatsDarabaseRefrence.push().setValue(newMessage);
                            }
                        });
                    }
                });
            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(chatActivity.this, "Sign in aborted",Toast.LENGTH_LONG).show();
                finish();
            }
            else if(resultCode == RESULT_OK)
            {
                Toast.makeText(chatActivity.this, "Successfully Signed in",Toast.LENGTH_LONG).show();
            }
        }
    }


    private void readMessage(final String myId,final String userId)
    {
        mchat=new ArrayList();
        reference=FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    JustChatModel chat=snapshot.getValue(JustChatModel.class);
                    if(chat.getReciever().equals(myId) && chat.getSender().equals(userId) || chat.getReciever().equals(userId) && chat.getSender().equals(myId))
                    {
                        mchat.add(chat);
                    }
                    mMessageAdapter=new MessageChatAdapter(mchat);
                    recyclerView.setAdapter(mMessageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
