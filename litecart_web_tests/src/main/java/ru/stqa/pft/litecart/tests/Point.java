package ru.stqa.pft.litecart.tests;

public class Point {

  private double x;
  private double y;


  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point p2) {
    return (Math.sqrt(Math.pow(p2.x - this.x, 2) + Math.pow(p2.y - this.y, 2)));
  }

}
