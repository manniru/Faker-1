package io.kimo.faker.mvp.view.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import io.kimo.faker.R;
import io.kimo.faker.mvp.view.ui.fragment.InternetFragment;
import io.kimo.faker.mvp.view.ui.fragment.LoremFragment;
import io.kimo.faker.mvp.view.ui.fragment.NameFragment;
import io.kimo.faker.mvp.view.ui.fragment.NumberFragment;
import io.kimo.faker.mvp.view.ui.fragment.PhoneFragment;
import io.kimo.faker.mvp.view.ui.fragment.UrlFragment;


public class MainActivity extends AppCompatActivity {
    public static final int LOREM_FRAGMENT = 0;
    public static final int NAME_FRAGMENT = 1;
    public static final int NUMBER_FRAGMENT = 2;
    public static final int PHONE_FRAGMENT = 3;
    public static final int INTERNET_FRAGMENT = 4;
    public static final int URL_FRAGMENT = 5;

    private Toolbar toolbar;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar);

        configureToolbar();
        configureDrawer(savedInstanceState);

        if(savedInstanceState == null) {
            showFragment(LOREM_FRAGMENT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_opensource);
        menuItem.setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_github).actionBar().color(Color.WHITE));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_opensource:

                new LibsBuilder()
                        .withFields(R.string.class.getFields())
                        .withLicenseShown(true)
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAboutDescription("This application exemplifies how Faker works in Android. The list below contains all open source libraries used in this example. <br /> <b>Check them out!</b>")
                        .withActivityTitle("Open Source")
                        .withActivityTheme(R.style.AppTheme)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .start(MainActivity.this);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureDrawer(Bundle savedInstanceState) {

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(
                        new AccountHeaderBuilder()
                                .withActivity(this)
                                .withHeaderBackground(R.drawable.drawer_background)
                                .withProfileImagesClickable(false)
                                .build()
                )
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Faker Components"),
                        new PrimaryDrawerItem()
                                .withName("Lorem")
                                .withIcon(GoogleMaterial.Icon.gmd_text_format)
                                .withIconTintingEnabled(true)
                                .withIdentifier(LOREM_FRAGMENT),
                        new PrimaryDrawerItem()
                                .withName("Name")
                                .withIcon(GoogleMaterial.Icon.gmd_person)
                                .withIconTintingEnabled(true)
                                .withIdentifier(NAME_FRAGMENT),
                        new PrimaryDrawerItem()
                                .withName("Number")
                                .withIcon(GoogleMaterial.Icon.gmd_filter_9_plus)
                                .withIconTintingEnabled(true)
                                .withIdentifier(NUMBER_FRAGMENT),
                        new PrimaryDrawerItem()
                                .withName("Phone")
                                .withIcon(GoogleMaterial.Icon.gmd_call)
                                .withIconTintingEnabled(true)
                                .withIdentifier(PHONE_FRAGMENT),
                        new PrimaryDrawerItem()
                                .withName("Internet")
                                .withIcon(GoogleMaterial.Icon.gmd_public)
                                .withIconTintingEnabled(true)
                                .withIdentifier(INTERNET_FRAGMENT),
                        new PrimaryDrawerItem()
                                .withName("Url")
                                .withIcon(GoogleMaterial.Icon.gmd_photo)
                                .withIconTintingEnabled(true)
                                .withIdentifier(URL_FRAGMENT)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        if(iDrawerItem != null) {
                            showFragment(iDrawerItem.getIdentifier());
                        }

                        return false;
                    }
                })
                .withSelectedItem(LOREM_FRAGMENT)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    @Override
    public void onBackPressed() {
        if(drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(int flag) {
        switch (flag) {
            case LOREM_FRAGMENT:
                replace(LoremFragment.newInstance());
                break;
            case NAME_FRAGMENT:
                replace(NameFragment.newInstance());
                break;
            case NUMBER_FRAGMENT:
                replace(NumberFragment.newInstance());
                break;
            case PHONE_FRAGMENT:
                replace(PhoneFragment.newInstance());
                break;
            case INTERNET_FRAGMENT:
                replace(InternetFragment.newInstance());
                break;
            case URL_FRAGMENT:
                replace(UrlFragment.newInstance());
        }
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
