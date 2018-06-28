package com.aloineinc.journalapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloineinc.journalapplication.localdb.model.JournalModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JournalsAdapter extends RecyclerView.Adapter<JournalsAdapter.JournalsViewHolder> {

    private Context context;
    private List<JournalModel> journalsList;

    public class JournalsViewHolder extends RecyclerView.ViewHolder {
        public TextView journal;
        public TextView dot;
        public TextView intervals;

        public JournalsViewHolder(View view) {
            super(view);
            journal = view.findViewById(R.id.journal);
            dot = view.findViewById(R.id.dot);
            intervals = view.findViewById(R.id.interval);
        }
    }


    public JournalsAdapter(Context context, List<JournalModel> journalsList) {
        this.context = context;
        this.journalsList = journalsList;
    }

    @Override
    public JournalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_ui, parent, false);

        return new JournalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JournalsViewHolder holder, int position) {
        JournalModel journal = journalsList.get(position);

        holder.journal.setText(journal.getJournal());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8211;"));

        // Formatting and displaying intervals
        holder.intervals.setText(formatDate(journal.getInterval()));
    }

    @Override
    public int getItemCount() {
        return journalsList.size();
    }

    /**
     * Formatting intervals to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
