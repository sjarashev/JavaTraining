package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("c2d1aa91441d5fc5138d75d4702e26577e61cecc ");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("sjarashev", "Java_B10")).commits();
    for (RepoCommit commit:commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
