package com.mcltechnologies.customninepatch;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.mcltechnologies.customninepatch.util.NinePatchBitmapFactory;

public class MainActivity extends AppCompatActivity {

    Button buttonSmall;
    Button buttonWide;
    Button buttonTall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSmall = (Button) findViewById(R.id.button_small);
        buttonWide = (Button) findViewById(R.id.button_wide);
        buttonTall = (Button) findViewById(R.id.button_tall);

        Bitmap bitmap = NinePatchBitmapFactory.getBitmapFromAsset(getApplicationContext(), "ninepatch.png");
        Rect rect = new Rect(5,5,120,37);
        buttonSmall.setBackground(NinePatchBitmapFactory.getNinePatchFromBitmap(getBaseContext(), bitmap, rect));
        buttonWide.setBackground(NinePatchBitmapFactory.getNinePatchFromBitmap(getBaseContext(), bitmap, rect));
        buttonTall.setBackground(NinePatchBitmapFactory.getNinePatchFromBitmap(getBaseContext(), bitmap, rect));
    }
}
