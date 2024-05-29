package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_GET_ASTRO_DATA;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdStatus extends Controller<String>
{
    public CmdStatus(Processor machine)
    {
        super("get_status", machine);
    }

    /** @noinspection IfCanBeSwitch*/
    @Override
    protected void onPostExecute(String cmdResult)
    {
        if (cmdResult != null) {
            if (cmdResult.equals("RDY")) {
                machine.set(MSG_GET_ASTRO_DATA);
            }
            else if (cmdResult.equals("NOT_RDY")) {
                machine.set(MSG_NOT_READY);
            } else if (cmdResult.equals("TIMEOUT")) {
                machine.set(MSG_CONN_ERROR);
            }
        }
    }

    @Override
    protected String deserialize(BufferedReader br)
    {
        return new Gson().fromJson(br, String.class);
    }

    @Override
    public String backgroundFunc()
    {
        return callServer(null);
    }
}
