package opensource.abcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class HttpRequest {

	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
/*            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");*/
            // 建立实际的连接
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	// System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    public static String readRequestContent(HttpServletRequest request){
        String res = "";
        try{ 
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
            while(true){
               String tmp = br.readLine(); 
               if(tmp == null){
                   break;
               }
               res += tmp;
            }
        }catch(Exception e){
           e.printStackTrace();
        }
        return res;
    }

    public static JSONArray parseRequestJsonArray(HttpServletRequest request){
        JSONArray res = new JSONArray();
        try{ 
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
            String s = "";
            while(true){
               String tmp = br.readLine(); 
               if(tmp == null){
                   break;
               }
               s += tmp;
            }
            res = JSONArray.fromObject(s);
        }catch(Exception e){
           e.printStackTrace();
        }
        return res;
    }

    public static JSONObject parseRequestJsonDict(HttpServletRequest request){
        JSONObject res = new JSONObject();
        try{ 
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
            String s = "";
            while(true){
               String tmp = br.readLine(); 
               if(tmp == null){
                   break;
               }
               s += tmp;
            }
            res = JSONObject.fromObject(s);
        }catch(Exception e){
           e.printStackTrace();
        }
        return res;
    }

    public static <T> T getBean(HttpServletRequest request, Class<T> beanClass){
        T bean = null;
        try {
            bean = beanClass.newInstance();
            if(request.getMethod().equals("POST")){
                JSONObject jo = parseRequestJsonDict(request);
                JSONObject2Bean.parseJSONObject2Bean(jo, bean);
            }else if(request.getMethod().equals("GET")){
                Map<String, String[]> map = request.getParameterMap();
                Map2Bean.parseMap2Bean(map, bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }

}
