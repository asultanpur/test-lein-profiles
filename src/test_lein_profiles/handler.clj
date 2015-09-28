(ns test-lein-profiles.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.jdbc :as sql]
            [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def db (env :database-url))

(defn greet [id]
  (:message (first (sql/query db [(str "SELECT message FROM greeting WHERE id='" id "'")]))))

(defroutes app-routes
  (GET "/" [] (greet "hello"))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
