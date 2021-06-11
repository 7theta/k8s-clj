(ns k8s.core
  (:require [org.httpkit.client :as http]
            [clojure.string :as s]
            [jsonista.core :as json]
            [inflections.core :as inflections]
            [stencil.core :as stencil]))

(declare call api camel-keys keybab-keys)

(def ^:private api-map
  {:ConfigMap "/api/v1/namespaces/{{namespace}}/configmaps/"
   :Deployment "/apis/apps/v1/namespaces/{{namespace}}/deployments/"
   :Namespace "/api/v1/namespaces/"
   :NetworkPolicy "/apis/networking.k8s.io/v1/namespaces/{{namespace}}/networkpolicies/"
   :PersistentVolume "/api/v1/persistentvolumes/"
   :PersistentVolumeClaim "/api/v1/namespaces/{{namespace}}/persistentvolumeclaims/"
   :Service "/api/v1/namespaces/{{namespace}}/services/"})

(defn post
  [uri request]
  (call http/post uri request))

(defn put
  [uri request]
  (call http/put uri request))

(defn delete
  [uri request]
  (call http/delete uri request))

(defn get
  [uri request]
  (call http/get uri request))

(defn- call
  [operation uri {:keys [kind metadata] :as request}]
  {:pre [(some? kind)
         (some? metadata)]}
  (let [url (str uri (stencil/render-string (api kind operation) metadata))
        body (camel-keys (dissoc request :kind))]
    (let [response @(operation url {:headers {"content-type" "application/json"}
                                    :body (json/write-value-as-string body)})]
      (merge response
             {:body ((comp keybab-keys json/read-value) (:body response))}))))

(defn- api
  [k op]
  (when-let [url (k api-map)]
    (if (= http/post op)
      url
      (str url "{{name}}"))))

(defn- camel-keys
  "Recursively travel the map, and convert keyword keys to camel case string keys"
  [m] (inflections/transform-keys m (fn [k]
                                      (if (keyword? k)
                                        ((comp #(inflections/camel-case % :lower) name) k)
                                        k))))

(defn- keybab-keys
  "Recursively travel the map and convert string keys from camel case to keybab-case keywords"
  [m] (inflections/transform-keys m (fn [k]
                                      (if (string? k)
                                        ((comp keyword inflections/hyphenate) k)
                                        k))))
