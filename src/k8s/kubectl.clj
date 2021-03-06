(ns k8s.kubectl
  (:require [k8s.core :as k8s]))

(declare k8s-rest)

(defn create!
  [uri request]
  (k8s-rest k8s/post uri request))

(defn patch!
  [uri request]
  (k8s-rest k8s/patch uri request))

(defn replace!
  [uri request]
  (k8s-rest k8s/put uri request))

(defn delete!
  [uri request]
  (k8s-rest k8s/delete uri request))

(defn get
  [uri request]
  (k8s-rest k8s/get uri request))

(defn safe-get
  [uri request]
  (try
    (k8s-rest k8s/get uri request)
    (catch Exception e nil)))

(defn apply!
  [uri request]
  (println (str "apply! " (:kind request) "/" (:name (:metadata request))))
  (if (safe-get uri request)
    (patch! uri request)
    (create! uri request)))

(defn- k8s-rest
  [f uri request]
  (let [response (f uri request)
        status (-> response :status)]
    (cond
      (nil? status) response
      (and (<= status 299)
           (>= status 200)) (-> response :body)
      :else (throw (Exception. (-> response :body :message))))))
