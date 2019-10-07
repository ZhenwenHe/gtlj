package gtl.math;

import gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-16.
 */
public strictfp final class Float128
        implements Serializable, Comparable, Cloneable {
    /**
     * The value nearest to the constant Pi.
     */
    public static final Float128 PI = new Float128(
            3.141592653589793116e+00,
            1.224646799147353207e-16);

    /**
     * The value nearest to the constant 2 * Pi.
     */
    public static final Float128 TWO_PI = new Float128(
            6.283185307179586232e+00,
            2.449293598294706414e-16);

    /**
     * The value nearest to the constant Pi / 2.
     */
    public static final Float128 PI_2 = new Float128(
            1.570796326794896558e+00,
            6.123233995736766036e-17);

    /**
     * The value nearest to the constant e (the natural logarithm base).
     */
    public static final Float128 E = new Float128(
            2.718281828459045091e+00,
            1.445646891729250158e-16);

    /**
     * A value representing the result of an operation which does not return a valid numeric.
     */
    public static final Float128 NaN = new Float128(Double.NaN, Double.NaN);

    /**
     * The smallest representable relative difference between two {link @ DoubleDouble} values
     */
    public static final double EPS = 1.23259516440783e-32;  /* = 2^-106 */
    /**
     * The value to split a double-precision value on during multiplication
     */
    private static final double SPLIT = 134217729.0D; // 2^27+1, for IEEE double
    private static final int MAX_PRINT_DIGITS = 32;
    private static final Float128 TEN = Float128.valueOf(10.0);
    private static final Float128 ONE = Float128.valueOf(1.0);
    private static final String SCI_NOT_EXPONENT_CHAR = "E";
    private static final String SCI_NOT_ZERO = "0.0E0";
    /**
     * The high-order component of the double-double precision value.
     */
    private double hi = 0.0;
    /**
     * The low-order component of the double-double precision value.
     */
    private double lo = 0.0;

    /**
     * Creates a new DoubleDouble with value 0.0.
     */
    public Float128() {
        init(0.0);
    }

    /**
     * Creates a new DoubleDouble with value x.
     *
     * @param x the value to initialize
     */
    public Float128(double x) {
        init(x);
    }

    /**
     * Creates a new DoubleDouble with value (hi, lo).
     *
     * @param hi the high-order component
     * @param lo the high-order component
     */
    public Float128(double hi, double lo) {
        init(hi, lo);
    }

    /**
     * Creates a new DoubleDouble with value equal to the argument.
     *
     * @param dd the value to initialize
     */
    public Float128(Float128 dd) {
        init(dd);
    }

    /**
     * Creates a new DoubleDouble with value equal to the argument.
     *
     * @param str the value to initialize by
     * @throws NumberFormatException if <tt>str</tt> is not a valid representation of a numeric
     */
    public Float128(String str)
            throws NumberFormatException {
        this(parse(str));
    }

    private static Float128 createNaN() {
        return new Float128(Double.NaN, Double.NaN);
    }

    /**
     * Converts the string argument to a DoubleDouble numeric.
     *
     * @param str a string containing a representation of a numeric value
     * @return the extended precision version of the value
     * @throws NumberFormatException if <tt>s</tt> is not a valid representation of a numeric
     */
    public static Float128 valueOf(String str)
            throws NumberFormatException {
        return parse(str);
    }

    /**
     * Converts the <tt>double</tt> argument to a DoubleDouble numeric.
     *
     * @param x a numeric value
     * @return the extended precision version of the value
     */
    public static Float128 valueOf(double x) {
        return new Float128(x);
    }
  
  /*
  double getHighComponent() { return hi; }
  
  double getLowComponent() { return lo; }
  */

    // Testing only - should not be public
  /*
  public void RENORM()
  {
    double s = hi + lo;
    double err = lo - (s - hi);
    hi = s;
    lo = err;
  }
  */

    /**
     * Creates a new DoubleDouble with the value of the argument.
     *
     * @param dd the DoubleDouble value to copy
     * @return a copy of the input value
     */
    public static Float128 copy(Float128 dd) {
        return new Float128(dd);
    }

    /**
     * Computes the square of this value.
     *
     * @return the square of this value.
     */
    public static Float128 sqr(double x) {
        return valueOf(x).selfMultiply(x);
    }

    public static Float128 sqrt(double x) {
        return valueOf(x).sqrt();
    }

    /**
     * Creates a string of a given length containing the given character
     *
     * @param ch  the character to be repeated
     * @param len the len of the desired string
     * @return the string
     */
    private static String stringOfChar(char ch, int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(ch);
        }
        return buf.toString();
    }

    /**
     * Determines the decimal magnitude of a numeric.
     * The magnitude is the exponent of the greatest power of 10 which is less than
     * or equal to the numeric.
     *
     * @param x the numeric to find the magnitude of
     * @return the decimal magnitude of x
     */
    private static int magnitude(double x) {
        double xAbs = Math.abs(x);
        double xLog10 = Math.log(xAbs) / Math.log(10);
        int xMag = (int) Math.floor(xLog10);
        /**
         * Since log computation is inexact, there may be an off-by-one error
         * in the computed magnitude.
         * Following tests that magnitude is correct, and adjusts it if not
         */
        double xApprox = Math.pow(10, xMag);
        if (xApprox * 10 <= xAbs)
            xMag += 1;

        return xMag;
    }

    /**
     * Converts a string representation of a real numeric into a DoubleDouble value.
     * The format accepted is similar to the standard Java real numeric syntax.
     * It is defined by the following regular expression:
     * <pre>
     * [<tt>+</tt>|<tt>-</tt>] {<i>digit</i>} [ <tt>.</tt> {<i>digit</i>} ] [ ( <tt>e</tt> | <tt>E</tt> ) [<tt>+</tt>|<tt>-</tt>] {<i>digit</i>}+
     * </pre>
     *
     * @param str the string to parse
     * @return the value of the parsed numeric
     * @throws NumberFormatException if <tt>str</tt> is not a valid representation of a numeric
     */
    public static Float128 parse(String str)
            throws NumberFormatException {
        int i = 0;
        int strlen = str.length();

        // skip leading whitespace
        while (Character.isWhitespace(str.charAt(i)))
            i++;

        // check for sign
        boolean isNegative = false;
        if (i < strlen) {
            char signCh = str.charAt(i);
            if (signCh == '-' || signCh == '+') {
                i++;
                if (signCh == '-') isNegative = true;
            }
        }

        // scan all digits and accumulate into an integral value
        // Keep track of the location of the decimal point (if any) to allow scaling later
        Float128 val = new Float128();

        int numDigits = 0;
        int numBeforeDec = 0;
        int exp = 0;
        while (true) {
            if (i >= strlen)
                break;
            char ch = str.charAt(i);
            i++;
            if (Character.isDigit(ch)) {
                double d = ch - '0';
                val.selfMultiply(TEN);
                // MD: need to optimize this
                val.selfAdd(d);
                numDigits++;
                continue;
            }
            if (ch == '.') {
                numBeforeDec = numDigits;
                continue;
            }
            if (ch == 'e' || ch == 'E') {
                String expStr = str.substring(i);
                // this should catch any format problems with the exponent
                try {
                    exp = Integer.parseInt(expStr);
                } catch (NumberFormatException ex) {
                    throw new NumberFormatException("Invalid exponent " + expStr + " in string " + str);
                }
                break;
            }
            throw new NumberFormatException("Unexpected character '" + ch
                    + "' at position " + i
                    + " in string " + str);
        }
        Float128 val2 = val;

        // scale the numeric correctly
        int numDecPlaces = numDigits - numBeforeDec - exp;
        if (numDecPlaces == 0) {
            val2 = val;
        } else if (numDecPlaces > 0) {
            Float128 scale = TEN.pow(numDecPlaces);
            val2 = val.divide(scale);
        } else if (numDecPlaces < 0) {
            Float128 scale = TEN.pow(-numDecPlaces);
            val2 = val.multiply(scale);
        }
        // apply leading sign, if any
        if (isNegative) {
            return val2.negate();
        }
        return val2;

    }

    /**
     * Creates and returns a copy of this value.
     *
     * @return a copy of this value
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            // should never reach here
            return null;
        }
    }

    private final void init(double x) {
        this.hi = x;
        this.lo = 0.0;
    }

    private final void init(double hi, double lo) {
        this.hi = hi;
        this.lo = lo;
    }

    private final void init(Float128 dd) {
        hi = dd.hi;
        lo = dd.lo;
    }

    /**
     * Set the value for the Float128 object. This method supports the mutating
     * operations concept described in the class documentation (see above).
     *
     * @param value a Float128 instance supplying an extended-precision value.
     * @return a self-reference to the Float128 instance.
     */
    public Float128 setValue(Float128 value) {
        init(value);
        return this;
    }

    /**
     * Set the value for the Float128 object. This method supports the mutating
     * operations concept described in the class documentation (see above).
     *
     * @param value a floating point value to be stored in the instance.
     * @return a self-reference to the Float128 instance.
     */
    public Float128 setValue(double value) {
        init(value);
        return this;
    }

    /**
     * Returns a new DoubleDouble whose value is <tt>(this + y)</tt>.
     *
     * @param y the addend
     * @return <tt>(this + y)</tt>
     */
    public final Float128 add(Float128 y) {
        return copy(this).selfAdd(y);
    }

    /**
     * Returns a new DoubleDouble whose value is <tt>(this + y)</tt>.
     *
     * @param y the addend
     * @return <tt>(this + y)</tt>
     */
    public final Float128 add(double y) {
        return copy(this).selfAdd(y);
    }

    /**
     * Adds the argument to the value of <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the addend
     * @return this object, increased by y
     */
    public final Float128 selfAdd(Float128 y) {
        return selfAdd(y.hi, y.lo);
    }

    /**
     * Adds the argument to the value of <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the addend
     * @return this object, increased by y
     */
    public final Float128 selfAdd(double y) {
        double H, h, S, s, e, f;
        S = hi + y;
        e = S - hi;
        s = S - e;
        s = (y - e) + (hi - s);
        f = s + lo;
        H = S + f;
        h = f + (S - H);
        hi = H + h;
        lo = h + (H - hi);
        return this;
        // return selfAdd(y, 0.0);
    }

    private final Float128 selfAdd(double yhi, double ylo) {
        double H, h, T, t, S, s, e, f;
        S = hi + yhi;
        T = lo + ylo;
        e = S - hi;
        f = T - lo;
        s = S - e;
        t = T - f;
        s = (yhi - e) + (hi - s);
        t = (ylo - f) + (lo - t);
        e = s + T;
        H = S + e;
        h = e + (S - H);
        e = t + h;

        double zhi = H + e;
        double zlo = e + (H - zhi);
        hi = zhi;
        lo = zlo;
        return this;
    }

    /**
     * Computes a new DoubleDouble object whose value is <tt>(this - y)</tt>.
     *
     * @param y the subtrahend
     * @return <tt>(this - y)</tt>
     */
    public final Float128 subtract(Float128 y) {
        return add(y.negate());
    }

    /**
     * Computes a new DoubleDouble object whose value is <tt>(this - y)</tt>.
     *
     * @param y the subtrahend
     * @return <tt>(this - y)</tt>
     */
    public final Float128 subtract(double y) {
        return add(-y);
    }

    /**
     * Subtracts the argument from the value of <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the addend
     * @return this object, decreased by y
     */
    public final Float128 selfSubtract(Float128 y) {
        if (isNaN()) return this;
        return selfAdd(-y.hi, -y.lo);
    }

    /**
     * Subtracts the argument from the value of <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the addend
     * @return this object, decreased by y
     */
    public final Float128 selfSubtract(double y) {
        if (isNaN()) return this;
        return selfAdd(-y, 0.0);
    }

    /**
     * Returns a new DoubleDouble whose value is <tt>-this</tt>.
     *
     * @return <tt>-this</tt>
     */
    public final Float128 negate() {
        if (isNaN()) return this;
        return new Float128(-hi, -lo);
    }

    /**
     * Returns a new DoubleDouble whose value is <tt>(this * y)</tt>.
     *
     * @param y the multiplicand
     * @return <tt>(this * y)</tt>
     */
    public final Float128 multiply(Float128 y) {
        if (y.isNaN()) return createNaN();
        return copy(this).selfMultiply(y);
    }

    /**
     * Returns a new DoubleDouble whose value is <tt>(this * y)</tt>.
     *
     * @param y the multiplicand
     * @return <tt>(this * y)</tt>
     */
    public final Float128 multiply(double y) {
        if (Double.isNaN(y)) return createNaN();
        return copy(this).selfMultiply(y, 0.0);
    }

    /**
     * Multiplies this object by the argument, returning <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the value to multiply by
     * @return this object, multiplied by y
     */
    public final Float128 selfMultiply(Float128 y) {
        return selfMultiply(y.hi, y.lo);
    }

    /**
     * Multiplies this object by the argument, returning <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the value to multiply by
     * @return this object, multiplied by y
     */
    public final Float128 selfMultiply(double y) {
        return selfMultiply(y, 0.0);
    }

    private final Float128 selfMultiply(double yhi, double ylo) {
        double hx, tx, hy, ty, C, c;
        C = SPLIT * hi;
        hx = C - hi;
        c = SPLIT * yhi;
        hx = C - hx;
        tx = hi - hx;
        hy = c - yhi;
        C = hi * yhi;
        hy = c - hy;
        ty = yhi - hy;
        c = ((((hx * hy - C) + hx * ty) + tx * hy) + tx * ty) + (hi * ylo + lo * yhi);
        double zhi = C + c;
        hx = C - zhi;
        double zlo = c + hx;
        hi = zhi;
        lo = zlo;
        return this;
    }

    /**
     * Computes a new DoubleDouble whose value is <tt>(this / y)</tt>.
     *
     * @param y the divisor
     * @return a new object with the value <tt>(this / y)</tt>
     */
    public final Float128 divide(Float128 y) {
        double hc, tc, hy, ty, C, c, U, u;
        C = hi / y.hi;
        c = SPLIT * C;
        hc = c - C;
        u = SPLIT * y.hi;
        hc = c - hc;
        tc = C - hc;
        hy = u - y.hi;
        U = C * y.hi;
        hy = u - hy;
        ty = y.hi - hy;
        u = (((hc * hy - U) + hc * ty) + tc * hy) + tc * ty;
        c = ((((hi - U) - u) + lo) - C * y.lo) / y.hi;
        u = C + c;

        double zhi = u;
        double zlo = (C - u) + c;
        return new Float128(zhi, zlo);
    }

    /**
     * Computes a new DoubleDouble whose value is <tt>(this / y)</tt>.
     *
     * @param y the divisor
     * @return a new object with the value <tt>(this / y)</tt>
     */
    public final Float128 divide(double y) {
        if (Double.isNaN(y)) return createNaN();
        return copy(this).selfDivide(y, 0.0);
    }

    /**
     * Divides this object by the argument, returning <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the value to divide by
     * @return this object, divided by y
     */
    public final Float128 selfDivide(Float128 y) {
        return selfDivide(y.hi, y.lo);
    }

    /**
     * Divides this object by the argument, returning <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the value to divide by
     * @return this object, divided by y
     */
    public final Float128 selfDivide(double y) {
        return selfDivide(y, 0.0);
    }

    private final Float128 selfDivide(double yhi, double ylo) {
        double hc, tc, hy, ty, C, c, U, u;
        C = hi / yhi;
        c = SPLIT * C;
        hc = c - C;
        u = SPLIT * yhi;
        hc = c - hc;
        tc = C - hc;
        hy = u - yhi;
        U = C * yhi;
        hy = u - hy;
        ty = yhi - hy;
        u = (((hc * hy - U) + hc * ty) + tc * hy) + tc * ty;
        c = ((((hi - U) - u) + lo) - C * ylo) / yhi;
        u = C + c;

        hi = u;
        lo = (C - u) + c;
        return this;
    }

    /**
     * Returns a DoubleDouble whose value is  <tt>1 / this</tt>.
     *
     * @return the reciprocal of this value
     */
    public final Float128 reciprocal() {
        double hc, tc, hy, ty, C, c, U, u;
        C = 1.0 / hi;
        c = SPLIT * C;
        hc = c - C;
        u = SPLIT * hi;
        hc = c - hc;
        tc = C - hc;
        hy = u - hi;
        U = C * hi;
        hy = u - hy;
        ty = hi - hy;
        u = (((hc * hy - U) + hc * ty) + tc * hy) + tc * ty;
        c = ((((1.0 - U) - u)) - C * lo) / hi;

        double zhi = C + c;
        double zlo = (C - zhi) + c;
        return new Float128(zhi, zlo);
    }

    /**
     * Returns the largest (closest to positive infinity)
     * value that is not greater than the argument
     * and is equal to a mathematical integer.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return the largest (closest to positive infinity)
     * value that is not greater than the argument
     * and is equal to a mathematical integer.
     */
    public Float128 floor() {
        if (isNaN()) return NaN;
        double fhi = Math.floor(hi);
        double flo = 0.0;
        // Hi is already integral.  Floor the low word
        if (fhi == hi) {
            flo = Math.floor(lo);
        }
        // do we need to renormalize here?
        return new Float128(fhi, flo);
    }

    /**
     * Returns the smallest (closest to negative infinity) value
     * that is not less than the argument and is equal to a mathematical integer.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return the smallest (closest to negative infinity) value
     * that is not less than the argument and is equal to a mathematical integer.
     */
    public Float128 ceil() {
        if (isNaN()) return NaN;
        double fhi = Math.ceil(hi);
        double flo = 0.0;
        // Hi is already integral.  Ceil the low word
        if (fhi == hi) {
            flo = Math.ceil(lo);
            // do we need to renormalize here?
        }
        return new Float128(fhi, flo);
    }


    /*------------------------------------------------------------
     *   Ordering Functions
     *------------------------------------------------------------
     */

    /**
     * Returns an integer indicating the sign of this value.
     * <ul>
     * <li>if this value is &gt; 0, returns 1
     * <li>if this value is &lt; 0, returns -1
     * <li>if this value is = 0, returns 0
     * <li>if this value is NaN, returns 0
     * </ul>
     *
     * @return an integer indicating the sign of this value
     */
    public int signum() {
        if (hi > 0) return 1;
        if (hi < 0) return -1;
        if (lo > 0) return 1;
        if (lo < 0) return -1;
        return 0;
    }

    /**
     * Rounds this value to the nearest integer.
     * The value is rounded to an integer by adding 1/2 and taking the floor of the result.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return this value rounded to the nearest integer
     */
    public Float128 rint() {
        if (isNaN()) return this;
        // may not be 100% correct
        Float128 plus5 = this.add(0.5);
        return plus5.floor();
    }

    /*------------------------------------------------------------
     *   Conversion Functions
     *------------------------------------------------------------
     */

    /**
     * Returns the integer which is largest in absolute value and not further
     * from zero than this value.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return the integer which is largest in absolute value and not further from zero than this value
     */
    public Float128 trunc() {
        if (isNaN()) return NaN;
        if (isPositive())
            return floor();
        else
            return ceil();
    }

    /**
     * Returns the absolute value of this value.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, it is returned.
     * </ul>
     *
     * @return the absolute value of this value
     */
    public Float128 abs() {
        if (isNaN()) return NaN;
        if (isNegative())
            return negate();
        return new Float128(this);
    }

    /*------------------------------------------------------------
     *   Predicates
     *------------------------------------------------------------
     */

    /**
     * Computes the square of this value.
     *
     * @return the square of this value.
     */
    public Float128 sqr() {
        return this.multiply(this);
    }

    /**
     * Squares this object.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @return the square of this value.
     */
    public Float128 selfSqr() {
        return this.selfMultiply(this);
    }

    /**
     * Computes the positive square root of this value.
     * If the numeric is NaN or negative, NaN is returned.
     *
     * @return the positive square root of this numeric.
     * If the argument is NaN or less than zero, the result is NaN.
     */
    public Float128 sqrt() {
    /* Strategy:  Use Karp's trick:  if x is an approximation
    to sqrt(a), then

       sqrt(a) = a*x + [a - (a*x)^2] * x / 2   (approx)

    The approximation is accurate to twice the accuracy of x.
    Also, the multiplication (a*x) and [-]*x can be done with
    only half the precision.
 */

        if (isZero())
            return valueOf(0.0);

        if (isNegative()) {
            return NaN;
        }

        double x = 1.0 / Math.sqrt(hi);
        double ax = hi * x;

        Float128 axdd = valueOf(ax);
        Float128 diffSq = this.subtract(axdd.sqr());
        double d2 = diffSq.hi * (x * 0.5);

        return axdd.add(d2);
    }

    /**
     * Computes the value of this numeric raised to an integral power.
     * Follows semantics of Java Math.pow as closely as possible.
     *
     * @param exp the integer exponent
     * @return x raised to the integral power exp
     */
    public Float128 pow(int exp) {
        if (exp == 0.0)
            return valueOf(1.0);

        Float128 r = new Float128(this);
        Float128 s = valueOf(1.0);
        int n = Math.abs(exp);

        if (n > 1) {
            /* Use binary exponentiation */
            while (n > 0) {
                if (n % 2 == 1) {
                    s.selfMultiply(r);
                }
                n /= 2;
                if (n > 0)
                    r = r.sqr();
            }
        } else {
            s = r;
        }

        /* Compute the reciprocal if n is negative. */
        if (exp < 0)
            return s.reciprocal();
        return s;
    }

    /**
     * Computes the minimum of this and another Float128 numeric.
     *
     * @param x a Float128 numeric
     * @return the minimum of the two numbers
     */
    public Float128 min(Float128 x) {
        if (this.le(x)) {
            return this;
        } else {
            return x;
        }
    }

    /**
     * Computes the maximum of this and another Float128 numeric.
     *
     * @param x a Float128 numeric
     * @return the maximum of the two numbers
     */
    public Float128 max(Float128 x) {
        if (this.ge(x)) {
            return this;
        } else {
            return x;
        }
    }

    /**
     * Converts this value to the nearest double-precision numeric.
     *
     * @return the nearest double-precision numeric to this value
     */
    public double doubleValue() {
        return hi + lo;
    }

    /**
     * Converts this value to the nearest integer.
     *
     * @return the nearest integer to this value
     */
    public int intValue() {
        return (int) hi;
    }

    /**
     * Tests whether this value is equal to 0.
     *
     * @return true if this value is equal to 0
     */
    public boolean isZero() {
        return hi == 0.0 && lo == 0.0;
    }

    /**
     * Tests whether this value is less than 0.
     *
     * @return true if this value is less than 0
     */
    public boolean isNegative() {
        return hi < 0.0 || (hi == 0.0 && lo < 0.0);
    }


    /*------------------------------------------------------------
     *   Output
     *------------------------------------------------------------
     */

    /**
     * Tests whether this value is greater than 0.
     *
     * @return true if this value is greater than 0
     */
    public boolean isPositive() {
        return hi > 0.0 || (hi == 0.0 && lo > 0.0);
    }

    /**
     * Tests whether this value is NaN.
     *
     * @return true if this value is NaN
     */
    public boolean isNaN() {
        return Double.isNaN(hi);
    }

    /**
     * Tests whether this value is equal to another <tt>DoubleDouble</tt> value.
     *
     * @param y a DoubleDouble value
     * @return true if this value = y
     */
    public boolean equals(Float128 y) {
        return hi == y.hi && lo == y.lo;
    }

    /**
     * Tests whether this value is greater than another <tt>DoubleDouble</tt> value.
     *
     * @param y a DoubleDouble value
     * @return true if this value &gt; y
     */
    public boolean gt(Float128 y) {
        return (hi > y.hi) || (hi == y.hi && lo > y.lo);
    }

    /**
     * Tests whether this value is greater than or equals to another <tt>DoubleDouble</tt> value.
     *
     * @param y a DoubleDouble value
     * @return true if this value &gt;= y
     */
    public boolean ge(Float128 y) {
        return (hi > y.hi) || (hi == y.hi && lo >= y.lo);
    }

    /**
     * Tests whether this value is less than another <tt>DoubleDouble</tt> value.
     *
     * @param y a DoubleDouble value
     * @return true if this value &lt; y
     */
    public boolean lt(Float128 y) {
        return (hi < y.hi) || (hi == y.hi && lo < y.lo);
    }

    /**
     * Tests whether this value is less than or equal to another <tt>DoubleDouble</tt> value.
     *
     * @param y a DoubleDouble value
     * @return true if this value &lt;= y
     */
    public boolean le(Float128 y) {
        return (hi < y.hi) || (hi == y.hi && lo <= y.lo);
    }

    /**
     * Compares two DoubleDouble objects numerically.
     *
     * @return -1,0 or 1 depending on whether this value is less than, equal to
     * or greater than the value of <tt>o</tt>
     */
    public int compareTo(Object o) {
        Float128 other = (Float128) o;

        if (hi < other.hi) return -1;
        if (hi > other.hi) return 1;
        if (lo < other.lo) return -1;
        if (lo > other.lo) return 1;
        return 0;
    }

    /**
     * Dumps the components of this numeric to a string.
     *
     * @return a string showing the components of the numeric
     */
    public String dump() {
        return "Float128<" + hi + ", " + lo + ">";
    }

    /**
     * Returns a string representation of this numeric, in either standard or scientific notation.
     * If the magnitude of the numeric is in the range [ 10<sup>-3</sup>, 10<sup>8</sup> ]
     * standard notation will be used.  Otherwise, scientific notation will be used.
     *
     * @return a string representation of this numeric
     */
    public String toString() {
        int mag = magnitude(hi);
        if (mag >= -3 && mag <= 20)
            return toStandardNotation();
        return toSciNotation();
    }

    /**
     * Returns the string representation of this value in standard notation.
     *
     * @return the string representation in standard notation
     */
    public String toStandardNotation() {
        String specialStr = getSpecialNumberString();
        if (specialStr != null)
            return specialStr;

        int[] magnitude = new int[1];
        String sigDigits = extractSignificantDigits(true, magnitude);
        int decimalPointPos = magnitude[0] + 1;

        String num = sigDigits;
        // add a leading 0 if the decimal point is the first char
        if (sigDigits.charAt(0) == '.') {
            num = "0" + sigDigits;
        } else if (decimalPointPos < 0) {
            num = "0." + stringOfChar('0', -decimalPointPos) + sigDigits;
        } else if (sigDigits.indexOf('.') == -1) {
            // no point inserted - sig digits must be smaller than magnitude of numeric
            // add zeroes to dEnd to make numeric the correct size
            int numZeroes = decimalPointPos - sigDigits.length();
            String zeroes = stringOfChar('0', numZeroes);
            num = sigDigits + zeroes + ".0";
        }

        if (this.isNegative())
            return "-" + num;
        return num;
    }

    /**
     * Returns the string representation of this value in scientific notation.
     *
     * @return the string representation in scientific notation
     */
    public String toSciNotation() {
        // special case zero, to allow as
        if (isZero())
            return SCI_NOT_ZERO;

        String specialStr = getSpecialNumberString();
        if (specialStr != null)
            return specialStr;

        int[] magnitude = new int[1];
        String digits = extractSignificantDigits(false, magnitude);
        String expStr = SCI_NOT_EXPONENT_CHAR + magnitude[0];

        // should never have leading zeroes
        // MD - is this correct?  Or should we simply strip them if they are present?
        if (digits.charAt(0) == '0') {
            throw new IllegalStateException("Found leading zero: " + digits);
        }

        // add decimal point
        String trailingDigits = "";
        if (digits.length() > 1)
            trailingDigits = digits.substring(1);
        String digitsWithDecimal = digits.charAt(0) + "." + trailingDigits;

        if (this.isNegative())
            return "-" + digitsWithDecimal + expStr;
        return digitsWithDecimal + expStr;
    }

    /**
     * Extracts the significant digits in the decimal representation of the argument.
     * A decimal point may be optionally inserted in the string of digits
     * (as long as its position lies within the extracted digits
     * - if not, the caller must prepend or append the appropriate zeroes and decimal point).
     *
     * @param y               the numeric to extract ( >= 0)
     * @param decimalPointPos the position in which to insert a decimal point
     * @return the string containing the significant digits and possibly a decimal point
     */
    private String extractSignificantDigits(boolean insertDecimalPoint, int[] magnitude) {
        Float128 y = this.abs();
        // compute *correct* magnitude of y
        int mag = magnitude(y.hi);
        Float128 scale = TEN.pow(mag);
        y = y.divide(scale);

        // fix magnitude if off by one
        if (y.gt(TEN)) {
            y = y.divide(TEN);
            mag += 1;
        } else if (y.lt(ONE)) {
            y = y.multiply(TEN);
            mag -= 1;
        }

        int decimalPointPos = mag + 1;
        StringBuffer buf = new StringBuffer();
        int numDigits = MAX_PRINT_DIGITS - 1;
        for (int i = 0; i <= numDigits; i++) {
            if (insertDecimalPoint && i == decimalPointPos) {
                buf.append('.');
            }
            int digit = (int) y.hi;
//      System.out.println("printDump: [" + i + "] digit: " + digit + "  y: " + y.dump() + "  buf: " + buf);

            /**
             * This should never happen, due to heuristic checks on remainder below
             */
            if (digit < 0 || digit > 9) {
//        System.out.println("digit > 10 : " + digit);
//        throw new IllegalStateException("Internal errror: found digit = " + digit);
            }
            /**
             * If a negative remainder is encountered, simply terminate the extraction.
             * This is robust, but maybe slightly inaccurate.
             * My current hypothesis is that negative remainders only occur for very small lo components,
             * so the inaccuracy is tolerable
             */
            if (digit < 0) {
                break;
                // throw new IllegalStateException("Internal errror: found digit = " + digit);
            }
            boolean rebiasBy10 = false;
            char digitChar = 0;
            if (digit > 9) {
                // set flag to re-bias after next 10-shift
                rebiasBy10 = true;
                // output digit will dEnd up being '9'
                digitChar = '9';
            } else {
                digitChar = (char) ('0' + digit);
            }
            buf.append(digitChar);
            y = (y.subtract(Float128.valueOf(digit))
                    .multiply(TEN));
            if (rebiasBy10)
                y.selfAdd(TEN);

            boolean continueExtractingDigits = true;
            /**
             * Heuristic check: if the remaining portion of
             * y is non-positive, assume that output is complete
             */
//      if (y.hi <= 0.0)
//        if (y.hi < 0.0)
//        continueExtractingDigits = false;
            /**
             * Check if remaining digits will be 0, and if so don't output them.
             * Do this by comparing the magnitude of the remainder with the expected precision.
             */
            int remMag = magnitude(y.hi);
            if (remMag < 0 && Math.abs(remMag) >= (numDigits - i))
                continueExtractingDigits = false;
            if (!continueExtractingDigits)
                break;
        }
        magnitude[0] = mag;
        return buf.toString();
    }


    /*------------------------------------------------------------
     *   Input
     *------------------------------------------------------------
     */

    /**
     * Returns the string for this value if it has a known representation.
     * (E.g. NaN or 0.0)
     *
     * @return the string for this special numeric
     * or null if the numeric is not a special numeric
     */
    private String getSpecialNumberString() {
        if (isZero()) return "0.0";
        if (isNaN()) return "NaN ";
        return null;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Float128) {
            Float128 f = (Float128) i;
            this.lo = f.lo;
            this.hi = f.hi;
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.lo = in.readDouble();
        this.hi = in.readDouble();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeDouble(this.lo);
        out.writeDouble(this.hi);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 16;
    }
}