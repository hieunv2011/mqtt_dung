package com.example.prj2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;  // Thêm import để sử dụng JSONObject

public class MainActivity extends AppCompatActivity {

    private static final String BROKER_URL = "tcp://103.1.238.175:1883";
    private static final String CLIENT_ID = "android";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "testadmin";
    private MqttHandler mqttHandler;
    private TextView textViewReceivedMessage1;
    private TextView textViewReceivedMessage2;
    private TextView textViewReceivedMessage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewReceivedMessage1 = findViewById(R.id.textViewReceivedMessage1);  // Lấy TextView để hiển thị dữ liệu
        textViewReceivedMessage2 = findViewById(R.id.textViewReceivedMessage2);
        textViewReceivedMessage3 = findViewById(R.id.textViewReceivedMessage3);
        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL, CLIENT_ID, USERNAME, PASSWORD);
        mqttHandler.setMqttMessageListener(this::updateReceivedMessage); // Đăng ký callback để nhận thông điệp

        // Đăng ký nhận thông điệp từ topic "686868"
        mqttHandler.subscribe("sensor");

        // Tìm các nút và gán sự kiện bấm nút
        Button buttonPublish = findViewById(R.id.buttonPublish);
        buttonPublish.setOnClickListener(view -> publishMessage("button", "Android"));

        Button buttonG1 = findViewById(R.id.buttonG1);
        buttonG1.setOnClickListener(view -> publishMessage("button", "a"));

        Button buttonG2 = findViewById(R.id.buttonG2);
        buttonG2.setOnClickListener(view -> publishMessage("button", "b"));

        Button buttonG3 = findViewById(R.id.buttonG3);
        buttonG3.setOnClickListener(view -> publishMessage("button", "c"));
    }

    @Override
    protected void onDestroy() {
        mqttHandler.disconnect();
        super.onDestroy();
    }

    // Hàm xuất bản thông điệp lên một chủ đề
    private void publishMessage(String topic, String message) {
        mqttHandler.publish(topic, message);  // Gửi thông điệp lên topic tương ứng
    }

    // Hàm này sẽ được gọi khi có thông điệp JSON mới từ topic "686868"
    private void updateReceivedMessage(JSONObject jsonObject) {
        try {
            // Giả sử JSON có một trường "data" mà bạn muốn hiển thị
            String data1 = jsonObject.getString("G1");
            String data2 = jsonObject.getString("G2");
            String data3 = jsonObject.getString("G3");
            textViewReceivedMessage1.setText(data1);
            textViewReceivedMessage2.setText(data2);
            textViewReceivedMessage3.setText(data3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
