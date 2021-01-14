package com.example.recyclerviewtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ViewHolder> {

    private List<Tools> mToolsList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View toolsView;
        TextView toolsName;
        ImageView toolsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            toolsView = itemView;
            toolsName = itemView.findViewById(R.id.item_tv);
            toolsImage = itemView.findViewById(R.id.item_iv);
        }
    }

    public ToolAdapter(List<Tools> mToolsList){
        this.mToolsList = mToolsList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tools_item,parent,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.toolsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Tools tools = mToolsList.get(position);
                Toast.makeText(v.getContext(),"you clicked view:" + tools.getToolesName(),Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.toolsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Tools tools = mToolsList.get(position);
                Toast.makeText(v.getContext(),"you clicked image:" + tools.getToolesName(),Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tools tools = mToolsList.get(position);
        holder.toolsName.setText(tools.getToolesName());
        holder.toolsImage.setImageResource(tools.getToolsId());
    }

    @Override
    public int getItemCount() {
        return mToolsList.size();
    }
}
