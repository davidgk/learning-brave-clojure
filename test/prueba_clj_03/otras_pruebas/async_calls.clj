(ns prueba-clj-03.otras-pruebas.async-calls
  (:require
    [clojure.core.async :as async :refer :all]
    [clojure.test :refer :all]))



(defn async-task [x]
  (+ x 1))



(defn create-list-futures [values]
  (let  [result (ref [])]
    (loop [a-operation values]
      (if (empty? a-operation)
        nil
        (do
          (dosync (ref-set result (conj @result (future (async-task (first a-operation))))))
          (recur (rest a-operation)))))
    @result))

(deftest create-list-futures-test
  (testing "t1"
    (let [values [1 2 3]
          result (create-list-futures values)]
    (is (= 3 (count result))))))

(defn execute-task [values]
  (let  [
         futures (doall (map async-task values))
         result (ref [])]
    (loop [a-operation futures]
      (if (empty? a-operation)
        nil
        (do
          (println @result)
          (dosync (ref-set result (conj @result @(first a-operation))))
          (recur (rest a-operation)))))
    @result))

(deftest task-test
  (testing "t1"
    (let [values [1 2 3]
          result (execute-task values)]
      (is (= [2 3 4] result)))))

;; https://stackoverflow.com/questions/66020675/idiomatic-way-handle-and-concat-multiple-http-requests-in-clojure

