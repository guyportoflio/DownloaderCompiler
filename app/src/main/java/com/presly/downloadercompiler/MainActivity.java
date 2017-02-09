package com.presly.downloadercompiler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity implements TextDownloader.Callbacks, ImageDownloader.Callbacks {

    private EditText linkImage;
    private EditText linkText;
    private TextView textView1;
    private ProgressDialog progressDialog1;
    private ImageView imageView1;
    private EditText linkVid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.textView1);
        linkText = (EditText) findViewById(R.id.linkText);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        linkImage = (EditText) findViewById(R.id.linkImage);

        linkVid = (EditText) findViewById(R.id.linkVideo);


    }

    public void sendToTextDownloader(View view) {
        TextDownloader textDownloader = new TextDownloader(this);
        String link = "http://android-demo-apis.appspot.com/simple/zen";
        String convertToString = linkText.getText().toString();
        if (convertToString.equals(link) || convertToString.equals("enter link")) {
            textDownloader.execute(link);
        } else {

            textDownloader.execute(convertToString);
        }
    }


    public void downloadImage(View view) {
        ImageDownloader imageDownloader = new ImageDownloader(this);
        String link = "https://cdn3.iconfinder.com/data/icons/snowish/64x64/apps/default-applications-capplet.png";
        String convertToString = linkImage.getText().toString();
        if (convertToString.equals(link) || convertToString.equals("image link")) {
            imageDownloader.execute(link);
        } else {

            imageDownloader.execute(convertToString);
        }
    }


    @Override
    public void onAboutToBegin() {
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Downloading");
        progressDialog1.setMessage("Please wait...");
        progressDialog1.setIcon(R.drawable.loadingball);
        progressDialog1.show();
    }

    @Override
    public void onSuccess(Bitmap downloadedBitmap) {
        progressDialog1.dismiss();
        imageView1.setImageBitmap(downloadedBitmap);
    }

    @Override
    public void onSuccess(String downloadedText) {
        progressDialog1.dismiss();
        textView1.setText(downloadedText);
    }

    @Override
    public void onError(int httpStatusCode, String errorMessage) {
        progressDialog1.dismiss();
        textView1.setText("Error: " + httpStatusCode + " happened.  full message:" + errorMessage);
    }


    public void videoLinkOnClickEvent(View view) {
        String link = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        String convertToString = linkVid.getText().toString();
        if (convertToString.equals(link) || convertToString.equals("video link")) {
            Uri vidUri = Uri.parse(link);
            VideoView vid = (VideoView) findViewById(R.id.myVideo);
            vid.setVideoURI(vidUri);

            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(vid);
            vid.setMediaController(vidControl);
            vid.start();
        } else {
            Uri vidUri = Uri.parse(convertToString);
            VideoView vid = (VideoView) findViewById(R.id.myVideo);
            vid.setVideoURI(vidUri);

            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(vid);
            vid.setMediaController(vidControl);
            vid.start();
        }

    }
}
