package cn.edu.cug.gtl.decompose;

class Envelope extends GeoObject {
    //多维，依次存储每个维度的最小值和最大值
    private double[] minData;   //每个维度的最小值
    private double[] maxData;   //每个维度的最大值
    private int numDims;

    Envelope(int numDims, double[] minData, double[] maxData) {
        this.numDims = numDims;
        this.minData = minData;
        this.maxData = maxData;
    }

    Envelope(int numDims) {
        this.numDims = numDims;
        minData = new double[numDims];
        maxData = new double[numDims];
    }

    boolean encloses(final GeoObject r) {
        if (r instanceof Vertex) {
            double[] data = ((Vertex) r).getData();
            for (int i = 0; i < numDims; i++) {
                if (data[i] < minData[i] || data[i] > maxData[i]) return false;
            }
        } else {
            double[] rMin = ((Envelope) r).getMinData();
            double[] rMax = ((Envelope) r).getMaxData();
            for (int i = 0; i < numDims; i++) {
                if (rMin[i] < minData[i] || rMax[i] > maxData[i]) return false;
            }
        }
        return true;
    }

    double[] getMinData() {
        return minData;
    }

    double[] getMaxData() {
        return maxData;
    }
}
