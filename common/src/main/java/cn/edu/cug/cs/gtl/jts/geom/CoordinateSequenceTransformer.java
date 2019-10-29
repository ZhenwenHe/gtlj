package cn.edu.cug.cs.gtl.jts.geom;

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2004-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */


// JTS dependencies

import cn.edu.cug.cs.gtl.jts.geom.CoordinateSequence;

// OpenGIS dependencies
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;


/**
 * Interface that should be implemented by classes able to apply the provided
 * {@linkplain MathTransform transformation} to a
 * {@linkplain CoordinateSequence coordinate sequence}.
 *
 * @author Andrea Aime
 * @version $Id$
 * @source $URL$
 * @since 2.1
 */
public interface CoordinateSequenceTransformer {
    /**
     * Returns a transformed coordinate sequence.
     *
     * @param sequence  The sequence to transform.
     * @param transform The transformation to apply.
     * @throws TransformException if at least one coordinate can't be transformed.
     */
    public CoordinateSequence transform(CoordinateSequence sequence, MathTransform transform)
            throws TransformException;
}
