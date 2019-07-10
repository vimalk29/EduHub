package com.eduhub.company.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduhub.company.R;
import com.eduhub.company.activities.DownloadActivity;
import com.eduhub.company.model.Upload;
import java.util.ArrayList;

public class FileSharingAdapter extends RecyclerView.Adapter<FileSharingAdapter.ViewHolder> {
    ArrayList<Upload> uploads;
    Context context;
    private long downloadID;

    public FileSharingAdapter(ArrayList<Upload> uploads, Context context){
        this.context = context;
        this.uploads = uploads;
    }
    @NonNull
    @Override
    public FileSharingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.file_item, parent, false);
        FileSharingAdapter.ViewHolder viewHolder = new FileSharingAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FileSharingAdapter.ViewHolder viewHolder, final int i) {
        final Upload upload = uploads.get(i);
        Log.d("1234file", "onBindViewHolder: "+uploads.size());
        viewHolder.buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Downloading File", Toast.LENGTH_SHORT);
                Log.d("1234", "onClick: chlo shuru kre 1 khel"+upload.getName()+" "+upload.getUrl());
                /*Intent intent = new Intent(context, DownloadActivity.class);
                intent.putExtra("fileName", upload.getName());
                intent.putExtra("url", upload.getUrl());

                context.startActivity(intent);*/
            }
        });
        Log.d("123456", "onBindViewHolder: sdxrfcgvhbjnkjbhvgcfxdzfcgvhbjnbhgvcfxd");
        viewHolder.fileName.setText(upload.getName());
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        ImageView buttonDownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            buttonDownload = itemView.findViewById(R.id.buttonDownload);
        }
    }
}
