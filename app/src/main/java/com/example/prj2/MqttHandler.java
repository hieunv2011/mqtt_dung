package com.example.prj2;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;  // Thêm import để sử dụng JSONObject

public class MqttHandler {

    private MqttClient client;
    private MqttMessageListener listener;

    // Cập nhật phương thức connect để hỗ trợ thông tin đăng nhập (username, password)
    public void connect(String brokerUrl, String clientId, String username, String password) {
        try {
            // Set up the persistence layer
            MemoryPersistence persistence = new MemoryPersistence();

            // Initialize the MQTT client
            client = new MqttClient(brokerUrl, clientId, persistence);

            // Set up the connection options
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setUserName(username);  // Cung cấp tên người dùng
            connectOptions.setPassword(password.toCharArray());  // Cung cấp mật khẩu

            // Connect to the broker
            client.connect(connectOptions);

            // Thiết lập callback để nhận tin nhắn từ broker
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // Xử lý khi kết nối bị mất
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    if ("sensor".equals(topic)) {
                        try {
                            // Chuyển đổi payload (dữ liệu nhận được) thành chuỗi JSON
                            String receivedMessage = new String(message.getPayload());

                            // Parse chuỗi JSON
                            JSONObject jsonObject = new JSONObject(receivedMessage);

                            // Gửi thông tin về MainActivity
                            if (listener != null) {
                                listener.onMessageReceived(jsonObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken token) {
                    // Không cần xử lý gì ở đây
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Hàm để đăng ký (subscribe) chủ đề
    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Hàm để hủy kết nối
    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Hàm để xuất bản tin nhắn
    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1); // Thiết lập chất lượng dịch vụ (QoS)
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Cung cấp một interface để MainActivity nhận dữ liệu
    public void setMqttMessageListener(MqttMessageListener listener) {
        this.listener = listener;
    }

    // Interface dùng để gửi thông điệp đến MainActivity
    public interface MqttMessageListener {
        void onMessageReceived(JSONObject message);  // Thay đổi loại dữ liệu từ String thành JSONObject
    }
}
