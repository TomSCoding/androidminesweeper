package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CustomSquares extends BaseAdapter {
    // Custom square adapter representing each square on the grid
    private Context context;
    private List<Square> squares;

    CustomSquares(Context context) {
        this.context = context;
    }

    void setSquares(List<Square> squares)
    {
        this.squares = squares;
    }

    public int getCount() {
        return squares.size();
    }

    public Square getItem(int position) {
        return squares.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private float dpToPixels(Context c, float dp) {
        return dp * c.getResources().getDisplayMetrics().density;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        // Set the relative layout for each square
        RelativeLayout relativeLayout=new RelativeLayout(context);
        relativeLayout.setLayoutParams(new GridView.LayoutParams((int)dpToPixels(context, 34), (int)dpToPixels(context, 34)));


        relativeLayout.setBackgroundColor(Color.parseColor("#000000"));


        Log.d("Item = ", String.valueOf(getItem(position)));
        //relativeLayout.addView(imageView);

        return relativeLayout;
    }
}