package com.gruporosul.vallasrosul.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.adapter.VisitasAdapter;
import com.gruporosul.vallasrosul.manager.PrefManager;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsActivity extends AppCompatActivity
        implements VisitasAdapter.OnItemClickListener {

    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerVisitas)
    RecyclerView mRecyclerVisitas;
    @BindView(R.id.content_locations)
    ConstraintLayout mContentLocations;

    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLayoutManager;
    private VisitasAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PrefManager.get(this).isLoggedIn()) {
            startActivity(new Intent(LocationsActivity.this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);

        setToolbar();

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(VallasWebAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mVallasApi = mRestAdapter.create(VallasWebAPI.class);

        mRecyclerVisitas.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerVisitas.setLayoutManager(mLayoutManager);

        mRecyclerVisitas.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Obteniendo visitas");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        getVisitas();

        final String user = PrefManager.get(this).getString(this, "PREF_TOKEN");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, user, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locations, menu);
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
    public void onItemClick(VisitasAdapter.ViewHolder item, int position) {

    }

    public void getVisitas() {
    }
}
