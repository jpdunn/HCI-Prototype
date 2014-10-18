package com.authorwjf.talk2me;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnInitListener {

	protected static final int REQUEST_OK = 1;
	private TextToSpeech tts;
	Handler handler;
	String responseText;
	TextView saidText;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(this);
		tts = new TextToSpeech(this, this);
		handler = new Handler();
		saidText = (TextView)findViewById(R.id.text1);

	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		try {
			startActivityForResult(i, REQUEST_OK);
		} catch (Exception e) {
			Toast.makeText(this, "Error initializing speech to text engine.",
					Toast.LENGTH_LONG).show();
		}
	}
	
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {
			ArrayList<String> thingsYouSaid = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			((TextView) findViewById(R.id.text1)).setText(thingsYouSaid.get(0));

			switch (thingsYouSaid.get(0)) {
			case "take me somewhere":
				speakOut("Where would you like to go?");
				break;
				
			case "what is my current location":
				speakOut("Your current location is: James Cook University, Smithfield");
				break;

			case "change profile":
				speakOut("What profile would you like to change to?");
				
				
				break;
				
			case "where is the nearest fuel station":
				speakOut("The nearest fuel station is at yo mummas house");
				break;
				
			case "begin navigation":
				speakOut("Starting navigation");
				break;
				
			case "finish navigation":
				speakOut("Ending navigation");
				break;
				
			case "change current route":
			speakOut("Re-routing");
			break;
			
			default:
				break;
			}

		}

	}

	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			}
		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut(String response) {

		tts.speak(response, TextToSpeech.QUEUE_FLUSH, null);
	}

}
