package com.example.acer.banglaocr4kids;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class Upload_API extends AppCompatActivity
{
    private ImageView imageView;
    private Button submit;
    private Dialog dialog;
    private TextView textView;
    public File imgFile;
    String imagePath="/storage/sdcard0/canvas.png";
    private String SERVER_URL="http://113.11.120.208/upload";
    int serverResponseCode = 0;
    URL url;
    HttpURLConnection httpURLConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__api);
        imageView=(ImageView) findViewById(R.id.imgView);
        submit=(Button) findViewById(R.id.btnSubmit);
        textView=(TextView)findViewById(R.id.txtview);
        imgFile= new File("/storage/sdcard0/canvas.png");

        if(imgFile.exists())
        {
            Bitmap mybmap= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(mybmap);

        }
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    String sourceFileUri = "/storage/sdcard0/canvas.png";

                    HttpURLConnection conn = null;
                    DataOutputStream dos = null;
                    String lineEnd = "\r\n";
                    String twoHyphens = "--";
                    String boundary = "*****";
                    int bytesRead, bytesAvailable, bufferSize;
                    byte[] buffer;
                    int maxBufferSize = 1 * 1024 * 1024;
                    File sourceFile = new File(sourceFileUri);

                    if (sourceFile.isFile()) {

                        try {
                            String upLoadServerUri = SERVER_URL;

                            // open a URL connection to the Servlet
                            FileInputStream fileInputStream = new FileInputStream(
                                    sourceFile);
                            URL url = new URL(upLoadServerUri);

                            // Open a HTTP connection to the URL
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true); // Allow Inputs
                            conn.setDoOutput(true); // Allow Outputs
                            conn.setUseCaches(false); // Don't use a Cached Copy
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Connection", "Keep-Alive");
                            conn.setRequestProperty("ENCTYPE",
                                    "multipart/form-data");
                            conn.setRequestProperty("Content-Type",
                                    "multipart/form-data;boundary=" + boundary);
                            conn.setRequestProperty("bill", sourceFileUri);

                            dos = new DataOutputStream(conn.getOutputStream());

                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                                    + sourceFileUri + "\"" + lineEnd);

                            dos.writeBytes(lineEnd);

                            // create a buffer of maximum size
                            bytesAvailable = fileInputStream.available();

                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            buffer = new byte[bufferSize];

                            // read file and write it into form...
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                            while (bytesRead > 0) {

                                dos.write(buffer, 0, bufferSize);
                                bytesAvailable = fileInputStream.available();
                                bufferSize = Math
                                        .min(bytesAvailable, maxBufferSize);
                                bytesRead = fileInputStream.read(buffer, 0,
                                        bufferSize);

                            }

                            // send multipart form data necesssary after file
                            // data...
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens
                                    + lineEnd);

                            // Responses from the server (code and message)
                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn
                                    .getResponseMessage();

                            if (serverResponseCode == 200) {

                                // messageText.setText(msg);
                                Toast.makeText(getApplicationContext(), "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();

                                // recursiveDelete(mDirectory1);

                            }

                            // close the streams //
                            fileInputStream.close();
                            dos.flush();
                            dos.close();

                        } catch (Exception e) {

                            // dialog.dismiss();
                            e.printStackTrace();

                        }
                        // dialog.dismiss();

                    } // End else block


                } catch (Exception ex) {
                    // dialog.dismiss();

                    ex.printStackTrace();
                }

            }




        });

    }

}




