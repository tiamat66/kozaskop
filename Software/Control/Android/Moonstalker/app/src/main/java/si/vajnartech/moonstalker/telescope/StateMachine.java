package si.vajnartech.moonstalker.telescope;

import static si.vajnartech.moonstalker.C.CALIBRATOR;
import static si.vajnartech.moonstalker.C.ST_ASTRO_DATA;
import static si.vajnartech.moonstalker.C.ST_CALIBRATED;
import static si.vajnartech.moonstalker.C.ST_CALIBRATING;
import static si.vajnartech.moonstalker.C.ST_CONNECTION_ERROR;
import static si.vajnartech.moonstalker.C.ST_ERROR;
import static si.vajnartech.moonstalker.C.ST_IDLE;
import static si.vajnartech.moonstalker.C.ST_INFO;
import static si.vajnartech.moonstalker.C.ST_NOT_CONNECTED;
import static si.vajnartech.moonstalker.C.ST_NOT_READY;
import static si.vajnartech.moonstalker.C.ST_POS;
import static si.vajnartech.moonstalker.C.ST_READY;
import static si.vajnartech.moonstalker.C.ST_WAITING;
import static si.vajnartech.moonstalker.C.ST_WARNING;

import android.os.Bundle;

import si.vajnartech.moonstalker.AstroObject;
import si.vajnartech.moonstalker.ControlFragment;
import si.vajnartech.moonstalker.MainActivity;
import si.vajnartech.moonstalker.ManualMoveFragment;
import si.vajnartech.moonstalker.R;
import si.vajnartech.moonstalker.processor.DataAstroObj;

public class StateMachine extends Thread
{
    protected boolean running;
    public Status status = new Status();
    public Status mode = new Status();

    protected MainActivity act;

    public StateMachine(MainActivity act)
    {
        this.act = act;
        running = true;
        start();
    }

    /**
     * @noinspection BusyWait
     */
    @Override
    public void run()
    {
        while (running) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (status.hasChanged()) {
                if (status.get() == ST_WAITING) {
                    act.setInfoMessage(R.string.waiting);
                } else if (status.get() == ST_NOT_CONNECTED) {
                    act.setInfoMessage(R.string.not_connected);
                    act.updateFab(R.color.colorError);
                    act.logMessage("...not connected");
                } else if (status.get() == ST_READY) {
                    act.setInfoMessage(R.string.ready);
                    act.updateFab(R.color.colorOk2);
                    act.updateMenu(true, true, false, false);
                } else if (status.get() == ST_CONNECTION_ERROR) {
                    act.setInfoMessage(R.string.connection_failed);
                    act.updateFab(R.color.colorError);
                    act.logMessage("...connection error");
                } else if (status.get() == ST_ASTRO_DATA) {
                    act.astroData = new DataAstroObj(status.data);
                    status.set(ST_READY);
                } else if (status.get() == ST_POS) {
                    String[] res = status.message.split(" ");
                    act.curObject.setPosition(res[0], res[1]);
                    act.setPosMessage();
                    status.set(ST_IDLE);
                } else if (status.get() == ST_NOT_READY) {
                    act.setInfoMessage(R.string.not_ready);
                } else if (status.get() == ST_ERROR) {
                    if (status.message.equals("END_LIMIT_SW_TRIG"))
                        act.setInfoMessage(R.string.end_limit_sw_trig);
                } else if (status.get() == ST_WARNING) {
                    if (status.message.equals("BTRY_LOW"))
                        act.setInfoMessage(R.string.btry_low);
                } else if (status.get() == ST_INFO) {
                    act.logMessage(status.message);
                }

            } else if (mode.hasChanged()) {
                if (mode.get() == ST_CALIBRATING) {
                    act.setFragment("manual", ManualMoveFragment.class, new Bundle());
                    act.promptToCalibration();
                    act.setInfoMessage(R.string.calibrating);
                } else if (mode.get() == ST_CALIBRATED) {
                    act.curObject = new AstroObject(CALIBRATOR);
                    act.setFragment("control", ControlFragment.class, new Bundle());
                    act.updateMenu(false, true, true, true);
                    act.setInfoMessage(R.string.ready);
                }
            }
        }
    }
}
