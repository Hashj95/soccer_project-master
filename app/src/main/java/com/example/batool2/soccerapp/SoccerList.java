package com.example.batool2.soccerapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SoccerList extends Activity implements View.OnClickListener{

    List<TeamDatabase> teamList = new ArrayList<TeamDatabase>();
    ListView listView;
    int longClickItemIndex;
    ArrayAdapter<TeamDatabase> adapter;
    DBHandler dbHandler;

    private static final int EDIT = 0,DELETE = 1;



    TeamDatabase database;

    Button detail;
    Button addTeam;
    Button deleteTeam;

    EditText teamName;
    EditText yearEstablished;
    EditText managerName;

    TabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_list);

        listView = (ListView) findViewById(R.id.listView);

        teamName = (EditText) findViewById(R.id.teamNameField);
        yearEstablished = (EditText) findViewById((R.id.establishedField));
        managerName = (EditText) findViewById((R.id.managerNameField));

        dbHandler = new DBHandler(getApplicationContext());

        detail = (Button) findViewById(R.id.detailButton);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoccerList.this, SecondActivity.class));
                Intent intent = new Intent(SoccerList.this,SecondActivity.class);

                intent.putExtra(teamName.getText().toString(),true);
                intent.putExtra(yearEstablished.getText().toString(),true);
                intent.putExtra(managerName.getText().toString(),true);
                startActivity(intent);

            }
        });

        addTeam = (Button) findViewById(R.id.addTeamButton);

        addTeam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TeamDatabase team = new TeamDatabase(dbHandler.getTeamCount(), String.valueOf(teamName.getText()), String.valueOf(yearEstablished.getText()), String.valueOf(managerName.getText()));
                if(!teamExists(team)) {
                    dbHandler.createTeam(team);
                    teamList.add(team);
                    populateList();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Team Exists!",Toast.LENGTH_SHORT).show();
                }


            }

        });




        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickItemIndex = position;
                return false;
            }
        });

        tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Creator");
        tabSpec.setContent(R.id.creatorTab);
        tabSpec.setIndicator("Create a team");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Teams List");
        tabSpec.setContent(R.id.teamStoreTab);
        tabSpec.setIndicator("Teams List");
        tabHost.addTab(tabSpec);

        teamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                addTeam.setEnabled(!teamName.getText().toString().trim().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        List<TeamDatabase> addableTeams = dbHandler.getAllTeams();

        int teamCount = dbHandler.getTeamCount();

        for(int i = 0; i < teamCount; i++)
        {
            teamList.add(addableTeams.get(i));
            if(!addableTeams.isEmpty())
            {
                populateList();
            }
        }


    }

    private void populateList()
    {
        adapter = new TeamListAdapter();
        listView.setAdapter(adapter);
    }
/*
    private boolean addTeams(String name, String established, String managerName)
    {

            database = new TeamDatabase(name,established,managerName);
            teamList.add(database);
            adapter.notifyDataSetChanged();
            return true;
    }*/

    @Override
    public void onClick(View v) {


    }



    private class TeamListAdapter extends ArrayAdapter<TeamDatabase>
    {

        public TeamListAdapter()
        {
            super(SoccerList.this, R.layout.list_view_items, teamList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            if (view == null)
            {

                view = getLayoutInflater().inflate(R.layout.list_view_items, parent, false);

                TeamDatabase currentTeamList = teamList.get(position);

                TextView name = (TextView) view.findViewById(R.id.team_name);
                name.setText(currentTeamList.getTeamName());

                TextView managerName = (TextView) view.findViewById(R.id.name_of_manager);
                managerName.setText(currentTeamList.getManagerName());

                TextView yearOfEstablishment = (TextView) view.findViewById(R.id.year_of_establishment);
                yearOfEstablishment.setText(currentTeamList.getYearEstablished());

            }

            return view;
        }



    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderIcon(R.mipmap.edit_icon);
        menu.setHeaderTitle("Team Options");
        menu.add(Menu.NONE,DELETE,menu.NONE,"Delete Team");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    private boolean teamExists(TeamDatabase team)
    {

        String teamName = team.getTeamName();
        int teamCount = teamList.size();

        for(int i = 0; i < teamCount; i++)
        {

            if(teamName.compareToIgnoreCase(teamList.get(i).getTeamName()) == 0)
            {

                return true;
            }

        }
        return false;

    }

    public boolean onContextItemSelected(MenuItem item)
    {

        switch (item.getItemId()){
            case DELETE:
                dbHandler.deleteTeam(teamList.get(longClickItemIndex));
                teamList.remove(longClickItemIndex);
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);

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


    }


