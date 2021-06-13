package ir.mohammadhf.painterkid.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayerSingleton {
    private static MusicPlayerSingleton musicPlayerSingleton;

    private int currentPosition = 0;
    private int userSelectedMusicId;

    private MediaPlayer musicPlayer;

    private MusicPlayerSingleton(int userSelectedMusicId) {
        this.userSelectedMusicId = userSelectedMusicId;
    }

    public static MusicPlayerSingleton singleInstance(Context context, String musicName) {
        if (musicPlayerSingleton == null) {
            musicName = "music_" + musicName.toLowerCase();
            int id = context.getResources().getIdentifier(musicName,
                    "raw",
                    context.getApplicationContext().getPackageName());
            musicPlayerSingleton = new MusicPlayerSingleton(id);
        }
        return musicPlayerSingleton;
    }

    public static void ResetMusicPlayer() {
        musicPlayerSingleton = null;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getUserSelectedMusicId() {
        return userSelectedMusicId;
    }

    public void startMusic(Context context) {
        musicPlayer = MediaPlayer.create(context, userSelectedMusicId);
        musicPlayer.seekTo(currentPosition);
        musicPlayer.setLooping(true);
        musicPlayer.start();
        increaseToMax();
    }

    public void releaseMusic() {
        if (musicPlayer != null) {
            currentPosition = musicPlayer.getCurrentPosition();
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    public void decreaseToHalf() {
        musicPlayer.setVolume(0.2f, 0.2f);
    }

    public void increaseToMax() {
        musicPlayer.setVolume(0.9f, 0.9f);
    }
}
