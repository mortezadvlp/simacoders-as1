package com.simacoders.morteza.customrecyclerview1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<classData> list = new ArrayList<>();
        for (int i= 0; i<5; i++){
            classData data = new classData();
            data.title = "Title #" + String.valueOf(i);
            data.details = "Details of item #" + String.valueOf(i);
            list.add(data);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final MyAdapter adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

        ImageButton btnAdd = findViewById(R.id.imageButtonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classData data = new classData();
                data.title = "New Title";
                data.details = "New Details";
                adapter.add(data);
            }
        });

        ImageButton btnDel = findViewById(R.id.imageButtonDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.remove();
            }
        });

    }

    public class classData{
        public String title;
        public String details;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        ArrayList<classData> myDataset;
        int currentPosition;

        public MyAdapter(ArrayList<classData> myDataset) {
            this.myDataset = myDataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            classData data = myDataset.get(position);
            holder.textViewTitle.setText(data.title);
            holder.textViewDetails.setText(data.details);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indx = holder.getAdapterPosition();
                    currentPosition = indx;
                    Toast.makeText(MainActivity.this,
                            "Selected Item: " + String.valueOf(indx),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return myDataset.size();
        }

        public void add(classData data){
            myDataset.add(currentPosition, data);
            notifyItemInserted(currentPosition);
        }

        public void remove(){
            myDataset.remove(currentPosition);
            notifyItemRemoved(currentPosition);
            notifyItemRangeChanged(currentPosition, getItemCount());
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView imageView;
            public TextView textViewTitle;
            public TextView textViewDetails;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewDetails = itemView.findViewById(R.id.textViewDetails);
            }
        }
    }

}
