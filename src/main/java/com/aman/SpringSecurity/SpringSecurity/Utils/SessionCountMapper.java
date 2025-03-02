package com.aman.SpringSecurity.SpringSecurity.Utils;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import java.util.Map;
import static com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions.*;

public class SessionCountMapper {
    private static final Map<Subscriptions, Integer> map = Map.of(
            FREE,1,
            BASIC,2,
            PREMIUM,3
    );


    public static Integer getActiveSessionBasedOnSubscription(Subscriptions subscriptions){
        return map.get(subscriptions);
    }
}
