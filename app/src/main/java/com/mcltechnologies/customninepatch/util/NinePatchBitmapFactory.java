package com.mcltechnologies.customninepatch.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: bgriffey
 * Date: 12/27/12
 * Time: 2:37 PM
 */
public class NinePatchBitmapFactory {

    private static final int NO_COLOR = 0x00000001;

    public static NinePatchDrawable createNinePatchWithCapInsets(Resources res, Bitmap bitmap,
                                                                 List<Range> rangeListX, List<Range> rangeListY , String srcName) {
        ByteBuffer buffer = getByteBuffer(rangeListX,rangeListY);
        return new NinePatchDrawable(res,bitmap, buffer.array(), new Rect(), srcName);
    }

    private static ByteBuffer getByteBuffer(List<Range> rangeListX, List<Range> rangeListY) {
        ByteBuffer buffer = ByteBuffer.allocate(4 + 4*7 + 4*2*rangeListX.size() + 4*2*rangeListY.size() + 4*9).order(ByteOrder.nativeOrder());
        buffer.put((byte)0x01); // was serialised
        buffer.put((byte) (rangeListX.size() * 2) ); // x div
        buffer.put((byte) (rangeListY.size() * 2) ); // y div
        buffer.put((byte)0x09); // color

        // skip
        buffer.putInt(0);
        buffer.putInt(0);

        // padding
        buffer.putInt(0);
        buffer.putInt(0);
        buffer.putInt(0);
        buffer.putInt(0);

        // skip 4 bytes
        buffer.putInt(0);

        for (Range range : rangeListX) {
            buffer.putInt( range.start );
            buffer.putInt( range.end );
        }
        for (Range range : rangeListY) {
            buffer.putInt( range.start);
            buffer.putInt( range.end );
        }
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);
        buffer.putInt(NO_COLOR);

        return buffer;
    }

    public static NinePatchDrawable getNinePatchFromBitmap(Context context, Bitmap bitmap) {
        final Range rangeX = new Range(5, 120);
        final Range rangeY = new Range(5, 37);

        return createNinePatchWithCapInsets(
                context.getResources(),
                bitmap,
                new ArrayList<Range>(){{add(rangeX);}},
                new ArrayList<Range>(){{add(rangeY);}},
                null
        );
    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath){
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


    public static class Range {
        private int start;
        private int end;

        public Range(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
}