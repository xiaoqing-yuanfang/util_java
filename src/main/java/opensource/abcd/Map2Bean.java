package opensource.abcd;


import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Iterator;

public class Map2Bean {

    public static void parseMap2Bean(Map<String,String[]> map, Object ob){
        Class obClass = ob.getClass();
        Method[] ms = obClass.getMethods();
        Set<String> keys = map.keySet();
        for(Iterator it=keys.iterator(); it.hasNext();){
            String ele = (String)it.next();
            for(int i=0; i<ms.length; i++){
                String mn = ms[i].getName();
                if(mn.contains("set") && mn.toLowerCase().contains(ele)){
                    try {
                        ms[i].invoke(ob, map.get(ele)[0]);
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
