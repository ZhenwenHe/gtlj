package cn.edu.cug.cs.gtl.jts.dissolve;

import cn.edu.cug.cs.gtl.jts.geom.Coordinate;
import cn.edu.cug.cs.gtl.jts.edgegraph.EdgeGraph;
import cn.edu.cug.cs.gtl.jts.edgegraph.HalfEdge;
import cn.edu.cug.cs.gtl.jts.geom.Coordinate;


/**
 * A graph containing {@link DissolveHalfEdge}s.
 *
 * @author Martin Davis
 */
class DissolveEdgeGraph extends EdgeGraph {
    protected HalfEdge createEdge(Coordinate p0) {
        return new DissolveHalfEdge(p0);
    }


}
