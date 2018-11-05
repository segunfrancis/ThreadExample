package com.example.computer.threadexample;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ThreadExampleActivity extends AppCompatActivity {

    // Variable Declarations
    int red, pink, purple, deepPurple, indigo, blue, lightBlue, cyan, teal, green, lime;
    TextView dateTextView;
    Button displayDateButton, colorChangeButton;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_example);

        // Attaching Variables to their corresponding Views
        dateTextView = findViewById(R.id.date_textView);
        displayDateButton = findViewById(R.id.show_date_button);
        colorChangeButton = findViewById(R.id.color_change_button);
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
        colorChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> colorList = Arrays.asList(red, pink, purple, deepPurple, indigo, blue, lightBlue, cyan, teal, green, lime);
                Random random = new Random();
                int randomColor = colorList.get(random.nextInt(colorList.size()));
                cardView.setBackgroundColor(randomColor);
            }
        });

        displayDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                        // Putting the UI thread to sleep for 20 seconds
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
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
            dateTextView.setText(newString);
        }
    };
}