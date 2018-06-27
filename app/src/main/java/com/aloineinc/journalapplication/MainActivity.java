package com.aloineinc.journalapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloineinc.journalapplication.Userauthentication.UserLoginActivity;
import com.aloineinc.journalapplication.localdb.JournalDbHelper;
import com.aloineinc.journalapplication.localdb.model.JournalModel;
import com.aloineinc.journalapplication.localdb.utilities.SeparatorItemDecoration;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private JournalsAdapter mJournalsAdapter;
    private List<JournalModel> journalsList = new ArrayList<>();
    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mRecyclerView;
    private TextView mJournalsView;

    private JournalDbHelper mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mJournalsView = findViewById(R.id.empty_journals_view);

        mDb = new JournalDbHelper(this);

        journalsList.addAll(mDb.getAllJournals());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJournalDialog(false, null, -1);
            }
        });
        mJournalsAdapter = new JournalsAdapter(this, journalsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SeparatorItemDecoration(this, LinearLayoutManager.VERTICAL, 16));






        firebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                        startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
                }

            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.logout:
                firebaseAuth.signOut();

        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void controlEmptyJournals() {
        // you can check journalsList.size() > 0

        if (mDb.getJournalsCount() > 0) {
            mJournalsView.setVisibility(View.GONE);
        } else {
            mJournalsView.setVisibility(View.VISIBLE);
        }
    }
    private void createJournal(String journal) {
        // inserting journal in db and getting
        // newly inserted journal id
        long id = mDb.insertJournal(journal);

        // get the newly inserted journal from db
        JournalModel n = mDb.getJournal(id);

        if (n != null) {
            // adding new journal to array list at 0 position
            journalsList.add(0, n);

            // refreshing the list
            mJournalsAdapter.notifyDataSetChanged();

            controlEmptyJournals();
        }
    }
    private void updateJournal(String journal, int position) {
        JournalModel n = journalsList.get(position);
        // updating journal text
        n.setJournal(journal);

        // updating journal in db
        mDb.updateJournal(n);

        // refreshing the list
        journalsList.set(position, n);
        mJournalsAdapter.notifyItemChanged(position);

        controlEmptyJournals();
    }

    private void deleteJournal(int position) {
        // deleting the journal from db
        mDb.deleteJournal(journalsList.get(position));

        // removing the journal from the list
        journalsList.remove(position);
        mJournalsAdapter.notifyItemRemoved(position);

        controlEmptyJournals();
    }

    private void showJournalDialog(final boolean shouldUpdate, final JournalModel journal, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.journal_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputJournal = view.findViewById(R.id.journal);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_journal_title) : getString(R.string.lbl_edit_journal_title));

        if (shouldUpdate && journal != null) {
            inputJournal.setText(journal.getJournal());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputJournal.getText().toString())) {
                    inputJournal.setError("Field cannot be blank");
                    Toast.makeText(MainActivity.this, "Enter journal!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating journal
                if (shouldUpdate && journal != null) {
                    // update journal by it's id
                    updateJournal(inputJournal.getText().toString(), position);
                } else {
                    // create new journal
                    createJournal(inputJournal.getText().toString());
                }
            }
        });
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showJournalDialog(true, journalsList.get(position), position);
                } else {
                    deleteJournal(position);
                }
            }
        });
        builder.show();
    }

}