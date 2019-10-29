package cn.edu.cug.cs.gtl.offices.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;

import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;
import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;

public class Image {
    private BufferedImage bufferedImage;

    private Image() {
    }

    /**
     * Constructs a <code>BufferedImage</code> of one of the predefined
     * image types.  The <code>ColorSpace</code> for the image is the
     * default sRGB space.
     *
     * @param width     width of the created image
     * @param height    height of the created image
     * @param imageType type of the created image
     * @see ColorSpace
     * @see #TYPE_INT_RGB
     * @see #TYPE_INT_ARGB
     * @see #TYPE_INT_ARGB_PRE
     * @see #TYPE_INT_BGR
     * @see #TYPE_3BYTE_BGR
     * @see #TYPE_4BYTE_ABGR
     * @see #TYPE_4BYTE_ABGR_PRE
     * @see #TYPE_BYTE_GRAY
     * @see #TYPE_USHORT_GRAY
     * @see #TYPE_BYTE_BINARY
     * @see #TYPE_BYTE_INDEXED
     * @see #TYPE_USHORT_565_RGB
     * @see #TYPE_USHORT_555_RGB
     */
    public Image(int width, int height, int imageType) {
        this.bufferedImage = new BufferedImage(width, height, imageType);
    }

    /**
     * Constructs a <code>BufferedImage</code> of one of the predefined
     * image types:
     * TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED.
     *
     * <p> If the image type is TYPE_BYTE_BINARY, the number of
     * entries in the color model is used to determine whether the
     * image should have 1, 2, or 4 bits per pixel.  If the color model
     * has 1 or 2 entries, the image will have 1 bit per pixel.  If it
     * has 3 or 4 entries, the image with have 2 bits per pixel.  If
     * it has between 5 and 16 entries, the image will have 4 bits per
     * pixel.  Otherwise, an IllegalArgumentException will be thrown.
     *
     * @param width     width of the created image
     * @param height    height of the created image
     * @param imageType type of the created image
     * @param cm        <code>IndexColorModel</code> of the created image
     * @throws IllegalArgumentException if the imageType is not
     *                                  TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED or if the imageType is
     *                                  TYPE_BYTE_BINARY and the color map has more than 16 entries.
     * @see #TYPE_BYTE_BINARY
     * @see #TYPE_BYTE_INDEXED
     */
    public Image(int width, int height, int imageType, IndexColorModel cm) {
        this.bufferedImage = new BufferedImage(width, height, imageType, cm);
    }

    /**
     * Constructs a new <code>BufferedImage</code> with a specified
     * <code>ColorModel</code> and <code>Raster</code>.  If the number and
     * types of bands in the <code>SampleModel</code> of the
     * <code>Raster</code> do not match the number and types required by
     * the <code>ColorModel</code> to represent its color and alpha
     * components, a {@link RasterFormatException} is thrown.  This
     * method can multiply or divide the color <code>Raster</code> data by
     * alpha to match the <code>alphaPremultiplied</code> state
     * in the <code>ColorModel</code>.  Properties for this
     * <code>BufferedImage</code> can be established by passing
     * in a {@link Hashtable} of <code>String</code>/<code>Object</code>
     * pairs.
     *
     * @param cm                    <code>ColorModel</code> for the new image
     * @param raster                <code>Raster</code> for the image data
     * @param isRasterPremultiplied if <code>true</code>, the data in
     *                              the raster has been premultiplied with alpha.
     * @param properties            <code>Hashtable</code> of
     *                              <code>String</code>/<code>Object</code> pairs.
     * @throws RasterFormatException    if the number and
     *                                  types of bands in the <code>SampleModel</code> of the
     *                                  <code>Raster</code> do not match the number and types required by
     *                                  the <code>ColorModel</code> to represent its color and alpha
     *                                  components.
     * @throws IllegalArgumentException if
     *                                  <code>raster</code> is incompatible with <code>cm</code>
     * @see ColorModel
     * @see Raster
     * @see WritableRaster
     */
    public Image(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
        this.bufferedImage = new BufferedImage(cm, raster, isRasterPremultiplied, properties);
    }

    /**
     * @return
     */
    public int getWidth() {
        return this.bufferedImage.getWidth();
    }

    /**
     * @return
     */
    public int getHeight() {
        return this.bufferedImage.getHeight();
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public int getRGB(int x, int y) {
        return this.bufferedImage.getRGB(x, y);
    }

    /**
     * @param x
     * @param y
     * @param rgb
     */
    public void setRGB(int x, int y, int rgb) {
        this.bufferedImage.setRGB(x, y, rgb);
    }

    /**
     * gray image
     *
     * @return
     */
    public Image gray() {
        int w = getWidth();
        int h = getHeight();
        Image image = new Image(w, h, TYPE_BYTE_GRAY);
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                int rgb = getRGB(i, j);
                image.setRGB(i, j, rgb);
            }
        }
        return image;
    }

    /**
     * binary image
     *
     * @return
     */
    public Image binary() {
        int w = getWidth();
        int h = getHeight();
        Image image = new Image(w, h, TYPE_BYTE_BINARY);
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                int rgb = getRGB(i, j);
                image.setRGB(i, j, rgb);
            }
        }
        return image;
    }

    /**
     * Writes an image using an arbitrary <code>ImageWriter</code>
     * that supports the given format to a <code>File</code>.  If
     * there is already a <code>File</code> present, its contents are
     * discarded.
     *
     * @param formatName a <code>String</code> containing the informal
     *                   name of the format.
     * @param output     a <code>File</code> to be written to.
     * @return <code>false</code> if no appropriate writer is found.
     * @throws IllegalArgumentException if any parameter is
     *                                  <code>null</code>.
     * @throws IOException              if an error occurs during writing.
     */
    public void write(String formatName, File output) throws IOException {
        ImageIO.write(this.bufferedImage, formatName, output);
    }


    /**
     * Writes an image using an arbitrary <code>ImageWriter</code>
     * that supports the given format to an <code>OutputStream</code>.
     *
     * <p> This method <em>does not</em> close the provided
     * <code>OutputStream</code> after the write operation has completed;
     * it is the responsibility of the caller to close the stream, if desired.
     *
     * <p> The current cache settings from <code>getUseCache</code>and
     * <code>getCacheDirectory</code> will be used to control caching.
     *
     * @param formatName a <code>String</code> containing the informal
     *                   name of the format.
     * @param output     an <code>OutputStream</code> to be written to.
     * @return <code>false</code> if no appropriate writer is found.
     * @throws IllegalArgumentException if any parameter is
     *                                  <code>null</code>.
     * @throws IOException              if an error occurs during writing.
     */
    public void write(String formatName, OutputStream output) throws IOException {
        ImageIO.write(this.bufferedImage, formatName, output);
    }

    /**
     * Returns a <code>BufferedImage</code> as the result of decoding
     * a supplied <code>File</code> with an <code>ImageReader</code>
     * chosen automatically from among those currently registered.
     * The <code>File</code> is wrapped in an
     * <code>ImageInputStream</code>.  If no registered
     * <code>ImageReader</code> claims to be able to read the
     * resulting stream, <code>null</code> is returned.
     *
     * <p> The current cache settings from <code>getUseCache</code>and
     * <code>getCacheDirectory</code> will be used to control caching in the
     * <code>ImageInputStream</code> that is created.
     *
     * <p> Note that there is no <code>read</code> method that takes a
     * filename as a <code>String</code>; use this method instead after
     * creating a <code>File</code> from the filename.
     *
     * <p> This method does not attempt to locate
     * <code>ImageReader</code>s that can read directly from a
     * <code>File</code>; that may be accomplished using
     * <code>IIORegistry</code> and <code>ImageReaderSpi</code>.
     *
     * @param input a <code>File</code> to read from.
     * @return a <code>BufferedImage</code> containing the decoded
     * contents of the input, or <code>null</code>.
     * @throws IllegalArgumentException if <code>input</code> is
     *                                  <code>null</code>.
     * @throws IOException              if an error occurs during reading.
     */
    public static Image read(File input) throws IOException {
        Image im = new Image();
        im.bufferedImage = ImageIO.read(input);
        return im;
    }

    /**
     * Returns a <code>BufferedImage</code> as the result of decoding
     * a supplied <code>InputStream</code> with an <code>ImageReader</code>
     * chosen automatically from among those currently registered.
     * The <code>InputStream</code> is wrapped in an
     * <code>ImageInputStream</code>.  If no registered
     * <code>ImageReader</code> claims to be able to read the
     * resulting stream, <code>null</code> is returned.
     *
     * <p> The current cache settings from <code>getUseCache</code>and
     * <code>getCacheDirectory</code> will be used to control caching in the
     * <code>ImageInputStream</code> that is created.
     *
     * <p> This method does not attempt to locate
     * <code>ImageReader</code>s that can read directly from an
     * <code>InputStream</code>; that may be accomplished using
     * <code>IIORegistry</code> and <code>ImageReaderSpi</code>.
     *
     * <p> This method <em>does not</em> close the provided
     * <code>InputStream</code> after the read operation has completed;
     * it is the responsibility of the caller to close the stream, if desired.
     *
     * @param input an <code>InputStream</code> to read from.
     * @return a <code>BufferedImage</code> containing the decoded
     * contents of the input, or <code>null</code>.
     * @throws IllegalArgumentException if <code>input</code> is
     *                                  <code>null</code>.
     * @throws IOException              if an error occurs during reading.
     */
    public static Image read(InputStream input) throws IOException {
        Image im = new Image();
        im.bufferedImage = ImageIO.read(input);
        return im;
    }

    /**
     * Returns a <code>BufferedImage</code> as the result of decoding
     * a supplied <code>URL</code> with an <code>ImageReader</code>
     * chosen automatically from among those currently registered.  An
     * <code>InputStream</code> is obtained from the <code>URL</code>,
     * which is wrapped in an <code>ImageInputStream</code>.  If no
     * registered <code>ImageReader</code> claims to be able to read
     * the resulting stream, <code>null</code> is returned.
     *
     * <p> The current cache settings from <code>getUseCache</code>and
     * <code>getCacheDirectory</code> will be used to control caching in the
     * <code>ImageInputStream</code> that is created.
     *
     * <p> This method does not attempt to locate
     * <code>ImageReader</code>s that can read directly from a
     * <code>URL</code>; that may be accomplished using
     * <code>IIORegistry</code> and <code>ImageReaderSpi</code>.
     *
     * @param input a <code>URL</code> to read from.
     * @return a <code>BufferedImage</code> containing the decoded
     * contents of the input, or <code>null</code>.
     * @throws IllegalArgumentException if <code>input</code> is
     *                                  <code>null</code>.
     * @throws IOException              if an error occurs during reading.
     */
    public static Image read(URL input) throws IOException {
        Image im = new Image();
        im.bufferedImage = ImageIO.read(input);
        return im;
    }

}
