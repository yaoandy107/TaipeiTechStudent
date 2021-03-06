package com.Ntut.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.Ntut.MainActivity;
import com.Ntut.R;
import com.Ntut.model.EventInfo;
import com.Ntut.model.Model;


import java.util.List;

/**
 * Created by blackmaple on 2017/5/15.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private MainActivity context;
    private List<EventInfo> eventList;

    EventAdapter(List<EventInfo> eventList, Context context) {
        this.context = (MainActivity) context;
        this.eventList = eventList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView date;
        TextView location;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            title = itemView.findViewById(R.id.event_title);
            date = itemView.findViewById(R.id.event_date);
            location = itemView.findViewById(R.id.event_location);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final EventInfo event = eventList.get(position);
        final ImageView image = holder.image;
        event.getImage(context).into(image);
        final TextView title = holder.title;
        title.setText(event.getTitle());
        TextView date = holder.date;
        date.setText(event.getStartDate());
        TextView location = holder.location;
        location.setText(event.getLocation());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("detail", event);
            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(context
                    , new Pair<>(image, "event_image")
                    , new Pair<>(title, "event_title")).toBundle();
            context.startActivity(intent, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return eventList == null ? 0 : eventList.size();
    }

    public void add(EventInfo eventInfo) {
        eventList.add(eventInfo);
    }

    void saveData() {
        Model.getInstance().setEventArray(eventList);
        Model.getInstance().saveEventArray();
    }

    void clear() {
        eventList.clear();
    }
}
