package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.C.CALIBRATOR;
import static si.vajnartech.moonstalker.C.MD_CALIBRATING;
import static si.vajnartech.moonstalker.C.MD_MOVING;
import static si.vajnartech.moonstalker.C.MD_NOT_CALIBRATED;
import static si.vajnartech.moonstalker.C.ST_CONNECTION_ERROR;
import static si.vajnartech.moonstalker.C.ST_ERROR;
import static si.vajnartech.moonstalker.C.ST_NOT_READY;
import static si.vajnartech.moonstalker.C.ST_READY;
import static si.vajnartech.moonstalker.OpCodes.MSG_BATTERY;
import static si.vajnartech.moonstalker.OpCodes.MSG_BATTERY_RES;
import static si.vajnartech.moonstalker.OpCodes.MSG_CALIBRATED;
import static si.vajnartech.moonstalker.OpCodes.MSG_CALIBRATING;
import static si.vajnartech.moonstalker.OpCodes.MSG_CONNECT;
import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_TIMEOUT;
import static si.vajnartech.moonstalker.OpCodes.MSG_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_GET_ASTRO_DATA;
import static si.vajnartech.moonstalker.OpCodes.MSG_GOT_ASTRO_DATA;
import static si.vajnartech.moonstalker.OpCodes.MSG_INFO;
import static si.vajnartech.moonstalker.OpCodes.MSG_MOVE;
import static si.vajnartech.moonstalker.OpCodes.MSG_MOVE_END;
import static si.vajnartech.moonstalker.OpCodes.MSG_MOVE_START;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVE_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVS_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_MV_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;
import static si.vajnartech.moonstalker.OpCodes.MSG_POSITION;
import static si.vajnartech.moonstalker.OpCodes.MSG_READY;
import static si.vajnartech.moonstalker.OpCodes.MSG_WARNING;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Objects;

import si.vajnartech.moonstalker.AstroObject;
import si.vajnartech.moonstalker.ControlFragment;
import si.vajnartech.moonstalker.MainActivity;
import si.vajnartech.moonstalker.ManualMoveFragment;
import si.vajnartech.moonstalker.R;
import si.vajnartech.moonstalker.telescope.Status;

public class Processor extends Thread
{
    protected boolean running;
    public Status status = new Status();
    public Status mode = new Status();

    protected MainActivity act;
    protected Handler ioQueue;
    protected Handler uxQueue;
    protected HashMap<Integer, Ball> actions = new HashMap<>();

    public Processor(MainActivity act)
    {
        this.act = act;
        running = true;
        uxQueue = new Handler(Looper.getMainLooper());
        initTable(this);
        mode.set(MD_NOT_CALIBRATED);
        start();
    }

    @Override
    public void run()
    {
        Looper.prepare();

        ioQueue = new Handler(Objects.requireNonNull(Looper.myLooper()));

        Looper.loop();
    }
    public void set(int id)
    {
        Ball ball = actions.get(id);
        if (ball != null) {
            if (ball.ioAction != null) {
                ioQueue.post(ball.ioAction);
            }
            if (ball.uxAction != null) {
                uxQueue.post(ball.uxAction);
            }
        }
    }

    public void set(int id, DataAstroObj data)
    {
        status.data = data;
        set(id);
    }

    public void set(int id, String message)
    {
        status.message = message;
        set(id);
    }

    // poglej za vse state TODO state je lahk samo ready, not ready error batery low ostale so mode
    private void initTable(Processor machine)
    {
        // MSG_CONNECT
        actions.put(MSG_CONNECT, new Ball(() -> new CmdStatus(this),
                () -> act.setInfoMessage(R.string.connecting)));
        // MSG_GET_ASTRO_DATA
        actions.put(MSG_GET_ASTRO_DATA, new Ball(() -> new CmdGetAstroData(machine),
                null));
        // MSG_CONN_ERROR
        actions.put(MSG_CONN_ERROR, new Ball(null, () -> {
            act.setInfoMessage(R.string.connection_failed);
            act.updateFab(R.color.colorError);
            act.logMessage("...connection error");
            status.set(ST_CONNECTION_ERROR);
        }));
        // MSG_GOT_ASTRO_DATA
        actions.put(MSG_GOT_ASTRO_DATA, new Ball(null, () -> {
            act.astroData = new DataAstroObj(status.data);
            act.setInfoMessage(R.string.ready);
            status.set(ST_READY);
            act.updateFab(R.color.colorOk);
            act.updateMenu(true, true, false, false);
        }));
        // MSG_NOT_READY
        actions.put(MSG_NOT_READY, new Ball(null,
                () -> {
                    act.setInfoMessage(R.string.not_ready);
                    status.set(ST_NOT_READY);
                }
        ));
        // MSG_MOVE_START
        actions.put(MSG_MOVE_START, new Ball(
                () -> new CmdMoveStart(machine, status.message),
                null
        ));
        // MSG_CONN_TIMEOUT
        actions.put(MSG_CONN_TIMEOUT, new Ball(null,
                () -> act.setInfoMessage(R.string.timeout)));
        // MSG_MVS_ACK
        actions.put(MSG_MVS_ACK, new Ball(null,
                () -> {
                    act.setInfoMessage(R.string.moving);
                    act.updateFab(R.color.colorMoving);
                    mode.set(MD_MOVING);
                 }));
        // MSG_CALIBRATING
        actions.put(MSG_CALIBRATING, new Ball(null,
                () -> {
                    act.setFragment("manual", ManualMoveFragment.class, new Bundle());
                    act.promptToCalibration();
                    act.setInfoMessage(R.string.calibrating);
                    mode.set(MD_CALIBRATING);
                }));
        // MSG_CALIBRATED
        actions.put(MSG_CALIBRATED, new Ball(
                () -> new CmdCalibrated(this, status.message),
                () -> {
                    act.curObject = new AstroObject(CALIBRATOR);
                    act.setFragment("control", ControlFragment.class, new Bundle());
                    act.updateMenu(false, true, true, true);
                    act.setInfoMessage(R.string.ready);
                }
        ));
        // MSG_READY
        actions.put(MSG_READY, new Ball(null,
                () -> {
                    act.setInfoMessage(R.string.ready);
                    act.updateFab(R.color.colorOk);
                    act.updateMenu(true, true, false, false);
                    status.set(ST_READY);
                }
                ));
        // MSG_ERROR
        actions.put(MSG_ERROR, new Ball(null,
                () -> {
                    if (status.message.equals("END_LIMIT_SW_TRIG"))
                        act.setInfoMessage(R.string.end_limit_sw_trig);
                    status.set(ST_ERROR);
                }
                ));
        // MSG_WARNING
        actions.put(MSG_WARNING, new Ball(null,
                () -> {
                    if (status.message.equals("BTRY_LOW"))
                        act.setInfoMessage(R.string.btry_low);
                }
                ));
        // MSG_INFO
        actions.put(MSG_INFO, new Ball(null,
                () -> act.logMessage(status.message)));
        // MSG_POSITION
        actions.put(MSG_POSITION, new Ball(null,
                () -> {
                    String[] res = status.message.split(" ");
                    act.curObject.setPosition(res[0], res[1]);
                    act.setPosMessage();
                }));
        // MSG_BATTERY
        actions.put(MSG_BATTERY, new Ball(() -> new CmdBattery(this),
                null
                ));
        // MSG_MOVE
        actions.put(MSG_MOVE, new Ball(() -> new CmdMove(this, status.message),
                null
                ));
        // MSG_MV_ACK
        actions.put(MSG_MV_ACK, new Ball(null,
                () -> {
                    act.setInfoMessage(R.string.moving);
                    act.updateFab(R.color.colorMoving);
                    mode.set(MD_MOVING);
                }));
        // MSG_BATTERY_RES
        actions.put(MSG_BATTERY_RES, new Ball(null,
                () -> act.setInfoMessage(R.string.btry_voltage)));
        // MSG_MOVE_END
        actions.put(MSG_MOVE_END, new Ball(() -> new CmdMoveEnd(this),
                null));
        // MSG_MVE_ACK
        actions.put(MSG_MVE_ACK, new Ball(null,
                () -> {
                    act.setInfoMessage(R.string.ready);
                    status.set(ST_READY);
                    act.updateFab(R.color.colorOk);
                }));
    }

}
