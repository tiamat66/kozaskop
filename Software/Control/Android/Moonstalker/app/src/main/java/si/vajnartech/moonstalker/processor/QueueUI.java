package si.vajnartech.moonstalker.processor;

import static si.vajnartech.moonstalker.C.CALIBRATOR;
import static si.vajnartech.moonstalker.C.ST_ASTRO_DATA;
import static si.vajnartech.moonstalker.C.ST_BATTERY;
import static si.vajnartech.moonstalker.C.ST_CALIBRATED;
import static si.vajnartech.moonstalker.C.ST_CALIBRATING;
import static si.vajnartech.moonstalker.C.ST_CONNECTION_ERROR;
import static si.vajnartech.moonstalker.C.ST_ERROR;
import static si.vajnartech.moonstalker.C.ST_INFO;
import static si.vajnartech.moonstalker.C.ST_MOVING;
import static si.vajnartech.moonstalker.C.ST_NOT_READY;
import static si.vajnartech.moonstalker.C.ST_POS;
import static si.vajnartech.moonstalker.C.ST_READY;
import static si.vajnartech.moonstalker.C.ST_WAITING;
import static si.vajnartech.moonstalker.C.ST_WARNING;
import static si.vajnartech.moonstalker.OpCodes.MSG_BATTERY;
import static si.vajnartech.moonstalker.OpCodes.MSG_BATTERY_RES;
import static si.vajnartech.moonstalker.OpCodes.MSG_CALIBRATED;
import static si.vajnartech.moonstalker.OpCodes.MSG_CALIBRATING;
import static si.vajnartech.moonstalker.OpCodes.MSG_CONNECT;
import static si.vajnartech.moonstalker.OpCodes.MSG_CONN_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_ERROR;
import static si.vajnartech.moonstalker.OpCodes.MSG_GET_ASTRO_DATA;
import static si.vajnartech.moonstalker.OpCodes.MSG_INFO;
import static si.vajnartech.moonstalker.OpCodes.MSG_MOVE;
import static si.vajnartech.moonstalker.OpCodes.MSG_MOVE_END;
import static si.vajnartech.moonstalker.OpCodes.MSG_MOVE_START;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVE_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_MVS_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_MV_ACK;
import static si.vajnartech.moonstalker.OpCodes.MSG_NOT_READY;
import static si.vajnartech.moonstalker.OpCodes.MSG_PING;
import static si.vajnartech.moonstalker.OpCodes.MSG_POS;
import static si.vajnartech.moonstalker.OpCodes.MSG_READY;
import static si.vajnartech.moonstalker.OpCodes.MSG_WARNING;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

import si.vajnartech.moonstalker.telescope.Actions;

public class QueueUI extends Handler
{
    protected Actions actions;

    public QueueUI(Actions act)
    {
        super();
        this.actions = act;
    }

    @Override
    public void handleMessage(Message message)
    {
        if (message.what == MSG_CONNECT) {
            new CmdStatus(this);
            actions.updateStatus(ST_WAITING);
        } else if (message.what == MSG_BATTERY) {
            new CmdBattery(this);
            actions.updateStatus(ST_WAITING);
        } else if (message.what == MSG_MOVE) {
            String obj = (String) message.obj;
            new CmdMove(this, obj);
            actions.updateStatus(ST_WAITING);
        } else if (message.what == MSG_CALIBRATED) {
            actions.updateMode(ST_CALIBRATED);
            String obj = (String) message.obj;
            new CmdCalibrated(this, obj);
        } else if (message.what == MSG_GET_ASTRO_DATA) {
            actions.updateStatus(ST_ASTRO_DATA, message.obj);
        } else if (message.what == MSG_NOT_READY) {
            actions.updateStatus(ST_NOT_READY);
        } else if (message.what == MSG_MV_ACK) {
            actions.updateStatus(ST_MOVING);
        } else if (message.what == MSG_CONN_ERROR) {
            actions.updateStatus(ST_CONNECTION_ERROR);
        } else if (message.what == MSG_READY) {
            actions.updateStatus(ST_READY);
        } else if (message.what == MSG_ERROR) {
            String msg = (String) message.obj;
            actions.updateStatus(ST_ERROR, msg);
        } else if (message.what == MSG_WARNING) {
            String msg = (String) message.obj;
            actions.updateStatus(ST_WARNING, msg);
        } else if (message.what == MSG_INFO) {
            String msg = (String) message.obj;
            actions.updateStatus(ST_INFO, msg);
        } else if (message.what == MSG_BATTERY_RES) {
            String msg = (String) message.obj;
            actions.updateStatus(ST_BATTERY, msg);
        } else if (message.what == MSG_POS) {
            String msg = (String) message.obj;
            actions.updateStatus(ST_POS, msg);
        } else if (message.what == MSG_CALIBRATING) {
            actions.updateMode(ST_CALIBRATING);
        } else if (message.what == MSG_MOVE_START) {
            String obj = (String) message.obj;
            new CmdMoveStart(this, obj);
            actions.updateStatus(ST_WAITING);
        } else if (message.what == MSG_MOVE_END) {
            new CmdMoveEnd(this);
            actions.updateStatus(ST_WAITING);
        } else if (message.what == MSG_MVS_ACK) {
            // todo
        } else if (message.what == MSG_MVE_ACK) {
            // todo
        }
    }
}
