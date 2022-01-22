package com.example.androidapis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.postViewHolder> {

    List<Posts> postsList;
    Context context;
    public PostsAdapter(Context context, List<Posts> posts) {
        this.context = context;
        postsList = posts;
    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new postViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int position) {
        Posts posts = postsList.get(position);
        holder.id.setText("Body: " +posts.getBody());
        holder.userId.setText("UserId: " +posts.getUserId());
        holder.theBody.setText("Id: " +posts.getId());
        holder.title.setText("Title: " +posts.getTitle());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class postViewHolder extends RecyclerView.ViewHolder{
        TextView userId, id, title, theBody;
        public postViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.itemId);
            userId = itemView.findViewById(R.id.userId);
            theBody = itemView.findViewById(R.id.body);
            title = itemView.findViewById(R.id.itemTittle);

        }
    }
}
