package com.example.acer.banglaocr4kids;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private CanvasView canvasView;
    private Button readyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView=(CanvasView) findViewById(R.id.canvas);
        readyBtn=(Button) findViewById(R.id.readybtn);

        readyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File folder= new File(Environment.getExternalStorageDirectory().toString());
                boolean success=false;
                if(!folder.exists())
                {
                    success=folder.mkdirs();

                }
                System.out.println(success+"folder");
                File file=new File(Environment.getExternalStorageDirectory().toString()+"/canvas.png");
                if(!file.exists()){
                    try
                    {
                        success=file.createNewFile();
                    }catch(IOException err)
                    {
                        err.printStackTrace();
                    }
                 System.out.println(success+"file");
                    FileOutputStream ostream=null;
                    try{
                        ostream=new FileOutputStream(file);
                        System.out.println(ostream);
                        View targetV=canvasView;
                        Bitmap bmap=canvasView.getBitmap();
                        Bitmap save=Bitmap.createBitmap(320,480, Bitmap.Config.ARGB_8888);
                        Paint paint=new Paint();
                        paint.setColor(Color.WHITE);
                        Canvas now=new Canvas(save);
                        now.drawRect(new Rect(0,0,320,480),paint);
                        now.drawBitmap(bmap,new Rect(0,0,bmap.getWidth(),bmap.getHeight()),new Rect(0,0,320,480), null);
                        if(save==null){
                            System.out.println("NULL BITMAP SAVED");

                        }
                        save.compress(Bitmap.CompressFormat.PNG,100,ostream);

                    }catch(IOException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"NULL Error", Toast.LENGTH_SHORT).show();
                    }

                    Intent i= new Intent(MainActivity.this, Upload_API.class);
                    startActivity(i);



                }
            }
        });

    }

    public void clearCanvas(View V){
        canvasView.clearCanvas();


    }
}
