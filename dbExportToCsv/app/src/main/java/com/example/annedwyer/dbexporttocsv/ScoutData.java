package com.example.annedwyer.dbexporttocsv;

/**
 * Created by annedwyer on 12/17/16.
 */



public class ScoutData {

    private int _id;
    private String _match_num;
    private String _team_num;
    private String _alliance;
    private String _tablet_id;

    public ScoutData()
    {
    }
    public ScoutData(String match_num, String team_num, String alliance, String tablet_id)
    {

        this._match_num=match_num;
        this._team_num=team_num;
        this._alliance=alliance;
        this._tablet_id=tablet_id;
    }



    public void setId(int id) {
        this._id = _id;
    }
    public void setMatchNum(String match_num) {
        this._match_num = _match_num;
    }


    public void setAlliance(String alliance) {
        this._alliance = _alliance;
    }

    public void setTabletId(String tablet_id) {
        this._tablet_id = _tablet_id;
    }

    public void setTeamNum(String team_num) {
        this._team_num = _team_num;
    }


    public int getId() {
        return _id;
    }

    public String getMatchNum() {
        return _match_num;
    }
    public String getTeamNum() {
        return _team_num;
    }
    public String getAlliance() {
        return _alliance;
    }
    public String getTabletId() {
        return _tablet_id;
    }

}





