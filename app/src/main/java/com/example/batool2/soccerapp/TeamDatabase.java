package com.example.batool2.soccerapp;

public class TeamDatabase {

    private String team_name, manager_name, year_established,player_first_name,player_last_name,player_nationality;
    private int id,player_goals,player_fouls,player_red_cards,player_yellow_cards,player_assists;

    public TeamDatabase(int ID, String name, String established, String managerName)
    {
        team_name = name;
        year_established = established;
        manager_name = managerName;
        id = ID;
    }

    public TeamDatabase(String firstName,String lastName, String team, String nationality, int goals, int fouls, int redCards, int yellowCards,int assists)
    {
        player_first_name = firstName;
        player_last_name = lastName;
        team_name = team;
        player_nationality = nationality;
        player_goals = goals;
        player_fouls = fouls;
        player_red_cards = redCards;
        player_yellow_cards = yellowCards;
        player_assists = assists;

    }

    public String getTeamName()
    {

        return team_name;
    }
    public int getId()
    {

        return id;
    }
    public String getYearEstablished()
    {

        return year_established;
    }
    public String getManagerName()
    {

        return manager_name;
    }


    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getYear_established() {
        return year_established;
    }

    public void setYear_established(String year_established) {
        this.year_established = year_established;
    }

    public String getPlayer_first_name() {
        return player_first_name;
    }

    public void setPlayer_first_name(String player_first_name) {
        this.player_first_name = player_first_name;
    }

    public String getPlayer_last_name() {
        return player_last_name;
    }

    public void setPlayer_last_name(String player_last_name) {
        this.player_last_name = player_last_name;
    }

    public String getPlayer_nationality() {
        return player_nationality;
    }

    public void setPlayer_nationality(String player_nationality) {
        this.player_nationality = player_nationality;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer_goals() {
        return player_goals;
    }

    public void setPlayer_goals(int player_goals) {
        this.player_goals = player_goals;
    }

    public int getPlayer_fouls() {
        return player_fouls;
    }

    public void setPlayer_fouls(int player_fouls) {
        this.player_fouls = player_fouls;
    }

    public int getPlayer_red_cards() {
        return player_red_cards;
    }

    public void setPlayer_red_cards(int player_red_cards) {
        this.player_red_cards = player_red_cards;
    }

    public int getPlayer_yellow_cards() {
        return player_yellow_cards;
    }

    public void setPlayer_yellow_cards(int player_yellow_cards) {
        this.player_yellow_cards = player_yellow_cards;
    }

    public int getPlayer_assists() {
        return player_assists;
    }

    public String getFullName()
    {

        return player_first_name + "##" + player_last_name;
    }

    public void setPlayer_assists(int player_assists) {
        this.player_assists = player_assists;
    }
}
