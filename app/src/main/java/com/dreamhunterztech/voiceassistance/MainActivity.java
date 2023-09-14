package com.dreamhunterztech.voiceassistance;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sachinvarma.easypermission.EasyPermissionConstants;
import com.sachinvarma.easypermission.EasyPermissionInit;
import com.sachinvarma.easypermission.EasyPermissionList;

import net.gotev.speech.GoogleVoiceTypingDisabledException;
import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;
import net.gotev.speech.SpeechRecognitionNotAvailable;
import net.gotev.speech.ui.SpeechProgressView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements SpeechDelegate,
        TextToSpeech.OnInitListener{

    TextView assistinput;
    SpeechProgressView progress;
    ImageView Voiceassist;
    LinearLayout linearLayout;
    TextToSpeech tts;
    List<String> permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Speech.init(this, getPackageName());
        tts = new TextToSpeech(this, this);
        assistinput = (TextView) findViewById(R.id.assistinput);
        progress = (SpeechProgressView) findViewById(R.id.progress);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        Voiceassist = (ImageView) findViewById(R.id.assistmic);

        permission = new ArrayList<>();
        permission.add(EasyPermissionList.RECORD_AUDIO);

        int[] colors = {
                ContextCompat.getColor(MainActivity.this, android.R.color.holo_purple),
                ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_bright),
                ContextCompat.getColor(MainActivity.this, android.R.color.black),
                ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark),
                ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark),
                ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_dark)
        };
        progress.setColors(colors);

        Voiceassist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // you must have android.permission.RECORD_AUDIO granted at this point
                    Speech.getInstance().startListening(progress,new SpeechDelegate() {
                        @Override
                        public void onStartOfSpeech() {
                            linearLayout.setVisibility(View.VISIBLE);
                            assistinput.setText("");
                            Log.i("speech", "speech recognition is now active");
                        }

                        @Override
                        public void onSpeechRmsChanged(float value) {
                            Log.d("speech", "rms is now: " + value);
                        }

                        @Override
                        public void onSpeechPartialResults(List<String> results) {
                            StringBuilder str = new StringBuilder();
                            for (String res : results) {
                                str.append(res).append(" ");
                            }

                            Log.i("speech", "partial result: " + str.toString().trim());
                        }

                        @Override
                        public void onSpeechResult(String result) {

                            switch (result)
                            {
                                case "" :
                                    Speech.getInstance().say("Sorry i can't hear you please repeat your words");
                                    assistinput.setText(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;
                                case "hello" :
                                    Speech.getInstance().say("Hello sir How may i help you");
                                    linearLayout.setVisibility(View.GONE);
                                    assistinput.setText(result);
                                    break;
                                case "how are you" :
                                    Speech.getInstance().say("I am fine sir");
                                    linearLayout.setVisibility(View.GONE);
                                    assistinput.setText(result);
                                    break;
                                case "tell me about you" :
                                    Speech.getInstance().say("Hy I am Nova Version 1.001 powered By Google  At your service");
                                    assistinput.setText(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;
                                case "tell me about the best trekking routes in ndia" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("ya sure sir ");
                                    openminibrowser(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                case "tell me about programming language" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("ya sure sir ");
                                    openminibrowser(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                case "how you help me" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("as per my feature i can help you about the questionaries you ask");
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                case "I am feeling headache what should I do" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("you need to relax at one place and have some rest and I am opening some suggestion for you Kindly go through them.");
                                    openminibrowser(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                case "I'm feeling headache what should I do" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("you need to relax at one place and have some rest and I am opening some suggestion for you Kindly go through them.");
                                    openminibrowser(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                case "I am feeling like vomiting please help me out" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("you need to drink more water and I am opening some suggestions for you. kindly go through it");
                                    openminibrowser(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                case "I'm feeling like vomiting please help me out" :
                                    assistinput.setText(result);
                                    Speech.getInstance().say("you need to drink more water and I am opening some suggestions for you. kindly go through it");
                                    openminibrowser(result);
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                                default:
                                    assistinput.setText(result);
                                    Speech.getInstance().say("sorry , sir this content is not match up with my feature ");
                                    linearLayout.setVisibility(View.GONE);
                                    break;

                            }

                        }
                    });
                } catch (SpeechRecognitionNotAvailable exc) {
                    Log.e("speech", "Speech recognition is not available on this device!");
                } catch (GoogleVoiceTypingDisabledException exc) {
                    Log.e("speech", "Google voice typing must be enabled!");
                }
            }
        });
    }

    @Override
    public void onStartOfSpeech() {

    }

    @Override
    public void onSpeechRmsChanged(float value) {

    }

    @Override
    public void onSpeechPartialResults(List<String> results) {

    }

    @Override
    public void onSpeechResult(String result) {

    }



    private void openminibrowser(String data)
    {
        final Dialog smallweb = new Dialog(MainActivity.this);
        smallweb.requestWindowFeature(Window.FEATURE_NO_TITLE);
        smallweb.setCanceledOnTouchOutside(false);
        smallweb.setCancelable(false);
        smallweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        smallweb.setContentView(R.layout.smallwebviewlayout);
        ImageView closebtn = (ImageView) smallweb.findViewById(R.id.webdialogclose);
        final ProgressBar pbar = (ProgressBar) smallweb.findViewById(R.id.webprogress);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallweb.dismiss();
            }
        });

        WebView view = (WebView) smallweb.findViewById(R.id.smartwebview);
        view.setSoundEffectsEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setDisplayZoomControls(false);
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setPluginState(WebSettings.PluginState.ON);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().supportZoom();
        view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });
        view.setWebViewClient(new WebViewClient()
                              {


                                  @Override
                                  public void onPageFinished(WebView view, String url) {
                                      if (pbar.getProgress()==100)
                                      {
                                          pbar.setProgress(0);
                                      }
                                  }

                                  @Override
                                  public void onLoadResource(WebView view, String url) {
                                      pbar.setProgress(view.getProgress());
                                  }

                                  @Override
                                  public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                      super.onReceivedError(view, request, error);

                                  }

                                  @Override
                                  public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                                      return super.shouldOverrideUrlLoading(view, request);
                                  }
                              }

        );
        view.loadUrl("https://www.google.com/search?q="+data);
        smallweb.show();

        Window window = smallweb.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            Speech.getInstance().shutdown();
            tts.stop();
            tts.shutdown();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.KOREAN );

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
               /* speakOut();*/
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


/*
    private void speakOut() {
        try {
            translateinit ();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = assistinput.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }*/




        private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
        private static final String CLIENT_SECRET = "PUBLIC_SECRET";
        private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

        /**
         * Entry Point
         */
      void translateinit () throws Exception {
          String fromLang = "en";
          String toLang = "ja";
          String text = "Let's have some fun!";

         translate(fromLang, toLang, text);

      }

        /**
         * Sends out a WhatsApp message via WhatsMate WA Gateway.
         */
        public void translate(String fromLang, String toLang, String text) throws Exception {
            // TODO: Should have used a 3rd party library to make a JSON string from an object
            String jsonPayload = new StringBuilder()
                    .append("{")
                    .append("\"fromLang\":\"")
                    .append(fromLang)
                    .append("\",")
                    .append("\"toLang\":\"")
                    .append(toLang)
                    .append("\",")
                    .append("\"text\":\"")
                    .append(text)
                    .append("\"")
                    .append("}")
                    .toString();

            URL url = new URL(ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
            conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(jsonPayload.getBytes());
            os.flush();
            os.close();

            int statusCode = conn.getResponseCode();
            System.out.println("Status Code: " + statusCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
            ));
            String output;
            while ((output = br.readLine()) != null)
            {
                assistinput.setText(output);
            }
            Log.e(">>test",output);
            conn.disconnect();
        }



    @Override
    protected void onStart() {
        super.onStart();
        new EasyPermissionInit(MainActivity.this, permission);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EasyPermissionConstants.INTENT_CODE:

                if (data != null) {
                    boolean isGotAllPermissions =
                            data.getBooleanExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION, false);

                    if(data.hasExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION)){
                        if (isGotAllPermissions) {
                            /* Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();*/
                        } else {
                            /*  Toast.makeText(this, "All permission not Granted", Toast.LENGTH_SHORT).show();*/
                        }}

                    // if you want to know which are the denied permissions.
                    if (data.getSerializableExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST) != null) {

                        ArrayList  deniedPermissions = new ArrayList<>();

                        deniedPermissions.addAll((Collection<? extends String>) data.getSerializableExtra(
                                EasyPermissionConstants.DENIED_PERMISSION_LIST));

                        if (deniedPermissions.size() > 0) {
                            for (int i = 0; i < deniedPermissions.size(); i++) {

                                if(EasyPermissionList.READ_EXTERNAL_STORAGE == deniedPermissions.get(i)) {

                                    Toast.makeText(this, "Storage Permission not granted", Toast.LENGTH_SHORT)
                                            .show();

                                }


                                if(EasyPermissionList.CAMERA == deniedPermissions.get(i)) {

                                    Toast.makeText(this, "Storage Permission not granted", Toast.LENGTH_SHORT)
                                            .show();

                                }

                            }
                        }
                    }
                }
        }
    }

}
