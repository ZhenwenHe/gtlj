package cn.edu.cug.cs.gtl.mps;

import java.util.Random;

public class Utility {

    /**
     * @param x value to check
     * @return true if the input value is NAN
     * @brief Check if a value is NAN
     */
    public static boolean isNAN(final float x) {
        return Float.isNaN(x);
    }

    /**
     * @param oneDIndex index of the 1D array
     * @param dimX      dimension X of the 3D array
     * @param dimY      dimension Y of the 3D array
     * @param idx       output index X,Y,Z
     * @brief Convert 1D index to 3D index
     */
    public static void oneDTo3D(final int oneDIndex, final int dimX, final int dimY, Vec3i idx) {
        idx._z = oneDIndex / (dimX * dimY);
        idx._y = (oneDIndex - idx._z * dimX * dimY) / dimX;
        idx._x = oneDIndex - dimX * (idx._y + dimY * idx._z);
    }

    public static Vec3i oneDTo3D(final int oneDIndex, final int dimX, final int dimY) {
        int idxZ = oneDIndex / (dimX * dimY);
        int idxY = (oneDIndex - idxZ * dimX * dimY) / dimX;
        int idxX = oneDIndex - dimX * (idxY + dimY * idxZ);
        return new Vec3i(idxX, idxY, idxZ);
    }


    /**
     * @param idxX      index X of the 3D array
     * @param idxY      index Y of the 3D array
     * @param idxZ      index Z of the 3D arary
     * @param dimX      dimension X of the 3D array
     * @param dimY      dimension Y of the 3D array
     * @param oneDIndex output index of the 1D array
     * @brief Convert 3D index to 1D index
     */

    public static void threeDto1D(final int idxX, final int idxY, final int idxZ, final int dimX, final int dimY, Vec1i oneDIndex) {
        oneDIndex._x = idxX + dimX * (idxY + idxZ * dimY);
    }

    public static int threeDto1D(final int idxX, final int idxY, final int idxZ, final int dimX, final int dimY) {
        int oneDIndex = idxX + dimX * (idxY + idxZ * dimY);
        return oneDIndex;
    }

    /**
     * @param seconds seconds
     * @param hrMnSec output hour minute second
     * @brief Convert seconds to hour minute second
     */
    public static void secondsToHrMnSec(final int seconds, HrMnSec hrMnSec) {
        hrMnSec.minute = seconds / 60;
        hrMnSec.second = seconds % 60;
        hrMnSec.hour = hrMnSec.minute / 60;
        hrMnSec.minute = hrMnSec.minute % 60;
    }

    public static HrMnSec secondsToHrMnSec(final int seconds) {
        HrMnSec hrMnSec = new HrMnSec();
        hrMnSec.minute = seconds / 60;
        hrMnSec.second = seconds % 60;
        hrMnSec.hour = hrMnSec.minute / 60;
        hrMnSec.minute = hrMnSec.minute % 60;
        return hrMnSec;
    }

    /**
     * @param inValue    input value
     * @param maxValue   the maximum value that aValue could have
     * @param wrapAround if true then if aValue is greater than maxValue, it will be modulo with the maxValue. If false the aValue will be clamp to maxValue
     * @brief Limit a value, could be clamp it or wrap around
     */
    public static int limitValue(final int inValue, final int maxValue, final boolean wrapAround) {
        int aValue = inValue;
        if (wrapAround) {
            while (aValue < 0) aValue = maxValue + aValue;
            aValue = aValue % maxValue;
        } else {//Clamp
            if (aValue > (maxValue - 1)) aValue = maxValue - 1;
            else if (aValue < 0) aValue = 0;
        }
        return aValue;
    }

    /**
     * @param ti     the 3D grid
     * @param tiDimX grid size X
     * @param tiDimY grid size Y
     * @param tiDimZ grid size Z
     * @return a random value from the grid
     * @brief Get a random value from a voxel grid
     */
    public static float getRandomValue(final float[][][] ti, final int tiDimX, final int tiDimY, final int tiDimZ) {
        Random r = new java.util.Random();
        int tiRandomX, tiRandomY, tiRandomZ;
        tiRandomX = (r.nextInt() % (int) (tiDimX));
        tiRandomY = (r.nextInt() % (int) (tiDimY));
        tiRandomZ = (r.nextInt() % (int) (tiDimZ));
        return ti[tiRandomZ][tiRandomY][tiRandomX];
    }

    /**
     * @param filename the file name
     * @return filename extension
     * @brief Get a file extension
     */
    public static String getExtension(final String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
