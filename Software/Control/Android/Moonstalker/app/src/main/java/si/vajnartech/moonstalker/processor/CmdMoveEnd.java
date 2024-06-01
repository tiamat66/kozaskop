package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVE_ACK;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdMoveEnd extends Controller<String>
{
    public CmdMoveEnd(Processor queue)
    {
        super("end_move", queue);
    }

    @Override
    protected void onPostExecute(String cmdResult)
    {
        if (cmdResult != null) {
           if (cmdResult.equals("TIMEOUT")) {
               machine.set(MSG_CONN_ERROR) ;
            } else if (cmdResult.startsWith("MVE_ACK")) {
               machine.set(MSG_MVE_ACK);
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
