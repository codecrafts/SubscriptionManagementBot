package codecrafts;

import java.time.LocalDate;

public class Subscription {

    public enum Type {
        Month,
        Year,
        Custom
    }

    protected final Type type_;
    protected final LocalDate payDate_;
    protected final LocalDate startDate_; // by default startDate = payDate
    protected final LocalDate expiryDate_;
    protected final Member member_;

    Subscription(Member member, Type type, LocalDate payDate, LocalDate startDate) {
        type_ = type;
        payDate_ = payDate;
        startDate_ = startDate;
        member_ = member;

        if (type_ == Type.Month) {
            expiryDate_ = startDate_.plusMonths(1);
        } else if (type_ == Type.Year) {
            expiryDate_ = startDate_.plusYears(1);
        } else {
            expiryDate_ = startDate_;
        }
    }

    public Type type() { return type_; }
    public LocalDate dateOfPayment() { return payDate_; }
    public LocalDate dateOfStart() { return startDate_; }
    public Member member() { return member_; }
    public LocalDate dateOfExpiry() { return expiryDate_; }

    boolean isActive() {
        return LocalDate.now().isBefore( dateOfExpiry()) && LocalDate.now().isAfter(dateOfStart());
    }

    @Override
    public String toString() { return '(' + startDate_.toString() + ';' + dateOfExpiry().toString() + ')'; }

}
