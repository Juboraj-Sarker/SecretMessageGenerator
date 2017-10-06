package juborajsarker.secretmessagegenerator.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import juborajsarker.secretmessagegenerator.R;

import static android.content.Context.CLIPBOARD_SERVICE;


public class SecretMessageFragment extends Fragment {

    InterstitialAd mInterstitialAd;

    int count = 0;

    View view;

    EditText inputOriginalMsg, inputOriginalPasscode, outputSecretMsg;
    Button makeSecret, copySecretMsg, shareSecretMsg, clearText;

    String originalMsg, passcode;
    String final_secret_msg = "";


    public SecretMessageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_secret_message, container, false);

        init();



        return view;
    }

    private void init() {

        inputOriginalMsg = (EditText) view.findViewById(R.id.input_original_msg_ET);
        inputOriginalPasscode = (EditText) view.findViewById(R.id.input_original_passcode_ET);
        outputSecretMsg = (EditText) view.findViewById(R.id.output_secret_msg_ET);

        makeSecret = (Button) view.findViewById(R.id.btn_make_secret);
        copySecretMsg = (Button) view.findViewById(R.id.btn_secret_message_copy);
        shareSecretMsg = (Button) view.findViewById(R.id.btn_secret_msg_share);
        clearText = (Button) view.findViewById(R.id.btn_clear_all_text);


        makeSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                count++;

                originalMsg = inputOriginalMsg.getText().toString();
                passcode = inputOriginalPasscode.getText().toString();

                if (inputOriginalMsg.getText().toString().equals("")){

                    Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
                    outputSecretMsg.setText("");
                    outputSecretMsg.setVerticalScrollBarEnabled(true);
                    outputSecretMsg.setMovementMethod(new ScrollingMovementMethod());
                    outputSecretMsg.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getActivity()
                            .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }else {


                    char[] msg;
                    int temp = 0;
                    int pc = 0;
                    int i;

                    msg = originalMsg.toCharArray();

                    if (inputOriginalPasscode.getText().toString().equals("")){


                        pc = 200;

                        for (int j = 0; j < originalMsg.length(); j++) {

                            msg[j] = (char) (msg[j] + pc);

                        }



                        InputMethodManager inputManager = (InputMethodManager) getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity()
                                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        pc = 0;
                        temp = 0;

                        String temp_msg = String.valueOf(msg);
                        final_secret_msg = "AXaeLuRJ3V52nlX" + temp_msg + "qgeAaUYn==";


                        if (inputOriginalMsg.getText().toString().equals("")){


                            outputSecretMsg.setText("");
                            Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
                        }else {


                            outputSecretMsg.setText(final_secret_msg);
                            outputSecretMsg.setVerticalScrollBarEnabled(true);
                            outputSecretMsg.setMovementMethod(new ScrollingMovementMethod());
                            outputSecretMsg.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                        }



                    }else {




                        for (i = 0; i < passcode.length(); i++) {

                            temp = passcode.charAt(i);
                            pc = pc + temp;
                        }


                        while (pc < 210) {

                            pc = pc + 50;
                        }

                        while (pc > 350) {

                            pc = pc - 20;
                        }


                        for (int j = 0; j < originalMsg.length(); j++) {

                            msg[j] = (char) (msg[j] + pc);

                        }

                        InputMethodManager inputManager = (InputMethodManager) getActivity().
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity().
                                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        pc = 0;
                        temp = 0;

                        String temp_msg = String.valueOf(msg);
                        final_secret_msg = "AXaeLuRJ3V52nlX" + temp_msg + "qgeAaUYn==";

                        if (inputOriginalMsg.getText().toString().equals("")) {

                            outputSecretMsg.setText("");
                            Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();

                        } else {

                            outputSecretMsg.setText(final_secret_msg);
                            outputSecretMsg.setVerticalScrollBarEnabled(true);
                            outputSecretMsg.setMovementMethod(new ScrollingMovementMethod());
                            outputSecretMsg.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);


                        }
                    }


                }



                if ( count % 3 == 0 ){


                    mInterstitialAd = new InterstitialAd(getContext());
                    mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));

                    AdRequest adRequest = new AdRequest.Builder().addTestDevice("93448558CC721EBAD8FAAE5DA52596D3").build();
                    mInterstitialAd.loadAd(adRequest);



                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            showInterstitial();
                        }
                    });


                }

            }
        });







        copySecretMsg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {


                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);

                if (outputSecretMsg.getText().toString().equals("")) {

                    Toast.makeText(getContext(), "Nothing to copy", Toast.LENGTH_SHORT).show();

                } else {
                    String s = outputSecretMsg.getText().toString();
                    ClipData clipe = ClipData.newPlainText("label", s);
                    clipboard.setPrimaryClip(clipe);
                    Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }


            }
        });


        shareSecretMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (outputSecretMsg.getText().toString().equals("")){

                    Toast.makeText(getContext(), "Output message is empty", Toast.LENGTH_SHORT).show();

                }else {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = outputSecretMsg.getText().toString();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));
                }


            }
        });


        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputOriginalMsg.setText("");
                inputOriginalPasscode.setText("");
                outputSecretMsg.setText("");

            }
        });

    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}
