package cn.edu.cug.cs.gtl.mps;

public class Main {
    public static void main(String[] args) {
        String parameterFile;

//        if (argc>1) {
//            parameterFile = argv[1];
//        } else {
        parameterFile = "C:\\Users\\Administrator\\Desktop\\SOFTX-D-16-00042-master\\Source_code\\mpslib-1.1\\mps_snesim.txt";
//        }
        float _v = Float.NaN;
        SNESIMTree snesimTree = new SNESIMTree(parameterFile);
        snesimTree.startSimulation();

    }
}
