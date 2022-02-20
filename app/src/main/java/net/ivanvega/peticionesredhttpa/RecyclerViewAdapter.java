package net.ivanvega.peticionesredhttpa;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<Heroe> listaAlbum;
    Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Heroe> albums){
        this.context=context;
        listaAlbum = albums;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.row_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(listaAlbum.get(position).name);
        holder.txturl.setText(listaAlbum.get(position).url);
        requestImageMethod(listaAlbum.get(position).url,holder.img);
    }

    @Override
    public int getItemCount() {
        return listaAlbum.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txturl;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txturl = itemView.findViewById(R.id.txtUrl);
            img = itemView.findViewById(R.id.imgImage);
        }
    }

    void requestImageMethod(String url, ImageView img){
        ImageRequest imgReq = new
                ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img.setImageBitmap(response);
                    }

                },80,80,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                error -> {
                    error.printStackTrace();
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(imgReq);
    }
}
