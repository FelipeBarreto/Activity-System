package br.ufc.great.iot.activitysystem;

import android.util.Log;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Felipe on 03/12/2015.
 */
public class DataReader{

    private int lineNumber = 0;

    private ReaderThread mReader;

    private boolean running = false;

    private MainActivity mActivity;

    DataReader(MainActivity activity){
        this.mActivity = activity;
    }

    public void startDetection(){
        running = true;
        mReader = new ReaderThread();
        mReader.start();
    }

    public void stopDetection(){
        running = false;
        mReader.interrupt();
    }

    private class ReaderThread extends Thread{

        @Override
        public void run() {
            Scanner scanner = null;
            try {
                scanner = new Scanner(mActivity.getAssets().open("data.csv"));
            } catch (IOException e) {
                Log.e("Data Reader", "Cannot open data file");
                return;
            }

            while(running){
                if(!scanner.hasNextLine()){
                    Log.d("Data Reader", "EOF");
                    lineNumber = 0;
                    running = false;
                    continue;
                }

                String line = scanner.nextLine();
                if(lineNumber == 0){
                    lineNumber++;
                    continue; // skip the first line.
                }

                lineNumber++;
                String[] tokens = line.split(";");
                mActivity.updateUI(tokens[1], tokens[2], tokens[3]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("Data Reader", "Reader Thread interrupted");
                }
            }
        }
    }
}
