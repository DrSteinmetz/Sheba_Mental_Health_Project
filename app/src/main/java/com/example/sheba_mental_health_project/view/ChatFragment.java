package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ChatAdapter;
import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.ChatViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private ChatAdapter mChatAdapter;

    private EditText mChatBoxEt;
    private ImageButton mSendBtn;
    private ExtendedFloatingActionButton mScrollDownBtn;

    private LinearLayoutManager mLinearLayoutManager;

    private boolean mIsShown = false;

    private final String TAG = "ChatFragment";


    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Chat)).get(ChatViewModel.class);

        Observer<String> onUploadChatMessageToCloudFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getUploadMessageFailed().observe(this, onUploadChatMessageToCloudFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view_message_list);
        mChatBoxEt = rootView.findViewById(R.id.chatbox_et);
        mSendBtn = rootView.findViewById(R.id.send_btn);
        final TextView recipientNameTv = rootView.findViewById(R.id.tool_bar_username);
        mScrollDownBtn = rootView.findViewById(R.id.scroll_down_btn);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        recipientNameTv.setText(mViewModel.getRecipientFullName());

        final FirestoreRecyclerOptions<ChatMessage> recyclerOptions = new FirestoreRecyclerOptions
                .Builder<ChatMessage>().setQuery(mViewModel.getChatQuery(), ChatMessage.class)
                .build();

        mChatAdapter = new ChatAdapter(recyclerOptions, mViewModel.getUserEmail());
        Log.d(TAG, "onCreateView: " + recyclerOptions);

        final int[] newMessagesCount = {0};

        mChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                final int messagesCount = mChatAdapter.getItemCount();
                final int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (messagesCount - 1)) &&
                                lastVisiblePosition == (positionStart - 1)) {
                    mRecyclerView.scrollToPosition(positionStart);
                } else if (lastVisiblePosition < (messagesCount)) {
                    newMessagesCount[0]++;
                    mScrollDownBtn.show();
                    final String unreadMessagesCount = newMessagesCount[0] + " Unread Messages";
                    mScrollDownBtn.setText(unreadMessagesCount);
                    mScrollDownBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mRecyclerView.smoothScrollToPosition(positionStart);
                            mScrollDownBtn.hide();
                            newMessagesCount[0] = 0;
                        }
                    });
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && mScrollDownBtn.isShown()) {
                    mScrollDownBtn.hide();
                    newMessagesCount[0] = 0;
                }
            }
        });

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChatBoxEt.getText().length() > 0) {
                    final String messageContent = mChatBoxEt.getText().toString();
                    mViewModel.uploadMessageToCloud(messageContent);

                    mChatBoxEt.setText("");
                }
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom > bottom) {
                    mRecyclerView.smoothScrollToPosition(mChatAdapter.getItemCount());
                    mLinearLayoutManager.setStackFromEnd(true);
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                }

                mLinearLayoutManager.setStackFromEnd(false);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
            }
        });

        mChatBoxEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final Animation animShow = new ScaleAnimation(0f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animShow.setDuration(200);
                animShow.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mSendBtn.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                final Animation animBegone = new ScaleAnimation(1f, 0f, 1f, 0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animBegone.setDuration(200);
                animBegone.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mSendBtn.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                if (s.length() > 0 && !mIsShown) {
                    mSendBtn.startAnimation(animShow);
                    mIsShown = true;
                } else if (s.length() < 1) {
                    mSendBtn.startAnimation(animBegone);
                    mIsShown = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mRecyclerView.setAdapter(mChatAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mChatAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mViewModel != null) {
            mViewModel.updateLastChatMessage();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        mChatAdapter.stopListening();
    }
}
