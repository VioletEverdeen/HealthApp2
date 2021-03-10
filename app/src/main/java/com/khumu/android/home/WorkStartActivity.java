//package com.khumu.android.home;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.khumu.android.R;
//
//public class WorkStartActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_start);
//
//// 화면 전환 프래그먼트 선언 및 초기 화면 설정
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.Linear1, WorkStartFragment.newInstance()).commit();
//    }
//
//    public void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.Linear1, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
//    }
//
//}
