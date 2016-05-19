package de.mybambergapp.dto;

/**
 * Created by christian on 19.05.16.
 */
public class PreferencesDTO {
    public boolean culture;
    public boolean art;
    public boolean sport;
    public boolean history;
    public boolean music;

    public PreferencesDTO(){

    }

    public boolean isCulture() {
        return culture;
    }

    public void setCulture(boolean culture) {
        this.culture = culture;
    }

    public boolean isArt() {
        return art;
    }

    public void setArt(boolean art) {
        this.art = art;
    }

    public boolean isSport() {
        return sport;
    }

    public void setSport(boolean sport) {
        this.sport = sport;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }
}
