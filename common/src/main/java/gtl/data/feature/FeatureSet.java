package gtl.data.feature;


import gtl.feature.Feature;
import gtl.feature.FeatureType;
import gtl.io.DataSet;
import java.io.IOException;

public interface FeatureSet extends DataSet<FeatureType, Feature> {
    FeatureWriter getFeatureWriter() throws IOException;
    FeatureReader getFeatureReader() throws IOException;
    FeatureType getFeatureType() throws IOException;
}
