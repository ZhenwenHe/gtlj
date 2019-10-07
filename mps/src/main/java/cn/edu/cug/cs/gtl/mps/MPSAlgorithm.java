package cn.edu.cug.cs.gtl.mps;
import com.google.common.collect.Maps;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedInts;
import cn.edu.cug.cs.gtl.annotation.Internal;
import cn.edu.cug.cs.gtl.util.StringUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.htrace.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer.Vanilla.std;

/**
 * @brief Abstract Multiple Points Statistic algorithm
 *
 * This class contains some shared procedures and functions for different implementation of MPS algorithms.
 * This class cannot be used directly, view DirectSimulation for an example of how to implement an algorithm from this base.
 */
public abstract class MPSAlgorithm {
    /**
     * @brief The simulation grid
     */
    protected Array3Df _sg = new Array3Df();
    /**
     * @brief The hard data grid (same size as simulation grid)
     */
    protected Array3Df _hdg = new Array3Df();
    /**
     * @brief Temporary grid 1 - meaning define by type of sim-algorithm (same size as simulation grid)
     */
    protected Array3Df _tg1 = new Array3Df();
    /**
     * @brief Temporary grid 2 - meaning define by type of sim-algorithm (same size as simulation grid)
     */
    protected Array3Df _tg2 = new Array3Df();
    /**
     * @brief hard data search radius for multiple grids
     */
    protected float _hdSearchRadius;
    /**
     * @brief A copy of the simulation grid used for debugging which counts the number of iterations
     */
    protected Array3Df _sgIterations = new Array3Df();
    /**
     * @brief The simulation path
     */
    protected ArrayList<Integer> _simulationPath = new ArrayList<>();
    /**
     * @brief multigrids levels
     */
    protected int _totalGridsLevel;
    /**
     * @brief Dimension X of the simulation Grid
     */
    protected int _sgDimX;
    /**
     * @brief Dimension Y of the simulation Grid
     */
    protected int _sgDimY;
    /**
     * @brief Dimension Z of the simulation Grid
     */
    protected int _sgDimZ;
    /**
     * @brief Coordinate X Min of the simulation grid in world coordinate
     */
    protected float _sgWorldMinX;
    /**
     * @brief Coordinate Y Min of the simulation grid in world coordinate
     */
    protected float _sgWorldMinY;
    /**
     * @brief Coordinate Z Min of the simulation grid in world coordinate
     */
    protected float _sgWorldMinZ;
    /**
     * @brief Size of a cell in X direction in world coordinate
     */
    protected float _sgCellSizeX;
    /**
     * @brief Size of a cell in Y direction in world coordinate
     */
    protected float _sgCellSizeY;
    /**
     * @brief Size of a cell in Z direction in world coordinate
     */
    protected float _sgCellSizeZ;
    /**
     * @brief Maximum conditional data allowed
     */
    protected int _maxCondData;
    /**
     * @brief Define type of random simulation grid path
     */
    protected int _shuffleSgPath;
    /**
     * @brief Define entropy factor for random simulation path
     */
    protected int _shuffleEntropyFactor;
    /**
     * @brief The number of realization created
     */
    protected int _realizationNumbers;
    /**
     * @brief If in debug mode, some extra files and informations will be created
     * Different levels of debug are available :
     * -1 : no information
     * 0 : with on console text about time elapsed
     * 1 : with grid preview on console
     * 2 : extra files are exported (iteration counter) to output folder
     */
    protected int _debugMode;
    /**
     * @brief Show the simulation grid result in the console
     */
    protected boolean _showPreview;
    /**
     * @brief Initial value of the simulation
     */
    protected float _seed;
    /**
     * @brief Maximum number of iterations
     */
    protected int _maxIterations;
    /**
     * @brief Dimension X of the training image
     */
    protected int _tiDimX;
    /**
     * @brief Dimension Y of the training image
     */
    protected int _tiDimY;
    /**
     * @brief Dimension Z of the training image
     */
    protected int _tiDimZ;
    /**
     * @brief Maximum neighbour allowed when doing the neighbour search function
     */
    protected int _maxNeighbours;
    /**
     * @brief Make a random training image path
     */
    protected boolean _shuffleTiPath;
    /**
     * @brief Training image search path
     */
    protected ArrayList<Integer> _tiPath = new ArrayList<>();
    /**
     * @brief Maximum threads used for the simulation
     */
    protected int _numberOfThreads;
    /**
     * @brief Traininng image's filename
     */
    protected String _tiFilename = new String();
    /**
     * @brief Output directory to store the result
     */
    protected String _outputDirectory = new String();
    /**
     * @brief Hard data filenames used for the simulation
     */
    protected String _hardDataFileNames = new String();
    /**
     * @brief Soft data filenames used for the simulation
     */
    ArrayList<String> _softDataFileNames = new ArrayList<>();
    /**
     * @brief Soft data categories
     */
    ArrayList<Float> _softDataCategories = new ArrayList<>();
    /**
     * @brief Softdata grid
     */
    ArrayList<Array3Df> _softDataGrids = new ArrayList<>();
    /**
     * @brief The training image
     */
    protected Array3Df _TI = new Array3Df();
    /**
     * @brief Threads used for the simulation
     */
    protected ArrayList<Thread> _threads = new ArrayList<>();
    /**
     * @brief Atomic flag used to sychronize threads
     */
    protected AtomicBoolean _jobDone = new AtomicBoolean();

    /**
     * @brief simulation algorithm main function
     * @param sgIdxX index X of a node inside the simulation grind
     * @param sgIdxY index Y of a node inside the simulation grind
     * @param sgIdxZ index Z of a node inside the simulation grind
     * @param level level of the current grid
     * @return found node's value
     */
    protected abstract float _simulate(final int sgIdxX, final int sgIdxY, final int sgIdxZ, final int level) ;

    /**
     * @brief Abstract function allow acces to the beginning of each simulation of each multiple grid
     * @param level the current grid level
     */
    protected abstract void _InitStartSimulationEachMultipleGrid(final int level);

    /**
     * @brief Abstract function for starting the simulation
     */
//    public abstract void startSimulation() ;

    /**
     * @brief get the simulation grid
     * @return the simulation grid
     */
    public Array3Df sg()  {return _sg;}
    /**
     * @brief set the simulation grid
     * @param sg new simulation grid
     */
    public void setSg(Array3Df sg) {_sg = sg;}
    /**
     * @brief get the iterations simulation grid
     * @return iterations simulation grid
     */
    public Array3Df sgIterations()  {return _sgIterations;}
    /**
     * @brief set the iterations simulation grid
     * @param sgIterations new iterations simulation grid
     */
    public void setsgIterations(Array3Df sgIterations) {_sgIterations = sgIterations;}
    /**
     * @brief get the simulation path
     * @return the simulation path
     */
    public ArrayList<Integer> simulationPath()  {return _simulationPath;}
    /**
     * @brief set the simulation path
     * @param simulationPath new simulation path
     */
    public void setSimulationPath(ArrayList<Integer> simulationPath) {_simulationPath = simulationPath;}
    /**
     * @brief get the simulation grid dimension X
     * @return simulation grid dimension X
     */
    public int sgDimX()  {return _sgDimX;}
    /**
     * @brief set new dimension X to the simulation grid. The sg need to be reinitialized after calling this function
     * @param sgDimX new dimension X of the sg
     */
    public void setSgDimX(int sgDimX) {_sgDimX = sgDimX;}
    /**
     * @brief get the simulation grid dimension Y
     * @return simulation grid dimension Y
     */
    public int sgDimY()  {return _sgDimY;}
    /**
     * @brief set new dimension Y to the simulation grid. The sg need to be reinitialized after calling this function
     * @param sgDimY new dimension Y of the sg
     */
    public void setSgDimY(int sgDimY) {_sgDimY = sgDimY;}
    /**
     * @brief get the simulation grid dimension Z
     * @return simulation grid dimension Z
     */
    public int sgDimZ()  {return _sgDimZ;}
    /**
     * @brief set new dimension Z to the simulation grid. The sg need to be reinitialized after calling this function
     * @param sgDimZ new dimension Z of the sg
     */
    public void setSgDimZ(int sgDimZ) {_sgDimZ = sgDimZ;}
    /**
     * @brief Getter shuffleSgPath
     * @return shuffleSgPath
     */
    public int shuffleSgPath()  {return _shuffleSgPath;}
    /**
     * @brief Setter ShuffleSgPath
     * @param shuffleSgPath new value
     */
    public void setShuffleSgPath(int shuffleSgPath) {_shuffleSgPath = shuffleSgPath;}
    /**
     * @brief get the realization numbers
     * @return realization number
     */
    public int realizationNumbers()  {return _realizationNumbers;}
    /**
     * @brief set the realization numbers
     * @param realizationNumbers new realization numbers
     */
    public void setRealizationNumbers(int realizationNumbers) {_realizationNumbers = realizationNumbers;}
    /**
     * @brief get is debug mode
     * @return debug mode
     */
    public int debugMode()  {return _debugMode;}
    /**
     * @brief set debug mode
     * @param debugMode new debug mode
     */
    public void setDebugMode(int debugMode) {_debugMode = debugMode;}
    /**
     * @brief get is preview showed
     * @return is preview showed
     */
    public boolean showPreview()  {return _showPreview;}
    /**
     * @brief set ShowPreview
     * @param showPreview new showpreview
     */
    public void setShowPreview(boolean showPreview) {_showPreview = showPreview;}
    /**
     * @brief Getter maxIterations
     * @return maxIterations
     */
    public int maxIterations()  {return _maxIterations;}
    /**
     * @brief Setter MaxIterations
     * @param maxIterations new value
     */
    public void setMaxIterations(int maxIterations) {_maxIterations = maxIterations;}
    /**
     * @brief Getter numberOfThreads
     * @return numberOfThreads
     */
    public int numberOfThreads()  {return _numberOfThreads;}
    /**
     * @brief Setter NumberOfThreads
     * @param numberOfThreads new value
     */
    public void setNumberOfThreads(int numberOfThreads) {_numberOfThreads = numberOfThreads;}
    /**
     * @brief Getter tiFilename
     * @return tiFilename
     */
    public String tiFilename()  {return _tiFilename;}
    /**
     * @brief Setter TiFilename
     * @param tiFilename new value
     */
    public void setTiFilename(String tiFilename) {_tiFilename = tiFilename;}
    /**
     * @brief Getter outputDirectory
     * @return outputDirectory
     */
    public String outputDirectory()  {return _outputDirectory;}
    /**
     * @brief Setter OutputDirectory
     * @param outputDirectory new value
     */
    public void setOutputDirectory(final String outputDirectory) {_outputDirectory = outputDirectory;}
    /**
     * @brief Getter hardDataFileNames
     * @return hardDataFileNames
     */
    public String hardDataFileNames()  {return _hardDataFileNames;}
    /**
     * @brief Setter HardDataFileNames
     * @param hardDataFileNames new value
     */
    public void setHardDataFileNames(final String hardDataFileNames) {_hardDataFileNames = hardDataFileNames;}
    /**
     * @brief Getter TI
     * @return TI
     */
    public Array3Df TI()  {return _TI;}
    /**
     * @brief Setter TI
     * @param TI new value
     */
    public void setTI(Array3Df TI) {_TI = TI;}
    /**
     * @brief Getter tiDimX
     * @return tiDimX
     */
    public int tiDimX()  {return _tiDimX;}
    /**
     * @brief Setter TiDimX
     * @param tiDimX new value
     */
    public void setTiDimX(int tiDimX) {_tiDimX = tiDimX;}
    /**
     * @brief Getter tiDimY
     * @return tiDimY
     */
    public int tiDimY()  {return _tiDimY;}
    /**
     * @brief Setter TiDimY
     * @param tiDimY new value
     */
    public void setTiDimY(int tiDimY) {_tiDimY = tiDimY;}
    /**
     * @brief Getter tiDimZ
     * @return tiDimZ
     */
    public int tiDimZ()  {return _tiDimZ;}
    /**
     * @brief Setter TiDimZ
     * @param tiDimZ new value
     */
    public void setTiDimZ(int tiDimZ) {_tiDimZ = tiDimZ;}
    /**
     * @brief get the maximum neighbours number used for neighbour search
     * @return maximum neighbour number
     */
    public int maxNeighbours()  {return _maxNeighbours;}
    /**
     * @brief set maximum neighbours number
     * @param maxNeighbours new maximum neighbour number
     */
    public void setMaxNeighbours(int maxNeighbours) {_maxNeighbours = maxNeighbours;}
    /**
     * @brief Getter shuffleTiPath
     * @return shuffleTiPath
     */
    public boolean shuffleTiPath()  {return _shuffleTiPath;}
    /**
     * @brief Setter ShuffleTiPath
     * @param shuffleTiPath new value
     */
    public void setShuffleTiPath(boolean shuffleTiPath) {_shuffleTiPath = shuffleTiPath;}
    /**
     * @brief Getter tiPath
     * @return tiPath
     */
    public ArrayList<Integer> tiPath()  {return _tiPath;}
    /**
     * @brief Setter TiPath
     * @param tiPath new value
     */
    public void setTiPath(ArrayList<Integer> tiPath) {_tiPath = tiPath;}

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @brief Constructors
     */
    public MPSAlgorithm() {

    }

    /**
     * @brief Initialize the Hard Data Grid with a value, default is NaN
     * @param hdg the simulation GRID
     * @param sgDimX dimension X of the grid
     * @param sgDimY dimension Y of the gri
     * @param sgDimZ dimension Z of the grid
     * @param value value of each grid node default is NAN
     */
    void _initializeHDG(Array3Df hdg, final int sgDimX, final int sgDimY, final int sgDimZ, final float value) {
        hdg.resize(sgDimX, sgDimY, sgDimZ,value);
    }

    void _initializeHDG(Array3Df hdg, final int sgDimX, final int sgDimY, final int sgDimZ) {
        hdg.resize(sgDimX, sgDimY, sgDimZ,Float.NaN);
    }

    /**
     * @brief Initialize the Simulation Grid with a value, default is NaN
     * @param sg the simulation GRID
     * @param sgDimX dimension X of the grid
     * @param sgDimY dimension Y of the gri
     * @param sgDimZ dimension Z of the grid
     * @param value value of each grid node default is NAN
     */
    void _initializeSG(Array3Df sg, final int sgDimX, final  int sgDimY, final int sgDimZ, final float value) {
        sg.resize(sgDimX,sgDimY,sgDimZ,value);
    }

    void _initializeSG(Array3Df sg, final int sgDimX, final  int sgDimY, final int sgDimZ) {
        sg.resize(sgDimX,sgDimY,sgDimZ,Float.NaN);
    }

    /**
     * @brief Initialize the Simulation Grid from a 3D grid with values
     * The Copy and simulation grid must have the same dimension in X, Y and Z
     * @param sg the simulation GRID
     * @param sgDimX dimension X of the grid
     * @param sgDimY dimension Y of the gri
     * @param sgDimZ dimension Z of the grid
     * @param grid the grid to copy data in
     * @param nanValue value in copy grid considered to be a NAN value default is -1
     */
    void _initializeSG(Array3Df sg, final int sgDimX, final int sgDimY, final int sgDimZ, Array3Df grid, final float nanValue) {
        for (int z = 0; z < sgDimZ; z ++) {
            for (int y = 0; y < sgDimY; y++) {
                for (int x = 0; x < sgDimX; x++) {
                    if(!Utility.isNAN(grid.getElement(x, y, z))) sg.setElement(x, y, z, grid.getElement(x, y, z));
                    else sg.setElement(x, y, z, Float.NaN);
                    System.out.println("init:: " + x + y + z + " VAL " +  _sg.getElement(x, y, z) + " " + grid.getElement(x, y, z));
                }
            }
        }
    }

    void _initializeSG(Array3Df sg, final int sgDimX, final int sgDimY, final int sgDimZ, Array3Df grid) {
        _initializeSG( sg,  sgDimX, sgDimY,  sgDimZ,  grid, -1.0f);
    }

    /**
     * @brief Initilize a sequential simulation path
     * @param sgDimX dimension X of the path
     * @param sgDimY dimension Y of the path
     * @param sgDimZ dimension Z of the path
     * @param path output simulation path
     */
    void _initilizePath(final int sgDimX, final int sgDimY, final int sgDimZ, ArrayList<Integer> path) {
        //Putting sequential indices
        int cnt = 0;
        for (int z=0; z<sgDimZ; z++) {
            for (int y=0; y<sgDimY; y++) {
                for (int x=0; x<sgDimX; x++) {
                    //path[cnt] = cnt++;
                    path.add(cnt,cnt);
                    cnt++;
//                    path.set(cnt,cnt++);
                }
            }
        }
    }

    /**
     * @brief Generate a realization from a PDF defined as a map
     * @param Pdf the pdf as a std::map, realization from the pdf
     */
    //这里的Map下面好像没有修改，修改为final
    float _sampleFromPdf(final Map<Float, Float> Pdf) {
        float simulatedValue = Float.NaN;
        float randomValue;
        Random r = new Random();
        randomValue = r.nextFloat();

        float cumsum_pdf = 0; // integral conditional probability density (conditionalPdfFromTi)
        for(Map.Entry<Float,Float> e: Pdf.entrySet()){
            cumsum_pdf += e.getValue();
            if (cumsum_pdf >= randomValue) {
                simulatedValue = e.getKey();
                break;
            }
        }
        return simulatedValue;
    }

    /**
     * @brief Check if the current node is closed to a node in a given grid
     * The Given grid could be softdata or harddata grid
     * @param x coordinate X of the current node
     * @param y coordinate Y of the current node
     * @param z coordinate Z of the current node
     * @param level current grid level
     * @param grid given grid
     * @param searchRadius search radius for closed point search
     * @param closestCoordinates closest coordinates found
     * @return True if found a closed node
     */
    boolean _IsClosedToNodeInGrid(final int x, final int y, final int z, final int level, final Array3Df grid, final float searchRadius, Vec3i closestCoordinates) {
        //Using circular search
        //TODO: Need to check this again, it runs slow with HD
        ArrayList<Vec3i> L=new ArrayList<>();
        ArrayList<Float> V=new ArrayList<>();
        _circularSearch(x, y, z, grid, 1, searchRadius, L, V);
        //_circularSearch(x, y, z, grid, 1, std::pow(2, level), L, V);
        boolean foundClosest = L.size() > 0;
        if (foundClosest) {
            closestCoordinates.setX(x + L.get(0).getX());
            closestCoordinates.setY(y + L.get(0).getY());
            closestCoordinates.setZ(z + L.get(0).getZ());
        }
        return foundClosest;
    }

    /**
     * @brief Compute cpdf from softdata as map
     * @param x coordinate X of the current node
     * @param y coordinate Y of the current node
     * @param z coordinate Z of the current node
     * @param level current grid level
     * @param softPdf softPdf list
     * @param closestCoords closest point used with relocation, if not then the current x, y, z is used
     * @return computed value from softdata
     */
    boolean _getCpdfFromSoftData(final int x, final int y, final int z, final int level, Map<Float, Float> softPdf,  Vec3i closestCoords) {
        //Empty grids check
        if (_softDataGrids.isEmpty()) return false;
        //Out of bound check
        int sdgDimX = _softDataGrids.get(0).getDimensionX();//(int) _softDataGrids[0][0][0].size();
        int sdgDimY = _softDataGrids.get(0).getDimensionY();//(int)_softDataGrids[0][0].size();
        int sdgDimZ = _softDataGrids.get(0).getDimensionZ();//(int)_softDataGrids[0].size();
        if ((x >= sdgDimX) || (y >= sdgDimY) || (z >= sdgDimZ)) return false;
        //Define a default closest node at the current position
        closestCoords.setX(x);
        closestCoords.setY(y);
        closestCoords.setZ(z);
        if (level == 0) { //For coarse level, then check for the same node localtion in softdata grid
            //No value check
            if (Utility.isNAN(_softDataGrids.get(0).getElement(x,y,z))) return false;
        } else {
            //Check if node is closed to a search radius if using multiple level, doing the relocation here
            if (Utility.isNAN(_softDataGrids.get(0).getElement(closestCoords.getX(),closestCoords.getY(),closestCoords.getZ()))) {
                if (!_IsClosedToNodeInGrid(x, y, z, level, _softDataGrids.get(0), (float) Math.ceil(Math.pow(2, level) / 2), closestCoords)) return false;
            }
            //if (!_IsClosedToNodeInGrid(x, y, z, _softDataGrids[0], _hdSearchRadius, closestCoords)) return false;
            //Check if the closest node found already in the current relocated node, if so then stop
            //if(std::find(addedNodes.begin(), addedNodes.end(), closestCoords) != addedNodes.end()) return false;
        }
        //Perform computation
        //Looping through all the softdata categories grids and fill the conditional points
        //std::multimap<float, float> softPdf;
        float sumProbability = 0;
        int lastIndex = _softDataCategories.size() - 1;
        float f = 0.0f;
        for ( int i = 0; i < lastIndex; i ++) {
            f = _softDataGrids.get(i).getElement(closestCoords.getX(),closestCoords.getY(),closestCoords.getZ());
            sumProbability += f;
            softPdf.put(_softDataCategories.get(i), f);
        }
        //Last categorie
        softPdf.put(_softDataCategories.get(lastIndex), 1 - sumProbability);
        return true;
    }

    /**
     * @brief Show the SG in the terminal
     */
    final void _showSG(){
//        for (int z=0; z<_sgDimZ; z++) {
//            System.out.println("Z: " + (z + 1) + "/" + _sgDimZ);
//            for (int y=0; y<_sgDimY; y++) {
//                for (int x=0; x<_sgDimX; x++) {
//                    System.out.println(mps::io::onscreenChars[int(_sg[z][y][x]) % mps::io::onscreenChars.size()]);
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
    }

    /**
     * @brief Read different data (TI, hard and softdata from files)
     */
    void _readDataFromFiles() {
        //Reading TI file
        boolean readSucessfull = false;
        String fileExtension = Utility.getExtension(_tiFilename);
        if (fileExtension.equals("csv") || fileExtension.equals("txt"))
            readSucessfull = IO.readTIFromGS3DCSVFile(_tiFilename, _TI);
	    else if (fileExtension.equals( "dat") || fileExtension.equals("gslib") || fileExtension.equals("sgems") || fileExtension.equals("SGEMS"))
	        readSucessfull = IO.readTIFromGSLIBFile(_tiFilename, _TI);
	    else if (fileExtension.equals("grd3"))
	        readSucessfull = IO.readTIFromGS3DGRD3File(_tiFilename, _TI);
        if(!readSucessfull) {
            System.out.println("Error reading TI "+ _tiFilename);
            return;
        }

        //Reading Hard conditional data
        readSucessfull = false;
        fileExtension = Utility.getExtension(_hardDataFileNames);
        if (fileExtension.equals( "csv") || fileExtension.equals("txt"))
            readSucessfull =IO.readTIFromGS3DCSVFile(_hardDataFileNames, _hdg);
	    else if (fileExtension.equals( "gslib") || fileExtension.equals( "sgems" )|| fileExtension.equals("SGEMS"))
	        readSucessfull = IO.readTIFromGSLIBFile(_hardDataFileNames, _hdg);
	    else if (fileExtension.equals("dat"))
	        readSucessfull = IO.readHardDataFromEASFile(_hardDataFileNames, -999, _sgDimX, _sgDimY, _sgDimZ, _sgWorldMinX, _sgWorldMinY, _sgWorldMinZ, _sgCellSizeX, _sgCellSizeY, _sgCellSizeZ, _hdg);
	    else if (fileExtension.equals("grd3"))
	        readSucessfull = IO.readTIFromGS3DGRD3File(_hardDataFileNames, _hdg);
        if((!readSucessfull)&(_debugMode>-1)) {
            System.out.println("Error reading harddata " + _hardDataFileNames );
        }

        //Reading Soft conditional data
        for (int i=0; i<_softDataFileNames.size(); i++) {
            readSucessfull = false;
            fileExtension = Utility.getExtension(_softDataFileNames.get(i));
            if (fileExtension.equals("csv") || fileExtension.equals("txt"))
                readSucessfull = IO.readTIFromGS3DCSVFile(_softDataFileNames.get(i), _softDataGrids.get(i));
		    else if (fileExtension.equals("gslib") || fileExtension.equals("sgems") || fileExtension.equals("SGEMS"))
		        readSucessfull = IO.readTIFromGSLIBFile(_softDataFileNames.get(i), _softDataGrids.get(i));
		    else if (fileExtension.equals("dat"))
		        readSucessfull = IO.readSoftDataFromEASFile(_softDataFileNames.get(i), _softDataCategories, _sgDimX, _sgDimY, _sgDimZ, _sgWorldMinX, _sgWorldMinY, _sgWorldMinZ, _sgCellSizeX, _sgCellSizeY, _sgCellSizeZ, _softDataGrids); //EAS read only 1 file
		    else if (fileExtension.equals("grd3"))
		        readSucessfull = IO.readTIFromGS3DGRD3File(_softDataFileNames.get(i), _softDataGrids.get(i));
            if(!readSucessfull) {
                _softDataGrids.clear();
                if (_debugMode>-1) {
                    System.out.println("Error reading softdata "+ _softDataFileNames.get(i));
                }
            }

        }
    }

    /**
     * @brief Fill a simulation grid node from hard data and a search radius
     * @param x coordinate X of the current node
     * @param y coordinate Y of the current node
     * @param z coordinate Z of the current node
     * @param level current grid level
     * @param addedNodes list of added nodes
     * @param putbackNodes list of node to put back later
     */
    void _fillSGfromHD(final int x, final int y, final int z, final int level, ArrayList<Vec3i> addedNodes, ArrayList<Vec3i> putbackNodes) {
        //Searching closest value in hard data and put that into the simulation grid
        //Only search for the node not NaN and within the radius
        //Do this only if have a hard data defined or
        //If current node already has value
        if(!_hdg.isEmpty() && Utility.isNAN(_sg.getElement(x,y,z))) {
            Vec3i closestCoords = new Vec3i();
            //if (_IsClosedToNodeInGrid(x, y, z, level, _hdg, _hdSearchRadius, closestCoords)) {
            if (_IsClosedToNodeInGrid(x, y, z, level, _hdg, (float) Math.ceil(Math.pow(2, level) / 2), closestCoords)) { //Limit within the direct neighbor
                //Adding the closest point to a list to desallocate after
                //addedNodes.push_back(closestCoords);
                putbackNodes.add(closestCoords);
                //Adding the current location to a list to desallocate after
                addedNodes.add( new Vec3i(x, y, z));
                //Temporally put the closest node found to the sg cell
                //_sg[z][y][x] = _hdg[closestCoords.getZ()][closestCoords.getY()][closestCoords.getX()];
                _sg.setElement(x,y,z,_hdg.getElement(closestCoords.getX(),closestCoords.getY(),closestCoords.getZ()));
                //Temporally put NaN value to the hdg value relocated so the same point will not be relocated more than 2 times
                //_hdg[closestCoords.getZ()][closestCoords.getY()][closestCoords.getX()] = std::numeric_limits<float>::quiet_NaN();
                _hdg.setElement(closestCoords.getX(),closestCoords.getY(),closestCoords.getZ(),Float.NaN);
            }
        }
    }

    /**
     * @brief Clear the SG nodes from the list of added nodes found by _fillSGfromHD
     * @param addedNodes list of added nodes
     * @param putbackNodes list of node to put back later
     */
    void _clearSGFromHD(ArrayList<Vec3i> addedNodes, ArrayList<Vec3i> putbackNodes) {
        //Cleaning the allocated data from the SG
        Vec3i vec3iPushackNode, vec3iAddedNodes;
        for (int i=0; i < addedNodes.size(); i++) {
            //_hdg[putbackNodes[i].getZ()][putbackNodes[i].getY()][putbackNodes[i].getX()] = _sg[addedNodes[i].getZ()][addedNodes[i].getY()][addedNodes[i].getX()];
            vec3iAddedNodes = addedNodes.get(i);
            vec3iPushackNode = putbackNodes.get(i);
            _hdg.setElement(vec3iPushackNode,_sg.getElement(vec3iAddedNodes));
            if ((vec3iAddedNodes.getZ() != vec3iPushackNode.getZ()) || (vec3iAddedNodes.getY() != vec3iPushackNode.getY()) || (vec3iAddedNodes.getX() != vec3iPushackNode.getX())) {
                //_sg[addedNodes[i].getZ()][addedNodes[i].getY()][addedNodes[i].getX()] = std::numeric_limits<float>::quiet_NaN();
                _sg.setElement(vec3iAddedNodes,Float.NaN);
            }
        }
        addedNodes.clear();
        putbackNodes.clear();
    }

    /**
     * @brief Read a line of configuration file and put the result inside a vector data
     * @param file filestream
     * @param data output data
     * @return true if the line contains data
     */
    boolean _readLineConfiguration(BufferedReader file, ArrayList<String> data)  throws IOException {
        data.clear();
        String str = file.readLine().trim();
//        str.replaceAll(" ","");
        String [] ss = StringUtils.split(str, "#");
        for(int i=0;i<ss.length;++i){
            if(!ss[i].isEmpty())
                ss[i] = ss[i].replaceAll(" ","");
                data.add(ss[i]);
        }
        return (data.size() > 1);
    }

    /**
     * @brief Shuffle the simulation grid path based preferential to soft data
     * @param level current multi grid level
     */
    boolean _shuffleSgPathPreferentialToSoftData(final int level) {
        // PATH PREFERENTIAL BY ENTROPY OF SOFT DATA
        // facEntropy=0 --> prefer soft data, but disregard entroopy
        // facEntropy>0 --> higher number means more deterministic path based increasingly on Entropy
        float facEntropy = _shuffleEntropyFactor;
        int offset = (int)(Math.pow(2, level));
        float randomValue;				// ePath: forst col is random number, second col is an integer (index of node in SG grid)

        //MultiMap<float, int> ePath;
        MultiMap ePath = new MultiValueMap();
        int node1DIdx = -1;
        boolean isRelocated = false;
        boolean isAlreadyAllocated = false;
        ArrayList<Vec3i> allocatedNodesFromSoftData = new ArrayList<>(); //Using to allocate the multiple grid with closest hd values
        Map<Float, Float> softPdf = new HashMap<Float, Float>();
        Vec3i closestCoords = new Vec3i();
        Random random = new Random();                                                                   ///////////
        //Looping through each index of each multiple grid
        for (int z=0; z<_sgDimZ; z+= offset) {
            for (int y=0; y<_sgDimY; y+= offset) {
                for (int x=0; x<_sgDimX; x+= offset) {
                    randomValue = random.nextFloat();// ((float) rand() / (RAND_MAX));
                    // Any Soft data??
                    if (_getCpdfFromSoftData(x, y, z, level, softPdf, closestCoords)) {
                        //Check if the closest node found already in the current relocated node
                        for(Vec3i vec3i : allocatedNodesFromSoftData){
                            if(vec3i.equals(closestCoords)) {
                                isAlreadyAllocated = true;
                                break;
                            }
                        }
                        if (!isAlreadyAllocated) {
                            isRelocated = x != closestCoords.getX() || y != closestCoords.getY() || z != closestCoords.getZ();
                            float E; // total Entropy
                            float Ei; // Partial Entropy
                            float I; // Informatin content
                            float p; // probability
                            float q; // a priori 1D marginal
                            E=0;
                            for(Map.Entry<Float,Float> it: softPdf.entrySet()){
                                //// COMPUTE ENTROPY FOR SOFT PDF
                                if (_shuffleSgPath==2) {
                                    p = it.getValue();
                                    if (p==0) {
                                        Ei=0;
                                    } else {
                                        //Ei=p*(-1*log2(p));
                                        Ei=(float)(p*(-1*Math.log(p) / Math.log(2.))); //msvc2012 doesnt have log2 yet
                                    }
                                    E=E+Ei;
                                } else if (_shuffleSgPath==3) {
                                    // THIS IS REALLY ONLY FOR TESTING WITH THE STREBELLE TI!!!
                                    // soft probability
                                    p = it.getValue();
                                    // 1D marginal
                                    if ( (it.getKey()) < .5) {
                                        q=0.72f;
                                    } else {
                                        q=0.28f;
                                    }

                                    //Ei=p*(-1*log2(p/q));
                                    Ei=(float)(p*(-1*Math.log(p/q) / Math.log(2.)));
                                    E=E+Ei;
                                    //
                                }
                                //Put the relocated softdata into the softdata grid to continue the simulation
                                if (isRelocated) {
                                    for ( int i=0; i<_softDataCategories.size(); i++) {
                                        if (it.getKey() == _softDataCategories.get(i)) {
                                            _softDataGrids.get(i).setElement(x,y,z,it.getValue());
                                            break;
                                        }
                                    }
                                }
                            }
                            I=1-E; // Information content

                            // Order Soft preference based on Entropy, and use a factor to control
                            // the importance of Entropy
                            randomValue = randomValue - 1 - facEntropy*I;

                            if (_debugMode>1) {
                                System.out.print("SOFT DATA -- ");
                                System.out.print("cnt=" + node1DIdx  + " x=" + x + " y=" + y + " z=" + z);
                                System.out.print(" -- E=" + E);
                                System.out.print( ", I=" + I);
                                System.out.println( ", randomV=" + randomValue);
                            }
                        }
                    }
                    node1DIdx = Utility.threeDto1D(closestCoords.getX(), closestCoords.getY(), closestCoords.getZ(), _sgDimX, _sgDimY);
                    //ePath.insert ( std::pair<float,int>(randomValue, node1DIdx) );
                    ePath.put(Float.valueOf(randomValue),Integer.valueOf(node1DIdx));
                    //ePath[randomValue] = cnt;
                    //If closestCoords are different than current coordinate that mean there is a relocation so save the node to reinitialize
                    if (isRelocated) {
                        Vec3i nodeToBeReinitialized = new Vec3i(x,y,z);
                        allocatedNodesFromSoftData.add(nodeToBeReinitialized);
                    }
                }
            }
        }

        //Reset relocated node of soft data to NaN
        if (isRelocated) {
            for ( Vec3i ptToBeRelocated : allocatedNodesFromSoftData) {
                for ( int i=0; i<_softDataCategories.size(); i++) {
                    _softDataGrids.get(i).setElement(ptToBeRelocated,Float.NaN);
                }
            }
        }
        allocatedNodesFromSoftData.clear();

        if (_debugMode>-1) {
            System.out.println("Shuffling path, using preferential path with facEntropy="+facEntropy);
        }

        // Update simulation path
        int i=0;
        for(Object e: ePath.entrySet()){
            List  list= (List) ((Map.Entry)e).getValue();
            for(Object o: list){
                _simulationPath.set(i,(Integer) o);
                i++;
            }
        }

        if (_debugMode>1) {
            System.out.println("PATH = ");
            Vec3i tmp = new Vec3i();
            for (Integer it:_simulationPath) {
                Utility.oneDTo3D(it.intValue(), _sgDimX, _sgDimY,tmp);
                System.out.print(tmp.getX() +"," + tmp.getY()+ "," + tmp.getZ()+ "  ");
            }
            System.out.println("\n PATH END");
        }
        return true;
    }

    /**
     * @brief Start the simulation
     * Virtual function implemented from MPSAlgorithm
     */
    void startSimulation() {
        // Write license information to screen
        if (_debugMode>-2) {
            System.out.println("__________________________________________________________________________________");
            System.out.println("MPSlib: a C++ library for multiple point simulation");
            System.out.println("(c) 2015-2016 I-GIS (www.i-gis.dk) and");
            System.out.println("              Solid Earth Geophysics, Niels Bohr Institute (http://imgp.nbi.ku.dk)");
            System.out.println("This program comes with ABSOLUTELY NO WARRANTY;");
            System.out.println("This is free software, and you are welcome to redistribute it");
            System.out.println("under certain conditions. See 'COPYING.LESSER'for details.");
            System.out.println("__________________________________________________________________________________");
        }

        Random random = new Random();
        //Intitialize random seed or not
        if (_seed != 0)
            random.setSeed((long)_seed);    //same seed
        else
            random.setSeed(new Date().getTime()); //random seed

        //Get the output filename
        int found = 0;
        found = _tiFilename.lastIndexOf("/\\");
        String outputFilename = _outputDirectory + "/" + _tiFilename.substring(found+1);
        System.out.println("outputpath:" + outputFilename);

        //Doing the simulation
        double totalSecs = 0;
        long endNode, beginRealization, endRealization;
        double elapsedRealizationSecs, elapsedNodeSecs;
        int nodeEstimatedSeconds, lastProgress;
        //seconds, hours, minutes, // 后面使用对象没用到
        ArrayList<Vec3i> allocatedNodesFromHardData = new ArrayList<>(); //Using to allocate the multiple grid with closest hd values
        ArrayList<Vec3i> nodeToPutBack = new ArrayList<>(); //Using to allocate the multiple grid with closest hd values
        lastProgress = 0;
        int nodeCnt = 0, totalNodes = 0;
        int offset;   //g1DIdx用不到不要了

        for (int n=0; n<_realizationNumbers; n++) {
            beginRealization = System.currentTimeMillis();
            //Initialize the iteration count grid
            _initializeSG(_sgIterations, _sgDimX, _sgDimY, _sgDimZ, 0);
            //Initialize Simulation Grid from hard data or with NaN value
            _initializeSG(_sg, _sgDimX, _sgDimY, _sgDimZ);
            //Initialize temporary grids if debugMode is high
            if (_debugMode>1) {
                // Initialize some extra grids for extra information
                _initializeSG(_tg1, _sgDimX, _sgDimY, _sgDimZ);
                _initializeSG(_tg2, _sgDimX, _sgDimY, _sgDimZ);
            }

		/*if(!_hdg.empty()) {
		std::cout << "Initialize from hard data " << _hardDataFileNames << std::endl;
		_initializeSG(_sg, _sgDimX, _sgDimY, _sgDimZ, _hdg, std::numeric_limits<float>::quiet_NaN());
		} else {
		if (_debugMode>0) {
		std::cout << "Initialize with NaN value" << std::endl;
		}
		_initializeSG(_sg, _sgDimX, _sgDimY, _sgDimZ);
		}*/

            //Multi level grids
            for (int level = _totalGridsLevel; level>=0; level--) {
                _InitStartSimulationEachMultipleGrid(level);

                //For each space level from coarse to fine
                offset = (int)Math.pow(2, level);

                //Define a simulation path for each level
                if (_debugMode > -1) {
                    System.out.println("Define simulation path for level " + level);
                }
                _simulationPath.clear();
//                System.out.println(allocatedNodesFromHardData.size());
                nodeCnt = 0;
                totalNodes = (_sgDimX / offset) * (_sgDimY / offset) * (_sgDimZ / offset);
                for (int z=0; z<_sgDimZ; z+=offset) {
                    for (int y=0; y<_sgDimY; y+=offset) {
                        for (int x=0; x<_sgDimX; x+=offset) {
                            Vec1i vec1i = new Vec1i();
                            Utility.threeDto1D(x, y, z, _sgDimX, _sgDimY, vec1i);
                            _simulationPath.add(vec1i.getX());
                            //The relocation process happens if the current simulation grid value is still NaN
                            //Moving hard data to grid node only on coarsed level
                            if(level != 0) _fillSGfromHD(x, y, z, level, allocatedNodesFromHardData, nodeToPutBack);
                            else if(level == 0 && !_hdg.isEmpty() && Utility.isNAN(_sg.getElement(x,y,z))){
                                //Level = 0
                                //Fille the simulation node with the value from hard data grid
                                _sg.setElement(x,y,z,_hdg.getElement(x,y,z));
                            }
                            //Progression
                            if (_debugMode > -1 && !_hdg.isEmpty()) {
                                nodeCnt ++;
                                //Doing the progression
                                //Print progression on screen
                                int progress = (int)((nodeCnt / (float)totalNodes) * 100);
                                if ((progress % 10) == 0 && progress != lastProgress) { //Report every 10%
                                    lastProgress = progress;
                                    System.out.println("Relocating hard data to the simulation grid at level: " + level + " Progression (%): " + progress);
                                }
                            }
                        }
                    }
                }
                if (_debugMode > 2) {
                    IO.writeToGSLIBFile(outputFilename + "after_relocation_before_simulation" + n + "_level_" + level + ".gslib", _sg, _sgDimX, _sgDimY, _sgDimZ);
                    System.out.println("After relocation");
                    _showSG();
                }

                //System.out.println(allocatedNodesFromHardData.size());
                //Shuffle simulation path indices vector for a random path
                if (_debugMode > -1) {
                    System.out.println("Suffling simulation path using type " + _shuffleSgPath);
                }

                //Back to random path if no soft data
                if (_softDataGrids.isEmpty() && _shuffleSgPath==2) {
                    System.out.println("WARNING: no soft data found, switch to random path");
                    _shuffleSgPath = 1;
                }
                //Shuffling
                if (_shuffleSgPath==1) {
                    // random shuffling
                    Collections.shuffle(_simulationPath);
                } else if (_shuffleSgPath>1) {
                    // shuffling preferential to soft data
                    _shuffleSgPathPreferentialToSoftData(level);
                }


                //Performing the simulation
                //For each value of the path
                int progressionCnt = 0;
                totalNodes = _simulationPath.size();

                if (_debugMode > -1) {
                    System.out.println("Simulating " );
                }

                ////Cleaning the allocated data from the SG
                //_clearSGFromHD(allocatedNodesFromHardData);

                for (int ii=0; ii<_simulationPath.size(); ii++) {
                    //Get node coordinates
                    Vec3i vec3i = new Vec3i();
                    Utility.oneDTo3D(_simulationPath.get(ii), _sgDimX, _sgDimY, vec3i);
                    int SG_idxX = vec3i.getX(), SG_idxY = vec3i.getY(), SG_idxZ = vec3i.getZ();
                    //Performing simulation for non NaN value ...
                    if (Utility.isNAN(_sg.getElement(SG_idxX,SG_idxY,SG_idxZ)))
                        _sg.setElement(SG_idxX,SG_idxY,SG_idxZ,_simulate(SG_idxX, SG_idxY, SG_idxZ, level));

                    if (_debugMode > -1) {
                        //Doing the progression
                        //Print progression on screen
                        int progress = (int)((progressionCnt / (float)totalNodes) * 100);
                        progressionCnt ++;
                        if ((progress % 5) == 0 && progress != lastProgress) { //Report every 5%
                            lastProgress = progress;
                            endNode = System.currentTimeMillis();
                            elapsedNodeSecs = (double)((endNode - beginRealization)/1000);
                            nodeEstimatedSeconds = (int)((elapsedNodeSecs/(float)(progressionCnt)) * (float)(totalNodes - progressionCnt));

                            HrMnSec hrMnSec = Utility.secondsToHrMnSec(nodeEstimatedSeconds);
                            if (progress > 0) //Ignore first time that cant provide any time estimation
                                System.out.println("Level: " + level + " Progression (%): " + progress + " finish in: " + hrMnSec.hour + " h " + hrMnSec.minute +  " mn " + hrMnSec.second + " sec");
                        }
                    }
                }
                if (_debugMode > 2) {
                    IO.writeToGSLIBFile(outputFilename + "after_simulation" + n + "_level_" + level + ".gslib", _sg, _sgDimX, _sgDimY, _sgDimZ);
                    System.out.println( "After simulation");
                    _showSG();
                }

                //Cleaning the allocated data from the SG
                if(level != 0) _clearSGFromHD(allocatedNodesFromHardData, nodeToPutBack);
                if (_debugMode > 2) {
                    IO.writeToGSLIBFile(outputFilename + "after_cleaning_relocation" + n + "_level_" + level + ".gslib", _sg, _sgDimX, _sgDimY, _sgDimZ);
                    System.out.println("After cleaning relocation" );
                    _showSG();
                }

                //Printing SG out to check
                //if (level == 0 && _debugMode > -1) {


                if (_debugMode > 2) {
                    //Writting SG to file
                    IO.writeToGSLIBFile(outputFilename + "test_sg_" + n + "_level_" + level + ".gslib", _sg, _sgDimX, _sgDimY, _sgDimZ);
                }
            }

            if (_debugMode > 0) {
                _showSG();
            }

            if (_debugMode > -1) {
                endRealization = System.currentTimeMillis();
                elapsedRealizationSecs = (double)((endRealization - beginRealization) / 1000);
                totalSecs += elapsedRealizationSecs;
                System.out.println("Elapsed time (sec): " + elapsedRealizationSecs + "\t" + " total: " + totalSecs);
            }

            if (_debugMode > -2) {
                //Write result to file
                if (_debugMode > -1) {
                    System.out.println("Write simulation grid to hard drive...");
                }
                IO.writeToGSLIBFile(outputFilename + "_sg_" + n + ".gslib", _sg, _sgDimX, _sgDimY, _sgDimZ);
                IO.writeToGRD3File(outputFilename + "_sg_gs3d_" + n + ".grd3", _sg, _sgDimX, _sgDimY, _sgDimZ, _sgWorldMinX, _sgWorldMinY, _sgWorldMinZ, _sgCellSizeX, _sgCellSizeY, _sgCellSizeZ, 3);
                //MPS::io::writeToGS3DCSVFile(outputFilename + "_sg_gs3d_" + std::to_string(n) + ".csv", _sg, _sgDimX, _sgDimY, _sgDimZ, _sgWorldMinX, _sgWorldMinY, _sgWorldMinZ, _sgCellSizeX, _sgCellSizeY, _sgCellSizeZ);
                //MPS::io::writeToASCIIFile(outputFilename + "_sg_ascii" + std::to_string(n) + ".txt", _sg, _sgDimX, _sgDimY, _sgDimZ, _sgWorldMinX, _sgWorldMinY, _sgWorldMinZ, _sgCellSizeX, _sgCellSizeY, _sgCellSizeZ);
                //MPS::io::writeToGS3DCSVFile(outputFilename + "_ti_gs3d_" + std::to_string(n) + ".csv", _TI, _tiDimX, _tiDimY, _tiDimZ, _sgWorldMinX, _sgWorldMinY, _sgWorldMinZ, _sgCellSizeX, _sgCellSizeY, _sgCellSizeZ);
            }


            if (_debugMode>1) {
                //Write temporary grids to  file
                IO.writeToGSLIBFile(outputFilename + "_temp1_" + n + ".gslib", _tg1, _sgDimX, _sgDimY, _sgDimZ);
                IO.writeToGSLIBFile(outputFilename + "_temp2_" + n + ".gslib", _tg2, _sgDimX, _sgDimY, _sgDimZ);
            }


            if (_debugMode > 1) {
                //Write random path to file
//                c void writeToGSLIBFile(final String fileName, Array3Df sg, final int sgDimX, final int sgDimY, final int sgDimZ) {
//                public static void writeToGSLIBFile(final String fileName, final int[] iVector, final int dimX, final int dimY, final int dimZ) {
                IO.writeToGSLIBFile(outputFilename + "_path_" + n + ".gslib", _simulationPath, _sgDimX, _sgDimY, _sgDimZ);
            }
        }

        if (_debugMode > -1) {
            HrMnSec hrMnSec = Utility.secondsToHrMnSec((int)(totalSecs/_realizationNumbers));
            System.out.println("Total simulation time " + totalSecs + "s");
            System.out.println( "Average time for " + _realizationNumbers + " simulations (hours:minutes:seconds) : " + hrMnSec.hour + ":" + hrMnSec.minute + ":" + hrMnSec.second);
        }

        if(_debugMode > -1 ) {
            System.out.println("Number of threads: " + _numberOfThreads);
            System.out.println("Conditional points: " + _maxNeighbours);
            System.out.println( "Max iterations: " + _maxIterations);
            System.out.println("SG: " + _sgDimX + " " + _sgDimY + " " + _sgDimZ);
            System.out.println("TI: " + _tiFilename + " " + _tiDimX + " " + _tiDimY + " " + _tiDimZ + " " + _TI.getElement(0,0,0));
        }
    }

    /**
     * @brief Filling L and V vectors with data
     * @param grid the grid to search data
     * @param idxX search index X
     * @param idxY search index Y
     * @param idxZ search index Z
     * @param foundCnt how many node found
     * @param maxNeighboursLimit maximum number of neigbor nodes count
     * @param sgIdxX index X of the node in the 3D grid
     * @param sgIdxY index Y of the node in the 3D grid
     * @param sgIdxZ index Z of the node in the 3D grid
     * @param L output vector distances between a found nodes and the currrent node
     * @param V output vector values of the found nodes
     * @return true if foundCnt is greater than max neighbors allowed
     */
    boolean _addingData(final Array3Df grid, final int idxX, final int idxY, final int idxZ, Vec1i foundCnt, final int maxNeighboursLimit, final int sgIdxX, final int sgIdxY, final int sgIdxZ, ArrayList<Vec3i> L, ArrayList<Float> V) {
        if (!Utility.isNAN(grid.getElement(idxX,idxY,idxZ))){
            foundCnt._x++;
            if (foundCnt.getX() > maxNeighboursLimit) return true;
            Vec3i aCoords = new Vec3i();
            aCoords.setX(idxX - sgIdxX);
            aCoords.setY(idxY - sgIdxY);
            aCoords.setZ(idxZ - sgIdxZ);
            boolean isPresent = false;
            for (Vec3i vec3i : L) {
                if (vec3i.equals(aCoords)) {
                    isPresent = true;
                    break;
                }
            }
            if(!isPresent) {
                L.add(aCoords);
                V.add(grid.getElement(idxX,idxY,idxZ));
            }
        }
        return false;
    }

    /**
     * @brief Search data in a direction
     * @param grid the grid to search data
     * @param direction search direction (0: direction X, 1: direction Y, 2: direction Z)
     * @param idxX search index X
     * @param idxY search index Y
     * @param idxZ search index Z
     * @param foundCnt counter of found nodes
     * @param maxNeighboursLimit maximum number of neigbor nodes count
     * @param xOffset offset in X dimension of searching node
     * @param yOffset offset in Y dimension of searching node
     * @param zOffset offset in Z dimension of searching node
     * @param sgIdxX index X of the node in the 3D grid
     * @param sgIdxY index Y of the node in the 3D grid
     * @param sgIdxZ index Z of the node in the 3D grid
     * @param L output vector distances between a found nodes and the currrent node
     * @param V output vector values of the found nodes
     * @return true if foundCnt is greater than max neighbors allowed
     */
    void _searchDataInDirection(final Array3Df grid, final int direction, Vec1i idxX, Vec1i idxY, Vec1i idxZ, Vec1i foundCnt, final int maxNeighboursLimit, final int xOffset, final int yOffset, final int zOffset, final int sgIdxX, final int sgIdxY, final int sgIdxZ, ArrayList<Vec3i> L, ArrayList<Float> V) {
        if(direction == 0) { //Direction X
            for(int k=-yOffset; k<=yOffset; k++) {
                idxY.setX(sgIdxY + k);
                for(int j=-zOffset; j<=zOffset; j++) {
                    idxZ.setX(sgIdxZ + j);
                    //Adding value inside viewport only
                    if((idxX._x >= 0 && idxX._x < _sgDimX) && (idxY._x >= 0 && idxY._x < _sgDimY) && (idxZ._x >= 0 && idxZ._x < _sgDimZ)) {
                        if (_addingData(grid, idxX.getX(), idxY.getX(), idxZ.getX(), foundCnt, maxNeighboursLimit, sgIdxX, sgIdxY, sgIdxZ, L, V)) break;
                    }
                }
            }
        } else if(direction == 1) { //Direction Y
            for(int k=-xOffset+1; k<xOffset; k++) {
                idxX.setX(sgIdxX + k);
                for(int j=-zOffset+1; j<zOffset; j++) {
                    idxZ.setX(sgIdxZ + j);
                    //Adding value inside viewport only
                    if((idxX._x >= 0 && idxX._x < _sgDimX) && (idxY._x >= 0 && idxY._x < _sgDimY) && (idxZ._x >= 0 && idxZ._x < _sgDimZ)) {
                        if (_addingData(grid, idxX.getX(), idxY.getX(), idxZ.getX(), foundCnt, maxNeighboursLimit, sgIdxX, sgIdxY, sgIdxZ, L, V)) break;
                    }
                }
            }
        } else if(direction == 2) { //Direction Z
            for(int k=-xOffset+1; k<xOffset; k++) {
                idxX.setX(sgIdxX + k);
                for(int j=-yOffset+1; j<yOffset; j++) {
                    idxY.setX(sgIdxY + j);
                    //Adding value inside viewport only
                    if((idxX._x >= 0 && idxX._x < _sgDimX) && (idxY._x >= 0 && idxY._x < _sgDimY) && (idxZ._x >= 0 && idxZ._x < _sgDimZ)) {
                        if (_addingData(grid, idxX.getX(), idxY.getX(), idxZ.getX(), foundCnt, maxNeighboursLimit, sgIdxX, sgIdxY, sgIdxZ, L, V)) break;
                    }
                }
            }
        }
    }

    /**
     * @brief Searching a neighbor node using circular search and return back a vector L (distance between a found node and current node) and a vector V (value of the found node)
     * @param sgIdxX index X in the simulation grid
     * @param sgIdxY index Y in the simulation grid
     * @param sgIdxZ index Z in the simulation grid
     * @param grid the grid to search data
     * @param maxNeighboursLimit maximum number of neigbor nodes count
     * @param maxRadiusLimit maximum search radius allowed (-1 if not used any search radius limit)
     * @param L output vector distances between a found nodes and the currrent node
     * @param V output vector values of the found nodes
     */
    void _circularSearch(final int sgIdxX, final int sgIdxY, final int sgIdxZ, final Array3Df grid, final int maxNeighboursLimit, final float maxRadiusLimit, ArrayList<Vec3i> L, ArrayList<Float> V) {
        Vec1i foundCnt = new Vec1i(0);
//        int idxX, idxY, idxZ;
        Vec1i idxX = new Vec1i();
        Vec1i idxY = new Vec1i();
        Vec1i idxZ = new Vec1i();

        int xOffset, yOffset, zOffset;
        int maxXOffset = _sgDimX - 1;
        int maxYOffset = _sgDimY - 1;
        int maxZOffset = _sgDimZ - 1;

        int maxDim = Math.max(Math.max(maxXOffset,maxYOffset),maxZOffset);

        //Check center point
        if (!Utility.isNAN(grid.getElement(sgIdxX,sgIdxY,sgIdxZ))) {
            foundCnt._x ++;
            Vec3i aCoords = new Vec3i(0, 0, 0);
            L.add(aCoords);
            V.add(grid.getElement(sgIdxX,sgIdxY,sgIdxZ));
        }

        //random direction
        int randomDirection;

        for(int i=1; i<maxDim; i++) {
            //maximum neighbor count check
            if (foundCnt._x > maxNeighboursLimit) break;

            //maximum search radius check
            if (i > maxRadiusLimit && maxRadiusLimit != -1) break;

            //Initialize offset
            xOffset = yOffset = zOffset = i;

            //Get a random search direction
            Random random = new Random();
            randomDirection = random.nextInt() % 6;
            if (_debugMode > 2) {
                System.out.println("Random search directtion = " + randomDirection);
            }
            switch (randomDirection) {
                case 0 : //X Y Z
                    //direction +X
                    idxX._x = sgIdxX + xOffset;

                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -X
                    idxX._x = sgIdxX - xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Y
                    idxY._x = sgIdxY + yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Y
                    idxY._x = sgIdxY - yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Z
                    idxZ._x = sgIdxZ + zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Z
                    idxZ._x = sgIdxZ - zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);
                case 1 : //X Z Y
                    //direction +X
                    idxX._x = sgIdxX + xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -X
                    idxX._x = sgIdxX - xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Z
                    idxZ._x = sgIdxZ + zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Z
                    idxZ._x = sgIdxZ - zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Y
                    idxY._x = sgIdxY + yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Y
                    idxY._x = sgIdxY - yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);
                case 2 : //Y X Z
                    //direction +Y
                    idxY._x = sgIdxY + yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Y
                    idxY._x = sgIdxY - yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +X
                    idxX._x = sgIdxX + xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -X
                    idxX._x = sgIdxX - xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Z
                    idxZ._x = sgIdxZ + zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Z
                    idxZ._x = sgIdxZ - zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);
                case 3 : //Y Z X
                    //direction +Y
                    idxY._x = sgIdxY + yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Y
                    idxY._x = sgIdxY - yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Z
                    idxZ._x = sgIdxZ + zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Z
                    idxZ._x = sgIdxZ - zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +X
                    idxX._x = sgIdxX + xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -X
                    idxX._x = sgIdxX - xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);
                case 4 : //Z X Y
                    //direction +Z
                    idxZ._x = sgIdxZ + zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Z
                    idxZ._x = sgIdxZ - zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +X
                    idxX._x = sgIdxX + xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -X
                    idxX._x = sgIdxX - xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Y
                    idxY._x = sgIdxY + yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Y
                    idxY._x = sgIdxY - yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);
                default : //Z Y X
                    //direction +Z
                    idxZ._x = sgIdxZ + zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Z
                    idxZ._x = sgIdxZ - zOffset;
                    _searchDataInDirection(grid, 2, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +Y
                    idxY._x = sgIdxY + yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -Y
                    idxY._x = sgIdxY - yOffset;
                    _searchDataInDirection(grid, 1, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction +X
                    idxX._x = sgIdxX + xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);

                    //direction -X
                    idxX._x = sgIdxX - xOffset;
                    _searchDataInDirection(grid, 0, idxX, idxY, idxZ, foundCnt, maxNeighboursLimit, xOffset, yOffset, zOffset, sgIdxX, sgIdxY, sgIdxZ, L, V);
            }
        }
//        System.out.println( "After searching: " + L.size() + " " + V.size());
    }

}
