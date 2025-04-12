(ns gogo-x-roads.core
  (:gen-class)
  (:require [clojure.java.jdbc :as jdbc]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css include-js]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :refer [run-jetty]]
            [buddy.hashers :as hashers]
            [gogo-x-roads.db :as db]
            [gogo-x-roads.gif :as gif]))

(defn init []
  (gif/generate-coffee-gif!))

(defn start-server []
  (init))

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
      [:img.dancing-cup {:src "/images/coffee_dance.gif" :alt "Dancing Coffee"}]
      [:div {:class "coffee-cup" :onclick "checkClicks()"}]
      [:div {:class "blurb"} "The secret is in the coffee...Can you figure it out?"]
      [:p {:class "hint"} "Click the cup a magical number of times to find out more"]]
     (include-js "/app.js")]))

(defn about-page []
  (html5
    [:head
     [:title "gogo's X roads"]
     (include-css "/styles.css")]
    [:body
     [:div {:class "about"}
      [:h1 "About"]
      [:p "gogo's X roads is a simple web app that demonstrates the use of Clojure, Compojure, and Hiccup. It is a simple game where you click on a coffee cup to reveal a secret message. The secret is in the coffee...Can you figure it out?"]
      ]
    [:div {:class "programs"}
      [:div {:class "program-card" :id "moveit"}
       [:img {:src "/images/moveit-icon.png" :alt "MoveIT Icon"}]
       [:h2 "MoveIT"]
       [:p "Discover wellness through yoga, hiking, and more."]]
      [:div {:class "program-card" :id "codeit"}
       [:img {:src "/images/codeit-icon.png" :alt "CodeIT Icon"}]
       [:h2 "CodeIT"]
       [:p "Learn computer science with games."]]
      [:div {:class "program-card" :id "growit"}
       [:img {:src "/images/growit-icon.png" :alt "GrowIT Icon"}]
       [:h2 "GrowIT"]
       [:p "Grow a sustainable future through gardening."]]
      [:div {:class "program-card" :id "makeit"}
       [:img {:src "/images/makeit-icon.png" :alt "MakeIT Icon"}]
       [:h2 "MakeIT"]
       [:p "Gain real-world experience and build projects."]]]]))

(defroutes app-routes
  (POST "/login" [username password] (login-user username password))
  (POST "/signup" [username password] (signup-user username password))
  (GET "/" [] (landing-page))
  (GET "/about" [] (about-page))
  (route/resources "/" {:root "public"}) ;;static routes for css and js
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main []
  (db/initialize-db)
  (gif/generate-coffee-gif!)
  (run-jetty app {:port 3000}))
