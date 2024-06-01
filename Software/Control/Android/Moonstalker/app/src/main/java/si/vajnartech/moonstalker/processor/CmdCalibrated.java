package si.vajnartech.moonstalker.processor;

import java.io.BufferedReader;

public class CmdCalibrated extends Controller<Void>
{
    protected String object;

    public CmdCalibrated(Processor machine, String object)
    {
        super("calibrated", machine);
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
