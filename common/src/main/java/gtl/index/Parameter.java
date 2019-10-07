package gtl.index;

/**
 * Created by ZhenwenHe on 2016/12/24.
 */
public class Parameter<T> {
    T value;
    String name;

    public Parameter(T value, String name) {
        this.value = value;
        this.name = name;
    }

    public Parameter(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
