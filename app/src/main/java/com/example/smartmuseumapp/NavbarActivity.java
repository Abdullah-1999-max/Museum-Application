package com.example.smartmuseumapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ImageButton;

import com.example.smartmuseumapp.Home.fragments.HomeFragment;
import com.example.smartmuseumapp.fragment.AboutFragment;
import com.example.smartmuseumapp.fragment.Calander;
import com.example.smartmuseumapp.fragment.Events;
import com.example.smartmuseumapp.fragment.Location;
import com.example.smartmuseumapp.fragment.Shop;
import com.example.smartmuseumapp.Ticketing.fragments.Ticketing;
import com.example.smartmuseumapp.fragment.DonationFragment;
import com.example.smartmuseumapp.fragment.MembershipFragment;
import com.example.smartmuseumapp.fragment.RateusFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    String user_id;
    String user_name;
    private DrawerLayout drawer;
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment = HomeFragment.newInstance();
    private NotificationFragment notificationFragment = NotificationFragment.newInstance();
    private AboutFragment aboutFragment = AboutFragment.newInstance();
    private WalletFragment WalletFragment = com.example.smartmuseumapp.WalletFragment.newInstance();
    private Ticketing ticketing = Ticketing.newInstance();
    private Events events = Events.newInstance();
    private Calander calander = Calander.newInstance();
    private Location location = Location.newInstance();
    private MembershipFragment membershipFragment = MembershipFragment.newInstance();
    private Shop shop = Shop.newInstance();
    private RateusFragment rateusFragment = RateusFragment.newInstance();
    private DonationFragment donationFragment = DonationFragment.newInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ImageButton menuRight = findViewById(R.id.leftRight);
        menuRight.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        changeFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
           Fragment f= getSupportFragmentManager().findFragmentById(R.id.f_container);

            switch (item.getItemId()) {

                case R.id.action_home:
                    if(f instanceof HomeFragment){

                    }else{
                    changeFragment(new HomeFragment());
                    }
                    break;
                case R.id.action_notify:
                    changeFragment(new NotificationFragment());
                    break;
                case R.id.action_about:
                    changeFragment(new AboutFragment());
                    break;
                case R.id.action_wallet:
                    changeFragment(new WalletFragment());
                    break;


            }
            return true;
        });


        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.f_container, homeFragment);
        transaction.commit();
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
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(NavbarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawers();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.nav_ticket:
                changeFragment(new Ticketing());

                break;
            case R.id.nav_events:
                ft.replace(R.id.f_container, events);
                break;
            case R.id.nav_map:
                ft.replace(R.id.f_container, location);
                break;
            case R.id.nav_calander:
                ft.replace(R.id.f_container, calander);
                break;
            case R.id.nav_shop:
                ft.replace(R.id.f_container, shop);
                break;
            case R.id.nav_member:
                ft.replace(R.id.f_container, membershipFragment);
                break;
            case R.id.nav_donate:
                ft.replace(R.id.f_container, donationFragment);
                break;
            case R.id.nav_rateus:
                ft.replace(R.id.f_container, rateusFragment);
                break;

        }

        ft.commit();
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.f_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
