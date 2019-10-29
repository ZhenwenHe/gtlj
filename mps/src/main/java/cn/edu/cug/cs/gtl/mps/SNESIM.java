package cn.edu.cug.cs.gtl.mps;


import cn.edu.cug.cs.gtl.util.StringUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.*;
import java.util.*;

public class SNESIM extends MPSAlgorithm {
    ////////父类的三个抽象方法，必须实现
    protected float _simulate(final int sgIdxX, final int sgIdxY, final int sgIdxZ, final int level) {
        float tmp = 0.0f;
        return tmp;
    }


    protected void _InitStartSimulationEachMultipleGrid(final int level) {
    }

//    public  void startSimulation(){}
    /**
     * @brief template size X
     */
    int _templateSizeX;

    /**
     * @brief template size y
     */
    int _templateSizeY;

    /**
     * @brief template size z
     */
    int _templateSizeZ;

    /**
     * @brief Min node count allowed
     */
    int _minNodeCount;

    /**
     * @brief List of available faces in the template
     */
    ArrayList<Vec3i> _templateFaces = new ArrayList<>();
    //std::vector<mps::Vec3i> _templateFaces;

    /**
     * @brief patterns dictionary
     */
    ArrayList<Map<ArrayList<Vec1i>, Integer>> _patternsDictionary = new ArrayList<>();
    //std::vector<std::map<std::vector<float>, int>> _patternsDictionary;


    /**
     * @brief Class using to sort by 3D distance element in a list
     */
    protected class TemplateSorter implements Comparator<Integer> {
        int _templateSizeX;
        int _templateSizeY;
        int _templateSizeZ;

        public TemplateSorter(final int templateSizeX, final int templateSizeY, final int templateSizeZ) {
            _templateSizeZ = templateSizeZ;
            _templateSizeY = templateSizeY;
            _templateSizeX = templateSizeX;
        }

        @Override
        public int compare(Integer idx1, Integer idx2) {
            int idx1X, idx1Y, idx1Z, idx2X, idx2Y, idx2Z;
            Vec3i vec3i1 = new Vec3i();
            Vec3i vec3i2 = new Vec3i();
            int templateCenterX = (int) Math.floor(_templateSizeX / 2);
            int templateCenterY = (int) Math.floor(_templateSizeY / 2);
            int templateCenterZ = (int) Math.floor(_templateSizeZ / 2);
            Utility.oneDTo3D(idx1, _templateSizeX, _templateSizeY, vec3i1);
            Utility.oneDTo3D(idx2, _templateSizeX, _templateSizeY, vec3i2);
            idx1X = vec3i1.getX();
            idx1Y = vec3i1.getY();
            idx1Z = vec3i1.getZ();
            idx2X = vec3i2.getX();
            idx2Y = vec3i2.getY();
            idx2Z = vec3i2.getZ();

            if ((Math.pow(idx1X - templateCenterX, 2) + Math.pow(idx1Y - templateCenterY, 2) + Math.pow(idx1Z - templateCenterZ, 2)) < (Math.pow(idx2X - templateCenterX, 2) + Math.pow(idx2Y - templateCenterY, 2) + Math.pow(idx2Z - templateCenterZ, 2))) {
                return 1;
            } else {
                return 0;
            }

        }
        // Java不支持运算符重载  ，实现了Comparator接口实现比较功能；
      /*   public bool operator()(const int& idx1, const int& idx2) const {
            int idx1X, idx1Y, idx1Z, idx2X, idx2Y, idx2Z;
            int templateCenterX = (int)floor(_templateSizeX / 2);
            int templateCenterY = (int)floor(_templateSizeY / 2);
            int templateCenterZ = (int)floor(_templateSizeZ / 2);
            mps::utility::oneDTo3D(idx1, _templateSizeX, _templateSizeY, idx1X, idx1Y, idx1Z);
            mps::utility::oneDTo3D(idx2, _templateSizeX, _templateSizeY, idx2X, idx2Y, idx2Z);
            return (pow(idx1X - templateCenterX, 2) + pow(idx1Y - templateCenterY, 2) + pow(idx1Z - templateCenterZ, 2)) < (pow(idx2X - templateCenterX, 2) + pow(idx2Y - templateCenterY, 2) + pow(idx2Z - templateCenterZ, 2));
        }*/


    }

    /**
     * @param fileName configuration filename
     * @brief Read configuration file
     */
    void _readConfigurations(final String fileName) {

        // Check that parameter file exist
        File tmp = new File(fileName);
        if (!tmp.exists()) {
            System.out.print("Paremeter file '" + fileName + "' does not exist -> quitting" + "\n");
            System.exit(0);
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            //String str;
            // std::stringstream ss;
            // String s;
            ArrayList<String> data = new ArrayList<String>();

            // Process each line
            // Number of realizations
            _readLineConfiguration(file, data);
            _realizationNumbers = Integer.parseInt(data.get(1));

            // Initial value
            _readLineConfiguration(file, data);
            _seed = Float.parseFloat(data.get(1));

            // Multiple grid level
            _readLineConfiguration(file, data);
            _totalGridsLevel = Integer.parseInt(data.get(1));

            //Min node count
            _readLineConfiguration(file, data);
            _minNodeCount = Integer.parseInt(data.get(1));

            //Max conditional count
            _readLineConfiguration(file, data);
            _maxCondData = Integer.parseInt(data.get(1));

            // Template size x
            _readLineConfiguration(file, data);
            _templateSizeX = Integer.parseInt(data.get(1));

            // Template size y
            _readLineConfiguration(file, data);
            _templateSizeY = Integer.parseInt(data.get(1));

            // Template size z
            _readLineConfiguration(file, data);
            _templateSizeZ = Integer.parseInt(data.get(1));

            // Simulation Grid size X
            _readLineConfiguration(file, data);
            _sgDimX = Integer.parseInt(data.get(1));

            // Simulation Grid size Y
            _readLineConfiguration(file, data);
            _sgDimY = Integer.parseInt(data.get(1));

            // Simulation Grid size Z
            _readLineConfiguration(file, data);
            _sgDimZ = Integer.parseInt(data.get(1));

            // Simulation Grid World min X
            _readLineConfiguration(file, data);
            _sgWorldMinX = Float.parseFloat(data.get(1));

            // Simulation Grid World min Y
            _readLineConfiguration(file, data);
            _sgWorldMinY = Float.parseFloat(data.get(1));

            // Simulation Grid World min Z
            _readLineConfiguration(file, data);
            _sgWorldMinZ = Float.parseFloat(data.get(1));

            // Simulation Grid Cell Size X
            _readLineConfiguration(file, data);
            _sgCellSizeX = Float.parseFloat(data.get(1));

            // Simulation Grid Cell Size Y
            _readLineConfiguration(file, data);
            _sgCellSizeY = Float.parseFloat(data.get(1));

            // Simulation Grid Cell Size Z
            _readLineConfiguration(file, data);
            _sgCellSizeZ = Float.parseFloat(data.get(1));

            // TI filename
            _readLineConfiguration(file, data);
            //Removing spaces
            data.get(1).replaceAll(" ", "");
            _tiFilename = data.get(1);

            // Output directory
            _readLineConfiguration(file, data);
            //Removing spaces
            data.get(1).replaceAll(" ", "");
            _outputDirectory = data.get(1);

            // Shuffle SGPATH
            _readLineConfiguration(file, data);
            //_shuffleSgPath = (stoi(data[1]) != 0); // TMH:Update to make use of _ShuffleSgPath=2
            _shuffleSgPath = Integer.parseInt(data.get(1));

            // Shuffle Entropy Factor
            // _readLineConfiguration(file, ss, data, s, str);
            //_shuffleEntropyFactor = (stoi(data[1]));
            _shuffleEntropyFactor = 4;
            // Shuffle TI Path
            _readLineConfiguration(file, data);
            _shuffleTiPath = (Integer.parseInt(data.get(1)) != 0);

            // Hard data
            _readLineConfiguration(file, data);
            //Removing spaces
            data.get(1).replaceAll(" ", "");
            _hardDataFileNames = data.get(1);

            // Hard data search radius
            _readLineConfiguration(file, data);
            _hdSearchRadius = Float.parseFloat(data.get(1));

            //Get the maximum value of template size into the search radius
            _hdSearchRadius = Math.max(Math.max(_templateSizeX, _templateSizeY), _templateSizeZ);

            // Softdata categories
            //std::cout << s << std::endl;
            if (_readLineConfiguration(file, data)) {
                String[] ss = StringUtils.split(data.get(1), ";");
                for (int i = 0; i < ss.length; ++i) {
                    if (!ss[i].isEmpty())
                        _softDataCategories.add(Float.parseFloat(ss[i]));

                }
            }

            // Softdata filenames
            if (_readLineConfiguration(file, data)) {
                String[] ss = StringUtils.split(data.get(1), ";");
                for (int i = 0; i < ss.length; ++i) {
                    if (!ss[i].isEmpty()) {
                        ss[i].replaceAll(" ", ""); //Removing spaces
                        _softDataFileNames.add(ss[i]);
                    }


                }
                // Resize softdata grids
                // _softDataGrids.resize(_softDataFileNames.size());//Arrayist 自动扩容，不需要设置大小；
            }
            // Number of threads
            _readLineConfiguration(file, data);
            _numberOfThreads = Integer.parseInt(data.get(1));
            // DEBUG MODE
            _readLineConfiguration(file, data);
            _debugMode = Integer.parseInt(data.get(1));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * @param sizeX template size X
     * @param sizeX template size Y
     * @param sizeX template size Z
     * @brief Construct templatefaces and sort them around template center
     */
    void _constructTemplateFaces(final int sizeX, final int sizeY, final int sizeZ) {
        int templateCenterX = (int) Math.floor(sizeX / 2);
        int templateCenterY = (int) Math.floor(sizeY / 2);
        int templateCenterZ = (int) Math.floor(sizeZ / 2);
        int totalTemplateIndices = sizeX * sizeY * sizeZ;
        int totalTemplates = 0;
        //std::cout << "template center (x, y, z): " << templateCenterX << " " << templateCenterY << " " << templateCenterZ << std::endl;

        //Create a template path
        ArrayList<Integer> templatePath = new ArrayList<Integer>(totalTemplateIndices);

        //Initialize a sequential order
        _initilizePath(sizeX, sizeY, sizeZ, templatePath);

        //Sort template path with the distance to the template center
        //std::sort(templatePath.begin(), templatePath.end(),  new TemplateSorter(sizeX, sizeY, sizeZ));
        Collections.sort(templatePath, new TemplateSorter(sizeX, sizeY, sizeZ));


        //Loop through all template indices
        //initialize faces
        _templateFaces.clear();
        //Initialize the faces with center point(0, 0, 0)
        _templateFaces.add(new Vec3i(0, 0, 0));
        int offsetX, offsetY, offsetZ;
        int templateIdxX, templateIdxY, templateIdxZ;
        Vec3i templateIdx = new Vec3i();
        for (int i = 0; i < totalTemplateIndices; i++) {
            //std::cout << templatePath[i] << " ";
            Utility.oneDTo3D(templatePath.get(i), sizeX, sizeY, templateIdx);
            templateIdxX = templateIdx.getX();
            templateIdxY = templateIdx.getY();
            templateIdxZ = templateIdx.getZ();
            offsetX = templateIdxX - templateCenterX;
            offsetY = templateIdxY - templateCenterY;
            offsetZ = templateIdxZ - templateCenterZ;
            //Ignore center point
            if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
                _templateFaces.add(new Vec3i(offsetX, offsetY, offsetZ));
            }
        }

        ////Forcing a template like in article
        //_templateFaces.clear();
        //_templateFaces.push_back(MPS::Coords3D(0, 0, 0));
        //_templateFaces.push_back(MPS::Coords3D(0, 1, 0));
        //_templateFaces.push_back(MPS::Coords3D(1, 0, 0));
        //_templateFaces.push_back(MPS::Coords3D(0, -1, 0));
        //_templateFaces.push_back(MPS::Coords3D(-1, 0, 0));
        ////Showing the template faces
        //std::cout << _templateFaces.size() << std::endl;
        //for (unsigned int i=0; i<_templateFaces.size(); i++) {
        //	std::cout << _templateFaces[i].getX() << " " << _templateFaces[i].getY() << " " << _templateFaces[i].getZ() << std::endl;
        //}

    }


    /**
     * @param conditionalPoints list of all found conditional points
     * @param x                 coordinate X of the current node
     * @param y                 coordinate Y of the current node
     * @param z                 coordinate Z of the current node
     * @brief Getting a node value by calculating the cummulatie probability distribution function
     */
    float _cpdf(Map<Float, Integer> conditionalPoints, final int x, final int y, final int z) {
        // TMH: RENAME TO _cpdfFromConditionalCount
        //      RENAME conditionalPoints to conditionalCount

        //Fill probability from TI
        //float foundValue = numeric_limits<Float>::quiet_NaN();
        float foundValue = Float.NaN;

        // Loop through all the found conditional data values, and find the number of occurences
        // as the sum of the occurences of each conditional data value.
        float totalCounter = 0;
        for (Map.Entry<Float, Integer> e : conditionalPoints.entrySet()) {
            totalCounter += e.getValue();
            // std::cout << "cpdf ti1: " << iter->first << " " << iter->second << std::endl;
        }
        //std::cout << "totalCounter " << totalCounter << std::endl;

        // obtain the conditional probability from the training image
        // by dividing the number of counts for each condtional data value, with the
        // number of count (totalCounter)
        Map<Float, Float> probabilitiesFromTI = new HashMap<>();
        for (Map.Entry<Float, Integer> e : conditionalPoints.entrySet()) {
            probabilitiesFromTI.put(e.getKey(), (float) e.getValue() / (float) totalCounter);
            // std::cout << "cpdf ti2: " << iter->first << " " << (float)(iter->second) / (float)totalCounter << std::endl;
        }

        // Perhaps some soft data needs to be combined with the
        // conditional probability found from the previously simulated data.

        //Map<Float, Float> probabilitiesCombined = new HashMap<>();
        MultiMap probabilitiesCombined = new MultiValueMap();

        //Fill probability from Softdata
        boolean useSoftData = true;
        //Empty grids check
        if (_softDataGrids.isEmpty()) useSoftData = false;
            //No value check
        else if (Utility.isNAN(_softDataGrids.get(0).getElement(x, y, z))) useSoftData = false;
        //Not same size check
        //else if (_softDataCategories.size() != probabilitiesFromTI.size()) useSoftData = false;

        //Perform computation of probabilities from soft data
        if (useSoftData) {
            Map<Float, Float> probabilitiesFromSoftData = new HashMap<>();
            float sumProbability = 0;
            int lastIndex = _softDataCategories.size() - 1;
            //_softDataGrids[0][z][y][x] = 0.8;
            for (int i = 0; i < lastIndex; i++) {
                sumProbability += _softDataGrids.get(i).getElement(x, y, z);
                probabilitiesFromSoftData.put(_softDataCategories.get(i), _softDataGrids.get(i).getElement(x, y, z));
                //std::cout << "cpdf sd: " << _softDataCategories[i] << " " << _softDataGrids[i][z][y][x] << std::endl;
            }
            //Last categorie
            probabilitiesFromSoftData.put(_softDataCategories.get(lastIndex), 1 - sumProbability);
            //std::cout << "cpdf sd: " << _softDataCategories[lastIndex] << " " << 1 - sumProbability << std::endl;
            //Compute the combined probabilities from TI and Softdata
            float totalValue = 0;


            Iterator searchIter;
            Iterator e = probabilitiesFromSoftData.keySet().iterator();
            while (e.hasNext()) {
                //Size check
                e.next();
                Iterator it = probabilitiesFromTI.keySet().iterator();
                while (it.hasNext()) {
                    it.next();
                    if (e.equals(it)) {
                        searchIter = it;
                        if (searchIter.hasNext()) {
                            totalValue += probabilitiesFromSoftData.get(e) * probabilitiesFromTI.get(it);
                        } else {
                            totalValue += 0;
                        }
                        break;
                    }

                }
                //if (searchIter != probabilitiesFromTI.end()) totalValue += iter->second * probabilitiesFromTI[iter->first];
                // else totalValue += 0; //iter->second; //If probability from TI has less elements than sd then ignore it
            }


            //Normalize and compute cummulated value
            float cumulateValue = 0;
            Iterator e1 = probabilitiesFromSoftData.keySet().iterator();
            while (e1.hasNext()) {
                //Size check
                Iterator it = probabilitiesFromTI.keySet().iterator();
                while (it.hasNext()) {
                    if (e1.equals(it)) {
                        searchIter = it;
                        if (searchIter.hasNext()) {
                            cumulateValue += probabilitiesFromSoftData.get(e1) * probabilitiesFromTI.get(it) / totalValue;
                        } else {
                            cumulateValue += 0;
                        }
                    }
                    it.next();
                }
                probabilitiesCombined.put(cumulateValue, e1);
            }/////这里和上面的循环不知道对不对；


          /*  for(std::map<float,float>::iterator iter = probabilitiesFromSoftData.begin(); iter != probabilitiesFromSoftData.end(); ++iter) {
                //Size check
                searchIter = probabilitiesFromTI.find(iter->first);
                if (searchIter != probabilitiesFromTI.end()) cumulateValue += (iter->second * probabilitiesFromTI[iter->first]) / totalValue;
                else cumulateValue += 0; //(iter->second) / totalValue;  //If probability from TI has less elements than sd then ignore it
                probabilitiesCombined.put(cumulateValue, iter->first);
            }*/
        } else {
            //Only from TI
            float cumulateValue = 0;
            for (Map.Entry<Float, Float> e : probabilitiesFromTI.entrySet()) {
                cumulateValue += e.getValue();
                probabilitiesCombined.put(cumulateValue, e.getKey());
            }
        }

        //Getting the probability distribution value
        //Random possible value between 0 and 1 then scale into the maximum probability
        float randomValue = (float) Math.random();
        //randomValue = 0.8;
        for (Object e : probabilitiesCombined.entrySet()) {
            if ((float) ((Map.Entry) e).getKey() >= randomValue) {
                List list = (List) ((Map.Entry) e).getValue();
                int mm = 0;
                for (Object o : list) {
                    foundValue = (float) list.get(mm);
                    mm++;
                }
                break;
            }
        }
        //std::cout << randomValue << " " << foundValue << std::endl;
        return foundValue;
    }


}
