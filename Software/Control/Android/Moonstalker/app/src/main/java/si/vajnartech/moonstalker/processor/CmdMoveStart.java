package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_TIMEOUT;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVS_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdMoveStart extends Controller<String>
{
    protected String direction;

    public CmdMoveStart(Processor machine, String direction)
    {
        super("start_move", machine);
        this.direction = direction;
    }

    @Override
    protected void onPostExecute(String cmdResult)
    {
        if (cmdResult != null) {
            if (cmdResult.equals("NOT_RDY")) {
                machine.set(MSG_NOT_READY);
            } else if (cmdResult.equals("TIMEOUT")) {
                machine.set(MSG_CONN_TIMEOUT);
            } else if (cmdResult.startsWith("MVS_ACK")) {
                machine.set(MSG_MVS_ACK);
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
        return callServer(direction);
    }
}
