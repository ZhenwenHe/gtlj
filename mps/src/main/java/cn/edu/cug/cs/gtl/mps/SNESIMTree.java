package cn.edu.cug.cs.gtl.mps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SNESIMTree extends SNESIM {
    /**
     * @brief Structure for the tree node
     */
    protected class TreeNode {

        public float value;
        public int counter;
        public int level;
        public ArrayList<TreeNode> children = new ArrayList<>();
    }

    /**
     * @brief Search tree
     */
    protected ArrayList<TreeNode> _searchTree = new ArrayList<>();

    /**
     * @brief Constructors from a configuration file
     */
    SNESIMTree(final String configurationFile) {
        super();
        initialize(configurationFile);
    }


    /**
     * @param configurationFile configuration file name
     * @brief Initialize the simulation from a configuration file
     */
    void initialize(final String configurationFile) {
        //Reading configuration file
        _readConfigurations(configurationFile);

        //Reading data from files
        _readDataFromFiles();

        //Checking the TI array dimensions
        _tiDimX = (int) _TI.getDimensionX();
        _tiDimY = (int) _TI.getDimensionX();
        _tiDimZ = (int) _TI.getDimensionZ();

        if (_debugMode > -1) {
            System.out.print("TI size (X,Y,Z):" + _tiDimX + " " + _tiDimY + " " + _tiDimZ + "\n");
        }
    }

    /**
     * @param level the current grid level
     * @brief Abstract function allow acces to the beginning of each simulation of each multiple grid
     */
    protected void _InitStartSimulationEachMultipleGrid(final int level) {
        int totalLevel = _totalGridsLevel;
        ////Adaptive template size, reserve for later
        //int minTemplateX = 4 < _templateSizeX ? 4 : _templateSizeX;
        //int minTemplateY = 4 < _templateSizeY ? 4 : _templateSizeY;
        //int minTemplateZ = 4 < _templateSizeZ ? 4 : _templateSizeZ; //Initialize at minimum size, lowest best template is at 4 x 4 x 4
        //Using the original template size
        int minTemplateX = _templateSizeX;
        int minTemplateY = _templateSizeY;
        int minTemplateZ = _templateSizeZ;
        //  x
        //x 0 x
        //  x
        int templateX = minTemplateX, templateY = minTemplateY, templateZ = minTemplateZ;
        //Ajust the template size based on the current level, template get smaller when the level get lower
        templateX = (int) (_templateSizeX - (totalLevel - level) * (Math.ceil(_templateSizeX - minTemplateX) / totalLevel));
        templateY = (int) (_templateSizeY - (totalLevel - level) * (Math.ceil(_templateSizeY - minTemplateY) / totalLevel));
        templateZ = (int) (_templateSizeZ - (totalLevel - level) * (Math.ceil(_templateSizeZ - minTemplateZ) / totalLevel));

        //Building template structure
        _constructTemplateFaces(templateX, templateY, templateZ);

        //Scanning the TI and build the search tree
        //Building the search tree
        _searchTree.clear();

        int offset = (int) (Math.pow(2, level));
        if (_debugMode > -1) {
            System.out.println("level: " + level + " offset: " + offset);
            System.out.print("original template size X: " + _templateSizeX + " adjusted template size X: " + templateX + "\n");
            System.out.print("original template size Y: " + _templateSizeY + " adjusted template size Y: " + templateY + "\n");
            System.out.print("original template size Z: " + _templateSizeZ + " adjusted template size Z: " + templateZ + "\n");
        }

        int tiX, tiY, tiZ;
        int deltaX, deltaY, deltaZ;
        int nodeCnt = 0;
        boolean foundExistingValue = false;
        int foundIdx = 0;
        int totalNodes = _tiDimX * _tiDimY * _tiDimZ;
        int lastProgress = 0;
        //Put the current node as the root node
        ArrayList<TreeNode> currentTreeNode;
        //std::vector<TreeNode>* currentTreeNode = &_searchTree;

        for (int z = 0; z < _tiDimZ; z += 1) {
            for (int y = 0; y < _tiDimY; y += 1) {
                for (int x = 0; x < _tiDimX; x += 1) {
                    //For each pixel
                    nodeCnt++;
                    if (_debugMode > -1) {
                        //Doing the progression
                        //Print progression on screen
                        int progress = (int) ((nodeCnt / (float) totalNodes) * 100);
                        if ((progress % 10) == 0 && progress != lastProgress) { //Report every 10%
                            lastProgress = progress;
                            System.out.print("Building search tree at level: " + level + " Progression (%): " + progress + "\n");
                        }
                    }


                    //Reset current node to root node
                    currentTreeNode = _searchTree;
                    //currentTreeNode = &_searchTree;
                    for (int i = 0; i < _templateFaces.size(); i++) {
//                        System.out.println("templateFaces"+i);
                        //Go deeper in the pattern template or to a higher level node
                        deltaX = offset * _templateFaces.get(i).getX();
                        deltaY = offset * _templateFaces.get(i).getY();
                        deltaZ = offset * _templateFaces.get(i).getZ();
                        tiX = x + deltaX;
                        tiY = y + deltaY;
                        tiZ = z + deltaZ;

                        foundExistingValue = false;
                        foundIdx = 0;
                        //Checking of NaN value
                        if ((tiX < 0 || tiX >= _tiDimX) || (tiY < 0 || tiY >= _tiDimY) || (tiZ < 0 || tiZ >= _tiDimZ) || Utility.isNAN(_TI.getElement(tiX, tiY, tiZ))) { //Out of bound or nan
                            break; //Ignore border stop here
                        } else {
                            //Searching the TI cell value inside the current node
                            for (int j = 0; j < currentTreeNode.size(); j++) {
//                                System.out.println("current" + j);
                                if (_TI.getElement(tiX, tiY, tiZ) == currentTreeNode.get(j).value) {
                                    //Existing value so increase the counter
                                    foundExistingValue = true;
                                    currentTreeNode.get(j).counter = currentTreeNode.get(j).counter + 1;
                                    foundIdx = j;
                                    break;
                                }
                            }

                            //If value is not found then add a new value in the node
                            if (!foundExistingValue) {
                                TreeNode aTreeNode = new TreeNode();
                                aTreeNode.counter = 1;
                                aTreeNode.value = _TI.getElement(tiX, tiY, tiZ);
                                aTreeNode.level = i;
                                currentTreeNode.add(aTreeNode);
                                foundIdx = currentTreeNode.size() - 1;
                            }
                            //Switching the current node to the children
                            currentTreeNode = currentTreeNode.get(foundIdx).children;
                        }
                    }
                }
            }
        }
        if (_debugMode > -1) {
            System.out.print("Finish building search tree" + "\n");
        }
        //std::cout << "Total nodes: " << nodeCnt << std::endl;
        //Check out dictionary
        //std::cout << "Dictionary info: " << std::endl;
        //std::cout << "Level: " << level << std::endl;
        ////Showing the search tree for debugging
        //std::list<std::vector<TreeNode>*> nodesToCheck;
        //nodesToCheck.push_back(&_searchTree[level]); //Put the root node in the list node to be checked
        ////Looping through all the node from top to bottom
        //while(nodesToCheck.size() > 0) {
        //	currentTreeNode = nodesToCheck.back();
        //	nodesToCheck.pop_back();
        //	//Showing the current node value and counter
        //	for (int i=0; i<currentTreeNode->size(); i++) {
        //		std::cout << currentTreeNode->operator[](i).level << " " << currentTreeNode->operator[](i).value << " " << currentTreeNode->operator[](i).counter << std::endl;
        //		//Adding the children node to the list node to be checked
        //		nodesToCheck.push_front(&(currentTreeNode->operator[](i).children));
        //	}
        //	//std::cout << "list size: " << nodesToCheck.size() << std::endl;
        //}

    }

    /**
     * @brief Start the simulation
     * Virtual function implemented from MPSAlgorithm
     */
    public void startSimulation() {
        //Call parent function
        super.startSimulation();
    }

    /**
     * @param sgIdxX index X of a node inside the simulation grind
     * @param sgIdxY index Y of a node inside the simulation grind
     * @param sgIdxZ index Z of a node inside the simulation grind
     * @param level  multigrid level
     * @return found node's value
     * @brief MPS dsim simulation algorithm main function
     */
    protected float _simulate(final int sgIdxX, final int sgIdxY, final int sgIdxZ, final int level) {
        //Initialize with node's value
        float foundValue = _sg.getElement(sgIdxX, sgIdxY, sgIdxZ);
        //If have NaN value then doing the simulation ...
        if (Utility.isNAN(_sg.getElement(sgIdxX, sgIdxY, sgIdxZ))) {
            int offset = (int) (Math.pow(2, level));
            int sgX, sgY, sgZ;
            int deltaX, deltaY, deltaZ;
            //foundValue = std::numeric_limits<float>::quiet_NaN();
            foundValue = Float.NaN;
            int maxConditionalPoints = -1, conditionPointsUsedCnt = 0;
            //Initialize a value
            // std::vector<float> aPartialTemplate;
            ArrayList<Float> aPartialTemplate = new ArrayList<>();
            //Building a template based on the neighbor points
            // Find conditional data
            for (int i = 1; i < _templateFaces.size(); i++) { //For all the set of templates available except the first one at the template center
                //For each template faces
                deltaX = offset * _templateFaces.get(i).getX();
                deltaY = offset * _templateFaces.get(i).getY();
                deltaZ = offset * _templateFaces.get(i).getZ();
                sgX = sgIdxX + deltaX;
                sgY = sgIdxY + deltaY;
                sgZ = sgIdxZ + deltaZ;
                if (!(sgX < 0 || sgX >= _sgDimX) && !(sgY < 0 || sgY >= _sgDimY) && !(sgZ < 0 || sgZ >= _sgDimZ)) {
                    //not overflow
                    if (!Utility.isNAN(_sg.getElement(sgIdxX, sgIdxY, sgIdxZ))) {
                        aPartialTemplate.add(_sg.getElement(sgIdxX, sgIdxY, sgIdxZ));
                    } else { //NaN value
                        aPartialTemplate.add(Float.NaN);
                    }
                } else aPartialTemplate.add(Float.NaN);
            }

            //for (unsigned int i=0; i<aPartialTemplate.size(); i++) {
            //	std::cout << aPartialTemplate[i] << " " ;
            //}
            //std::cout << std::endl;

            //Going through the search tree and get the value of the current template

            //std::vector<TreeNode>* currentTreeNode;
            // std::list<std::vector<TreeNode>*> nodesToCheck;
            // std::map<float, int> conditionalPoints;

            ArrayList<TreeNode> currentTreeNode = new ArrayList<>();
            ArrayList<ArrayList<TreeNode>> nodesToCheck = new ArrayList<>();
            Map<Float, Integer> conditionalPoints = new HashMap<>();
            int sumCounters = 0;
            int currentLevel = 0, maxLevel = 0;

            //For all possible values of root tree
            for (int j = 0; j < _searchTree.size(); j++) {
                conditionPointsUsedCnt = 0;
                maxLevel = 0;
                sumCounters = _searchTree.get(j).counter;
                nodesToCheck.clear();
                nodesToCheck.add(_searchTree.get(j).children); //Initialize at children in first level
                //Looping through all the node from top to bottom
                while (nodesToCheck.size() > 0) {
                    currentTreeNode = nodesToCheck.get(nodesToCheck.size() - 1);
                    nodesToCheck.remove(nodesToCheck.size() - 1);
                    //Showing the current node value and counter
                    for (int i = 0; i < currentTreeNode.size(); i++) {
                        if (Utility.isNAN(aPartialTemplate.get(currentTreeNode.get(i).level - 1))) {
                            //If the template value is non defined then just go to children
                            // nodesToCheck.push_front(currentTreeNode.get(i).children);
                            for (int k = nodesToCheck.size(); k > 0; --k) {
                                nodesToCheck.set(k, nodesToCheck.get((k - 1)));
                            }
                            if (nodesToCheck.size() != 0) {
                                nodesToCheck.set(0, currentTreeNode.get(i).children);
                            }
                        } else if (currentTreeNode.get(i).value == aPartialTemplate.get(currentTreeNode.get(i).level - 1)) {
                            currentLevel = currentTreeNode.get(i).level;
                            //Template found so go to higher level node
                            if (currentLevel > maxLevel) {
                                maxLevel = currentLevel;
                                //Restart counter at only maximum level
                                sumCounters = currentTreeNode.get(i).counter;
                                conditionPointsUsedCnt++;
                            } else if (currentLevel == maxLevel) {
                                //Adding the counter to the sum counters
                                sumCounters += currentTreeNode.get(i).counter;
                            }

                            //Only continue to the children node if the current node counter is big enough or if the number of conditional points used is smaller than a given limit
                            if (currentTreeNode.get(i).counter > _minNodeCount && (conditionPointsUsedCnt < _maxCondData || _maxCondData == -1)) {
                                //Adding the children node to the list node to be checked
                                //nodesToCheck.push_front(currentTreeNode.get(i).children);
                                for (int k = nodesToCheck.size(); k > 0; --k) {
                                    nodesToCheck.set(k, nodesToCheck.get((k - 1)));
                                }
                                if (nodesToCheck.size() != 0) {
                                    nodesToCheck.set(0, currentTreeNode.get(i).children);
                                }
                            }
                        }
                    }
                }
                //std::cout << _searchTree[level][j].value << " " << sumCounters << " " << maxLevel << std::endl;
                //finish searching for a value, now do the sum
                if (conditionPointsUsedCnt > maxConditionalPoints) {
                    conditionalPoints.clear();
                    conditionalPoints.put(_searchTree.get(j).value, sumCounters);
                    maxConditionalPoints = conditionPointsUsedCnt;
                    //foundValue = _searchTree[level][j].value;
                } else if (conditionPointsUsedCnt == maxConditionalPoints) {
                    conditionalPoints.put(_searchTree.get(j).value, sumCounters);
                }
            }

            if (_debugMode > 1) {
                _tg1.setElement(sgIdxX, sgIdxY, sgIdxZ, conditionPointsUsedCnt);
            }
            //Get the value from cpdf
            foundValue = _cpdf(conditionalPoints, sgIdxX, sgIdxY, sgIdxZ);
            //std::cout << std::endl;
        }
        return foundValue;
    }


}
