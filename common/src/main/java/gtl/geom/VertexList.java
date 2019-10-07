package gtl.geom;

/**
 * Created by hadoop on 17-3-20.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A list of {@link Vertex}s, which may
 * be set to prevent repeated coordinates from occurring in the list.
 */
class VertexList extends ArrayList<Vertex> {
    private static final long serialVersionUID = 1L;

    //With contributions from Markus Schaber [schabios@logi-track.com]
    //[Jon Aquino 2004-03-25]
    private final static Vertex[] coordArrayType = new Vertex[0];

    /**
     * Constructs a new list without any coordinates
     */
    public VertexList() {
        super();
    }

    /**
     * Constructs a new list from an array of Coordinates, allowing repeated points.
     * (I.e. this constructor produces a {@link VertexList} with exactly the same set of points
     * as the input array.)
     *
     * @param coord the initial coordinates
     */
    public VertexList(Vertex[] coord) {
        ensureCapacity(coord.length);
        add(coord, true);
    }

    /**
     * Constructs a new list from an array of Coordinates,
     * allowing caller to specify if repeated points are to be removed.
     *
     * @param coord         the array of coordinates to load into the list
     * @param allowRepeated if <code>false</code>, repeated points are removed
     */
    public VertexList(Vertex[] coord, boolean allowRepeated) {
        ensureCapacity(coord.length);
        add(coord, allowRepeated);
    }

    public Vertex getCoordinate(int i) {
        return (Vertex) get(i);
    }


    /**
     * Adds a section of an array of coordinates to the list.
     *
     * @param coord         The coordinates
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     * @param start         the index to start from
     * @param end           the index to add up to but not including
     * @return true (as by general collection contract)
     */
    public boolean add(Vertex[] coord, boolean allowRepeated, int start, int end) {
        int inc = 1;
        if (start > end) inc = -1;

        for (int i = start; i != end; i += inc) {
            add(coord[i], allowRepeated);
        }
        return true;
    }

    /**
     * Adds an array of coordinates to the list.
     *
     * @param coord         The coordinates
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     * @param direction     if false, the array is added in reverse order
     * @return true (as by general collection contract)
     */
    public boolean add(Vertex[] coord, boolean allowRepeated, boolean direction) {
        if (direction) {
            for (int i = 0; i < coord.length; i++) {
                add(coord[i], allowRepeated);
            }
        } else {
            for (int i = coord.length - 1; i >= 0; i--) {
                add(coord[i], allowRepeated);
            }
        }
        return true;
    }


    /**
     * Adds an array of coordinates to the list.
     *
     * @param coord         The coordinates
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     * @return true (as by general collection contract)
     */
    public boolean add(Vertex[] coord, boolean allowRepeated) {
        add(coord, allowRepeated, true);
        return true;
    }

    /**
     * Adds a coordinate to the list.
     *
     * @param obj           The coordinate to add
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     * @return true (as by general collection contract)
     */
    public boolean add(Object obj, boolean allowRepeated) {
        add((Vertex) obj, allowRepeated);
        return true;
    }

    /**
     * Adds a coordinate to the end of the list.
     *
     * @param coord         The coordinates
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     */
    public void add(Vertex coord, boolean allowRepeated) {
        // don't add duplicate coordinates
        if (!allowRepeated) {
            if (size() >= 1) {
                Vertex last = (Vertex) get(size() - 1);
                if (last.equals2D(coord)) return;
            }
        }
        super.add(coord);
    }

    /**
     * Inserts the specified coordinate at the specified position in this list.
     *
     * @param i             the position at which to insert
     * @param coord         the coordinate to insert
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     */
    public void add(int i, Vertex coord, boolean allowRepeated) {
        // don't add duplicate coordinates
        if (!allowRepeated) {
            int size = size();
            if (size > 0) {
                if (i > 0) {
                    Vertex prev = (Vertex) get(i - 1);
                    if (prev.equals2D(coord)) return;
                }
                if (i < size) {
                    Vertex next = (Vertex) get(i);
                    if (next.equals2D(coord)) return;
                }
            }
        }
        super.add(i, coord);
    }

    /**
     * Add an array of coordinates
     *
     * @param coll          The coordinates
     * @param allowRepeated if set to false, repeated coordinates are collapsed
     * @return true (as by general collection contract)
     */
    public boolean addAll(Collection<Vertex> coll, boolean allowRepeated) {
        boolean isChanged = false;
        for (Iterator i = coll.iterator(); i.hasNext(); ) {
            add((Vertex) i.next(), allowRepeated);
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Ensure this coordList is a ring, by adding the start point if necessary
     */
    public void closeRing() {
        if (size() > 0)
            add(new VertexImpl((VertexImpl) super.get(0)), false);
    }

    /**
     * Returns the Coordinates in this collection.
     *
     * @return the coordinates
     */
    public Vertex[] toCoordinateArray() {
        return (Vertex[]) toArray(coordArrayType);
    }

    /**
     * Returns a deep copy of this <tt>VertexList</tt> instance.
     *
     * @return a clone of this <tt>VertexList</tt> instance
     */
    public Object clone() {
        VertexList clone = (VertexList) super.clone();
        for (int i = 0; i < this.size(); i++) {
            clone.add(i, (Vertex) ((VertexImpl) super.get(i)).clone());
        }
        return clone;
    }
}
