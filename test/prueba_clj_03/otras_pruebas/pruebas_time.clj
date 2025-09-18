(ns prueba-clj-03.otras-pruebas.pruebas-time
  (:require [clojure.test :refer :all]
            [clj-logging.core :as log]))

(require '[clojure.java-time :as jt])

(defn my-function [arg1 arg2]
  (let [start (jt/instant)]
    (println "Doing something...")
    (Thread/sleep 1000) ; Simulate some work
    (let [end (jt/instant)]
      (jt/duration-between start end))))

(let [elapsed-time (my-function "a" "b")]
  (println "Elapsed time:" elapsed-time))