package si.vajnartech.moonstalker.processor;

import java.io.BufferedReader;

public class CmdCalibrated extends Controller<Void>
{
    protected String object;

    public CmdCalibrated(QueueUI queue, String object)
    {
        super("calibrated", queue);
        this.object = object;
    }

    @Override
    protected void onPostExecute(Void unused)
    {
    }

    @Override
    protected Void deserialize(BufferedReader br)
    {
        return null;
    }

    @Override
    public Void backgroundFunc()
    {
        return callServer(object);
    }
}
