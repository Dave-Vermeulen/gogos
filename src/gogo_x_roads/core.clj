(ns gogo-x-roads.core
  (:gen-class)
  (:require [clojure.java.jdbc :as jdbc]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css include-js]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :refer [run-jetty]]
            [buddy.hashers :as hashers]
            [gogo-x-roads.db :as db]))

(defn signup-user [username password]
  (try
    (jdbc/insert! db/db-spec :users
                {:username username
                :password (hashers/derive password)})
      {:status 200 :body "User created!"}
      (catch Exception e
        {:status 500 :body "Error creating user!"})))
         
(defn login-user [username password]
  (let [user (first (jdbc/query db/db-spec
                               ["SELECT * FROM users WHERE username=?" username]))]
    (if (and user (hashers/check password (:password user)))
      {:status 200 :body "Login successful!"}
      {:status 401 :body "Invalid credentials!"})))


(defn landing-page []
  (html5
    [:head
     [:title "gogo's X roads"]
     (include-css "/styles.css")]
    [:body
     [:div {:class "landing"}
      [:div {:class "coffee-cup" :onclick "checkClicks()"}]
      [:div {:class "blurb"} "The secret is in the coffee...Can you figure it out?"]
      [:p {:class "hint"} "Click the cup a magical number of times to find out more"]]
     (include-js "/app.js")]))

(defroutes app-routes
  (POST "/login" [username password] (login-user username password))
  (POST "/signup" [username password] (signup-user username password))
  (GET "/" [] (landing-page))
  (route/resources "/") ;;static routes for css and js
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main []
  (db/initialize-db)
  (run-jetty app {:port 3000}))
