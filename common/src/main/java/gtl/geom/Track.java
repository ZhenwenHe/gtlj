package gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-27.
 */
public class Track implements gtl.io.Serializable, Comparable<Track> {
    private static final long serialVersionUID = 1L;

    Vector origin;
    Vector velocity;

    public Track() {
    }

    public Track(Vector origin, Vector velocity) {
        this.origin = (Vector) origin.clone();
        this.velocity = (Vector) velocity.clone();
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector origin) {
        this.origin = (Vector) origin.clone();
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = (Vector) velocity.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (!origin.equals(track.origin)) return false;
        return velocity.equals(track.velocity);
    }

    @Override
    public int hashCode() {
        int result = origin.hashCode();
        result = 31 * result + velocity.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "origin=" + origin +
                ", velocity=" + velocity +
                '}';
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public void copyFrom(Object i) {

    }

    @Override
    public boolean load(DataInput in) throws IOException {
        return false;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return false;
    }

    @Override
    public long getByteArraySize() {
        return 0;
    }

    @Override
    public int compareTo(Track o) {
        return 0;
    }
}
