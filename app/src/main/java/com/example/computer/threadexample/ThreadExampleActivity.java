package com.example.computer.threadexample;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ThreadExampleActivity extends AppCompatActivity {

    // Variable Declarations
    int progressStatus, red, pink, purple, deepPurple, indigo, blue, lightBlue, cyan, teal, green, lime;
    TextView textView2, myTextView;
    Button buttonClick, buttonClick2;
    ProgressBar progressBar;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_example);

        // Attaching Variables to their corresponding Views
        myTextView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        buttonClick = findViewById(R.id.button);
        buttonClick2 = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);
        cardView = findViewById(R.id.card_view);

        // Getting colour resources
        red = getResources().getColor(R.color.colorRed);
        pink = getResources().getColor(R.color.colorPink);
        purple = getResources().getColor(R.color.colorPurple);
        deepPurple = getResources().getColor(R.color.colorDeepPurple);
        indigo = getResources().getColor(R.color.colorIndigo);
        blue = getResources().getColor(R.color.colorBlue);
        lightBlue = getResources().getColor(R.color.colorLightBlue);
        cyan = getResources().getColor(R.color.colorCyan);
        teal = getResources().getColor(R.color.colorTeal);
        green = getResources().getColor(R.color.colorGreen);
        lime = getResources().getColor(R.color.colorLime);

        // Setting an OnClickListener on the button that changes the background colour of the cardView
        buttonClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> colorList = Arrays.asList(red, pink, purple, deepPurple, indigo, blue, lightBlue, cyan, teal, green, lime);
                Random random = new Random();
                int randomColor = colorList.get(random.nextInt(colorList.size()));
                cardView.setBackgroundColor(randomColor);
            }
        });

        // Setting an OnClickListener on the button that shows the Time and Date
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                progressStatus = 0;

                // Creating the Background Thread to enable task run in background
                class BackgroundThread extends Thread {
                    @Override
                    public void run() {
                        final Message message = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
                        String dateString = dateFormat.format(new Date());
                        bundle.putString("myKey", dateString);
                        message.setData(bundle);
                        while (progressStatus < 10) {
                            progressStatus++;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                    textView2.setText("Progress: " + progressStatus + "/" + progressBar.getMax());
                                }
                            });

                            // This would cause a 1 second delay for every increase in progressStatus
                            // This would result in a total delay of 10 seconds since progressStatus runs from 0 - 10
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendMessage(message);
                    }
                }
                BackgroundThread backgroundThread = new BackgroundThread();
                backgroundThread.start();
            }
        });
    }


    // Handling the Thread
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String newString = bundle.getString("myKey");
            myTextView.setText(newString);
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
        }
    };
}

    /**Alternatively; create a Runnable*/
    /**Runnable runnable = new Runnable() {
        public void run() {

            long endTime = System.currentTimeMillis() +
                    20 * 1000;

            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime -
                                System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }

            }
            handler.sendEmptyMessage(0);
        }
    };

    Thread mythread = new Thread(runnable);
      mythread.start();
      }*/