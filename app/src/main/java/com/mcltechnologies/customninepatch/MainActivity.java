package com.mcltechnologies.customninepatch;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

        Bitmap bitmap = getBitmapFromAsset(getApplicationContext(), "ninepatch.png");
        buttonSmall.setBackground(getNinePatchFromBitmap(bitmap));
        buttonWide.setBackground(getNinePatchFromBitmap(bitmap));
        buttonTall.setBackground(getNinePatchFromBitmap(bitmap));
    }

    private NinePatchDrawable getNinePatchFromBitmap(Bitmap bitmap) {
        final NinePatchBitmapFactory.Range rangeX = new NinePatchBitmapFactory.Range();
        rangeX.start = 5;
        rangeX.end = 120;

        final NinePatchBitmapFactory.Range rangeY = new NinePatchBitmapFactory.Range();
        rangeY.start = 5;
        rangeY.end = 37;

        return NinePatchBitmapFactory.createNinePatchWithCapInsets(
                getResources(),
                bitmap,
                new ArrayList<NinePatchBitmapFactory.Range>(){{add(rangeX);}},
                new ArrayList<NinePatchBitmapFactory.Range>(){{add(rangeY);}},
                null
        );
    }

    private static Bitmap getBitmapFromAsset(Context context, String filePath){
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            return null;
        }
        return bitmap;
    }
}
