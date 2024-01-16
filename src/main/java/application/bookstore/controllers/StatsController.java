package application.bookstore.controllers;


import application.bookstore.views.StatsView;


public class StatsController {
    private StatsView statsView;
    public StatsController(StatsView statsView) {
        this.statsView = statsView;
    }
}
