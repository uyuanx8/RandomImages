package com.uyuanx.randomimages;

import java.util.ArrayList;

public class ResultList {
        private int total;
        private int total_pages;

    ArrayList < Result > results = new ArrayList< Result >();


        // Getter Methods

        public int getTotal() {
            return total;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        // Setter Methods

        public void setTotal(int total) {
            this.total = total;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
