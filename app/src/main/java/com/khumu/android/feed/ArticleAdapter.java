package com.khumu.android.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khumu.android.KhumuApplication;
import com.khumu.android.R;
import com.khumu.android.articleDetail.ArticleDetailActivity;
import com.khumu.android.data.Article;
import com.khumu.android.data.BookmarkArticle;
import com.khumu.android.data.LikeArticle;
import com.khumu.android.repository.BookmarkArticleRepository;
import com.khumu.android.repository.LikeArticleRepository;
import com.khumu.android.usecase.ArticleUseCase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{
    private final static String TAG = "ArticleAdapter";
    public List<Article> articleList;
    @Inject
    public LikeArticleRepository likeArticleRepository;
    @Inject
    public BookmarkArticleRepository bookmarkArticleRepository;
    @Inject
    public ArticleUseCase articleUseCase;
    // Adapter는 바깥 UI 상황을 최대한 모르고싶지만, Toast를 위해 context를 주입함.
    private Context context;

    public ArticleAdapter(List<Article> articleList, Context context) {
        KhumuApplication.container.inject(this);
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // attach to root를 하지 않을 것이기 때문에 parent인 recyclerview를 전달하지 않아도 된다(null).
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article_item, parent, false);
        ArticleViewHolder holder = new ArticleViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.articleBoardNameTV.setText(article.getBoardDisplayName());
        holder.articleTitleTV.setText(article.getTitle());
        holder.articleContentTV.setText(article.getContent());
        // usecase를 활용해 사용할 data를 적절히 변환한 뒤 리턴받음.
        holder.articleAuthorNicknameTV.setText(articleUseCase.getAuthorNickname(article));
        if(articleUseCase.amIAuthor(article)){
            holder.articleAuthorNicknameTV.setTextColor(context.getColor(R.color.red_300));
        } else{
            holder.articleAuthorNicknameTV.setTextColor(context.getColor(R.color.gray_500));
        }

        holder.articleLikeIcon.setImageResource(getArticleLikedImage(article));
        holder.articleLikeCountTV.setText(String.valueOf(article.getLikeArticleCount()));

        holder.articleBookmarkIcon.setImageResource(getArticleBookmarkedImage(article));
        holder.articleBookmarkCountTV.setText(String.valueOf(article.getBookmarkArticleCount()));

        holder.articleCommentCountTV.setText(String.valueOf(article.getCommentCount()));
        holder.articleCreatedAtTV.setText(String.valueOf(article.getArticleCreatedAt()));

        holder.itemView.setTag(position);
        holder.articleBodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
                // intent에서 해당 article에 대한 정보들을 저장
                intent.putExtra("article", article);
                v.getContext().startActivity(intent);
            }
        });

        holder.articleLikeWrapperLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try{
                            likeArticleRepository.toggleLikeArticle(new LikeArticle(article.getID()));
                            boolean liked = article.isLiked();
                            if(liked){
                                article.setLiked(false);
                                article.setLikeArticleCount(article.getLikeArticleCount() - 1);
                            } else{
                                article.setLiked(true);
                                article.setLikeArticleCount(article.getLikeArticleCount() + 1);
                            }
                            // Network thread 에서 작업 수행 후 MainThread에 UI 작업을 Post
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.articleLikeIcon.setImageResource(getArticleLikedImage(article));
                                    holder.articleLikeCountTV.setText(String.valueOf(article.getLikeArticleCount()));
                                }
                            });
                        } catch (LikeArticleRepository.BadRequestException e){
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        holder.articleBookmarkWrapperLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try{
                            bookmarkArticleRepository.toggleBookmarkArticle(new BookmarkArticle(article.getID()));
                            boolean bookmarked = article.isBookmarked();
                            if(bookmarked){
                                article.setBookmarked(false);
                                article.setBookmarkArticleCount(article.getBookmarkArticleCount() - 1);
                            } else{
                                article.setBookmarked(true);
                                article.setBookmarkArticleCount(article.getBookmarkArticleCount() + 1);
                            }
                            // Network thread 에서 작업 수행 후 MainThread에 UI 작업을 Post
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.articleBookmarkIcon.setImageResource(getArticleBookmarkedImage(article));
                                    holder.articleBookmarkCountTV.setText(String.valueOf(article.getBookmarkArticleCount()));
                                }
                            });
                        } catch (BookmarkArticleRepository.BadRequestException e){
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        // 그냥 테스트용
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList == null ? 0 : articleList.size();
    }

    public void remove(int position){
        try{
            articleList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private int getArticleLikedImage(Article article){
        if(article.isLiked()){
            return R.drawable.ic_filled_heart;
        }
        return R.drawable.ic_empty_heart;
    }

    private int getArticleBookmarkedImage(Article article){
        if(article.isBookmarked()){
            return R.drawable.ic_filled_bookmark;
        }
        return R.drawable.ic_empty_bookmark;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ViewGroup articleBodyLayout;
        public ViewGroup articleLikeWrapperLayout;
        public ViewGroup articleBookmarkWrapperLayout;
        public TextView articleBoardNameTV;
        public TextView articleTitleTV;
        public TextView articleContentTV;
        public TextView articleAuthorNicknameTV;
        public TextView articleCommentCountTV;
        public TextView articleLikeCountTV;
        public TextView articleBookmarkCountTV;
        public ImageView articleLikeIcon;
        public ImageView articleBookmarkIcon;
        public TextView articleCreatedAtTV;
        // 이 view는 아마도 recycler view?
        public ArticleViewHolder(View view) {
            super(view);
            this.articleBodyLayout = (ViewGroup) view.findViewById(R.id.article_item_body_layout);
            this.articleLikeWrapperLayout = (ViewGroup) view.findViewById(R.id.article_item_like_wrapper_layout);
            this.articleBookmarkWrapperLayout = (ViewGroup) view.findViewById(R.id.article_item_bookmark_wrapper_layout);
            this.articleBoardNameTV = view.findViewById(R.id.article_item_board_name_tv);
            this.articleTitleTV = view.findViewById(R.id.article_item_title_tv);
            this.articleContentTV = view.findViewById(R.id.article_item_content_tv);
            this.articleAuthorNicknameTV = view.findViewById(R.id.article_item_author_nickname_tv);
            this.articleCommentCountTV = view.findViewById(R.id.article_item_comment_count_tv);
            this.articleLikeCountTV = view.findViewById(R.id.article_item_like_article_count_tv);
            this.articleBookmarkCountTV = view.findViewById(R.id.article_item_bookmark_count_tv);
            this.articleLikeIcon = view.findViewById(R.id.article_item_like_icon);
            this.articleBookmarkIcon = view.findViewById(R.id.article_item_bookmark_icon);
            this.articleCreatedAtTV = view.findViewById(R.id.article_item_created_at_tv);
        }
    }
}
