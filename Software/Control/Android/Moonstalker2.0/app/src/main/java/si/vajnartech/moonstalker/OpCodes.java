package si.vajnartech.moonstalker;

final class OpCodes
{
  static final int OUT_MSG = 1;
  static final int IN_MSG = 2;

  // in messages
  static final String READY   = "RDY";
  static final String BATTERY = "BTRY";
  static final String ERROR   = "FATAL_ERROR";
  static final String INIT    = "INIT";

  // out messages
  static final String MOVE        = "MV";
  static final String GET_STATUS  = "ST?";
  static final String GET_BATTERY = "BTRY?";
}
