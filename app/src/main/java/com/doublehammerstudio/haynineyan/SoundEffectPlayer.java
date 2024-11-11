package com.doublehammerstudio.haynineyan;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import java.util.HashMap;

public class SoundEffectPlayer {

    private static SoundEffectPlayer instance;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundMap;
    private boolean isLoaded = false;

    private SoundEffectPlayer(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();


        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        soundMap = new HashMap<>();

        // Set up OnLoadCompleteListener
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (status == 0) {
                isLoaded = true; // Sounds are ready to play
            } else {
                Log.e("SoundEffectPlayer", "Error loading sound with sampleId " + sampleId);
            }
        });

        // Load sounds into SoundPool and map them
        soundMap.put(R.raw.wood_button, soundPool.load(context, R.raw.wood_button, 1));
        soundMap.put(R.raw.correct_answer, soundPool.load(context, R.raw.correct_answer, 1));
        soundMap.put(R.raw.wrong_answer, soundPool.load(context, R.raw.wrong_answer, 1));
        soundMap.put(R.raw.rope_anim_sound, soundPool.load(context, R.raw.rope_anim_sound, 1));
        soundMap.put(R.raw.after_each_eval, soundPool.load(context, R.raw.after_each_eval, 1));
        soundMap.put(R.raw.answe_click_eval, soundPool.load(context, R.raw.answe_click_eval, 1));
        soundMap.put(R.raw.mutate, soundPool.load(context, R.raw.mutate, 1));
    }

    public static SoundEffectPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new SoundEffectPlayer(context.getApplicationContext());
        }
        return instance;
    }

    public void playWoodButtonSound() {
        playSound(R.raw.wood_button);
    }
    public void playMutateSound() {
        Integer soundId = soundMap.get(R.raw.mutate);
        if (soundId != null && isLoaded) {
            soundPool.play(soundId, 0.1f, 0.1f, 1, 0, 1.0f);

        } else {
            Log.e("SoundEffectPlayer", "Sound not loaded or not available");
        }
    }

    public void playCorrectAnswerSound() {
        playSound(R.raw.correct_answer);
    }

    public void playWrongAnswerSound() {
        playSound(R.raw.wrong_answer);
    }

    public void playRopeAnimSound() {
        playSound(R.raw.rope_anim_sound);
    }

    public void playAfterEachEvalSound() {
        playSound(R.raw.after_each_eval);
    }

    public void playAnswerClickEvalSound() {
        playSound(R.raw.answe_click_eval);
    }

    private void playSound(int soundResId) {
        Integer soundId = soundMap.get(soundResId);
        if (soundId != null && isLoaded) {
            soundPool.play(soundId, 0.65f, 0.65f, 1, 0, 1.0f);

        } else {
            Log.e("SoundEffectPlayer", "Sound not loaded or not available");
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
