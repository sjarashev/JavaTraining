package ru.stqa.pft.rest.tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.rest.appmanager.ApplicationManager;

import java.io.IOException;


public class TestBase {

  protected static final ApplicationManager app =
          new ApplicationManager();

  private boolean isIssueOpen(int issueId) throws IOException {
    String json = app.rest().getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    String state = parsed.getAsJsonObject().get("state_name").getAsString();
    boolean status = false;
    if(state.equals("Open")) {
      status = true;
    }
    return status;
  }

  @BeforeMethod(enabled = false)
  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
