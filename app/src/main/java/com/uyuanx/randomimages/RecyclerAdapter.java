package com.uyuanx.randomimages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Result> localDataSet;
    private Activity context;

    public RecyclerAdapter(Activity context, List<Result> dataSet) {
        this.context = context;
        localDataSet = dataSet;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mainPic;
        ImageView profilePic;
        TextView username;
        ImageButton linksBtn;

        ConstraintLayout subLayout;
        ImageButton profileBtn;
        ImageButton downloadBtn;
        ImageButton instaBtn;

        public ViewHolder(View view) {
            super(view);
            mainPic = view.findViewById(R.id.unsplashed);
            profilePic = view.findViewById(R.id.profile_pic);
            username = view.findViewById(R.id.username);
            subLayout = view.findViewById(R.id.sub_layout);
            profileBtn = view.findViewById(R.id.profile_btn);
            downloadBtn = view.findViewById(R.id.download_btn);
            instaBtn = view.findViewById(R.id.instagram_btn);

        }
    }

    public int getSize() {
        return localDataSet.size();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_image, viewGroup, false);
        return new RecyclerAdapter.ViewHolder(view);
    }

    public void setData(List<Result> results) {
        this.localDataSet = results;
        Log.d("shjang", "" + localDataSet.size());
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final int position) {
        Result resultData = localDataSet.get(position);
        if (resultData != null) {
            Log.d("ri_data", "Data Found");
            Log.d("ri_url", resultData.getUrls().getRegular());
            Glide.with(context.getApplicationContext())
                    .load(resultData.getUrls().getRegular())
                    .into(viewHolder.mainPic);
            Glide.with(context.getApplicationContext())
                    .load(resultData.getUser().getProfile_image().getLarge())
                    .into(viewHolder.profilePic);
            viewHolder.username.setText(resultData.getUser().getName());

        } else {
            Log.d("ri_data", "Data Missing");
        }
        viewHolder.mainPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int linksVisible = viewHolder.subLayout.getVisibility();
                if (linksVisible == View.VISIBLE) {
                    viewHolder.subLayout.setVisibility(View.GONE);
                } else {
                    viewHolder.subLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String profileUrl;
                if (resultData != null) {
                    profileUrl = resultData.getUser().getLinks().getHtml();
                    if (profileUrl != null) {
                        Log.d("ri_url", profileUrl);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl));
                        context.startActivity(browserIntent);
                    }
                }
            }
        });

        viewHolder.instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instagramUrl;
                if (resultData != null) {
                    instagramUrl = resultData.getUser().getSocial().getInstagram_username();
                    if (instagramUrl != null) {
                        instagramUrl = "https://www.instagram.com/" + instagramUrl;
                        Log.d("ri_url", instagramUrl);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
                        context.startActivity(browserIntent);
                    }
                }
            }
        });

        viewHolder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String downloadUrl;
                if (resultData != null) {
                    downloadUrl = resultData.getLinks().getDownload();
                    Log.d("ri_url", downloadUrl);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                    context.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
