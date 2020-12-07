package com.example.hazajobsfinal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hazajobsfinal.Modal.UserModal;
import com.example.hazajobsfinal.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.userViewHolder> {
    LayoutInflater inflater;
    Context cxt;
    List<UserModal> users;
    RequestOptions option;


    public UsersAdapter(Context context, List<UserModal> users) {
        this.inflater = LayoutInflater.from(context);
        this.cxt = context;
        this.users = users;
        option = new RequestOptions().override(470, 360).optionalCenterCrop().placeholder(R.mipmap.logo).error(R.mipmap.logo);
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rowuser, parent, false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        // Bind data to our custom layout
        holder.userName.setText("" + users.get(position).getFirstName() +"  "+ users.get(position).getLastName());
        holder.title.setText(users.get(position).getTitle());
        holder.motor.setText(users.get(position).getMotor());

        Glide.with(cxt).load(users.get(position).getImageUrl()).apply(option).into(holder.jobImageCover);
//        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
            TextView userName, title, motor;
            CircleImageView jobImageCover;
            Button btnDetail;
        public userViewHolder(@NonNull View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.usernameId);
                title = itemView.findViewById(R.id.titleId);
                motor = itemView.findViewById(R.id.motorId);
                jobImageCover = itemView.findViewById(R.id.imageProfilerId);
                btnDetail = itemView.findViewById(R.id.onViewBtnId);

            }
        }
    }



