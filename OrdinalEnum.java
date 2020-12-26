import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface OrdinalEnum {
    long getVal();

    @SuppressWarnings("unchecked")
    static <E extends Enum> Map getValues(Class clzz){
        Map m = new HashMap();
        for(Object e : EnumSet.allOf(clzz))
            m.put(((OrdinalEnum)e).getVal(), (E)e);

        return m;
    }
}
