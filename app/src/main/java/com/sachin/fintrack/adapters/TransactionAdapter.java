package com.sachin.fintrack.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin.fintrack.R;
import com.sachin.fintrack.databinding.RowTransactionBinding;
import com.sachin.fintrack.models.Category;
import com.sachin.fintrack.models.Transaction;
import com.sachin.fintrack.utils.Constants;
import com.sachin.fintrack.utils.Helper;
import com.sachin.fintrack.views.activites.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;
    List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = new ArrayList<>(transactions);
    }


    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountLbl.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getCategory());

        Category transactionCategory = null;
        if (transaction.getCategory() != null && !transaction.getCategory().isEmpty()) {
            transactionCategory = Constants.getCategoryDetails(transaction.getCategory());
        }

        if (transactionCategory != null) {
            holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
            holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));
        } else {
            holder.binding.categoryIcon.setImageResource(R.drawable.ic_other);
            holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(R.color.redColor));
        }

        holder.binding.accountLbl.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));

        if (transaction.getType() != null) {
            if (transaction.getType().equals(Constants.INCOME)) {
                holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor));
            } else if (transaction.getType().equals(Constants.EXPENSE)) {
                holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor));
            }
        }

        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
            deleteDialog.setTitle("Delete Transaction");
            deleteDialog.setMessage("Are you sure to delete this transaction?");
            deleteDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                ((MainActivity) context).viewModel.deleteTransaction(transaction);
            });
            deleteDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> deleteDialog.dismiss());
            deleteDialog.show();
            return false;
        });
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        RowTransactionBinding binding;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
