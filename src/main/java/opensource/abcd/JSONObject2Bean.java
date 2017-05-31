package opensource.abcd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import net.sf.json.JSONObject;

public class JSONObject2Bean {

    public static void parseJSONObject2Bean(JSONObject jo, Object ob){
        Class obClass = ob.getClass();
        Method[] ms = obClass.getMethods();
        for(@SuppressWarnings("unchecked")
        Iterator<String> it=jo.keys(); it.hasNext();){
            String ele = (String)it.next();
            for(int i=0; i<ms.length; i++){
                String mn = ms[i].getName();
                if(mn.contains("set") && mn.toLowerCase().contains(ele)){
                    try {
                        ms[i].invoke(ob, jo.get(ele));
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
