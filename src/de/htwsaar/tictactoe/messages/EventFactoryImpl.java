package de.htwsaar.tictactoe.messages;

import de.htwsaar.tictactoe.EventFactory;


/**
 * Created by DJ MacHack on 12.09.2016.
 */
public class EventFactoryImpl implements EventFactory<Event> {

    public EventFactoryImpl()
    {}

    @Override
    public Event createRegistered(int var1, String var2, String var4, int var5, int var6, int var7) {
        return null;
    }

    @Override
    public Event createRegistrationAborted() {
        return null;
    }

    @Override
    public Event createActNow(int var1) {
        return null;
    }

    @Override
    public Event createDoneActing(int var1) {
        return null;
    }

    @Override
    public Event createWinner(String var1) {
        return null;
    }
}
