package com.cogwheelSoft.androiddevelop.wakeup;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerHelper extends AppCompatActivity{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.helperRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.addAll(ItemsAdder.getCard());
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private ArrayList<ItemsAdder> itemsAdder = new ArrayList<>();

        public void addAll(List<ItemsAdder> card) {
            int number = getItemCount();
            this.itemsAdder.addAll(card);
            notifyItemRangeInserted(number, this.itemsAdder.size());
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new RecyclerViewHolder(viewHolder);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.bind(itemsAdder.get(position));
        }

        @Override
        public int getItemCount() {
            return itemsAdder.size();
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView functionName;
        private ImageView functionPreview;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            functionName = (TextView) itemView.findViewById(R.id.title);
            functionPreview = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bind(ItemsAdder itemsAdder) {
            functionPreview.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), itemsAdder.getCardImg()));
            functionName.setText(itemsAdder.getAbout());
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(RecyclerHelper.this, Settings_Preferences.class));
        overridePendingTransition(R.anim.settings_anim_reverce, R.anim.options_anim_reverce);
        finish();
    }
}