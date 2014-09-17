(ns pdfbomber.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json])
  (:use [clojure.string :only (split)]))

;;(def url "https://172.25.146.118:44300/RA/UploadPDF")
(def url "https://ak-dev.audkenni.is/RA/UploadPDF")

(defn uploadpdf []
  (client/post url {:multipart
                    [{:name "file"
                      :content (clojure.java.io/file
                                "test.pdf")}]
                    :insecure? true}))


(defn fire [n]
  (dotimes [_ n]
    (try
      (let [response (uploadpdf)
            data (json/read-str (:body response)
                                :key-fn keyword)]
        (println (str
                  "Status " (:status response)
                   "Filename " (:filename data))))
      (catch Exception e
        (println (str "exception: " (.getMessage e)))))))

(defn bomb [n m]
  (dotimes [_ n]
    (future (fire m))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main [& args]
  )

