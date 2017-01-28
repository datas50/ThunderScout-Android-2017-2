package com.example.annedwyer.alliance_api_test;

/**
 * Created by annedwyer on 1/16/17.
 */

public class Matchdata {
    private int _id;
    private String match_num;
    private String team_num;
    private String alliance;
    private String tablet_id;

    public Matchdata()
    {
    }
    public Matchdata(String match_num, String team_num, String alliance, String tablet_id)
    {

        this.match_num=match_num;
        this.team_num=team_num;
        this.alliance=alliance;
        this.tablet_id=tablet_id;
    }

    public void setId(int id) {
        this._id = _id;
    }
    public int getId() {
        return _id;
    }

    public String getMatch_num() {
        return match_num;
    }
    public void setMatch_num(String match_num) {
        this.match_num = match_num;
    }

    public String getTeam_num() {
        return team_num;
    }
    public void setTeam_num(String team_num) {
        this.team_num = team_num;
    }

    public String getAlliance_color() {
        return alliance;
    }
    public void setAlliance_color(String alliance) {
        this.alliance = alliance;
    }

    public String getTablet_id() {
        return tablet_id;
    }
    public void setTablet_id(String tablet_id) {
        this.tablet_id = tablet_id;
    }

}




