package com.example.jh.testforsim_uam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jh.testforsim_uam.dualsim.DoubleSimOperator;
import com.example.jh.testforsim_uam.dualsim.DualsimBase;

public class MainActivity extends AppCompatActivity {
    private DualsimBase myDualsimBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ((TextView) findViewById(R.id.myTextView)).setText(
                "sim0State:" + (myDualsimBase = DoubleSimOperator.getDoubleSim(getApplicationContext())).getSim0State() +
                        "\r\nsim1State:" + myDualsimBase.getSim1State() +
                        "\r\nsim0Imsi:" + myDualsimBase.getSim0Imsi() +
                        "\r\nsim1Imsi:" + myDualsimBase.getSim1Imsi()
        );


    }
}
