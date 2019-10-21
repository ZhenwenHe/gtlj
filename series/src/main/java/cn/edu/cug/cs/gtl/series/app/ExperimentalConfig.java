package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.config.Config;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExperimentalConfig{
    private String dataDirectory;
    private List<Pair<String,String>> dataFiles;
    private Pair<Integer,Integer> paaSizeRange;
    private Pair<Integer,Integer> alphabetRange;
    private String resultFileName;


    /**
     * Each element in the list is a pair.
     * The first item of a pair is a train file name,
     * and the second item of a pair is a test file name.
     * @return
     */
    public List<Pair<String, String>> getDataFiles() {
        return dataFiles;
    }

    public ExperimentalConfig(String experimentalDataDir){

        this.dataDirectory=experimentalDataDir;
        resultFileName = "example";
        dataFiles = new ArrayList<>();
        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {
                String trainFile = this.dataDirectory+File.separator+f.getName()+File.separator+f.getName()+"_TRAIN.tsv";
                String testFile = this.dataDirectory+File.separator+f.getName()+File.separator+f.getName()+"_TEST.tsv";
                dataFiles.add(new Pair<>(trainFile,testFile));
            }
        }

        this.paaSizeRange=new Pair<>(8,24);
        this.alphabetRange=new Pair<>(3,16);
    }


    public ExperimentalConfig(){
        this.dataDirectory= Config.getDataDirectory()+ File.separator+"UCRArchive_2018";
        resultFileName = "example";
        dataFiles = new ArrayList<>();
        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {
                String trainFile = this.dataDirectory+File.separator+f.getName()+File.separator+f.getName()+"_TRAIN.tsv";
                String testFile = this.dataDirectory+File.separator+f.getName()+File.separator+f.getName()+"_TEST.tsv";
                dataFiles.add(new Pair<>(trainFile,testFile));
            }
        }

        this.paaSizeRange=new Pair<>(8,24);
        this.alphabetRange=new Pair<>(3,16);
    }

    public String getResultFile() {
        return dataDirectory+File.separator+"output"+File.separator+resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public Pair<Integer, Integer> getPaaSizeRange() {
        return paaSizeRange;
    }

    public void setPaaSizeRange(Pair<Integer, Integer> paaSizeRange) {
        this.paaSizeRange = paaSizeRange;
    }

    public Pair<Integer, Integer> getAlphabetRange() {
        return alphabetRange;
    }

    public void setAlphabetRange(Pair<Integer, Integer> alphabetRange) {
        this.alphabetRange = alphabetRange;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void writeResults(List<Pair<String,double[]> > results) {
        File file = new File(getResultFile());
        try{
            file.createNewFile();
            //创建新工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //新建工作表
            HSSFSheet sheet = workbook.createSheet("sheet1");
            int r=0;
            int c=0;
            for(Pair<String,double[]> p: results){
                //创建行,行号作为参数传递给createRow()方法,第一行从0开始计算
                HSSFRow row = sheet.createRow(r);
                c=0;
                for(double d: p.second()){
                    //创建单元格,row已经确定了行号,列号作为参数传递给createCell(),第一列从0开始计算
                    HSSFCell cell = row.createCell(c);
                    //设置单元格的值,即C1的值(第一行,第三列)
                    cell.setCellValue(String.valueOf(d));
                    c++;
                }
                r++;
            }
            //输出到磁盘中
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
