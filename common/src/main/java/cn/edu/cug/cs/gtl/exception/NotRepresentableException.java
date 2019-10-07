package cn.edu.cug.cs.gtl.exception;

/**
 * Created by hadoop on 17-3-20.
 */

/**
 * Indicates that a {@link HCoordinate} has been computed which is
 * not representable on the Cartesian plane.
 *
 * @see HCoordinate
 */
public class NotRepresentableException extends Exception {

    public NotRepresentableException() {
        super("Projective point not representable on the Cartesian plane.");
    }

}

