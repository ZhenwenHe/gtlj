package gtl.jts.dissolve;

import gtl.jts.edgegraph.EdgeGraph;
import gtl.jts.edgegraph.HalfEdge;
import gtl.jts.geom.Coordinate;


/**
 * A graph containing {@link DissolveHalfEdge}s.
 * 
 * @author Martin Davis
 *
 */
class DissolveEdgeGraph extends EdgeGraph
{
  protected HalfEdge createEdge(Coordinate p0)
  {
    return new DissolveHalfEdge(p0);
  }
  

}
