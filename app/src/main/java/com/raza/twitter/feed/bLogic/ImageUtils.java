package com.raza.twitter.feed.bLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class ImageUtils {

    public static void loadImage(Context context, String url, ImageView imageView) {
        if (url == null || url.length() == 0 || url.equals("null")) {
            return;
        }
        Picasso.with(context).load(url).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, Callback mListener) {
        if (url == null || url.length() == 0 || url.equals("null")) {
            return;
        }
        Picasso.with(context).load(url).into(imageView, mListener);
    }

//    public static void loadResizedImage(Context context, String url, ImageView imageView, int reqWidth, int reqHeight) {
//        if (url == null || url.length() == 0 || url.equals("null")) {
//            return;
//        }
//        Picasso.with(context).load(url).error(R.drawable.user_placeholder_1).resize(reqWidth, reqHeight).into(imageView);
//    }
//
//    public static void loadResizedImage(Context context, File file, ImageView imageView, int reqWidth, int reqHeight) {
//        if (file == null || !file.exists()) {
//            return;
//        }
//        Picasso.with(context).load(file).error(R.drawable.user_placeholder_1).resize(reqWidth, reqHeight).error(R.drawable.user_placeholder_1).into(imageView);
//    }

    public static void loadImage(Context context, String url, ImageView imageView, int radius, int margin) {
        if (url == null || url.length() == 0 || url.equals("null")) {
            return;
        }
        Picasso.with(context)
                .load(url).transform(new RoundedTransformation(radius, margin))
                //.error(R.drawable.user_placeholder_1)
                .into(imageView);
    }

    public static class RoundedTransformation implements com.squareup.picasso.Transformation {
        private final int radius;
        private final int margin;  // dp

        // radius is corner radii in dp
        // margin is the board in dp
        public RoundedTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(final Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }

}
