package com.example.hi.od;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.ArrayList;

public class GetFileList extends Activity implements View.OnClickListener {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    public Button b[];
    public FTPClient ftp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song);


        /*
        String fileURL="http://192.168.140.1/";
        URL url = null;
        try {
            String fileName = "";
            url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                String disposition = httpConn.getHeaderField("Content-Disposition");

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length() - 1);
                    }
                }
            }
            LinearLayout lv = (LinearLayout) findViewById(R.id.lin);
            Button button1 = new Button(this);
            button1.setText(fileName);
            button1.setOnClickListener(this);
            lv.addView(button1);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){}






        */


        try {

            TextView txt = (TextView) findViewById(R.id.textView2);
            txt.setText("hello2");

            TextView tx1 = (TextView) findViewById(R.id.textView2);
            String server = "192.168.179.1";
            int port = 21;
            String user = "drashti";
            String pass = "password";
            tx1 = (TextView) findViewById(R.id.textView2);
            //tx2 = (EditText) findViewById(R.id.tx2);


            ftp = new FTPClient();

            //ftp = (FTPSClient)Globals.global_ftps;

            ftp.setDefaultTimeout(10000);
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ftp.connect(server, port);

            ftp.login(user, pass);
            //  FTPFile[] files1 = ftp.listFiles("/");
            showServerReply(ftp);
            String[] files2 = ftp.listNames();
            printNames(files2);
            //tx1.setText(txt.getText()+files2.toString());


           // FTPFile[] ftpFiles = ftp.listFiles();
            //TextView tz = (TextView) findViewById(R.id.textView3);
            //tz.setText((int) ftpFiles[0].getSize());

           /* if (ftpFiles != null && ftpFiles.length > 0) {
                //loop thru files
                for (FTPFile file : ftpFiles) {
                    if (!file.isFile()) {
                        continue;

                    //System.out.println("File is " + file.getName());

                    //get output stream
                    OutputStream output;
                    output = new FileOutputStream("/sdcard/hello/songy" + "/" + file.getName());
                    //get the file from the remote system
                    ftp.retrieveFile(file.getName(), output);
                    //close output stream
                    output.close();

                    //delete the file
                    ftp.deleteFile(file.getName());

                }
            }

            ftp.logout();
            ftp.disconnect();*/
        } catch (Exception e) {
            TextView txt = (TextView) findViewById(R.id.textView2);
            txt.setText(txt.getText().toString() + e.toString());
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    private void printNames(String files[]) {

        try {

            ArrayList<String> list = new ArrayList<>();
            //ListView l=(ListView)findViewById(R.id.listView);
            // TextView tx2 = (TextView) findViewById(R.id.textView2);
            if (files != null && files.length > 0) {
                for (String aFile : files) {
                    //tx2.setText(tx2.getText().toString() + "\n eee bhai" + aFile);
                    list.add(aFile);
                }
            }

            b = new Button[list.size()];
            int i = 0;
            for (String aFile : list) {
                TextView t = (TextView) findViewById(R.id.textView2);
                LinearLayout lv = (LinearLayout) findViewById(R.id.lin);
                b[i] = new Button(this);
                b[i].setId(i);

                b[i].setWidth(300);
                b[i].getBackground().setAlpha(10);        //Button button1 = new Button(this);
                b[i].setText(aFile);
                b[i].setTextColor(Color.WHITE);
                b[i].setOnClickListener(this);
                lv.addView(b[i]);
                i++;

            }

            //final StableArrayAdapter adapter = new StableArrayAdapter(this,
            //android.R.layout.simple_list_item_1, list);
            //l.setAdapter(new ArrayAdapter<String>(this,
            //android.R.layout.simple_list_item_1,list));


        } catch (Exception e) {
            TextView t = (TextView) findViewById(R.id.textView2);
            t.setText(t.getText().toString() + e.toString());
        }
    }


    private void showServerReply(FTPClient ftpClient) {
        TextView tx1 = (TextView) findViewById(R.id.textView2);
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                tx1.setText(tx1.getText().toString() + "\n" + "SERVER: " + aReply);
            }
        }
    }

    @Override
    public void onClick(View view) {

        MediaPlayer player;
        //FileInputStream fis = null;
        //ftp://localhost/%5BSongs.PK%5D%2001%20-%20Daawat-E-Ishq.mp3
        TextView t = (TextView) findViewById(R.id.textView2);
        String s;
        String x = view.getId() + "";
        Button bx = (Button) findViewById(view.getId());
        s=bx.getText().toString();

        Intent i=new Intent(this,MusicPlayer.class);
        Bundle bundle=new Bundle();

        bundle.putString("song",s);

        i.putExtras(bundle);
        startActivity(i);

/*
        Button bx = (Button) findViewById(view.getId());
        t.setText(bx.getText().toString());
        String path = "http://192.168.140.1/" + bx.getText().toString();
        try {

            player = new MediaPlayer();
            //fis = new FileInputStream("D://ftp/song/[Songs.PK] 01 - Daawat-E-Ishq");
            //player.setDataSource(path);
            //player.setLooping(true);
            //player.setOnErrorListener(this);
            //player.setOnBufferingUpdateListener(this);
            //player.setOnCompletionListener(this);
            // player.setOnPreparedListener(this);
            //player.setAudioStreamType(2);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (Exception e) {

            TextView tr = (TextView) findViewById(R.id.textView2);
            tr.setText(e.toString());
        }
        /*

        MediaPlayer m = new MediaPlayer();

        m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });

        try {


            AssetFileDescriptor descriptor = mContext.getAssets().openFd(soundPath);
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                    descriptor.getLength());

            descriptor.close();

            m.prepare();
            m.setVolume(100f, 100f);
            m.setLooping(false);
            m.start();

        } catch (Exception e) {
            //Your catch code here.
        }*/
    }

    public ArrayList<String> getList() throws IOException {

        ArrayList<String> songlist=new ArrayList<>();

        try {
            String[] files2 = ftp.listNames();
            for (String afile:files2)
            {
                songlist.add(afile);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return songlist;
    }
}
