package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVS_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;

import com.google.gson.Gson;

import java.io.BufferedReader;

public class CmdMoveStart extends Controller<String>
{
    protected String direction;

    public CmdMoveStart(QueueUI queue, String direction)
    {
        super("start_move", queue);
        this.direction = direction;
    }

    @Override
    protected void onPostExecute(String cmdResult)
    {
        if (cmdResult != null) {
            if (cmdResult.equals("NOT_RDY")) {
                queue.obtainMessage(MSG_NOT_READY).sendToTarget();
            } else if (cmdResult.equals("TIMEOUT")) {
                queue.obtainMessage(MSG_CONN_ERROR).sendToTarget();
            } else if (cmdResult.startsWith("MVS_ACK")) {
                queue.obtainMessage(MSG_MVS_ACK).sendToTarget();
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
