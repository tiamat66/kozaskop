package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_MV_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdMove extends Controller<String>
{
    protected String object;
    public CmdMove(Processor machine, String object)
    {
        super("move", machine);
        this.object = object;
    }

    @Override
    protected void onPostExecute(String cmdResult)
    {
        if (cmdResult != null) {
            if (cmdResult.equals("NOT_RDY")) {
                machine.set(MSG_NOT_READY);
            } else if (cmdResult.equals("TIMEOUT")) {
                machine.set(MSG_CONN_ERROR);
            } else if (cmdResult.startsWith("MV_ACK")) {
                machine.set(MSG_MV_ACK);
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
        return callServer(object);
    }
}

