package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.io.Filter;
import cn.edu.cug.cs.gtl.feature.Feature;

public interface FeatureFilter extends Filter<Feature> {
    static EnvelopeContainsFeatureFilter envelopeContains(){
        return new EnvelopeContainsFeatureFilter();
    }
    static EnvelopeContainsFeatureFilter envelopeContains(Envelope envelope){
        return new EnvelopeContainsFeatureFilter(envelope);
    }
}
