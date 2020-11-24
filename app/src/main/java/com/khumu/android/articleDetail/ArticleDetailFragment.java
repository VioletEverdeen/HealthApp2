package com.khumu.android.articleDetail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khumu.android.KhumuApplication;
import com.khumu.android.R;
import com.khumu.android.data.Comment;
import com.khumu.android.repository.CommentRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class ArticleDetailFragment extends Fragment {
    @Inject
    public CommentRepository commentRepository;
    private CommentViewModel commentViewModel;
    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView articleDetailTitleTV;
    private TextView articleDetailContentTV;
    private TextView articleCommentCountTV;
    private TextView articleAuthorUsernameTV;
    private TextView articleLikeCountTV;
    private ImageView articleLikeIcon;
    private TextView articleDetailCreatedAtTV;

    private EditText writeCommentContentET;
    private Button  writeCommentContentBTN;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        int articleID = intent.getIntExtra("articleID", 0);
        System.out.println(articleID);
        // Layout inflate 이전
        // savedInstanceState을 이용해 다룰 데이터가 있으면 다룸.
        super.onCreate(savedInstanceState);
        KhumuApplication.container.inject(this);
        commentViewModel = new ViewModelProvider(this,
                new CommentViewFactory(commentRepository, Integer.toString(articleID))
        ).get(CommentViewModel.class);
//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 나의 부모인 컨테이너에서 내가 그리고자 하는 녀석을 얻어옴. 사실상 루트로 사용할 애를 객체와.
        // inflate란 xml => java 객체
        View root = inflater.inflate(R.layout.fragment_article_detail, container, false);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // onCreateView에서 return된 view를 가지고 있다
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = view.findViewById(R.id.recycler_view_comment_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentArrayList);
        recyclerView.setAdapter(commentAdapter);


        articleDetailTitleTV = view.findViewById(R.id.article_detail_title_tv);
        articleDetailContentTV = view.findViewById(R.id.article_detail_content_tv);
        articleCommentCountTV = view.findViewById(R.id.article_detail_comment_count_tv);
        articleAuthorUsernameTV = view.findViewById(R.id.article_detail_author_username_tv);
        articleDetailCreatedAtTV= view.findViewById(R.id.article_detail_created_at_tv);
        articleLikeCountTV = view.findViewById(R.id.article_detail_like_article_count_tv);
        articleLikeIcon = view.findViewById(R.id.article_detail_like_icon);
        writeCommentContentET = view.findViewById(R.id.comment_write_content);
        writeCommentContentBTN = view.findViewById(R.id.comment_write_btn);

        commentViewModel.getLiveDataComments().observe(getViewLifecycleOwner(), new Observer<ArrayList<Comment>>() {
            @Override
            public void onChanged(ArrayList<Comment> changedSet) {
                int originalLength = commentArrayList.size();
                int newLength = changedSet.size();
                for (int i = originalLength; i<newLength; i++) {
                    commentArrayList.add(changedSet.get(i));
                }
                commentAdapter.notifyItemRangeInserted(originalLength, newLength-originalLength);
                if(newLength > 0) recyclerView.smoothScrollToPosition(newLength-1);
            }
        });

        initWithIntentExtra();
    }
    /*
    public class FetchCommentsAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                commentViewModel.CreateComment(new Comment(
                        null,
                        null,
                        0,
                        0
                ));
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }
     */
    private void initWithIntentExtra(){
        Intent intent = getActivity().getIntent();
        String titleString = intent.getStringExtra("articleTitle");
        String contentString = intent.getStringExtra("articleContent");
        int commentCountString = intent.getIntExtra("articleCommentCount", -1);
        String authorUsernameString = intent.getStringExtra("articleAuthorUsername");
        String articleCreatedAtString = intent.getStringExtra("articleCreatedAt");
        String articleLikeCountString = intent.getStringExtra("articleLikeCount");
        Boolean isLikedBooloean = intent.getBooleanExtra("articleIsLiked", false);

        articleDetailTitleTV.setText(titleString);
        articleDetailContentTV.setText(contentString);
        articleCommentCountTV.setText("댓글 " + commentCountString);
        articleAuthorUsernameTV.setText(authorUsernameString);
        articleDetailCreatedAtTV.setText(articleCreatedAtString);
        articleLikeCountTV.setText(articleLikeCountString);
        //articleLikeIcon.setImageResource();
    }
}
