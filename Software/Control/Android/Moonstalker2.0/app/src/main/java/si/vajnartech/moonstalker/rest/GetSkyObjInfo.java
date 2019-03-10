package si.vajnartech.moonstalker.rest;


@SuppressWarnings("FieldCanBeLocal")
public abstract class GetSkyObjInfo
{
  protected String       name;
  Object adapter;


  GetSkyObjInfo(String name, String URL)
  {
    HTTP.GetCompleteEvent ce = new HTTP.GetCompleteEvent()
    {
      @Override public void complete(HTTP http, String data)
      {
        process(data);
      }
    };
    this.name = name;
    new HTTP(URL + name, ce).executeOnExecutor(TPE.THREAD_POOL_EXECUTOR);
  }

  GetSkyObjInfo(String URL)
  {
    HTTP.GetCompleteEvent ce = new HTTP.GetCompleteEvent()
    {
      @Override public void complete(HTTP http, String data)
      {
        process(data);
      }
    };
    new HTTP(URL, ce).executeOnExecutor(TPE.THREAD_POOL_EXECUTOR);
  }

  protected abstract void process(String data);
  protected abstract String parse(String txt, String start, String end);
  protected abstract String parse(String txt, String start);

  protected class Parser
  {
    private String txt, start, end;

    Parser(String txt, String start, String end)
    {
      this.txt = txt;
      this.start = start;
      this.end = end;
    }

    Parser(String txt, String start)
    {
      this.txt = txt;
      this.start = start;
    }
  }
}

