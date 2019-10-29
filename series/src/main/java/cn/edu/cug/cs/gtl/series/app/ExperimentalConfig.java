package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.config.Config;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperimentalConfig {
    private String dataDirectory;
    private List<Pair<String, String>> dataFiles;
    private Pair<Integer, Integer> paaSizeRange;
    private Pair<Integer, Integer> alphabetRange;
    private String resultFileName;
    private String runtimeDatabase;

    public String getRuntimeDatabase() {
        return runtimeDatabase;
    }

    public void setRuntimeDatabase(String runtimeDatabase) {
        this.runtimeDatabase = runtimeDatabase;
    }

    /**
     * Each element in the list is a pair.
     * The first item of a pair is a train file name,
     * and the second item of a pair is a test file name.
     *
     * @return
     */
    public List<Pair<String, String>> getDataFiles() {
        return dataFiles;
    }

    public ExperimentalConfig(String runtimeDatabase) {

        Connection connection = null;
        List<String> valid_dataset_names = new ArrayList<>();

        try {
            if (runtimeDatabase == null)
                connection = DriverManager.getConnection("jdbc:sqlite:series.db");
            else
                connection = DriverManager.getConnection("jdbc:sqlite:" + runtimeDatabase);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(
                    "select data_dir, min_paasize, max_paasize, min_alphabet,max_alphabet,result_file from parameters");
            while (rs.next()) {
                this.dataDirectory = rs.getString("data_dir");
                this.resultFileName = rs.getString("result_file");
                if (this.resultFileName == null) {
                    this.resultFileName = "example.xls";
                }
                this.paaSizeRange = new Pair<>(
                        rs.getInt("min_paasize"),
                        rs.getInt("max_paasize") + 1);
                this.alphabetRange = new Pair<>(
                        rs.getInt("min_alphabet"),
                        rs.getInt("max_alphabet") + 1);
            }
            rs.close();

            rs = statement.executeQuery(
                    "select name from valid_dataset_names");
            while (rs.next()) {
                valid_dataset_names.add(rs.getString("name"));
            }
            rs.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        this.dataFiles = new ArrayList<>();
        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {
                boolean b = valid_dataset_names.stream().anyMatch(s -> s.equals(f.getName()));
                if (b) {//过滤不属于valid_dataset_names中的数据集
                    String trainFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TRAIN.tsv";
                    String testFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TEST.tsv";
                    dataFiles.add(new Pair<>(trainFile, testFile));
                }
            }
        }
    }


    public ExperimentalConfig() {
        this.dataDirectory = Config.getDataDirectory() + File.separator + "UCRArchive_2018";
        resultFileName = "example.xls";
        dataFiles = new ArrayList<>();
        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {
                String trainFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TRAIN.tsv";
                String testFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TEST.tsv";
                dataFiles.add(new Pair<>(trainFile, testFile));
            }
        }

        this.paaSizeRange = new Pair<>(5, 21);
        this.alphabetRange = new Pair<>(3, 17);
    }

    public String getResultFile() {
        return Config.getTestOutputDirectory() + File.separator + resultFileName;
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

    public void writeResults(List<Pair<String, double[]>> results) {
        File file = new File(getResultFile());
        try {
            file.createNewFile();
            //创建新工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //新建工作表
            HSSFSheet sheet = workbook.createSheet("sheet1");
            int r = 0;
            int c = 0;
            for (Pair<String, double[]> p : results) {
                //创建行,行号作为参数传递给createRow()方法,第一行从0开始计算
                HSSFRow row = sheet.createRow(r);
                c = 0;
                HSSFCell cell = row.createCell(c);
                //设置单元格的值,即C1的值(第一行,第三列)
                cell.setCellValue(p.first());
                for (double d : p.second()) {
                    //创建单元格,row已经确定了行号,列号作为参数传递给createCell(),第一列从0开始计算
                    ++c;
                    cell = row.createCell(c);
                    //设置单元格的值,即C1的值(第一行,第三列)
                    cell.setCellValue(String.valueOf(d));
                }
                r++;
            }
            //输出到磁盘中
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
