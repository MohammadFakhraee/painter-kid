package ir.mohammadhf.painterkid.feature.settings;

import io.reactivex.subjects.BehaviorSubject;
import ir.mohammadhf.painterkid.utils.DataFakeGenerator;
import ir.mohammadhf.painterkid.utils.SettingsSharedPreferences;

public class SettingsViewModel {
    private SettingsSharedPreferences settingsSharedPreferences;

    // FOR NEXT VERSION IF I WANTED TO ADD MUSIC
//    private BehaviorSubject<String> selectedMusic = BehaviorSubject.create();
    private BehaviorSubject<String> selectedTalker = BehaviorSubject.create();

    // FOR NEXT VERSION IF I WANTED TO ADD MUSIC
//    private int musicPos = 0;
    private int talkerPos = 0;

    public SettingsViewModel(SettingsSharedPreferences settingsSharedPreferences) {
        this.settingsSharedPreferences = settingsSharedPreferences;
        // FOR NEXT VERSION IF I WANTED TO ADD MUSIC
//        selectedMusic.onNext(settingsSharedPreferences.getMusicName());
//        for (int i = 0; i < DataFakeGenerator.getMusicStrings().size(); i++) {
//            String temp = DataFakeGenerator.getMusicStrings().get(i);
//            if (temp.equals(settingsSharedPreferences.getMusicName())) {
//                musicPos = i;
//            }
//        }
        selectedTalker.onNext(settingsSharedPreferences.getTalkerName());
        for (int i = 0; i < DataFakeGenerator.getTalkerStrings().size(); i++) {
            String temp = DataFakeGenerator.getTalkerStrings().get(i);
            if (temp.equals(settingsSharedPreferences.getTalkerName())) {
                talkerPos = i;
            }
        }
    }

    // FOR NEXT VERSION IF I WANTED TO ADD MUSIC
//    public void nextMusic() {
//        musicPos++;
//        if (musicPos == DataFakeGenerator.getMusicStrings().size())
//            musicPos = 0;
//        setSelectedMusic();
//    }
//
//    public void prevMusic() {
//        musicPos--;
//        if (musicPos == -1)
//            musicPos += DataFakeGenerator.getMusicStrings().size();
//        setSelectedMusic();
//    }
//    private void setSelectedMusic() {
//        String str = DataFakeGenerator.getMusicStrings().get(musicPos);
//        settingsSharedPreferences.setMusicName(str);
//        selectedMusic.onNext(str);
//    }

    public void nextTalker() {
        talkerPos++;
        if (talkerPos == DataFakeGenerator.getTalkerStrings().size())
            talkerPos = 0;
        setSelectedTalker();
    }

    public void prevTalker() {
        talkerPos--;
        if (talkerPos == -1)
            talkerPos += DataFakeGenerator.getTalkerStrings().size();
        setSelectedTalker();
    }

    private void setSelectedTalker() {
        String str = DataFakeGenerator.getTalkerStrings().get(talkerPos);
        settingsSharedPreferences.setTalkerName(str);
        selectedTalker.onNext(str);
    }
    // FOR NEXT VERSION IF I WANTED TO ADD MUSIC
//    public BehaviorSubject<String> getCurrentMusicName() {
//        return selectedMusic;
//    }

    public BehaviorSubject<String> getCurrentTalkerName() {
        return selectedTalker;
    }
}
