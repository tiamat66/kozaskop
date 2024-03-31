package si.vajnartech.moonstalker;

import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

@SuppressWarnings("SameParameterValue")
final class TerminalWindow
{
  private final TextView     tv;
  private final MainActivity act;

  TerminalWindow(MainActivity act)
  {
    this.act = act;
    tv = act.findViewById(R.id.msg_window);
  }

  void setBackgroundColor(int color)
  {
    tv.setBackgroundColor(color);
  }

  void setText(String msg)
  {
    C.curMessage = msg;
    act.refreshCurrentFragment();
  }

  void writePosition(AstroObject obj)
  {
    setText(formatPositionString(obj, 0));
  }

  void show(boolean sh)
  {
    if (sh)
      tv.setVisibility(View.VISIBLE);
    else
      tv.setVisibility(View.GONE);
  }

  private String formatPositionString(AstroObject obj,  int mode)
  {

    if (mode == 1)
      return String.format("%s | %s", obj.azimuth, obj.altitude);
    return String.format("%s (%s)\n%s | %s", obj.name, obj.constellation, obj.azimuth, obj.altitude);
  }
}
