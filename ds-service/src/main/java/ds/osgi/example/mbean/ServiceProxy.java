package ds.osgi.example.mbean;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;
import ds.osgi.example.api.MessageService;
import ds.osgi.example.api.ServiceProxyMBean;

import javax.management.*;
import java.lang.management.ManagementFactory;

@Component(immediate = true)
public class ServiceProxy extends StandardMBean implements ServiceProxyMBean {
    private static final String mbean = "ds.osgi.example:type=ServiceProxy";
    private MessageService messageService;
    private ObjectName oname;
    private String message;

    public ServiceProxy() throws NotCompliantMBeanException {
        super(ServiceProxyMBean.class);
    }

    @Activate
    public void init() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        oname = new ObjectName(mbean);
        mbs.registerMBean(this, oname);
    }

    @Deactivate
    public void destroy() throws MBeanRegistrationException, InstanceNotFoundException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.unregisterMBean(oname);
    }

    @Override
    public void startMessageService() {
        messageService.startMessageService();
    }

    @Override
    public void stopMessageService() {
        messageService.stopMessageService();
    }

    @Override
    public void setMessage(String message) {
        messageService.setMessage(message);
    }

    @Override
    public String getMessage() {
        return messageService.getMessage();
    }

    @Reference
    public void setMessageService(final MessageService messageService) {
        this.messageService = messageService;
    }

    public void unsetMessageService(final MessageService messageService) {
        this.messageService = null;
    }
}
