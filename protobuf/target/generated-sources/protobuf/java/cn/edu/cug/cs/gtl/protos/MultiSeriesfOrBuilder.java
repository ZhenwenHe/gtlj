// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/series.proto

package cn.edu.cug.cs.gtl.protos;

public interface MultiSeriesfOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.MultiSeriesf)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated float time = 1;</code>
   * @return A list containing the time.
   */
  java.util.List<java.lang.Float> getTimeList();
  /**
   * <code>repeated float time = 1;</code>
   * @return The count of time.
   */
  int getTimeCount();
  /**
   * <code>repeated float time = 1;</code>
   * @param index The index of the element to return.
   * @return The time at the given index.
   */
  float getTime(int index);

  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesf series = 2;</code>
   */
  java.util.List<cn.edu.cug.cs.gtl.protos.Seriesf> 
      getSeriesList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesf series = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.Seriesf getSeries(int index);
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesf series = 2;</code>
   */
  int getSeriesCount();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesf series = 2;</code>
   */
  java.util.List<? extends cn.edu.cug.cs.gtl.protos.SeriesfOrBuilder> 
      getSeriesOrBuilderList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Seriesf series = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.SeriesfOrBuilder getSeriesOrBuilder(
      int index);
}
