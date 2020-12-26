import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface IntegerEnum {
    int getVal();

    @SuppressWarnings("unchecked")
    static <E extends Enum> Map getValues(Class clzz){
        Map m = new HashMap();
        for(Object e : EnumSet.allOf(clzz))
            m.put(((IntegerEnum)e).getVal(), (E)e);

        return m;
    }
}
