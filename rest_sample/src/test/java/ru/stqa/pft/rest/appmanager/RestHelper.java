package ru.stqa.pft.rest.appmanager;

import org.apache.http.client.fluent.Executor;

public class RestHelper {

  private ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
  }

  public Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public Executor getExecutor1() {
    return Executor.newInstance().auth("tacbhrlqrqgary2bt5exm2lzjwjjmwhmsadf27i7znidvk5oobua", "");
  }

}
