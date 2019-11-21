package cn.edu.cug.cs.gtl.geotools.demo;

import java.io.File;

import cn.edu.cug.cs.gtl.feature.FeatureType;
import org.geotools.data.FeatureReader;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.feature.FeatureTypeHandler;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class DemoApp {
    public static void main(String [] args) throws Exception{
        // display a data store file chooser dialog for shapefiles
        File file = JFileDataStoreChooser.showOpenFile("shp", null);
        if (file == null) {
            return;
        }

        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();

        System.out.println(store.toString());
        FeatureJSON featureJSON = new FeatureJSON();
        featureJSON.setFeatureType(store.getSchema());
        System.out.println(store.getSchema().toString());
        FeatureReader<SimpleFeatureType, SimpleFeature> r = store.getFeatureReader();
        while (r.hasNext()){
            SimpleFeature feature = r.next();
            System.out.println(featureJSON.toString(feature));
        }

        // Create a map content and add our shapefile to it
        MapContent map = new MapContent();
        map.setTitle("Quickstart");

        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(featureSource, style);
        map.addLayer(layer);

        // Now display the map
        JMapFrame.showMap(map);
    }
}
