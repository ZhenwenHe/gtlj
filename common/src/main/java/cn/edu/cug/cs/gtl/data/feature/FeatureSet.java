package cn.edu.cug.cs.gtl.data.feature;


import cn.edu.cug.cs.gtl.feature.Feature;
import cn.edu.cug.cs.gtl.feature.FeatureType;
import cn.edu.cug.cs.gtl.io.DataSet;
import cn.edu.cug.cs.gtl.feature.Feature;
import cn.edu.cug.cs.gtl.feature.FeatureType;
import cn.edu.cug.cs.gtl.io.DataSet;

import java.io.IOException;

public interface FeatureSet extends DataSet<FeatureType, Feature> {
    FeatureWriter getFeatureWriter() throws IOException;

    FeatureReader getFeatureReader() throws IOException;

    FeatureType getFeatureType() throws IOException;
}
