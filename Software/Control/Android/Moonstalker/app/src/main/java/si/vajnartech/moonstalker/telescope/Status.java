package si.vajnartech.moonstalker.telescope;

import static si.vajnartech.moonstalker.C.ST_IDLE;
import static si.vajnartech.moonstalker.C.ST_NOT_CONNECTED;

import java.util.concurrent.atomic.AtomicInteger;

import si.vajnartech.moonstalker.processor.DataAstroObj;


public final class Status
{
    private final AtomicInteger value = new AtomicInteger(ST_NOT_CONNECTED);
    private final AtomicInteger curValue = new AtomicInteger(ST_IDLE);
    private final AtomicInteger prevValue = new AtomicInteger(ST_IDLE);
    public volatile String message = "";
    public volatile DataAstroObj data = null;

    public void set(int val)
    {
        prevValue.set(value.get());
        value.set(val);
    }

    public int get()
    {
        return value.get();
    }

    public boolean hasChanged()
    {
        if (curValue.get() != value.get()) {
            curValue.set(value.get());
            return true;
        }
        return false;
    }

    public void restore()
    {
        value.set(prevValue.get());
    }

}
