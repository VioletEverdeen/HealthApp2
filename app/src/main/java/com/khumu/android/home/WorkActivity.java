package com.khumu.android.home;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.khumu.android.R;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class WorkActivity extends AppCompatActivity {

    private BluetoothSPP bt;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_work);

        // get body_part
        String bodyPart = FragmentStartActivity.getBodyPart();
        Log.d("테스트", ""+bodyPart);

        // ImageView의 이미지 변경
        ImageView body_imageView = (ImageView)findViewById(R.id.body_imageView);
        body_imageView.setImageResource(getImageSource(bodyPart));

        // TextView의 글자 변경
        Button bodyText_button = (Button)findViewById(R.id.bodyText_button);
        bodyText_button.setText(getText(bodyPart));

        /*
        블루투스 설정
        https://blog.codejun.space/13
        https://chaniii.tistory.com/7 참고
         */
        bt = new BluetoothSPP(this); //Initializing
        if (!bt.isBluetoothAvailable()) { // 블루투스 사용 불가
            Toast.makeText(getApplicationContext(), "블루투스를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        // 데이터 수신
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show(); // 토스트로 데이터 띄움
                TextView textViewReceive = findViewById(R.id.textViewReceive);
                textViewReceive.setText(message);
            }
        });

        // 블루투스가 잘 연결이 되었는지 감지하는 리스너
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {//연결성공
                btnConnect.setText("종료하기");
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
                Log.d("블루투스", "Connected to " + name + "\n" + address);
            }

            public void onDeviceDisconnected() { //연결해제
                btnConnect.setText("연결하기");
                Log.d("블루투스", "연결이 해제되었습니다.");
                Toast.makeText(getApplicationContext(), "연결이 해제되었습니다.", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                btnConnect.setText("연결하기");
                Log.d("블루투스", "연결에 실패하였습니다.");
                Toast.makeText(getApplicationContext() , "연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 연결 시도: 현재 버튼의 상태에 따라 연결이 되어있으면 끊고, 반대면 연결
        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    // 앱 중단시 (액티비티 나가거나, 특정 사유로 중단시)
    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { // 앱의 상태를 보고 블루투스 사용 가능하면
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) { // 블루투스 사용 불가
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기끼리
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) { // 연결시도
            if (resultCode == Activity.RESULT_OK) // 연결됨
                bt.connect(data);

        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) { // 연결 가능
            if (resultCode == Activity.RESULT_OK) { // 연결됨
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);

            } else { // 사용불가
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // 알맞은 ImageSource 얻기
    private Integer getImageSource(String bodyPart){
        switch (bodyPart){
            case "arm" : return R.drawable.pic_arm;
            case "back" : return R.drawable.pic_high;
            case "stomach" : return R.drawable.pic_high;
            case "leg" : return R.drawable.pic_leg;
            default: return R.drawable.round_filled_light_background; // 데이터 받기 실패
        }
    }

    // 알맞은 Text 얻기
    private String getText(String bodyPart){
        switch (bodyPart){
            case "arm" : return "팔 운동을 시작합니다!";
            case "back" : return "등 운동을 시작합니다!";
            case "stomach" : return "배 운동을 시작합니다!";
            case "leg" : return "다리 운동을 시작합니다!";
            default: return "오류ㅠㅠㅠ";
        }
    }
}
