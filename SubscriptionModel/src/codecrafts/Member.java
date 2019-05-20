package codecrafts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Member {
    private final Resource res_;
    private final String id_;
    private final String contact_;
    private final List<Subscription> subs_;
    private Status status_;

    Member(Resource res, String id) {
        res_ = res;
        id_ = id;
        status_ = Status.SUSPENDED;
        subs_ = new ArrayList<>();
        contact_ = id_;
    }

    Subscription registerNewSubscription(Subscription.Type type, LocalDate payDate) {
        Subscription newSub = new Subscription(this, type, payDate, payDate);
        subs_.add(newSub);
        return newSub;
    }

    Subscription registerNewSubscription(Subscription.Type type) {
        Subscription newSub = new Subscription(this, type, LocalDate.now(), LocalDate.now());
        subs_.add(newSub);
        return newSub;
    }

    public enum Status {
        ACTIVE,
        SUSPENDED
    }

    void check(List<Engine.ISubscriptionEventNotifier> notifiers, Engine.Settings settings) {
        if (status_ == Status.SUSPENDED)
            return;

        // Find current active subscription
        Subscription curSub = null;
        for ( Subscription sub : subs_ ) {
            if ( sub.isActive() ) {
                curSub = sub;
            } else {
                continue;
            }

            if ( DAYS.between(LocalDate.now(), curSub.dateOfExpiry()) <= settings.expiryNotificationInDays()  ) {
                for (var notifier : notifiers ) {
                    notifier.onSubSoonExpired(curSub);
                }
            }
        }

        if (curSub == null && status_ == Status.ACTIVE) {
            status_ = Status.SUSPENDED;
            for (var notifier : notifiers ) {
                notifier.onSubExpired(curSub);
            }
        }
    }

    public String id() { return id_; }
    public String contact() { return  contact_; }
    public Status status() { return status_; }
    public Resource resource() { return  res_; }

    @Override
    public String toString() { return id_; }

    @Override
    public int hashCode() { return id_.hashCode(); }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if ( !(other instanceof Member) )
            return false;

        Member mem = (Member) other;
        return mem.id_ == this.id_;
    }
}
