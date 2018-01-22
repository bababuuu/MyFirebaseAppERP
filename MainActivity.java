package app.firebase.test.myfirebaseapp;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;
    TextView txtViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();

        try{
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        btnShowLocation = (Button) findViewById(R.id.buttonGetLoc);
        btnShowLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                gps = new GPSTracker(MainActivity.this);
                txtViewLocation = (TextView) findViewById(R.id.locationText);
                if (gps.CanGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    txtViewLocation.setText(latitude+""+longitude);
                    myRef = database.getReference("Location");
                    myRef.setValue(latitude+","+longitude);

                }else{
                    gps.showSettingsAlert();
                }
            }
        });

//        database = FirebaseDatabase.getInstance();
//        myRef =  database.getReference("userName");
//        Query query =  myRef.orderByChild("userName").equalTo("Author1");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String newDataString = dataSnapshot.getValue(String.class);
//                TextView txtView = (TextView) findViewById(R.id.myText);
//                txtView.setText(newDataString);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        //myRef.setValue("Yuhhuuu");
    }

//    public void setTextView(String text){
//        TextView textView = (TextView) findViewById(R.id.myText);
//        textView.setText(text);
//    }


//    public void onClickButton(View v){
//
//
//
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String input = editText.getText().toString();
//        myRef.setValue(input);
//
//    }


}
