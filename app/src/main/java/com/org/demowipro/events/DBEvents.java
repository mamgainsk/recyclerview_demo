package com.org.demowipro.events;

import com.org.demowipro.request_pojo.RowDescription;

import java.util.List;

/**
 * Event class to retrieve data from database for caching
 */
public class DBEvents {
    public static class DbRetrieved {
        private List<RowDescription> rows;

        public DbRetrieved(List<RowDescription> rows) {
            this.rows = rows;
        }

        public List<RowDescription> getRows() {
            return rows;
        }
    }
}
