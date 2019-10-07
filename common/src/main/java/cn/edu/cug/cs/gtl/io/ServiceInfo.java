package cn.edu.cug.cs.gtl.io;

import java.net.URI;
import java.util.Set;

public interface ServiceInfo {

    /**
     * Human readable title representing the service.
     *
     * <p>The title is used to represent the service in the context of a user interface and should
     * make use of the current Locale if possible.
     *
     * @return title, null if unsupported.
     */
    String getTitle();

    /**
     * Keywords associated with this service.
     *
     * <p>Maps to the Dublin Core Subject element.
     *
     * @return keywords associated with this service.
     */
    Set<String> getKeywords();

    /**
     * Human readable description of this service.
     *
     * <p>This use is understood to be in agreement with "dublin-core", implementors may use either
     * abstract or description as needed.
     *
     * <p>
     *
     * <ul>
     *   <li>Dublin Core: <quote> A textual description of the content of the resource, including
     *       abstracts in the case of document-like objects or content descriptions in the case of
     *       visual resources. </quote> When providing actual dublin-core metadata you can gather up
     *       all the description information into a single string for searching.
     *   <li>WMS: abstract
     *   <li>WFS: abstract
     *   <li>shapefile shp.xml information
     * </ul>
     *
     * @return Human readable description, may be null.
     */
    String getDescription();

    /**
     * Party responsible for providing this service.
     *
     * <p>Known mappings:
     *
     * <ul>
     *   <li>WMS contact info
     *   <li>File formats may wish to use the current user, or the last user to modify the file
     * </ul>
     *
     * @return URI identifying the publisher of this service
     */
    URI getPublisher();

    /**
     * A URI used to identify the service type.
     *
     * <p>Maps to the Dublin Code Format element.
     *
     * <p>
     *
     * <ul>
     *   <li>Service type for open web services
     *   <li>File format or extension for on disk files
     *   <li>XML schema namespace for this service type.
     * </ul>
     *
     * <p>
     *
     * @return URI used to identify service type
     */
    URI getSchema();

    /**
     * Returns the service source.
     *
     * <p>Maps to the Dublin Core Server Element.
     *
     * <p>
     *
     * <ul>
     *   <li>Open web services can use the online resource of their capabilies document
     *   <li>File formats may wish to use their parent directory
     * </ul>
     *
     * <p>
     *
     * @return Source of this service
     */
    URI getSource();
}
