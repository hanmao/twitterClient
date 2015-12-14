package com.codepath.apps.myTweets;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

/**
 * Created by hamao on 12/13/15.
 */
public class TwitterComposeDialog extends DialogFragment implements TwitterComposeDialogListener {
    private EditText TweetBody;
    private Button CancelTweet;
    private Button PostTweet;
    private TextView CharsLeft;
    private final int MAX_TWEET_CHAR_COUNT = 140;
    private int char_count;

    public TwitterComposeDialog() {
    }

    public static TwitterComposeDialog newInstance (String title)
    {
        TwitterComposeDialog frag = new TwitterComposeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.twitter_fragment, container);
        String title = getArguments().getString("title");
        getDialog().setTitle(title);

        TweetBody = (EditText) view.findViewById(R.id.etTweetBody);
        CancelTweet = (Button) view.findViewById(R.id.btnTweetCancel);
        PostTweet = (Button) view.findViewById(R.id.btnTweetPost);
        CharsLeft = (TextView) view.findViewById(R.id.tvCharsLeft);
        char_count = MAX_TWEET_CHAR_COUNT;

        CharsLeft.setText(Integer.toString(char_count));
        TweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int total = s.toString().length();
                CharsLeft.setText(Integer.toString(char_count - total));
            }
        });

        CancelTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        PostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TwitterComposeDialogListener listener = (TwitterComposeDialogListener) getActivity();
                listener.onFinishedComposingTweet(TweetBody.getText().toString());
                getDialog().dismiss();
            }
        });

        // Show soft keyboard automatically
        TweetBody.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;
    }

    @Override
    public void onFinishedComposingTweet(String inputText) {

    }
}

