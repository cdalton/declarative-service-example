package ds.osgi.example.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Registrar {
    private static final String mbean = "ds.osgi.example:type=MessageService";
    private ServiceProxy instance;
    private ObjectName oname;

    public void init() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        oname = new ObjectName(mbean);
        mbs.registerMBean(instance, oname);
    }

    public void destroy() throws MBeanRegistrationException, InstanceNotFoundException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.unregisterMBean(oname);
    }

    public ServiceProxy getInstance() {
        return instance;
    }

    public void setInstance(ServiceProxy instance) {
        this.instance = instance;
    }
}
