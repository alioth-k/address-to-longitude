package org.alithk;

import org.alithk.configs.Command;
import org.alithk.entity.AddressCoordinate;

import org.alithk.reader.CsvReader;
import org.alithk.writer.CsvWriter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        //获取地理编码器并初始化
        AddressEncoding addressEncoding = new AddressEncoding();
        addressEncoding.setKey(Command.KEY);
        //数据来源
        CsvReader csvReader=new CsvReader("files/origin.csv");
        //存入地址
        CsvWriter csvWriter=new CsvWriter("files/newAddress.csv");

        List<String> all = csvReader.getAll();
        List<String> head = Arrays.asList((all.get(0) + ",经度,纬度").split(","));
        //删除表头,余下是纯数据
        all.subList(0, Command.CSV_HEAD_DEPTH).clear();
        //插入O(1)复杂度
        List<List<String>> data=new LinkedList<>();
        data.add(head);
        int i=2;
        for (String s : all) {
            String addressToConvert = s.split(",")[Command.ADDRESS_INDEX];
            System.out.println("query the data from "+ i +" line");
            i++;

            String json = addressEncoding.getEncodingJson(addressToConvert);
            AddressCoordinate addressCoordinate = addressEncoding.getAddressCoordinate(json, Command.GAO_DE);
            List<String> line = new LinkedList<>(Arrays.asList(s.split(",")));
            line.add(String.valueOf(addressCoordinate.getLongitude()));
            line.add(String.valueOf(addressCoordinate.getLatitude()));
            data.add(line);
        }
        System.out.println("now writing these "+(i-1)+" lines data ...");
        csvWriter.write(data,0);
        long endTime=System.currentTimeMillis();
        System.out.println("\nthe program used "+(endTime-startTime)/1000.0+"s to accomplish the mission");

    }
}
