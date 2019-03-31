package com.example.vinze.roomnotifierconnection.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MenuInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vinze.roomnotifierconnection.Entities.Reminder;
import com.example.vinze.roomnotifierconnection.NotificationManager.AlertReceiver;
import com.example.vinze.roomnotifierconnection.R;
import com.example.vinze.roomnotifierconnection.Adapter.ReminderAdapter;
import com.example.vinze.roomnotifierconnection.ViewModels.ReminderViewModel;

import java.util.Calendar;
import java.util.List;

public class ReminderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int ADD_REMINDER_REQUEST = 1;
    public static final int EDIT_REMINDER_REQUEST = 2;

    public AlarmManager alarmManager;
    public long id;

    private ReminderViewModel reminderViewModel;

    private Intent switchToReminder;
    private Intent switchToSearch;
    private Intent switchToInfo;
    private Intent switchToUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderActivity.this, AddEditReminderActivity.class);
                startActivityForResult(intent, ADD_REMINDER_REQUEST);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        switchToReminder = this.getIntent();
        switchToSearch = new SearchActivity().getIntent();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReminderAdapter adapter = new ReminderAdapter();
        recyclerView.setAdapter(adapter);

        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(@Nullable List<Reminder> reminders) {
                adapter.submitList(reminders);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    reminderViewModel.delete(adapter.getReminderat(viewHolder.getAdapterPosition()));
                    Intent cancelIntent = new Intent(ReminderActivity.this, AlertReceiver.class);
                    PendingIntent pendingMorningIntent = PendingIntent.getBroadcast(ReminderActivity.this, 1000+(int)adapter.getReminderat(viewHolder.getAdapterPosition()).getId(), cancelIntent, 0);
                    PendingIntent pendingNoonIntent = PendingIntent.getBroadcast(ReminderActivity.this, 2000+(int)adapter.getReminderat(viewHolder.getAdapterPosition()).getId(), cancelIntent, 0);
                    PendingIntent pendingEveningIntent = PendingIntent.getBroadcast(ReminderActivity.this, 3000+(int)adapter.getReminderat(viewHolder.getAdapterPosition()).getId(), cancelIntent, 0);
                    alarmManager.cancel(pendingMorningIntent);
                    alarmManager.cancel(pendingNoonIntent);
                    alarmManager.cancel(pendingEveningIntent);
                }
                catch (Exception e){

                }

                Toast.makeText(ReminderActivity.this, "Erinnerung gelöscht", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

       /* adapter.setOnItemClickListener(new ReminderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reminder reminder) {
                Intent intent = new Intent(ReminderActivity.this, AddEditReminderActivity.class);
                intent.putExtra(AddEditReminderActivity.EXTRA_ID, reminder.getId());
                intent.putExtra(AddEditReminderActivity.EXTRA_MEDICATIONNAME, reminder.getMedicationName());
                intent.putExtra(AddEditReminderActivity.EXTRA_MORNINGTIME, reminder.getMorningTime());
                intent.putExtra(AddEditReminderActivity.EXTRA_NOONTIME, reminder.getNoonTime());
                intent.putExtra(AddEditReminderActivity.EXTRA_EVENINGTIME, reminder.getEveningTime());
                startActivityForResult(intent, EDIT_REMINDER_REQUEST);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REMINDER_REQUEST && resultCode == RESULT_OK) {

            String medicationname = data.getStringExtra(AddEditReminderActivity.EXTRA_MEDICATIONNAME);
            String morningTime = data.getStringExtra(AddEditReminderActivity.EXTRA_MORNINGTIME);
            String noonTime = data.getStringExtra(AddEditReminderActivity.EXTRA_NOONTIME);
            String eveningTime = data.getStringExtra(AddEditReminderActivity.EXTRA_EVENINGTIME);

            Reminder reminder = new Reminder(medicationname, morningTime,noonTime,eveningTime,false);

            reminderViewModel.insert(reminder);
            id = reminder.getId();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");



            if(!(" ").equals(morningTime))
            {
                Calendar cMorning = Calendar.getInstance();
                cMorning.setTimeInMillis(System.currentTimeMillis());

                int morningHour = Integer.parseInt(morningTime.substring(0,2));
                int morningMinute = Integer.parseInt(morningTime.substring(3,5));

                cMorning.set(Calendar.HOUR_OF_DAY, morningHour);
                cMorning.set(Calendar.MINUTE, morningMinute);
                cMorning.set(Calendar.SECOND, 0);
                cMorning.add(Calendar.HOUR,checkHour(morningHour));

                start(cMorning, 1000+(int)id, medicationname);
            }

            if(!(" ").equals(noonTime))
            {
                Calendar cNoon = Calendar.getInstance();
                cNoon.setTimeInMillis(System.currentTimeMillis());

                int noonHour = Integer.parseInt(noonTime.substring(0,2));
                int noonMinute = Integer.parseInt(noonTime.substring(3,5));

                cNoon.set(Calendar.HOUR_OF_DAY, noonHour);
                cNoon.set(Calendar.MINUTE, noonMinute);
                cNoon.set(Calendar.SECOND, 0);
                cNoon.add(Calendar.HOUR,checkHour(noonHour));

                start(cNoon, 2000+(int)id, medicationname);
            }

            if(!(" ").equals(eveningTime))
            {
                Calendar cEvening = Calendar.getInstance();
                cEvening.setTimeInMillis(System.currentTimeMillis());

                int eveningHour = Integer.parseInt(eveningTime.substring(0,2));
                int eveningMinute = Integer.parseInt(eveningTime.substring(3,5));

                cEvening.set(Calendar.HOUR_OF_DAY, eveningHour);
                cEvening.set(Calendar.MINUTE, eveningMinute);
                cEvening.set(Calendar.SECOND, 0);
                cEvening.add(Calendar.HOUR,checkHour(eveningHour));

                start(cEvening, 3000+(int)id, medicationname);
            }

            Toast.makeText(this, "Erinnerung gespeichert", Toast.LENGTH_SHORT).show();


        } else if (requestCode == EDIT_REMINDER_REQUEST && resultCode == RESULT_OK) {
            id = data.getIntExtra(AddEditReminderActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Erinnerung konnte nicht geändert werden!", Toast.LENGTH_SHORT).show();
                return;
            }

            String medicationName = data.getStringExtra(AddEditReminderActivity.EXTRA_MEDICATIONNAME);
            String morningTime = data.getStringExtra(AddEditReminderActivity.EXTRA_MORNINGTIME);
            String noonTime = data.getStringExtra(AddEditReminderActivity.EXTRA_NOONTIME);
            String eveningTime = data.getStringExtra(AddEditReminderActivity.EXTRA_EVENINGTIME);

            Reminder reminder = new Reminder(medicationName, morningTime,noonTime,eveningTime,false);
            reminder.setId((int)id);
            reminderViewModel.update(reminder);

            Toast.makeText(this, "Erinnerung geändert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erinnerung nicht gespeichert!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                reminderViewModel.deleteAllReminders();
                //TODO alarmManager.cancel();
                Toast.makeText(this, "Alle Erinnerungen gelöscht", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    public void start(Calendar calendar, int setCode, String medicationName) {
        Intent intent = new Intent(this, AlertReceiver.class);
        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        intent.putExtra("medicationName", medicationName);
        intent.putExtra("SETCODE", setCode);
        intent.putExtra("TIME", calendar.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, setCode, intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            System.out.println(System.currentTimeMillis());
            System.out.println(calendar.getTimeInMillis());
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        //Toast.makeText(this, "Alarm Set!" + " " + id, Toast.LENGTH_SHORT).show();
    }

    public int checkHour(int hour){
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.getTime().getHours();

        if(hour < currentHour){
            return 24-(currentHour-hour);
        }
        else
        {
            return 0;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_reminder) {
            if(switchToReminder == null){
                switchToReminder = new Intent(this, ReminderActivity.class);
                startActivity(switchToReminder);

            }
            else{
                startActivity(switchToReminder);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_search){
            if(switchToSearch == null){
                switchToSearch = new Intent(this, SearchActivity.class);
                startActivity(switchToSearch);

            }
            else{
                startActivity(switchToSearch);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_info){
            if(switchToInfo == null){
                switchToInfo = new Intent(this, InfoActivity.class);
                startActivity(switchToInfo);

            }
            else{
                startActivity(switchToInfo);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_userdata){
            if(switchToUserData == null){
                switchToUserData = new Intent(this, UserDataActivity.class);
                startActivity(switchToUserData);

            }
            else{
                startActivity(switchToUserData);
                System.out.println("not new");
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
