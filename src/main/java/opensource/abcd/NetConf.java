package opensource.abcd;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import net.juniper.netconf.Device;
import net.juniper.netconf.XML;
import net.juniper.netconf.NetconfException;

public class NetConf {
	public final static Logger logger =  LoggerFactory.getLogger(NetConf.class);

	public static Device connect(String host, int port, String username, String password){
		Device device = null;
		try{
			device = new Device(host, username, password, null, port);
			device.setPromptString("]]>]]>");
			try{
				device.connect();
				device.getSessionId();
			}catch(IllegalStateException e){
                e.printStackTrace();
				device = null;
			}
		}catch(NetconfException e){
		    e.printStackTrace();
			device = null;
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();
			device = null;
		}
		return device;
	}
	
	public static XML executeRpc(Device device, String rpc){
		XML reply = null;
		try{
			reply = device.executeRPC(rpc);
		}catch(Exception e){
			logger.info(e.toString());
		}
		return reply;
	}
	
	public static void main(String[] args){
        String rpc = "<rpc message-id=\"101\">"+ "*" +
                "</rpc>";
		Device d = NetConf.connect("*",22,"*","*");
		if(d != null){
			XML res = executeRpc(d, rpc);
			logger.info(res.toString());
			List<String> li = new ArrayList<String>();
			li.add("role-mapping-rules");
			List<Node> ln = res.findNodes(li);
			for(Iterator<Node> it=ln.iterator(); it.hasNext();)
			{
			}
		}
	}

}
