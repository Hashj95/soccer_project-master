package com.example.batool2.soccerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends SoccerList implements View.OnClickListener {

    Button goBack;
    Button addPlayer;
    TabHost tabHost;
    List<TeamDatabase> playerList = new ArrayList<TeamDatabase>();
    ArrayAdapter<TeamDatabase> playerAdapter;
    EditText firstName;
    EditText lastName;
    EditText teamName;
    EditText nationality;
    EditText goals;
    EditText fouls;
    EditText assists;
    EditText redCards;
    EditText yellowCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firstName = (EditText) findViewById(R.id.firstNameField);
        lastName = (EditText) findViewById(R.id.lastNameField);
        teamName = (EditText) findViewById(R.id.teamNameField);
        nationality = (EditText) findViewById(R.id.nationalityField);
        goals = (EditText) findViewById(R.id.goalsField);
        fouls = (EditText) findViewById(R.id.foulsField);
        redCards = (EditText) findViewById(R.id.redCardsField);
        yellowCards = (EditText) findViewById(R.id.yellowCardsField);
        assists = (EditText) findViewById(R.id.assistsField);


        listView = (ListView) findViewById(R.id.listView);

        //tabhost
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Creator");
        tabSpec.setContent(R.id.creatorTab);
        tabSpec.setIndicator("Create a player");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Players List");
        tabSpec.setContent(R.id.teamStoreTab);
        tabSpec.setIndicator("Players List");
        tabHost.addTab(tabSpec);

        dbHandler = new DBHandler(getApplicationContext());

        addPlayer = (Button) findViewById(R.id.addPlayerButton);

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TeamDatabase player = new TeamDatabase(String.valueOf(firstName.getText()),String.valueOf(lastName.getText()),String.valueOf(teamName.getText()),String.valueOf(nationality.getText()),Integer.valueOf(goals.getText().toString()),Integer.valueOf(fouls.getText().toString()),Integer.valueOf(redCards.getText().toString()),Integer.valueOf(yellowCards.getText().toString()),Integer.valueOf(assists.getText().toString()));
                if(!playerExists(player)) {
                    dbHandler.createTeam(player);
                    playerList.add(player);
                    populateList();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Player Exists!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {


        Intent myIntent = new Intent(this, SoccerList.class);

        startActivity(myIntent);

    }

    private boolean playerExists(TeamDatabase team)
    {

        String playerName = team.getPlayer_first_name() + "##" + team.getPlayer_first_name();

        int playerCount = playerList.size();

        for(int i = 0; i < playerCount; i++)
        {

            if(playerName.compareToIgnoreCase(playerList.get(i).getFullName()) == 0)
            {
                return true;
            }

        }
        return false;

    }

    private class PlayerListAdapter extends ArrayAdapter<TeamDatabase>
    {

        public PlayerListAdapter()
        {
            super(SecondActivity.this, R.layout.list_player_items, playerList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            if (view == null)
            {

                view = getLayoutInflater().inflate(R.layout.list_view_items, parent, false);

                TeamDatabase currentPlayerList = playerList.get(position);

                TextView firstName = (TextView) view.findViewById(R.id.player_first_name);
                firstName.setText(currentPlayerList.getPlayer_first_name());

                TextView lastName = (TextView) view.findViewById(R.id.player_last_name);
                lastName.setText(currentPlayerList.getPlayer_last_name());

                TextView teamName = (TextView) view.findViewById(R.id.team_name);
                teamName.setText(currentPlayerList.getTeam_name());

                TextView nationality = (TextView) view.findViewById(R.id.nationality_field);
                nationality.setText(currentPlayerList.getPlayer_nationality());

                TextView goals = (TextView) view.findViewById(R.id.goals_field);
                goals.setText(currentPlayerList.getPlayer_goals());

                TextView fouls = (TextView) view.findViewById(R.id.fouls_field);
                fouls.setText(currentPlayerList.getPlayer_fouls());

                TextView redCards = (TextView) view.findViewById(R.id.red_cards);
                redCards.setText(currentPlayerList.getYearEstablished());

                TextView yellowCards = (TextView) view.findViewById(R.id.yellow_cards);
                yellowCards.setText(currentPlayerList.getYearEstablished());

                TextView assists = (TextView) view.findViewById(R.id.assist_field);
                assists.setText(currentPlayerList.getYearEstablished());
            }

            return view;
        }



    }

    private void populateList()
    {
        adapter = new PlayerListAdapter();
        listView.setAdapter(playerAdapter);
    }

}
