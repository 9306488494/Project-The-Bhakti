package com.thebhakti;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yeshveer on 8/18/2018.
 */

class RecyclerAdapterProduct extends RecyclerView.Adapter<RecyclerAdapterProduct.ProductViewHolder> {
    Context mcontext;
    private ArrayList<DetailsofP> detaillist1;
    public RecyclerAdapterProduct(Context mcontext, ArrayList<DetailsofP> detaillist1) {
        this.mcontext=mcontext;
        this.detaillist1=detaillist1;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view;
       view= LayoutInflater.from(mcontext).inflate(R.layout.prod_related,parent,false);
        ProductViewHolder productViewHolder=new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        loadProductImage(holder,position);
        holder.nameOfProd.setText(detaillist1.get(position).getProdDetails());
        holder.textprice.setText(detaillist1.get(position).getProdPrice());



    }
    private void loadProductImage(final ProductViewHolder holder, int position) {
        // initilize image with image name

        ShareGanesh.fs= FirebaseStorage.getInstance();
        ShareGanesh.sr=ShareGanesh.fs.getReference().child(detaillist1.get(position).getProdname1()+"/"+detaillist1.get(position).getProdImageName());
        ShareGanesh.sr.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(uri.toString().equals(""))
                        {
                            holder.nameOfProd.setVisibility(View.GONE);
                            holder.textprice.setVisibility(View.GONE);
                            holder.prodImg.setVisibility(View.GONE);


                        }
                        else {
                            Picasso.get().load(uri.toString()).into(holder.prodImg);
                        }

                    }
                });
    }


    @Override
    public int getItemCount() {
        return detaillist1.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView prodImg;
        private TextView nameOfProd;
        private TextView textprice;
        private CardView productMainCardview;
        private TextView titleOfRelatedProducts;






        ProductViewHolder(View itemView) {
            super(itemView);
            prodImg = (ImageView) itemView.findViewById(R.id.prodImg);
            nameOfProd = (TextView) itemView.findViewById(R.id.nameOfProd);
            textprice = (TextView) itemView.findViewById(R.id.textprice);
            productMainCardview = (CardView) itemView.findViewById(R.id.product_main_cardview);
            titleOfRelatedProducts = (TextView) itemView.findViewById(R.id.title_of_related_products);


        }
    }
}
