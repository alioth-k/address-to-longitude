package org.alithk.writer;

import lombok.Setter;
import org.alithk.configs.Command;
import org.alithk.reader.CsvReader;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.List;


@Setter
public class CsvWriter {
    public CsvWriter() {
    }

    public CsvWriter(String path) {
        this.path = path;
    }

    private String path;
    public boolean write(List<List<String>> data,int skipLines){
        if (skipLines > 0) {
            data.subList(0, skipLines).clear();
        }
        File writeFile = new File(path);
        try{
            //通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
            int len= data.size(),i=0;
            while(i<len){
                for(int time=0;time<Command.BUFFER_LOOP_TIMES&&i<len;time++){
                    bufferedWriter.write(String.join(",", data.get(i)));
                    bufferedWriter.newLine();
                    i++;
                }
                bufferedWriter.flush();
            }
            bufferedWriter.flush();
            //关闭缓冲区，缓冲区没有调用系统底层资源，真正调用底层资源的是FileWriter对象，缓冲区仅仅是一个提高效率的作用
            //因此，此处的close()方法关闭的是被缓存的流对象
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public boolean writeLines(List<String> data,int skipLines){
        if (skipLines > 0) {
            data.subList(0, skipLines).clear();
        }
        File writeFile = new File(path);
        try{
            //通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
            int len= data.size(),i=0;
            while(i<len){
                for(int time = 0; time< Command.BUFFER_LOOP_TIMES && i<len ; time++){
                    bufferedWriter.write(data.get(i));
                    bufferedWriter.newLine();
                    i++;
                }
                bufferedWriter.flush();
            }
            bufferedWriter.flush();
            //关闭缓冲区，缓冲区没有调用系统底层资源，真正调用底层资源的是FileWriter对象，缓冲区仅仅是一个提高效率的作用
            //因此，此处的close()方法关闭的是被缓存的流对象
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Test
    public void test(){
        CsvWriter csvWriter=new CsvWriter();
        csvWriter.setPath("files/newAddress.csv");
        CsvReader csvReader=new CsvReader();
        csvReader.setPath("files/origin.csv");
        List<String> all = csvReader.getAll();
        csvWriter.writeLines(all,0);
    }
}
