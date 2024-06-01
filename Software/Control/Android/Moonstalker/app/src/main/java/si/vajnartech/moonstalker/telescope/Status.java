package si.vajnartech.moonstalker.telescope;

import static si.vajnartech.moonstalker.C.ST_NOT_READY;

import java.util.concurrent.atomic.AtomicInteger;

import si.vajnartech.moonstalker.processor.DataAstroObj;


public final class Status
{
    private final AtomicInteger value = new AtomicInteger(ST_NOT_READY);
    public volatile String message = "";
    public volatile DataAstroObj data = null;

    public void set(int val)
    {
        value.set(val);
    }

    public int get()
    {
        return value.get();
    }
}
