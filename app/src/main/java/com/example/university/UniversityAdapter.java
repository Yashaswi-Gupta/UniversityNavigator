package com.example.university;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.*;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder> {
    private Context context;
    private List<UniversityData> UniversityList;

    public UniversityAdapter(Context context, List<UniversityData> UniversityList){
        this.context = context;
        this.UniversityList = UniversityList;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.university_item_layout, parent, false);

        return new UniversityViewHolder(view);
    }

    public void filterList(List<UniversityData> filterList){
        UniversityList = filterList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder holder, int position) {

        UniversityData data = UniversityList.get(position);

        holder.universityName.setText(data.getName());
        holder.sno.setText(String.valueOf(position+1));

//        holder.itemView.setOnClickListener(v -> {
//            Vector<String> websiteUrls = data.getWeb_pages();
//            if (!websiteUrls.isEmpty()) {
//                String url = websiteUrls.get(0);
//                openWebView(url, v.getContext());
//            }
//        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("university", data.getName()) ;
            context.startActivity(intent);
        });
    }

//    private void openWebView(String url, Context context) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("url", url);
//        context.startActivity(intent);
//    }


    @Override
    public int getItemCount() {
        return UniversityList.size();
    }

    public class UniversityViewHolder extends RecyclerView.ViewHolder {

        private TextView sno, universityName;

        public UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            universityName = itemView.findViewById(R.id.universityname);
        }
    }
}

