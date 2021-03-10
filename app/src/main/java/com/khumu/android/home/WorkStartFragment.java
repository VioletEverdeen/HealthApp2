//package com.khumu.android.home;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import androidx.fragment.app.Fragment;
//
//import com.khumu.android.MainActivity;
//import com.khumu.android.R;
//
//public class WorkStartFragment  extends Fragment{
//    public static WorkStartFragment newInstance() {
//        return new WorkStartFragment();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
//        View view = inflater.inflate(R.layout.fragment_start, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.
//        Button arm = view.findViewById(R.id.arm); // click시 Fragment를 전환할 event를 발생시킬 버튼을 정의합니다.
//
//        arm.setOnClickListener(view1 -> {
//
//        // getActivity()로 MainActivity의 replaceFragment를 불러옵니다.
//            ((WorkStartActivity)getActivity()).replaceFragment(WorkArmFragment.newInstance());    // 새로 불러올 Fragment의 Instance를 Main으로 전달
//        });
//
//        return view;
//    }
//}


//package com.khumu.android.home;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.khumu.android.KhumuApplication;
//import com.khumu.android.R;
//import com.khumu.android.data.Article;
//import com.khumu.android.notifications.NotificationActivity;
//import com.khumu.android.repository.BoardRepository;
//import com.khumu.android.retrofitInterface.NotificationService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//public class WorkStartFragment extends Fragment {
//    @Inject
//    public BoardRepository br; //게시판가져오기
//    @Inject
//    public NotificationService ns;
//    private RecentArticleAdapter recentArticleAdapter;
//    private ListView recentArticlesListView;
//    private HomeViewModel homeViewModel;
//    private Toolbar toolbar;
//    private TextView notificationCountTV;
//    private Button arm;
//    private Button leg;
//    private Button back;
//    private Button stomach;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        // Layout inflate 이전
//        // savedInstanceState을 이용해 다룰 데이터가 있으면 다룸.
//
//
//        KhumuApplication.container.inject(this);
//        super.onCreate(savedInstanceState);
//        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return (T) new HomeViewModel(br, ns);
//            }
//        }).get(HomeViewModel.class);
//
//        recentArticleAdapter = new RecentArticleAdapter(
//                getContext(),
//                R.layout.layout_home_recent_article_item,
//                new ArrayList<Article>());
//    }
//
//
//
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        // 나의 부모인 컨테이너에서 내가 그리고자 하는 녀석을 얻어옴. 사실상 루트로 사용할 애를 객체와.
//        // inflate란 xml => java 객체
//        View root = inflater.inflate(R.layout.fragment_start, container, false);
//
//        return root;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        toolbar = view.findViewById(R.id.toolbar);
//        notificationCountTV = view.findViewById(R.id.notification_count_tv);
//        recentArticlesListView = view.findViewById(R.id.recent_articles_list);
//        recentArticlesListView.setAdapter(recentArticleAdapter);
//        arm=view.findViewById(R.id.arm);
//        leg=view.findViewById(R.id.leg);
//        back=view.findViewById(R.id.back);
//        stomach=view.findViewById(R.id.stomach);
//
//
//
//
//        setEventListeners();
//    }
//
//    public void setEventListeners() {
//        arm.setOnClickListener(l->{
//            Intent intent = new Intent(WorkStartFragment.this.getActivity(), WorkArmActivity.class);
//            startActivity(intent);
//        });
//
//        leg.setOnClickListener(l->{
//            Intent intent = new Intent(WorkStartFragment.this.getActivity(), WorkLegActivity.class);
//            startActivity(intent);
//        });
//        back.setOnClickListener(l->{
//            Intent intent = new Intent(WorkStartFragment.this.getActivity(), WorkBackActivity.class);
//            startActivity(intent);
//        });
//        stomach.setOnClickListener(l->{
//            Intent intent = new Intent(WorkStartFragment.this.getActivity(), WorkStomachActivity.class);
//            startActivity(intent);
//        });
//    }
//
//
//}