package com.sachin.fintrack.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sachin.fintrack.R;
import com.sachin.fintrack.adapters.MessageAdapter;
import com.sachin.fintrack.databinding.FragmentChatbotBinding;
import com.sachin.fintrack.models.MessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatbotFragment extends Fragment {

    FragmentChatbotBinding binding;
    RecyclerView recyclerView;
    EditText message;
    ImageView send;
    List<MessageModel>list;
    MessageAdapter adapter;
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();
    public ChatbotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater, container, false);

        recyclerView = binding.recyclerView;
        message = binding.message;
        send = binding.send;

        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(list);
        recyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.ch.setVisibility(View.VISIBLE);

                String question = message.getText().toString();

                if (question.isEmpty()){

                    Toast.makeText(getContext(), "write something", Toast.LENGTH_SHORT).show();
                }

                else {

                    addToChat(question, MessageModel.SENT_BY_ME);
                    message.setText("");

                    binding.ch.setVisibility(View.GONE);

                    callAPI(question);
                }
            }
        });
        return binding.getRoot();
    }

    private void callAPI(String question) {

        binding.ch.setVisibility(View.GONE);

        list.add(new MessageModel("Typing...",MessageModel.SENT_BY_BOT));

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("model", "gpt-3.5-turbo-instruct");
            jsonObject.put("prompt",question);
            jsonObject.put("max_tokens",5000);
            jsonObject.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                addResponse("Failed to load" + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()){

                    JSONObject jsonObject1 = null;

                    try {
                        jsonObject1 = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject1.getJSONArray("choices");

                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {

                    addResponse("Failed to load" + response.body().string());

                }
            }
        });

    }

    private void addResponse(String s) {

        list.remove(list.size() -1);
        addToChat(s,MessageModel.SENT_BY_BOT);
    }

    private void addToChat(String question, String sentByMe) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.add(new MessageModel(question, sentByMe));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }
}