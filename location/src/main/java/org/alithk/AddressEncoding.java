package org.alithk;
import lombok.Data;
import org.alithk.configs.Command;
import org.alithk.entity.AddressCoordinate;
import org.alithk.keys.Zhou;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

@Data
public class AddressEncoding{
    private String key;

    /**
     * 地址编码
     * @param address 目标弟子
     * @return 相关json
     */
    public String getEncodingJson(String address){
        String json = null;
        String methodUrl = "https://restapi.amap.com/v3/geocode/geo?key="+key+"&address="+address;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line;
        try {
            //在该处可添加所需参数
            URL url = new URL(methodUrl);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setRequestMethod("GET");// 默认GET请求
            connection.connect();// 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));// "\n"
                }

                json = result.toString();
//                System.out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                connection.disconnect();
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }

        }
        return json;
    }
    public AddressCoordinate getAddressCoordinate(String jsonString,int commandStyle){
        AddressCoordinate addressCoordinate = new AddressCoordinate();
        try{
            if (commandStyle== Command.GAO_DE){
                JSONObject jsonObject = new JSONObject(jsonString);
                if (Integer.parseInt((String) jsonObject.get("infocode"))!=Command.OK_GAO_DE){
                    return addressCoordinate;
                }
                JSONArray jsonArray= jsonObject.getJSONArray("geocodes");
                String[] location = ((String) jsonArray.getJSONObject(0).get("location")).split(",");
                String formattedAddress = (String) jsonArray.getJSONObject(0).get("formatted_address");
                addressCoordinate.setLongitude(Double.valueOf(location[0]));
                addressCoordinate.setLatitude(Double.valueOf(location[1]));
                addressCoordinate.setFormattedAddress(formattedAddress);
            }
        }catch (RuntimeException e){
            System.out.println(jsonString);
            throw new RuntimeException();
        }

        return addressCoordinate;
    }
    @Test
     public void test(){
        AddressEncoding addressEncoding=new AddressEncoding();
        addressEncoding.setKey(Zhou.GAO_DE);
        String json = addressEncoding.getEncodingJson("无锡市江南大学");
        addressEncoding.getAddressCoordinate(json,Command.GAO_DE);
     }


    private class JSONException {
    }
}