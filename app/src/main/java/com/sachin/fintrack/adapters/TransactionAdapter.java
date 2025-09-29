package com.sachin.fintrack.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin.fintrack.R;
import com.sachin.fintrack.databinding.RowTransactionBinding;
import com.sachin.fintrack.models.Category;
import com.sachin.fintrack.models.Transaction;
import com.sachin.fintrack.utils.Constants;
import com.sachin.fintrack.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final Context context;
    private final List<Transaction> transactions;
    private final OnTransactionDeleteListener deleteListener;

    public interface OnTransactionDeleteListener {
        void onDeleteTransaction(Transaction transaction);
    }

    public TransactionAdapter(Context context, List<Transaction> transactions, OnTransactionDeleteListener deleteListener) {
        this.context = context;
        this.transactions = new ArrayList<>(transactions);
        this.deleteListener = deleteListener;
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

        String account = transaction.getAccount() != null ? transaction.getAccount() : "N/A";
        holder.binding.accountLbl.setText(account);

        if (transaction.getDate() != null) {
            holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        } else {
            holder.binding.transactionDate.setText("");
        }

        String categoryName = transaction.getCategory() != null ? transaction.getCategory() : "Other";
        holder.binding.transactionCategory.setText(categoryName);

        Category transactionCategory = null;
        if (transaction.getCategory() != null && !transaction.getCategory().isEmpty()) {
            transactionCategory = Constants.getCategoryDetails(transaction.getCategory());
        }

        if (transactionCategory != null) {
            holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
            holder.binding.categoryIcon.setBackgroundTintList(
                    ContextCompat.getColorStateList(context, transactionCategory.getCategoryColor())
            );
        } else {
            holder.binding.categoryIcon.setImageResource(R.drawable.ic_other);
            holder.binding.categoryIcon.setBackgroundTintList(
                    ContextCompat.getColorStateList(context, R.color.redColor)
            );
        }

        holder.binding.accountLbl.setBackgroundTintList(
                ContextCompat.getColorStateList(context, Constants.getAccountsColor(account))
        );

        if (Constants.INCOME.equals(transaction.getType())) {
            holder.binding.transactionAmount.setTextColor(ContextCompat.getColor(context, R.color.greenColor));
        } else if (Constants.EXPENSE.equals(transaction.getType())) {
            holder.binding.transactionAmount.setTextColor(ContextCompat.getColor(context, R.color.redColor));
        } else {
            holder.binding.transactionAmount.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog deleteDialog = new AlertDialog.Builder(view.getContext()).create();
            deleteDialog.setTitle("Delete Transaction");
            deleteDialog.setMessage("Are you sure you want to delete this transaction?");
            deleteDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteTransaction(transaction);
                }
            });
            deleteDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> deleteDialog.dismiss());
            deleteDialog.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        RowTransactionBinding binding;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
