package Search;

public class ComboItem {
    private Object value;
    private String label;

    public ComboItem(Object value, String label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return label;
    }
}
