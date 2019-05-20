import codecrafts.Engine;
import codecrafts.Resource;
import codecrafts.Subscription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubscriptionsTest {

    private static final String resourceName = "privateGroup";
    private static final String[] memberNames = { "Oleg", "Eugene", "Viktoriya" };

    @Test
    public void smokeTest() {
        Resource resource = Engine.instance().registerNewResource(resourceName);
        for ( var name : memberNames ) {
            resource.registerNewSubscription(name, Subscription.Type.Month);
        }

        resource.registerNewSubscription("Alexey", Subscription.Type.Year);

        Engine.instance().registerNotifier(new Engine.ISubscriptionEventNotifier() {
            @Override
            public void onSubSoonExpired(Subscription sub) {
                assertTrue(false);
            }

            @Override
            public void onSubExpired(Subscription sub) {
                assertTrue(false);
            }
        });
    }

}