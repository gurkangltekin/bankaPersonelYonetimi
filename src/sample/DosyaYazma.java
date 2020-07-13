package sample;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DosyaYazma {

    private String dosyayaYaz;

    public boolean dosyaVarmi(String dosyaAdi){
        String path = Paths.get("").toAbsolutePath().toString();
        File file = new File(path + "\\src\\dosyalar\\" + dosyaAdi + ".txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void dosyaGuncelle(String str, List<String> lines) throws IOException {
        String path = Paths.get("").toAbsolutePath().toString();
        int size = lines.size();
        for ( int i = 0 ; i < size ; i++ ) {
            if ( i == 0 ){
                File file = new File(path + "\\src\\dosyalar\\" + str + ".txt");
                if(!file.exists()){
                    file.createNewFile();
                }
                if(lines.get(i) == null){
                    FileWriter fw = new FileWriter(file, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.newLine();
                    bw.close();
                }else{
                    FileWriter fw = new FileWriter(file, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(lines.get(i));
                    bw.newLine();
                    bw.close();
                }
            }else{
                File file = new File(path + "\\src\\dosyalar\\" + str + ".txt");
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(lines.get(i));
                bw.newLine();
                bw.close();
            }
        }
    }

    public void dosyayaYaz(String dosyaAdi) throws Exception{
        String path = Paths.get("").toAbsolutePath().toString();
        File file = new File(path + "\\src\\dosyalar\\" + dosyaAdi + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(this.getDosyayaYaz());
        bw.newLine();
        bw.close();
    }

    public List<String> dosyadanOku(String dosyaAdi) throws Exception {
        String path = Paths.get("").toAbsolutePath().toString();
        File file = new File(path + "\\src\\dosyalar\\" + dosyaAdi + ".txt");
        FileReader fileReader = new FileReader(file);
        String line;

        List<String> lines = new ArrayList<>();

        BufferedReader br = new BufferedReader(fileReader);
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        br.close();

        return lines;
    }

    public String getDosyayaYaz() {
        return dosyayaYaz;
    }

    public void setDosyayaYaz(String dosyayaYaz) {
        this.dosyayaYaz = dosyayaYaz;
    }
}
