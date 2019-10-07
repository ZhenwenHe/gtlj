package gtl.series.common.hax;

import gtl.series.common.MultiSeries;
import gtl.series.common.TimeSeries;
import gtl.series.dimensionality.DimensionalityUtils;
import gtl.series.distances.DistanceUtils;

import static java.lang.Math.sqrt;

public class Utils {
  /**
   * @brief Hexdecimal Aggregate approXimation (HAX). It transforms a numeric time series into a
   *     time series of hexdecimal numbers. The algorithm was proposed by Zhenwen He et al. and
   *     extends the PAA-based approach inheriting the original algorithm simplicity and low
   *     computational complexity while providing satisfactory sensitivity and selectivity in range
   *     query processing.
   * @param a Array with the input time series. it should be a n x 2 array; n is the length of the
   *     time series col(0) is the x-axis values col(1) is the y-axis values
   * @param w the total number of divisions.
   * @return result An array of hexdecimal numbers index [0x0,0xf].
   */
  public static byte[] hax(TimeSeries a, long w) {
    return null;
  }

  public static void linear(double[] xss, double[] yss, double[] slope, double[] intercept) {
    // TODO  linear
    long n = xss.length;
    // To compute the mean value of xss and yss
    double meanX = 0.0;
    double meanY = 0.0;
    for (int i = 0; i < n; ++i) {
      meanX += xss[i];
      meanY += yss[i];
    }
    meanX = meanX / n;
    meanY = meanY / n;

    double[] sumSquares = new double[2 * 2];

    // Assuming xss and yss contain the same number of time series
    // To compute the sumSquares
    //        sumSquares(af::span, af::span, i) =
    //                khiva::statistics::covariance(af::join(1, xss(af::span, i), yss(af::span,
    // i)));

    //
    //        double ssxm = sumSquares[0];
    //        double ssxym = sumSquares[1];
    //        double ssyxm = sumSquares[2];
    //        double ssym = sumSquares[3];

  }

  public static void hax_piecewise_stat(
      double[] columnX,
      double[] columnY,
      double start,
      double end,
      double[] avgX,
      double[] avgY,
      double[] angle) {
    // to compute avgX,avgY,avgA;

    int count = 0;
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = -minX;
    double maxY = -minY;

    int i_min_y = (int) start;
    int i_max_y = (int) start;
    // Compute avg for this segment
    for (int k = (int) start; k <= end; ++k) {
      minX = Math.min(minX, columnX[k]);
      maxX = Math.max(maxX, columnX[k]);
      avgX[0] += columnX[k];
      // 我认为下面两行应该注释掉
      //            minY = Math.min(minY,columnY[k]);
      //            maxY = Math.max(maxY,columnY[k]);

      if (minY - columnY[k] > 0) {
        minY = columnY[k];
        i_min_y = k;
      }
      if (maxY - columnY[k] < 0) {
        maxY = columnY[k];
        i_max_y = k;
      }
      avgY[0] = avgY[0] + columnY[k];
      count++;
    }
    avgX[0] = avgX[0] / count;
    avgY[0] = avgY[0] / count;

    // judge the fitting line angle
    double dx = 0.0, dy = 0.0;
    int i_mid = (int) ((start + end) / 2.0);
    if (i_max_y < i_mid && i_min_y > i_mid) {
      dx = maxX - minX;
      dy = minY - maxY;
    } else if (i_max_y > i_mid && i_min_y < i_mid) {
      dx = maxX - minX;
      dy = maxY - minY;
    } else if (i_max_y < i_mid && i_min_y < i_mid) {
      if (i_min_y > i_max_y) {
        dx = maxX - minX;
        dy = minY - maxY;
      } else {
        dx = maxX - minX;
        dy = maxY - minY;
      }
    } else if (i_max_y > i_mid && i_min_y > i_mid) {
      if (i_min_y > i_max_y) {
        dx = maxX - minX;
        dy = minY - maxY;
      } else {
        dx = maxX - minX;
        dy = maxY - minY;
      }
    } else { // TODO:linear regression
      // 涉及到regression::linear
      double[] xss = new double[(int) (end - start + 1)];
      double[] yss = new double[(int) (end - start + 1)];
      for (int i = 0; i < end - start + 1; ++i) {
        xss[i] = columnX[(int) (start + i)];
        yss[i] = columnY[(int) (start + i)];
      }

      double[] slope = new double[1];
      double[] intercept = new double[1];
      double slope_host, intercept_host;
      linear(xss, yss, slope, intercept);
      slope_host = slope[1];
      intercept_host = intercept[1];

      if (slope_host > 0) {
        dx = maxX - minX;
        dy = maxY - minY;
      } else {
        dx = maxX - minX;
        dy = minY - maxY;
      }
    }
    angle[0] = Math.atan2(dx, dy);
  }

  public static char hax_lookup_table(double minY, double maxY, double avg, double angle) {

    double y = (avg - minY) / (maxY - minY);
    double a = 0.5 + angle / Math.PI;

    int row = (int) (4 * y);
    int col = (int) (4 * a);

    row = row > 3 ? 3 : row;
    col = col > 3 ? 3 : col;
    char index = (char) (row * 4 + col);
    return index;
  }

  public static char[] hax(double[] x, double[] y, int n, int w) {
    double[] reducedColumnX = new double[w];
    double[] reducedColumnY = new double[w];
    double[] reducedColumnA = new double[w];

    double elemPerW = Integer.valueOf(n).doubleValue() / Integer.valueOf(w).doubleValue();
    double minX = Double.MAX_VALUE,
        minY = Double.MAX_VALUE,
        maxX = Double.MIN_VALUE,
        maxY = Double.MIN_VALUE;

    for (int i = 0; i < n; ++i) {
      if (minX > x[i]) minX = x[i];
      if (maxX < x[i]) maxX = x[i];
      if (minY > x[i]) minY = x[i];
      if (maxY < x[i]) maxY = x[i];
    }

    double start = 0.0;
    double end = elemPerW - 1;

    char[] reducedColumn = new char[w];
    // for each column
    for (int j = 0; j < w; ++j) {
      double[] avgX = new double[1];
      double[] avgY = new double[1];
      double[] angle = new double[1];

      avgX[0] = 0.0;
      avgY[0] = 0.0;
      angle[0] = 0.0;

      hax_piecewise_stat(x, y, start, end, avgX, avgY, angle);
      reducedColumnX[j] = avgX[0];
      reducedColumnY[j] = avgY[0];
      reducedColumnA[j] = angle[0];

      reducedColumn[j] = hax_lookup_table(minY, maxY, avgY[0], angle[0]);

      // Compute next segment
      start = Math.ceil(end);
      end = end + elemPerW;
      end = (end > n) ? n : end;
    }
    return reducedColumn;
  }

  /**
   * @brief calculate the hax distance between two time series a and b
   * @param a Array with the input time series. it should be a n x 2 array; n is the length of the
   *     time series col(0) is the x-axis values col(1) is the y-axis values
   * @param b Array with the input time series. it should be a n x 2 array; n is the length of the
   *     time series col(0) is the x-axis values col(1) is the y-axis values
   * @param w the total number of divisions.
   * @return result An array of hexdecimal numbers index [0x0,0xf].
   */
  static double[] hax_distance_table = new double[512];

  static boolean first = true;

  public static int[] hax_to_row(byte h, int row, int col) {
    int[] a = new int[2];
    a[0] = row = h / 4;
    a[1] = h - row * 4;
    return a;
  }

  public static final double[] generate_distance_table() {
    if (first == false) return hax_distance_table;
    int i_row = 0, i_col = 0, j_row = 0, j_col = 0;
    for (byte i = 0; i < 16; i++) {
      for (byte j = 0; j < 16; j++) {
        int[] a = hax_to_row(i, i_row, i_col);
        int[] b = hax_to_row(j, j_row, j_col);
        i_row = a[0];
        i_col = a[1];
        j_row = b[0];
        j_col = b[1];
        hax_distance_table[i * 16 + j] =
            (i_row - j_row) * (i_row - j_row) + (i_col - j_col) * (i_col - j_col);
        hax_distance_table[i * 16 + j] = sqrt(hax_distance_table[i * 16 + j]);
      }
    }
    first = false;
    return hax_distance_table;
  }

  public static double get_hax_distance(byte c1, byte c2) {
    if (first == true) generate_distance_table();

    return hax_distance_table[c1 * 16 + c2];
  }

  public static double hax_distance(byte[] a, byte[] b, long len) {
    double dis = 0.0;
    for (int i = 0; i < len; ++i) {
      dis += get_hax_distance((a[i]), b[i]);
    }
    return dis;
  }



}