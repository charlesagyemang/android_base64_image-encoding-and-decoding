package com.example.pianoafrik.simplepicturetakingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements IPickResult {

    private ImageView imageView;
    private TextView mTextMessage;
    String string = "Hello Charles";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView    = (ImageView) findViewById(R.id.imageView);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        PickSetup setup = new PickSetup();

        PickImageDialog.build(setup).show(this);


    }

    @Override
    public void onPickResult(PickResult pickResult) {

        if (pickResult.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);
           // imageView.setImageURI(pickResult.getUri());


//            byte[] encodeValue = Base64.encode(string.getBytes(), Base64.DEFAULT);
//            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);



            //mTextMessage.setText(encodeValue.toString());

            //Toast.makeText(this,  pickResult.toString(), Toast.LENGTH_LONG).show();

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());
            //Toast.makeText(this,  pickResult.getUri().toString(), Toast.LENGTH_LONG).show();

            //If you want the Bitmap.
            //imageView.setImageBitmap(pickResult.getBitmap());
            //getImageView().setImageBitmap(pickResult.getBitmap());

            //encode
            Bitmap bm = pickResult.getBitmap();
            String encodedString  = getEncoded64ImageStringFromBitmap(bm);
            //ecodedString is wht you have to save in the db.

            //Decode
            //when you call it from the db, this is how you decode it.
            byte[] decodedString = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);

            //get a bitmap from from the decoded string
            Bitmap decodedByte   = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


            mTextMessage.setText(decodedString.toString());

            //grab the decoded bitmap ad srImageLayout in the custom image view
            imageView.setImageBitmap(decodedByte);

            //Image path
            //r.getPath();
            //Toast.makeText(this, pickResult.getPath(), Toast.LENGTH_LONG).show();

        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
//
   }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }
}
