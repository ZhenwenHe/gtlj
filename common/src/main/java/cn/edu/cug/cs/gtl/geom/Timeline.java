package cn.edu.cug.cs.gtl.geom;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.common.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface Timeline extends Serializable {
    Identifier getIdentifier();

    List<Interval> getIntervals();

    List<String> getLabels();

    List<ComplexInterval> getLabeledIntervals();

    ComplexInterval[] getLabelIntervalArray();

    void addLabelInterval(ComplexInterval lb);

    //转化成以逗号分隔的字符串
    String toString();

    //解析以逗号分隔的字符串，并填充Timeline,返回自身
    Timeline parse(String s);

    static Timeline create(Identifier identifier, List<ComplexInterval> labeledIntervals) {
        return new TimelineImpl(identifier, labeledIntervals);
    }

    static Timeline create() {
        return new TimelineImpl();
    }

    static Timeline create(Identifier identifier, List<Interval> li, List<String> ls) {
        return new TimelineImpl(identifier, li, ls);
    }
}
