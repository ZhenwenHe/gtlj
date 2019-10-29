package cn.edu.cug.gtl.decompose;

class Vertex extends GeoObject {
    private double[] data;
    private int numDims;

    Vertex(int numDims) {
        this.numDims = numDims;
        this.data = new double[numDims];
    }

    Vertex(int numDims, double[] data) {
        this.numDims = numDims;
        this.data = data;
    }

    double[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        Vertex a = (Vertex) obj;
        if (numDims != a.numDims) return false;
        double[] b = a.data;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != b[i]) return false;
        }
        return true;
    }
}
