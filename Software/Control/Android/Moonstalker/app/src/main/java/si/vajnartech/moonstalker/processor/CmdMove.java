package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_MV_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdMove extends Controller<String>
{
    protected String object;
    public CmdMove(QueueUI queue, String object)
    {
        super("move", queue);
        this.object = object;
    }

    @Override
    protected void onPostExecute(String cmdResult)
    {
        if (cmdResult != null) {
            if (cmdResult.equals("NOT_RDY")) {
                queue.obtainMessage(MSG_NOT_READY).sendToTarget();
            } else if (cmdResult.equals("TIMEOUT")) {
                queue.obtainMessage(MSG_CONN_ERROR).sendToTarget();
            } else if (cmdResult.startsWith("MV_ACK")) {
                queue.obtainMessage(MSG_MV_ACK).sendToTarget();
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

