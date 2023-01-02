package com.example.mushroomcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealTimeUpdate extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Ref = database.getReference("Mushroom Growing House Condition/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_update);

        ProgressBar Hu = findViewById(R.id.progress_barHu);
        ProgressBar Temp = findViewById(R.id.progress_barTemp);
        TextView tHu = findViewById(R.id.textViewHu);
        TextView tTemp = findViewById(R.id.textViewTemp);
        TextView tFan = findViewById(R.id.textViewFan);

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float valueTemp = Float.parseFloat(dataSnapshot.child("Tempurature").getValue().toString());
                Temp.setProgress(Math.round(valueTemp));
                tTemp.setText(valueTemp.toString());

                Float valueHu = Float.parseFloat(dataSnapshot.child("Humidity").getValue().toString());
                Hu.setProgress(Math.round(valueHu));
                tHu.setText(valueHu.toString());

                if(valueTemp>28){
                    tFan.setText("High Temperature - Sprinkles On");
                    if(valueHu>=85){

                        tFan.setText("High Temperature - Sprinkles On");

                    }else{

                        tFan.setText("High Temperature, Low Humidity Level - Sprinkles On, Humidifier On");

                    }

                }else{

                    tFan.setText("Normal Temperature");

                    if(valueHu>=85){

                        tFan.setText("Normal Temperature, Normal Humidity");

                    }else{

                        tFan.setText("Low Humidity Level - Humidifier On");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}