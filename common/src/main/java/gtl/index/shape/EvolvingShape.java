package gtl.index.shape;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public interface EvolvingShape {
    RegionShape getVMBR();

    RegionShape getMBRAtTime(double t);
}
