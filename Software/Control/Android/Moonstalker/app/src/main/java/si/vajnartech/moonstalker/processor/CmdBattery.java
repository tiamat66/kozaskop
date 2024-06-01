package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_BATTERY_RES;
import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdBattery extends Controller<String>
{
    public CmdBattery(Processor queue) {
        super("battery", queue);
    }

    @Override
    protected void onPostExecute(String cmdResult)
    {
        String msg = getParams(cmdResult);

        if (cmdResult.startsWith("BTRY")) {
            machine.set(MSG_BATTERY_RES, msg);
        }  else if (cmdResult.equals("TIMEOUT")) {
            machine.set(MSG_CONN_ERROR);
        }
    }

    @Override
    protected String deserialize(BufferedReader br)
    {
        return new Gson().fromJson(br, String.class);
    }

    @Override
    public String backgroundFunc() {
        return callServer(null);
    }
}
