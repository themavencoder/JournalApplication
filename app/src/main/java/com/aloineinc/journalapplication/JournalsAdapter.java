package com.aloineinc.journalapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloineinc.journalapplication.localdb.model.JournalModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JournalsAdapter extends RecyclerView.Adapter<JournalsAdapter.JournalsViewHolder> {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Context mContext;
    private final List<JournalModel> mJournalsList;

    public class JournalsViewHolder extends RecyclerView.ViewHolder {
        final TextView journal;
       final  TextView dash;
       final TextView intervals;

        JournalsViewHolder(View view) {
            super(view);
            journal = view.findViewById(R.id.journal);
            dash = view.findViewById(R.id.dot);
            intervals = view.findViewById(R.id.interval);
        }
    }


    public JournalsAdapter(Context context, List<JournalModel> journalsList) {
        this.mContext = context;
        this.mJournalsList = journalsList;
    }

    @NonNull
    @Override
    public JournalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal, parent, false);

        return new JournalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalsViewHolder holder, int position) {
        JournalModel journal = mJournalsList.get(position);

        holder.journal.setText(journal.getJournal());

        // Displaying dot from HTML character code
        holder.dash.setText(Html.fromHtml("&#8211;"));

        // Formatting and displaying intervals
        holder.intervals.setText(formatDate(journal.getInterval()));
    }

    @Override
    public int getItemCount() {
        return mJournalsList.size();
    }

    /**
     * Formatting intervals to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(@SuppressWarnings("unused") String dateStr) {

            DateFormat fmt = SimpleDateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);
            Date date = Calendar.getInstance().getTime();
        return fmt.format(date);



    }
}
