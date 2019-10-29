package cn.edu.cug.cs.gtl.mps;

import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.*;
import java.util.ArrayList;

public class IO {

    /**
     * @param fileName source's file name
     * @return true if filename exists
     * @brief check if file exists
     */
    public static boolean fileExist(final String fileName) {
        return File.exists(fileName);
    }

    /**
     * @param fileName   source's file name
     * @param ti         the training image
     * @param channelIdx which channel to take from the file (-1 in channelIdx means doing the average of all channel)
     * @param meanFactor result read from file will be divided to the meanFactor
     * @return true if the reading process is sucessful
     * @brief Read a GSLIB file and put data inside a training image, multiple channel supported
     */
    public static boolean readTIFromGSLIBFile(final String fileName, Array3Df ti, final int channelIdx, final float meanFactor) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            //read dimensions
            String str = bufferedReader.readLine().trim();
            String[] ss = StringUtils.split(str, " ");
            int[] dims = new int[ss.length];
            for (int i = 0; i < dims.length; ++i) {
                dims[i] = Integer.parseInt(ss[i]);
            }
            //Number of channel
            str = bufferedReader.readLine().trim();
            int numberOfChannels = Integer.parseInt(str);
            //Channel labels ...
            for (int i = 0; i < numberOfChannels; i++) {
                //skip lines
                bufferedReader.readLine();
            }
            //Initialize TI dimensions
            ti.resize(dims[0], dims[1], dims[2], false);
            //Putting data inside
            int dataCnt = 0, idxX = 0, idxY = 0, idxZ;
            float dataValue;
            str = bufferedReader.readLine();
            while (str != null && !str.isEmpty()) {
                str = str.trim();
                if (numberOfChannels == 1)
                    dataValue = Float.parseFloat(str); //only 1 channel
                else if (numberOfChannels > 1) {
                    ss = StringUtils.split(str, " ");
                    float[] data = new float[ss.length];
                    for (int i = 0; i < ss.length; ++i) {
                        data[i] = Float.parseFloat(ss[i]);
                    }
                    if (channelIdx == -1) { //Average
                        dataValue = 0;
                        for (int i = 0; i < data.length; i++) {
                            dataValue += data[i];
                        }
                        dataValue /= data.length;
                    } else {
                        dataValue = data[channelIdx];
                    }
                } else {
                    dataValue = 0; //Put 0 if get no channel
                }

                //mps.Utility::oneDTo3D(dataCnt, dimensions[0], dimensions[1], idxX, idxY, idxZ);
                //ti[idxZ][idxY][idxX] = dataValue / meanFactor;
                ti.setElement(dataCnt, dataValue / meanFactor);
                dataCnt++;
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean readTIFromGSLIBFile(final String fileName, Array3Df ti, final int channelIdx) {
        return readTIFromGSLIBFile(fileName, ti, channelIdx, 1);
    }

    public static boolean readTIFromGSLIBFile(final String fileName, Array3Df ti) {
        return readTIFromGSLIBFile(fileName, ti, 0, 1);
    }

    /**
     * @param fileName source's file name
     * @param ti       the training image
     * @return true if the reading process is sucessful
     * @brief Read a GS3D csv file and put data inside a training image
     */
    public static boolean readTIFromGS3DCSVFile(final String fileName, Array3Df ti) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            int tiSizeX, tiSizeY, tiSizeZ;
            float minWorldX, minWorldY, minWorldZ;
            float stepX, stepY, stepZ;

            String str = null;
            //voxel size
            //X
            str = bufferedReader.readLine().trim();
            tiSizeX = Integer.parseInt(str);
            //Y
            str = bufferedReader.readLine().trim();
            tiSizeY = Integer.parseInt(str);
            //Z
            str = bufferedReader.readLine().trim();
            tiSizeZ = Integer.parseInt(str);
            //Min world coordinates
            //X
            str = bufferedReader.readLine().trim();
            minWorldX = Integer.parseInt(str);
            //Y
            str = bufferedReader.readLine().trim();
            minWorldY = Integer.parseInt(str);
            //Z
            str = bufferedReader.readLine().trim();
            minWorldZ = Integer.parseInt(str);
            //Voxel size
            //X
            str = bufferedReader.readLine().trim();
            stepX = Integer.parseInt(str);
            //Y
            str = bufferedReader.readLine().trim();
            stepY = Integer.parseInt(str);
            //Z
            str = bufferedReader.readLine().trim();
            stepZ = Integer.parseInt(str);

            //Data
            //Initialize TI dimensions, data cell is initialize to nan
            ti.resize(tiSizeX, tiSizeY, tiSizeZ);

            //Putting data inside
            int idxX = 0, idxY = 0, idxZ;
            float coordX, coordY, coordZ, dataValue;
            str = bufferedReader.readLine().trim();
            while (!str.isEmpty()) {
                //Read a line and keep values into vector data
                String[] ss = StringUtils.split(str, ";");
                float[] data = new float[ss.length];
                for (int i = 0; i < ss.length; ++i) {
                    data[i] = Float.parseFloat(ss[i]);
                }

                //Convert data read from world coordinate into local coordinate (start from 0, 0, 0 and cell size 1, 1, 1)
                coordX = data[0];
                coordY = data[1];
                coordZ = data[2];
                dataValue = data[3];
                idxX = (int) ((coordX - minWorldX) / stepX);
                idxY = (int) ((coordY - minWorldY) / stepY);
                idxZ = (int) ((coordZ - minWorldZ) / stepZ);
                //ti[idxZ][idxY][idxX] = dataValue;
                ti.setElement(idxX, idxY, idxZ, dataValue);
                str = bufferedReader.readLine().trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @param fileName source's file name
     * @param ti       the training image
     * @return true if the reading process is sucessful
     * @brief Read a GS3D grd3 file and put data inside a training image
     */
    public static boolean readTIFromGS3DGRD3File(final String fileName, Array3Df ti) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileName));

            int tiSizeX, tiSizeY, tiSizeZ;
            double minWorldX, minWorldY, minWorldZ;
            double stepX, stepY, stepZ;
            double blankValue;
            byte[] buff = new byte[8];
            int valueType;
            int[] arrayValueSize = {4, 8, 1, 2};
            //ID
            dataInputStream.readInt();//4
            //Version
            dataInputStream.readInt();//4
            //HeaderSize
            dataInputStream.readInt();
            //Grid value type
            valueType = dataInputStream.readInt();
            //Blank value
            blankValue = dataInputStream.readDouble();
            //Node X
            tiSizeX = dataInputStream.readInt();
            //Node Y
            tiSizeY = dataInputStream.readInt();
            //Node Z
            tiSizeZ = dataInputStream.readInt();
            //Min X
            minWorldX = dataInputStream.readDouble();
            //Min Y
            minWorldY = dataInputStream.readDouble();
            //Min Z
            minWorldZ = dataInputStream.readDouble();
            //Size X
            stepX = dataInputStream.readDouble();
            //Size Y
            stepY = dataInputStream.readDouble();
            //Size Z
            stepZ = dataInputStream.readDouble();
            //Min Value
            dataInputStream.readDouble();
            //Max Value
            dataInputStream.readDouble();

            //Data
            //Initialize TI dimensions, data cell is initialize to nan
            ti.resize(tiSizeX, tiSizeY, tiSizeZ);

            //Putting data inside
            int valueSize = arrayValueSize[valueType];
            byte dataChar;
            short dataShort;
            float dataSingle;
            double dataDouble;
            for (int z = 0; z < tiSizeZ; z++) {
                for (int y = 0; y < tiSizeY; y++) {
                    for (int x = 0; x < tiSizeX; x++) {
                        if (valueSize == 1) {
                            dataChar = dataInputStream.readByte();
                            if ((double) dataChar != blankValue && (float) dataChar >= 0)
                                ti.setElement(x, y, z, (float) dataChar);
                        } else if (valueSize == 2) {
                            dataShort = dataInputStream.readShort();
                            if ((double) dataShort != blankValue && (float) dataShort >= 0)
                                ti.setElement(x, y, z, (float) dataShort);
                        } else if (valueSize == 4) {
                            dataSingle = dataInputStream.readFloat();
                            if ((double) dataSingle != blankValue && (float) dataSingle >= 0)
                                ti.setElement(x, y, z, (float) dataSingle);
                        } else {
                            dataDouble = dataInputStream.readDouble();
                            if ((double) dataDouble != blankValue && (float) dataDouble >= 0)
                                ti.setElement(x, y, z, (float) dataDouble);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @param fileName  source's file name
     * @param dataSizeX grid size X
     * @param dataSizeY grid size Y
     * @param dataSizeZ grid size Z
     * @param minWorldX min world coordinate X
     * @param minWorldY min world coordinate Y
     * @param minWorldZ min world coordinate Z
     * @param stepX     cell size X
     * @param stepY     cell size Y
     * @param stepZ     cell size Z
     * @param data      reading result
     * @return true if the reading process is sucessful
     * @brief Read a EAS file and put in hard data grid
     */
    public static boolean readHardDataFromEASFile(final String fileName, final float noDataValue,
                                                  final int dataSizeX, final int dataSizeY, final int dataSizeZ,
                                                  final float minWorldX, final float minWorldY, final float minWorldZ,
                                                  final float stepX, final float stepY, final float stepZ, Array3Df data) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            //TITLE
            String str = bufferedReader.readLine();
            //Number of column
            int numberOfColumns;
            str = bufferedReader.readLine().trim();
            numberOfColumns = Integer.parseInt(str);
            //Column title
            for (int i = 0; i < numberOfColumns; i++) {
                str = bufferedReader.readLine();
            }
            //Data
            //Initialize TI dimensions, data cell is initialize to nan
            data.resize(dataSizeX, dataSizeY, dataSizeZ, Float.NaN);
            //Putting data inside
            int dataCnt = 0, idxX = 0, idxY = 0, idxZ = 0;
            Vec3i idx = new Vec3i();
            float coordX, coordY, coordZ, dataValue;
            str = bufferedReader.readLine().trim();
            while (!str.isEmpty()) {
                //Read a line and keep values into vector data
                String[] ss = StringUtils.split(str, " ");
                float[] lineData = new float[ss.length];
                for (int i = 0; i < ss.length; ++i)
                    lineData[i] = Float.parseFloat(ss[i]);

                if (numberOfColumns >= 4) { //XYZD file
                    //Convert data read from world coordinate into local coordinate (start from 0, 0, 0 and cell size 1, 1, 1)
                    coordX = lineData[0];
                    coordY = lineData[1];
                    coordZ = lineData[2];
                    dataValue = lineData[3];
                    idxX = (int) ((coordX - minWorldX) / stepX);
                    idxY = (int) ((coordY - minWorldY) / stepY);
                    idxZ = (int) ((coordZ - minWorldZ) / stepZ);
                    //_sgDimX
                    if (dataValue != noDataValue) {
                        if ((idxX > -1) & (idxY > -1) & (idxZ > -1) & (idxX < dataSizeX) & (idxY < dataSizeY) & (idxZ < dataSizeZ)) {
                            //data[idxZ][idxY][idxX] = dataValue;
                            data.setElement(idxX, idxY, idxZ, dataValue);
                        } else {
                            System.out.println("Hard data, " + fileName + ": Data outside simualtion grid ix,iy,iz=" + idxX + " " + idxY + " " + idxZ);
                        }
                    }
                } else { //Single column file
                    Utility.oneDTo3D(dataCnt++, dataSizeX, dataSizeY, idx);
                    idxX = idx.getX();
                    idxY = idx.getY();
                    idxZ = idx.getZ();
                    dataValue = lineData[0];
                    if (dataValue != noDataValue)
                        data.setElement(idxX, idxY, idxZ, dataValue);//data[idxZ][idxY][idxX] = dataValue;
                }

                str = bufferedReader.readLine().trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param fileName   source's file name
     * @param categories vector of available categories
     * @param dataSizeX  grid size X
     * @param dataSizeY  grid size Y
     * @param dataSizeZ  grid size Z
     * @param minWorldX  min world coordinate X
     * @param minWorldY  min world coordinate Y
     * @param minWorldZ  min world coordinate Z
     * @param stepX      cell size X
     * @param stepY      cell size Y
     * @param stepZ      cell size Z
     * @param data       reading result
     * @return true if the reading process is sucessful
     * @brief Read a EAS file and put in soft data grid
     */
    public static boolean readSoftDataFromEASFile(final String fileName, final ArrayList<Float> categories,
                                                  final int dataSizeX, final int dataSizeY, final int dataSizeZ,
                                                  final float minWorldX, final float minWorldY, final float minWorldZ,
                                                  final float stepX, final float stepY, final float stepZ,
                                                  ArrayList<Array3Df> data) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            //TITLE
            String str = bufferedReader.readLine();
            //Number of column
            int numberOfColumns;
            str = bufferedReader.readLine().trim();
            numberOfColumns = Integer.parseInt(str);
            //Column title
            for (int i = 0; i < numberOfColumns; i++) {
                str = bufferedReader.readLine();
            }

            //Data
            //Initialize softdata dimensions, data cell is initialize to nan
            data.ensureCapacity(categories.size());
            for (int nbCats = 0; nbCats < categories.size(); nbCats++) {
                data.get(nbCats).resize(dataSizeX, dataSizeY, dataSizeZ, Float.NaN);
            }

            //Putting data inside
            int dataCnt = 0, idxX = 0, idxY = 0, idxZ;
            Vec3i idx = new Vec3i();
            float coordX, coordY, coordZ;
            str = bufferedReader.readLine();
            while (str != null && !str.isEmpty()) {
                str = str.trim();
                //Read a line and keep values into vector data
                String[] ss = StringUtils.split(str, " ");
                float[] lineData = new float[ss.length];
                for (int i = 0; i < ss.length; ++i)
                    lineData[i] = Float.parseFloat(ss[i]);

                if (numberOfColumns > categories.size()) { //XYZD file
                    //Convert data read from world coordinate into local coordinate (start from 0, 0, 0 and cell size 1, 1, 1)
                    coordX = lineData[0];
                    coordY = lineData[1];
                    coordZ = lineData[2];
                    idxX = (int) ((coordX - minWorldX) / stepX);
                    idxY = (int) ((coordY - minWorldY) / stepY);
                    idxZ = (int) ((coordZ - minWorldZ) / stepZ);
                    for (int nbCats = 0; nbCats < categories.size(); nbCats++) {
                        if ((idxX > -1) & (idxY > -1) & (idxZ > -1) & (idxX < dataSizeX) & (idxY < dataSizeY) & (idxZ < dataSizeZ)) {
                            //data[nbCats][idxZ][idxY][idxX] = lineData[nbCats + 3];
                            data.get(nbCats).setElement(idxX, idxY, idxZ, lineData[nbCats + 3]);
                        } else {
                            System.out.println("Soft data, " + fileName + ": Data outside simualtion grid ix,iy,iz=" + idxX + " " + idxY + " " + idxZ);
                        }
                    }
                } else { //Single column file
                    Utility.oneDTo3D(dataCnt++, dataSizeX, dataSizeY, idx);
                    idxX = idx.getX();
                    idxY = idx.getY();
                    idxZ = idx.getZ();
                    for (int nbCats = 0; nbCats < categories.size(); nbCats++) {
                        data.get(nbCats).setElement(idxX, idxY, idxZ, lineData[nbCats]);
                    }
                }
                str = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * @param fileName destination's file name
     * @param sg       the simulation grid which is a 3D float vector
     * @param sgDimX   dimension X of SG
     * @param sgDimY   dimension Y of SG
     * @param sgDimZ   dimension Z of SG
     * @brief Write simulation 3D grid result into file
     */
    public static void writeToGSLIBFile(final String fileName, Array3Df sg, final int sgDimX, final int sgDimY, final int sgDimZ) {
        try {
            //这个以后需要修改
            String filename = fileName;
            filename = fileName.substring(2);
            System.out.println("filename123" + filename);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            // Header
            bufferedWriter.write(sgDimX + " " + sgDimY + " " + sgDimZ + "\r\n");
            bufferedWriter.write(1 + "\r\n");
            bufferedWriter.write("v" + "\r\n");
            for (int z = 0; z < sgDimZ; z++) {
                for (int y = 0; y < sgDimY; y++) {
                    for (int x = 0; x < sgDimX; x++) {
                        bufferedWriter.write(sg.getElement(x, y, z) + "\r\n");
                    }
                }
            }
            // Close the file stream explicitly
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileName destination's file name
     * @param iVector  the index array which is a integer vector
     * @param dimX     dimension X of iVector
     * @param dimY     dimension Y of iVector
     * @param dimZ     dimension Z of iVector
     * @brief Write indices array into a file
     */
    public static void writeToGSLIBFile(final String fileName, final ArrayList<Integer> iVector, final int dimX, final int dimY, final int dimZ) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            // Header
            bufferedWriter.write(dimX + " " + dimY + " " + dimZ + "\r\n");
            bufferedWriter.write(1 + "\r\n");
            bufferedWriter.write("v" + "\r\n");
            int cnt = 0;
            for (int z = 0; z < dimZ; z++) {
                for (int y = 0; y < dimY; y++) {
                    for (int x = 0; x < dimX; x++) {
                        bufferedWriter.write(iVector.get(cnt++) + "\r\n");
                    }
                }
            }
            // Close the file stream explicitly
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName  destination's file name
     * @param sg        the simulation grid which is a 3D float vector
     * @param sgDimX    dimension X of SG
     * @param sgDimY    dimension Y of SG
     * @param sgDimZ    dimension Z of SG
     * @param minWorldX minimal GeoGraphic X coordinate of the TI
     * @param minWorldY minimal GeoGraphic Y coordinate of the TI
     * @param minWorldZ minimal GeoGraphic Z coordinate of the TI
     * @param stepX     voxel size in X dimension
     * @param stepY     voxel size in Y dimension
     * @param stepZ     voxel size in Z dimension
     * @brief Write simulation 3D grid result into GS3D csv file
     */
    public static void writeToGS3DCSVFile(final String fileName, final Array3Df sg, final int sgDimX, final int sgDimY,
                                          final int sgDimZ, final float minWorldX, final float minWorldY, final float minWorldZ,
                                          final float stepX, final float stepY, final float stepZ) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            // Header
            bufferedWriter.write("X;Y;Z;Value" + "\r\n");
            for (int z = 0; z < sgDimZ; z++) {
                for (int y = 0; y < sgDimY; y++) {
                    for (int x = 0; x < sgDimX; x++) {
                        bufferedWriter.write((x * stepX + minWorldX) + ";" + (y * stepY + minWorldY) + ";" + (z * stepZ + minWorldZ) + ";"
                                + sg.getElement(x, y, z) + "\r\n");
                    }
                }
            }
            // Close the file stream explicitly
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName  destination's file name
     * @param sg        the simulation grid which is a 3D float vector
     * @param sgDimX    dimension X of SG
     * @param sgDimY    dimension Y of SG
     * @param sgDimZ    dimension Z of SG
     * @param minWorldX minimal GeoGraphic X coordinate of the TI
     * @param minWorldY minimal GeoGraphic Y coordinate of the TI
     * @param minWorldZ minimal GeoGraphic Z coordinate of the TI
     * @param stepX     voxel size in X dimension
     * @param stepY     voxel size in Y dimension
     * @param stepZ     voxel size in Z dimension
     * @brief Write simulation 3D grid result into an ASCII file
     */
    public static void writeToASCIIFile(final String fileName, final Array3Df sg,
                                        final int sgDimX, final int sgDimY, final int sgDimZ,
                                        final float minWorldX, final float minWorldY, final float minWorldZ,
                                        final float stepX, final float stepY, final float stepZ) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            // Header

            bufferedWriter.write(sgDimX + "\r\n");
            bufferedWriter.write(sgDimY + "\r\n");
            bufferedWriter.write(sgDimZ + "\r\n");
            bufferedWriter.write(minWorldX + "\r\n");
            bufferedWriter.write(minWorldY + "\r\n");
            bufferedWriter.write(minWorldZ + "\r\n");
            bufferedWriter.write(stepX + "\r\n");
            bufferedWriter.write(stepY + "\r\n");
            bufferedWriter.write(stepZ + "\r\n");
            for (int z = 0; z < sgDimZ; z++) {
                for (int y = 0; y < sgDimY; y++) {
                    for (int x = 0; x < sgDimX; x++) {
                        bufferedWriter.write((x * stepX + minWorldX) + ";" + (y * stepY + minWorldY) + ";" + (z * stepZ + minWorldZ) + ";" + sg.getElement(x, y, z) + "\r\n");
                    }
                }
            }
            // Close the file stream explicitly
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName  destination's file name
     * @param sg        the simulation grid which is a 3D float vector
     * @param sgDimX    dimension X of SG
     * @param sgDimY    dimension Y of SG
     * @param sgDimZ    dimension Z of SG
     * @param minWorldX minimal GeoGraphic X coordinate of the TI
     * @param minWorldY minimal GeoGraphic Y coordinate of the TI
     * @param minWorldZ minimal GeoGraphic Z coordinate of the TI
     * @param stepX     voxel size in X dimension
     * @param stepY     voxel size in Y dimension
     * @param stepZ     voxel size in Z dimension
     * @param valueType type of data 0:4byte 1:8byte 2:1byte 3:2byte
     * @brief Write simulation 3D grid result into a GS3D grd3 file
     */
    public static void writeToGRD3File(final String fileName, final Array3Df sg,
                                       final int sgDimX, final int sgDimY, final int sgDimZ,
                                       final float minWorldX, final float minWorldY, final float minWorldZ,
                                       final float stepX, final float stepY, final float stepZ,
                                       final int valueType) {
        try {
            //这个以后需要修改
            String filename = fileName;
            filename = fileName.substring(2);
            System.out.println("filename1111" + filename);
            DataOutputStream d = new DataOutputStream(new FileOutputStream(filename, true));
            //ID
            char id[] = {'G', 'R', 'D', '3'};
            byte[] idbytes = new byte[4];
            for (int i = 0; i < id.length; ++i) {
                idbytes[i] = (byte) (id[i]);
            }
            d.write(idbytes);

            //Version
            int version = 1;
            d.writeInt(version);
            //HeaderSize
            int headerSize = 100;
            d.writeInt(headerSize);
            //Grid value type
            d.writeInt(valueType);
            //Blank value
            double blankValue = Float.NaN;
            d.writeDouble(blankValue);
            //Node X
            d.writeInt(sgDimX);
            //Node Y
            d.writeInt(sgDimY);
            //Node Z
            d.writeInt(sgDimZ);
            //Min X
            double dMinWorldX = (double) minWorldX;
            d.writeDouble(dMinWorldX);
            //Min Y
            double dMinWorldY = (double) minWorldY;
            d.writeDouble(dMinWorldY);
            //Min Z
            double dMinWorldZ = (double) minWorldZ;
            d.writeDouble(dMinWorldZ);
            //Size X
            double dStepX = (double) stepX;
            d.writeDouble(dStepX);
            //Size Y
            double dStepY = (double) stepY;
            d.writeDouble(dStepY);
            //Size Z
            double dStepZ = (double) stepZ;
            d.writeDouble(dStepZ);

            //Get min/max value
            //TODO this can be done faster by passing through parameters ...
            float min = Integer.MIN_VALUE;
            float max = Integer.MAX_VALUE;
            float tmp = Float.NaN;
            for (int z = 0; z < sgDimZ; z++) {
                for (int y = 0; y < sgDimY; y++) {
                    for (int x = 0; x < sgDimX; x++) {
                        tmp = sg.getElement(x, y, z);
                        min = tmp < min && !Utility.isNAN(tmp) ? tmp : min;
                        max = tmp > max && !Utility.isNAN(tmp) ? tmp : max;
                    }
                }
            }

            //Min Value
            double minValue = min;
            d.writeDouble(minValue);
            //Max Value
            double maxValue = max;
            d.writeDouble(maxValue);

            //Data
            //Putting data inside
            int arrayValueSize[] = {4, 8, 1, 2};
            int valueSize = arrayValueSize[valueType];
            double dataDouble;
            int dataInt;
            short dataShort;
            byte dataChar;
            for (int z = 0; z < sgDimZ; z++) {
                for (int y = 0; y < sgDimY; y++) {
                    for (int x = 0; x < sgDimX; x++) {
                        tmp = sg.getElement(x, y, z);
                        if (valueSize == 1) {
                            dataChar = (byte) tmp;
                            d.write(dataChar);
                        } else if (valueSize == 2) {
                            dataShort = (short) tmp;
                            d.writeShort(dataShort);
                        } else if (valueSize == 4) {
                            dataInt = (int) tmp;
                            d.writeInt(dataInt);
                        } else {
                            dataDouble = (double) tmp;
                            d.writeDouble(dataDouble);
                        }
                    }
                }
            }

            // Close the file stream explicitly
            d.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
