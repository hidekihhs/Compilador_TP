/*
 * Finalidade: representa um token número.
 */
package analisadorlexico;
/**
 * @author daniele,hideki,padovani
 */
public class Num extends Token {

    public final int type;
    public final int valueInt;
    public final float valueFloat;

    public Num(int type, int value) {
        super(type);
        this.valueInt = value;
        this.valueFloat = 0;
        this.type = type;
    }

    public Num(int type, float value) {
        super(type);
        this.valueInt = 0;
        this.valueFloat = value;
        this.type = type;
    }

    @Override
    public String toString() {
        if (type == Tag.INT_CONST) {
            return "" + this.valueInt;
        } else {
        	return "" + this.valueFloat;
        }
    }
}
