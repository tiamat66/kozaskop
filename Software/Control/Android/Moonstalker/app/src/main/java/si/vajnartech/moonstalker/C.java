package si.vajnartech.moonstalker;

import java.util.UUID;

@SuppressWarnings("unused")
public final class C
{
  public static final String TAG = "PEPE";
  public static final AstroObject CALIBRATOR =
          new AstroObject("Polaris", "2h31m48.704s", "+89°15'50.72", "Ursa Major");

  static final int TRSHLD_BTRY = 11000;  // milivolts

  static UUID token = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

  static final int MINIMUM_TIME = 10000;  // 10s
  static final int MINIMUM_DISTANCE = 50; // 50m

  public static final double DEF_LONGITUDE = 13.82;
  public static final double DEF_LATITUDE = 46.45;

  // Named of paired BT device which acts like telescope
  static final String SERVER_NAME = "Naprava A32 uporabnika Zoran";

  // Mechanical characteristics
  private static final double MOTOR_STEPS_NUM      = 200.0;
  private static final double REDUCTOR_TRANSLATION = 30.0;
  private static final double BELT_TRANSLATION     = 48.0 / 14.0;
  static final         double K                    = MOTOR_STEPS_NUM * REDUCTOR_TRANSLATION * BELT_TRANSLATION;

  // Telescope status values
  public static final int ST_READY =    1;
  public static final int ST_ERROR =    2;
  static final int ST_TRACING =  5;
  public static final int ST_MOVING =   6;
  static final int ST_BTRY_LOW = 7;
  static final int ST_NOT_CAL =  9;
  public static final int ST_NOT_CONNECTED = 10;
  static final int ST_CONNECTED     = 12;
  public static final int ST_CALIBRATING   = 13;
  static final int ST_MANUAL   = 14;
  public static final int ST_CALIBRATED   = 17;
  static final int ST_MOVE_TO_OBJECT = 18;
  public static final int ST_NOT_READY = 19;
  static final int ST_WAITING_ACK = 20;
  public static final int ST_WAITING = 21;
  public static final int ST_CONNECTION_ERROR = 22;
  public static final int ST_WARNING = 23;
  public static final int ST_INFO = 24;
  public static final int ST_BATTERY = 25;
  public static final int ST_ASTRO_DATA = 26;
  public static final int ST_POS = 27;

  public static final int ST_IDLE = 28;

  // Triggers
  static final int ST_MOVING_S = 16;
  static final int ST_MOVING_E = 15;

  // Moving directions
  static final String N = "N";
  static final String E = "E";
  static final String S = "S";
  static final String W = "W";
  static final String NE = "NE";
  static final String SE = "SE";
  static final String SW = "SW";
  static final String NW = "NW";
  static final String NONE = "NONE";
  static final String CLEAR = "";
  static boolean monitoring = false;
  static boolean mStatus = false;
  static String  curMessage = "";
}



