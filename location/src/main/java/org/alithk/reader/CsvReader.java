package org.alithk.reader;



import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Setter
public class CsvReader {
    public CsvReader(String path) {
        this.path = path;
    }

    public CsvReader() {
    }

    private String path;
    public List<String> getAll(){
        List<String> lines=new LinkedList<>();
        if (path==null) {
            return lines;
        }
        File csv = new File(path);
        try{
            //从字符输入流读取文本，缓冲各个字符，从而实现字符、数组和行（文本的行数通过回车符来进行判定）的读取。
            FileReader fileReader = new FileReader(csv);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineDta = "";

            while ((lineDta = bufferedReader.readLine()) != null){
                lines.add(lineDta);
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }

    @Test
    public void test(){
        CsvReader csvReader=new CsvReader();
        csvReader.setPath("files/origin.csv");
        List<String> all = csvReader.getAll();
    }
}
