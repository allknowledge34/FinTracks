package com.sachin.fintrack.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin.fintrack.R;

public class MessageAdapter {

    public class viewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout leftChat, rightChat;
        TextView leftText, rightText;
        public viewHolder(@NonNull View itemView){
            super(itemView);

            leftChat = itemView.findViewById(R.id.left_chat);
            rightChat = itemView.findViewById(R.id.right_chat);
            leftText = itemView.findViewById(R.id.eft_txt);
            rightText = itemView.findViewById(R.id.right_txt);
        }
    }
}
