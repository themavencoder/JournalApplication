/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aloineinc.journalapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloineinc.journalapplication.userauthentication.UserLoginActivity;
import com.aloineinc.journalapplication.localdb.JournalDbHelper;
import com.aloineinc.journalapplication.localdb.model.JournalModel;
import com.aloineinc.journalapplication.localdb.utilities.RecyclerTouchListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private JournalsAdapter mJournalsAdapter;
    private final List<JournalModel> mJournalsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mJournalsView;

    private JournalDbHelper mDb;

    private DatabaseReference mMessagesDatabaseReference;

    private boolean mAppBarExpanded;
    private Menu mCollapsedMenu;
    private CollapsingToolbarLayout mCollapsingToolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCollapsingToolbar =findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setTitle("Journals");
        findViewById(R.id.header);
        init();
        mJournalsList.addAll(mDb.getAllJournals());

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully  expanded.
                if (Math.abs(verticalOffset) > 200) {
                    mAppBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    mAppBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
        gradientImage();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJournalDialog(false, null, -1);
            }
        });

        mJournalsAdapter = new JournalsAdapter(this, mJournalsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mJournalsAdapter);

        controlEmptyJournals();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));


        firebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                        startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
                }

            }
        };
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("entries");

    }

    private void init() {
        //noinspection unused
        CoordinatorLayout mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mJournalsView = findViewById(R.id.empty_journals_view);

        mDb = new JournalDbHelper(this);


    }
    private void createJournal(String journal) {
        mMessagesDatabaseReference.push().setValue(journal);
        // inserting journal in db and getting
        // newly inserted journal id
        long id = mDb.insertJournal(journal);

        // get the newly inserted journal from db
        JournalModel n = mDb.getJournal(id);

        if (n != null) {
            // adding new journal to array list at 0 position
            mJournalsList.add(0, n);

            // refreshing the list
            mJournalsAdapter.notifyDataSetChanged();

            controlEmptyJournals();
        }
    }
    /**
     * Updating journal in db and updating
     * item in the list by its position
     */
    private void updateJournal(String journal, int position) {
        JournalModel n = mJournalsList.get(position);
        // updating journal text
        n.setJournal(journal);

        // updating journal in db
        mDb.updateJournal(n);

        // refreshing the list
        mJournalsList.set(position, n);
        mJournalsAdapter.notifyItemChanged(position);

        controlEmptyJournals();
    }

    /**
     * Deleting journal from SQLite and removing the
     * item from the list by its position
     */
    private void deleteJournal(int position) {
        // deleting the journal from db
        mDb.deleteJournal(mJournalsList.get(position));

        // removing the journal from the list
        mJournalsList.remove(position);
        mJournalsAdapter.notifyItemRemoved(position);

        controlEmptyJournals();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showJournalDialog(true, mJournalsList.get(position), position);
                } else {
                    deleteJournal(position);
                }
            }
        });
        builder.show();
    }
    /**
     * Shows alert dialog with EditText options to enter / edit
     * a journal.
     * when shouldUpdate=true, it automatically displays old journal and changes the
     * button text to UPDATE
     */
    private void showJournalDialog(final boolean shouldUpdate, final JournalModel journal, final int position) {
        final ViewGroup nullParent = null;
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_journal, nullParent);

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

    private void controlEmptyJournals() {
        // you can check journalsList.size() > 0

        if (mDb.getJournalsCount() > 0) {
            mJournalsView.setVisibility(View.GONE);
        } else {
            mJournalsView.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        mCollapsedMenu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.logout:
                firebaseAuth.signOut();
                break;
            default:
                showJournalDialog(false, null, -1);


        }

  return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mCollapsedMenu != null
                && (!mAppBarExpanded || mCollapsedMenu.size() != 1)) {
            //collapsed
            mCollapsedMenu.add("Add")
                    .setIcon(R.drawable.ic_add_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return super.onPrepareOptionsMenu(mCollapsedMenu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void gradientImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header_image);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                mCollapsingToolbar.setContentScrimColor(mutedColor);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                      finish();
                    }
                }).create().show();
    }

}