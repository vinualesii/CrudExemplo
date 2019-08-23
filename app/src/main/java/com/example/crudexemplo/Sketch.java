package com.example.crudexemplo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Sketch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sketch);
        //btnSave = (Button) findViewById(R.id.btnsave);


        Tela view = new Tela(this);
        setContentView(view);
        addContentView(view.btnSave, view.params);

    }

    public class Tela extends View  {
        Paint paint;
        Canvas canvas = new Canvas();
        View view;
        Path path;
        Bitmap bitmap;
        Button btnSave;
        LinearLayout canvasLL;

        public RelativeLayout.LayoutParams params;


        public Tela (Context context) {
            super(context);

            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12);
            btnSave = new Button(context);

            path = new Path();
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            btnSave.setLayoutParams(params);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveImage(); // mSignature.save(view,StoredPath);
                    Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();

                }
            });
        }

        public Tela (Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Tela (Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

/*        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
        }
*/
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();
            float upx = 0;
            float upy = 0;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(touchX, touchY);
                    canvas.drawPath(path, paint);
                    break;

                case MotionEvent.ACTION_MOVE:
                    path.lineTo(touchX, touchY);
                    canvas.drawPath(path, paint);
                    break;

                case MotionEvent.ACTION_UP:
                    path.lineTo(touchX, touchY);
                    canvas.drawPath(path, paint);
                    break;
                    default:
                        return false;
            }
            invalidate();
            return true;
            }

        protected void onDraw (Canvas canvas) {
            super.onDraw(canvas);

            int w = getWidth();
            int h = getHeight();
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.odontograma);
            Bitmap scaled = bitmap.createScaledBitmap(bitmap, w, h, true);
            canvas.drawBitmap(scaled,0,0,null);


            //canvas.drawColor(Color.BLUE);
            //canvas.drawBitmap(bitmap,0,0,paint);
            canvas.drawPath(path,paint);
            }

        void saveImage() {


            try {
                String filename = Environment.getExternalStorageDirectory().toString();

                File f = new File(filename ,"/DCIM/myImage.png");
                f.createNewFile();
                System.out.println("file created " + f.toString());
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        }

    }
