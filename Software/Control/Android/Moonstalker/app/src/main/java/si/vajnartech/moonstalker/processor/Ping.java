package si.vajnartech.moonstalker.processor;

public class Ping extends Thread
{
    protected boolean running;
    protected Processor machine;

    public Ping(Processor machine)
    {
        this.machine = machine;
        running = true;
        start();
    }

    /** @noinspection BusyWait*/
    @Override
    public void run()
    {
        while(running) {
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            new CmdPing(machine);
        }
    }
}
