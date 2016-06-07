package de.mybambergapp.dto;

/**
 * Created by christian on 19.05.16.
 */
public class PreferencesDTO {


    public boolean culture;
    public boolean art;
    public boolean sport;
    public boolean history;
    public boolean concert;
    public boolean party;
    public int starthour;
    public int startminute;
    public int endhour;
    public int endminute;
    public int day;
    public int month;
    public int year;


    public String androidID;



    public PreferencesDTO(){

    }



//tags


    public boolean isConcert() {
        return concert;
    }

    public void setConcert(boolean concert) {
        this.concert = concert;
    }

    public boolean isParty() {
        return party;
    }

    public void setParty(boolean party) {
        this.party = party;
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

    public int getStarthour() {
        return starthour;
    }

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }
//Stunden
    public int getStartminute() {
        return startminute;
    }

    public void setStartminute(int startminute) {
        this.startminute = startminute;
    }

    public int getEndhour() {
        return endhour;
    }

    public void setEndhour(int endhour) {
        this.endhour = endhour;
    }

    public int getEndminute() {
        return endminute;
    }

    public void setEndminute(int endminute) {
        this.endminute = endminute;
    }

  // Tag-Monat-Jahr

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }





    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }

    @Override
    public String toString() {
        return "PreferencesDTO{" +
                "culture=" + culture +
                ", art=" + art +
                ", sport=" + sport +
                ", history=" + history +
                ", concert=" + concert +
                ", party=" + party +
                ", starthour=" + starthour +
                ", startminute=" + startminute +
                ", endhour=" + endhour +
                ", endminute=" + endminute +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", androidID='" + androidID + '\'' +
                '}';
    }
}

