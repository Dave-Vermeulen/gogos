 (ns gogo-x-roads.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec {:subprotocol "sqlite"
              :subname "db/gogo-x-roads.db"})

(defn initialize-db []
  (jdbc/execute! db-spec
                 ["CREATE TABLE IF NOT EXISTS users
                  (id INTEGER PRIMARY KEY,
                  username TEXT UNIQUE,
                  password TEXT)"]))

