package com.santhosh.allstarandroidchallenge.utillity;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class TextToSpeechUtils {

	private static TextToSpeech textToSpeech;

	public static boolean speak(final Context context,final String speech){
		if(textToSpeech==null){
			textToSpeech=new TextToSpeech(context,new TextToSpeech.OnInitListener() {
				@Override
				public void onInit(int arg0) {
					if(textToSpeech!=null && arg0==TextToSpeech.SUCCESS){
						speak(context, speech);
					}
				}
			});
		}
		return speak(context, speech);
	}

	private void speak(String speech) {
		if (textToSpeech != null) {
			textToSpeech.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	private void destroyTTS() {
		if (textToSpeech != null) {
			textToSpeech.shutdown();
			textToSpeech = null;
		}
	}

}
