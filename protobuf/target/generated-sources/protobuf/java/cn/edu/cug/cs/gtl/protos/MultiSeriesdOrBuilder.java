// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/series.proto

package cn.edu.cug.cs.gtl.protos;

public interface MultiSeriesdOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.MultiSeriesd)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated double time = 1;</code>
   * @return A list containing the time.
   */
  java.util.List<java.lang.Double> getTimeList();
  /**
   * <code>repeated double time = 1;</code>
   * @return The count of time.
   */
  int getTimeCount();
  /**
   * <code>repeated double time = 1;</code>
   * @param index The index of the element to return.
   * @return The time at the given index.
   */
  double getTime(int index);

  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesd series = 2;</code>
   */
  java.util.List<cn.edu.cug.cs.gtl.protos.Seriesd> 
      getSeriesList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesd series = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.Seriesd getSeries(int index);
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesd series = 2;</code>
   */
  int getSeriesCount();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesd series = 2;</code>
   */
  java.util.List<? extends cn.edu.cug.cs.gtl.protos.SeriesdOrBuilder> 
      getSeriesOrBuilderList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesd series = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.SeriesdOrBuilder getSeriesOrBuilder(
      int index);
}
