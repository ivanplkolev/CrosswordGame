

package example;

public interface WordInterface {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public int getStartX();

    public int getStartY();

    public int getDirection();

    public int getLength();

    public String getAnswer();

    public String getHint();
}