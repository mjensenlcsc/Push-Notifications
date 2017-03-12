//Mikel Jensen and Derry Everson
package edu.lcsc.isoptera.derry_mikel.pushnotifications;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linear = (LinearLayout) findViewById(R.id.LinearLayout1);
        linear.setBackgroundColor(Color.TRANSPARENT);


        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        messaging.subscribeToTopic("messaging");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linear.removeAllViewsInLayout();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TextView tv = new TextView(MainActivity.this);
                    tv.setTextSize(24f);
                    String title = snapshot.child("title").getValue().toString();
                    String body = snapshot.child("body").getValue().toString();
                    String html = "<b>" + title + "</b><br/>" + body;
                    //noinspection deprecation
                    tv.setText(Html.fromHtml(html));
                    linear.addView(tv, 0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }


}
