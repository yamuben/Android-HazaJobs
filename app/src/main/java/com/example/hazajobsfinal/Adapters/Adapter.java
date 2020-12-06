package com.example.hazajobsfinal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hazajobsfinal.Modal.JobModal;
import com.example.hazajobsfinal.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    LayoutInflater inflater;
    Context cxt;
    List<JobModal> jobs;

    public Adapter(Context cxt, List<JobModal> jobs){
        this.inflater = LayoutInflater.from(cxt);
        this.cxt = cxt;
        this.jobs = jobs;
//        option = new RequestOptions().override(470, 360).optionalCenterCrop().placeholder(R.drawable.btn_bg).error(R.drawable.btn_bg);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_job, parent, false);
        return new ViewHolder(view);
    }

//    public void deleteItem(int position){
//        tours.remove(position);
//        Toast.makeText(cxt, tours.get(position).getTourId(), Toast.LENGTH_LONG);
//    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to our custom layout
        holder.jobTitle.setText(jobs.get(position).getTitle());
        holder.owner.setText(jobs.get(position).getCity());
        holder.date.setText(jobs.get(position).getJobSalary());
//        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView jobTitle, owner, date;
        CircleImageView jobImageCover;
        Button btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitleId);
            owner = itemView.findViewById(R.id.postedById);
            date = itemView.findViewById(R.id.dateId);
            jobImageCover = itemView.findViewById(R.id.imageProfilerId);
            btnDetail = itemView.findViewById(R.id.onViewBtnId);

        }
    }
}
