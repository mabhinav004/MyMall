package com.example.mymall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview{

    private static final String TAG="GroceryItemActivity";
    public static final String GROCERY_ITEM_KEY="incoming_item";

    private boolean isBound;
    private TrackUserTime mService;

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TrackUserTime.LocalBinder binder=(TrackUserTime.LocalBinder) iBinder;
            mService=binder.getService();
            isBound=true;
            mService.setItem(incomingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
        }
    };

    private RecyclerView reviewRecView;
    private TextView txtName,txtPrice,txtDescription,txtAddReview;
    private ImageView itemImage,firstEmptyStar,firstFilledStar,secondEmptyStar,secondFilledStar,thirdEmptyStar,thirdFilledStar;
    private Button btnAddTocart;
    private RelativeLayout firstStarRelLayout,secondStarRelLayout,thirdStarRelLayout;
    private GroceryItem incomingItem;
    private MaterialToolbar toolbar;
    private ReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        initViews();
        setSupportActionBar(toolbar);
        adapter=new ReviewsAdapter();

        Intent intent=getIntent();
        if(null != intent){
            incomingItem=intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if(null != incomingItem){
                Utils.changeUserPoint(this,incomingItem,1);
                txtName.setText(incomingItem.getName());
                txtDescription.setText(incomingItem.getDescription());
                txtPrice.setText(incomingItem.getPrice()+"$");
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(itemImage);

                ArrayList<Review> reviews=Utils.getReviewById(this,incomingItem.getId());

                reviewRecView.setAdapter(adapter);
                reviewRecView.setLayoutManager(new LinearLayoutManager(this));

                if(null!= reviews){
                    if(reviews.size()>0){
                        adapter.setReviews(reviews);
                    }
                }

                btnAddTocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO:Add navigate to cart activity
                        Utils.addItemToCart(GroceryItemActivity.this,incomingItem);
                        Intent cartIntent=new Intent(GroceryItemActivity.this,CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                    }
                });

                txtAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddReviewDialog dialog=new AddReviewDialog();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY,incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"add review");
                    }
                });

               handleRating();

            }

        }
    }

    private void handleRating() {

        switch (incomingItem.getRate()){
            case 0:
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }

        firstStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate() != 1){
                    Utils.changeRate(GroceryItemActivity.this,incomingItem.getId(),1);

                    Utils.changeUserPoint(GroceryItemActivity.this,incomingItem,(1-incomingItem.getRate())*2);

                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });

        secondStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate() != 2){
                    Utils.changeRate(GroceryItemActivity.this,incomingItem.getId(),2);

                    Utils.changeUserPoint(GroceryItemActivity.this,incomingItem,(2-incomingItem.getRate())*2);

                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });

        thirdStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate() != 3){
                    Utils.changeRate(GroceryItemActivity.this,incomingItem.getId(),3);

                    Utils.changeUserPoint(GroceryItemActivity.this,incomingItem,(3-incomingItem.getRate())*2);

                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });

    }

    private void initViews() {

        txtName=findViewById(R.id.txtName);
        txtPrice=findViewById(R.id.txtPrice);
        txtDescription=findViewById(R.id.txtDescription);
        txtAddReview=findViewById(R.id.txtAddReview);
        itemImage=findViewById(R.id.itemImage);
        firstEmptyStar=findViewById(R.id.firstEmptyStar);
        firstFilledStar=findViewById(R.id.firstFilledStar);
        secondEmptyStar=findViewById(R.id.secondEmptyStar);
        secondFilledStar=findViewById(R.id.secondFilledStar);
        thirdEmptyStar=findViewById(R.id.thirdEmptyStar);
        thirdFilledStar=findViewById(R.id.thirdFilledStar);
        btnAddTocart=findViewById(R.id.btnAddToCart);
        reviewRecView=findViewById(R.id.reviewRecView);
        firstStarRelLayout=findViewById(R.id.firstStarRelLayout);
        secondStarRelLayout=findViewById(R.id.secondStarRelLayout);
        thirdStarRelLayout=findViewById(R.id.thirdStarRelLayout);
        toolbar=findViewById(R.id.toolbar);
    }

    @Override
    public void onAddReviewResult(Review review) {
        Log.e(TAG, "onAddReviewResult: new review: "+review);
        Utils.addReview(this,review);
        Utils.changeUserPoint(this,incomingItem,3);
        ArrayList<Review> reviews=Utils.getReviewById(this,review.getGroceryItemId());
        if(null!=reviews) {
            adapter.setReviews(reviews);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent=new Intent(this,TrackUserTime.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isBound){
            unbindService(connection);
        }
    }
}