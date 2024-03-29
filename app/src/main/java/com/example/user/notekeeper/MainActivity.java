package com.example.user.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NoteRecyclerAdapter MnoteRecyclerAdapter;

    private RecyclerView MrecyclerItems;
    private LinearLayoutManager MnotesLayoutManager;
    private CourseRecyclerAdapter McourseRecyclerAdapters;
    private GridLayoutManager McoursesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NoteActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initializeDisplayContent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //  MadapterNotes.notifyDataSetChanged();
        MnoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
        // final ListView listNotes=(ListView) findViewById(R.id.list_notes);
        //  List<NoteInfo> notes=DataManager.getInstance().getNotes();
        //  MadapterNotes = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,notes);
        //   listNotes.setAdapter(MadapterNotes);
        //   listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //      @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //           Intent intent=new Intent(NoteListActivity.this,NoteActivity.class);
        //              NoteInfo note=(NoteInfo) listNotes.getItemAtPosition(position);
        //             intent.putExtra(NoteActivity.NOTE_POSITION,position);
        //             startActivity(intent);
        //       }
        //   });
        MrecyclerItems = (RecyclerView) findViewById(R.id.list_items);
        MnotesLayoutManager = new LinearLayoutManager(this);
        McoursesLayoutManager = new GridLayoutManager(this,2);
        List<NoteInfo> note=DataManager.getInstance().getNotes();
        MnoteRecyclerAdapter = new NoteRecyclerAdapter(this,note);
        List<CourseInfo> courses=DataManager.getInstance().getCourses();
        McourseRecyclerAdapters = new CourseRecyclerAdapter(this,courses);



        displayNotes();


    }

    private void displayNotes() {
        MrecyclerItems.setAdapter(MnoteRecyclerAdapter);
        MrecyclerItems.setLayoutManager(MnotesLayoutManager);
        selectNavigationMenuItem(R.id.nav_notes);


    }

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        Menu menu=navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    private void displayCourses(){

        MrecyclerItems.setAdapter(McourseRecyclerAdapters);
        MrecyclerItems.setLayoutManager(McoursesLayoutManager);
selectNavigationMenuItem(R.id.nav_courses);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            displayNotes();
        } else if (id == R.id.nav_courses) {
            displayCourses();
        } else if (id == R.id.nav_share) {
             handleSelection(R.string.nav_share_message);
        } else if (id == R.id.nav_send) {
             handleSelection(R.string.nav_send_message);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleSelection(int message_id) {
        View view =findViewById(R.id.list_items);
        Snackbar.make(view,message_id,Snackbar.LENGTH_LONG).show();
    }
}
