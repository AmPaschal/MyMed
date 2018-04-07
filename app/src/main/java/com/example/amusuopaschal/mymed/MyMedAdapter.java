package com.example.amusuopaschal.mymed;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Amusuo Paschal on 02/04/2018.
 */

public class MyMedAdapter extends RecyclerView.Adapter<MyMedAdapter.MyMedViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public MyMedAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }


    @Override
    public MyMedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mymed_list_item, parent, false);
        return new MyMedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyMedViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position)){
            return;
        }



    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;
        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }

    class MyMedViewHolder extends RecyclerView.ViewHolder {

        TextView tvListItem;

        public MyMedViewHolder(View itemView) {
            super(itemView);
            tvListItem = (TextView) itemView.findViewById(R.id.tv_list_details);
        }
    }
}
