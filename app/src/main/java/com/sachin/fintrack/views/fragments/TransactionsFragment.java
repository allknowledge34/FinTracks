package com.sachin.fintrack.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sachin.fintrack.R;
import com.sachin.fintrack.adapters.TransactionAdapter;
import com.sachin.fintrack.databinding.FragmentTransactionsBinding;
import com.sachin.fintrack.models.Transaction;
import com.sachin.fintrack.models.UserModel;
import com.sachin.fintrack.utils.Constants;
import com.sachin.fintrack.utils.Helper;
import com.sachin.fintrack.viewmodels.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;
    private FirebaseFirestore firestore;
    private Calendar calendar;
    public MainViewModel viewModel;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        firestore = FirebaseFirestore.getInstance();

        loadUserData();

        calendar = Calendar.getInstance();
        updateDate();

        binding.nextDateBtn.setOnClickListener(c -> {
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });

        binding.previousDateBtn.setOnClickListener(c -> {
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.floatingActionButton.setOnClickListener(c -> {
            new AddTransactionFragment().show(getParentFragmentManager(), null);
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ("Monthly".contentEquals(tab.getText())) {
                    Constants.SELECTED_TAB = Constants.MONTHLY;
                    updateDate();
                } else if ("Daily".contentEquals(tab.getText())) {
                    Constants.SELECTED_TAB = Constants.DAILY;
                    updateDate();
                } else if ("Calendar".contentEquals(tab.getText())) {
                    Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                } else if ("Summary".contentEquals(tab.getText())) {
                    Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                } else if ("Notes".contentEquals(tab.getText())) {
                    Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Not needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Not needed
            }
        });

        binding.transactionsList.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.transactions.observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(
                        requireContext(),
                        transactions,
                        transaction -> viewModel.deleteTransaction(transaction) // delete action
                );
                binding.transactionsList.setAdapter(transactionAdapter);

                if (transactions != null && transactions.size() > 0) {
                    binding.emptyState.setVisibility(View.GONE);
                } else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.totalIncome.observe(getViewLifecycleOwner(), aDouble -> {
            binding.incomeLbl.setText(String.valueOf(aDouble));
        });

        viewModel.totalExpense.observe(getViewLifecycleOwner(), aDouble -> {
            binding.expenseLbl.setText(String.valueOf(aDouble));
        });

        viewModel.totalAmount.observe(getViewLifecycleOwner(), aDouble -> {
            binding.totalLbl.setText(String.valueOf(aDouble));
        });

        viewModel.getTransactions(calendar);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void updateDate() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar);
    }

    private void loadUserData() {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel model = documentSnapshot.toObject(UserModel.class);
                        if (documentSnapshot.exists() && model != null) {
                            binding.userName.setText(model.getName() != null ? model.getName() : "Guest");

                            Picasso.get()
                                    .load(model.getProfile())
                                    .placeholder(R.drawable.friend_2)
                                    .into(binding.profileImage);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
