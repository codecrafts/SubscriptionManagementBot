package codecrafts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resource {
    private final String id_;
    private final Map<String, Member> members_;

    Resource(String id) {
        id_ = id;
        members_ = new HashMap<>();
    }

    public void check() {
        check(Engine.instance().notifiers(), Engine.instance().settings());
    }

    // Provide access to new or existing member
    public Subscription registerNewSubscription(String memberId, Subscription.Type subType) {
        Member regMember = members_.get(memberId);
        if (regMember == null) {
            regMember = new Member(this, memberId);
            members_.put(regMember.id(), regMember);
        }

        return regMember.registerNewSubscription(subType);
    }

    // Check subscription list for actual
    void check(List<Engine.ISubscriptionEventNotifier> notifiers, Engine.Settings settings) {
        for ( var mem : members_.entrySet() ) {
            mem.getValue().check(notifiers, settings);
        }
    }

    @Override
    public String toString() { return id_; }

    @Override
    public int hashCode() { return id_.hashCode(); }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if ( !(other instanceof Resource) )
            return false;

        Resource mem = (Resource) other;
        return mem.id_ == this.id_;
    }
}
