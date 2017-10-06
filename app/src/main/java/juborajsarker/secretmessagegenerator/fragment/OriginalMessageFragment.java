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


public class OriginalMessageFragment extends Fragment {

    InterstitialAd mInterstitialAd;

    int count = 0;

    View view;



    EditText inputSecretMsg, inputSecretPasscode, outputOriginalMsg;
    Button returnOriginalMsg, copyOriginalMsg, shareOriginalMsg, clearText;

    String m="";


    public OriginalMessageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_original_message, container, false);

        init();

        return view;


    }

    private void init() {

        inputSecretMsg = (EditText) view.findViewById(R.id.input_secret_msg_ET);
        inputSecretPasscode = (EditText) view.findViewById(R.id.input_secret_passcode_ET);
        outputOriginalMsg = (EditText) view.findViewById(R.id.output_original_msg_ET);

        returnOriginalMsg = (Button) view.findViewById(R.id.btn_return_original);
        copyOriginalMsg = (Button) view.findViewById(R.id.btn_original_message_copy);
        shareOriginalMsg = (Button) view.findViewById(R.id.btn_original_message_share);
        clearText = (Button) view.findViewById(R.id.btn_clear_all_text_original);


        returnOriginalMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;

                char[] msg;
                int temp = 0;
                int pc = 0;
                int i;


                String get_msg = inputSecretMsg.getText().toString();

                if (inputSecretMsg.getText().toString().equals("")){


                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);


                    outputOriginalMsg.setText("");
                    Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();


                }else {

                    if (inputSecretMsg.getText().toString().length() <= 25){

                        m = get_msg;

                    }else {

                        m = get_msg.substring(15, get_msg.length() - 10 );

                    }


                    String p = inputSecretPasscode.getText().toString();

                    msg = m.toCharArray();

                    if (inputSecretPasscode.getText().toString().equals("")) {
                        pc = 200;

                        for (int j = 0; j < m.length(); j++) {

                            msg[j] = (char) (msg[j] - pc);

                        }

                        InputMethodManager inputManager = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        pc = 0;
                        temp = 0;



                        outputOriginalMsg.setText(String.valueOf(msg));
                        outputOriginalMsg.setVerticalScrollBarEnabled(true);
                        outputOriginalMsg.setMovementMethod(new ScrollingMovementMethod());
                        outputOriginalMsg.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);


                    } else {

                        for (i = 0; i < p.length(); i++) {

                            temp = p.charAt(i);
                            pc = pc + temp;
                        }


                        while (pc < 210) {

                            pc = pc + 50;
                        }


                        while (pc > 350){

                            pc = pc - 20;
                        }


                        for (int j = 0; j < m.length(); j++) {

                            msg[j] = (char) (msg[j] - pc);

                        }

                        InputMethodManager inputManager = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        pc = 0;
                        temp = 0;



                        outputOriginalMsg.setText(String.valueOf(msg));
                        outputOriginalMsg.setVerticalScrollBarEnabled(true);
                        outputOriginalMsg.setMovementMethod(new ScrollingMovementMethod());
                        outputOriginalMsg.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);


                    }

                }





                if (count % 3 == 0){


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


        copyOriginalMsg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {


                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);

                if (outputOriginalMsg.getText().toString().equals("")) {

                    Toast.makeText(getContext(), "Nothing to copy", Toast.LENGTH_SHORT).show();

                } else {
                    String s = outputOriginalMsg.getText().toString();
                    ClipData clipe = ClipData.newPlainText("label", s);
                    clipboard.setPrimaryClip(clipe);
                    Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });


        shareOriginalMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              if (outputOriginalMsg.getText().toString().equals("")){

                  Toast.makeText(getContext(), "Output message is empty", Toast.LENGTH_SHORT).show();

              }else {


                  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                  sharingIntent.setType("text/plain");
                  String shareBody = outputOriginalMsg.getText().toString();
                  sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                  startActivity(Intent.createChooser(sharingIntent, "Share using"));

              }

            }
        });


        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputSecretMsg.setText("");
                inputSecretPasscode.setText("");
                outputOriginalMsg.setText("");

            }
        });







    }







    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }



}
