package codecrafts;

import java.lang.reflect.Array;
import java.util.*;

// Do we need history of payments? (Payment entity). Might be useful
// TODO: Database support will be very cool - at least one day for it

public class Engine {

    // Notification interval
    public static class Settings {
        private final int expiryNotificationInDays;

        private Settings() {
            expiryNotificationInDays = 3;
        }

        public int expiryNotificationInDays() { return expiryNotificationInDays; }
    }

    public interface ISubscriptionEventNotifier {
        void onSubSoonExpired(codecrafts.Subscription sub);
        void onSubExpired(Subscription sub);
    }

    private static Engine instance_ = null;

    private final Settings settings_;
    private final Map<String, Resource> resources_;
    private final List<ISubscriptionEventNotifier> notifiers_;

    private Engine() {
        settings_ = new Settings();
        resources_ = new HashMap<>();
        notifiers_ = new ArrayList<>();
    }

    public static Engine instance() {
        if (instance_ == null)
            instance_ = new Engine();

        return instance_;
    }

    // TODO: Load member list from well-known text file (for manual onboarding)
    // TODO: The simplest option is to load from csv-file
    // Currently the simplest xml-solution in Java: XStream (third-party library) as:
    // XMLEncoder / XMLDecoder are designed for Java Beans (setXXX, getXXX)
    // JAXB is data binding technology (code generation of wrappers)
    // XMLReader/XMLWriter (aka SAX or DOM) is possible, but too long
    public void exportFromFile(String filePath) {

    }

    public void importToFile(String filePath) {

    }

    public void checkAll() {
        for ( var entry : resources_.entrySet() ) {
            entry.getValue().check(notifiers_, settings_);
        }
    }

    public Resource getResource(String id) { return resources_.get(id); }

    public Settings settings() { return settings_; }
    List<ISubscriptionEventNotifier> notifiers() { return  notifiers_; }

    public Resource registerNewResource(String id) {
        Resource newRes =  new Resource(id);
        resources_.put(id, newRes);
        return newRes;
    }

    public void registerNotifier(ISubscriptionEventNotifier notifier) {
        notifiers_.add(notifier);
    }

}
