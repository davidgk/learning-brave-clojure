(ns triangular-treasure.test.prueba-test
  (:require [clojure.test :refer :all]))

(defn triangular [n]
  (if (or (zero? n) (neg? n))
    0
    (let [result (loop [val 1
                        pos 1]
                   (if (= pos n)
                     val
                     (let [pos (inc pos)]
                       (recur (+ val pos) pos))))]
      result)))


(defn triangular_math_way [n]
  (if (or (zero? n) (neg? n))
    0
    (/ (* n (inc n)) 2)))

(defn triangular_not_work_for [n]
  "The issue is that for creates a sequence of maps, each with :add updated,
  but you are not accumulating the value.
  Instead, you repeatedly update the same initial map, so each element in the sequence is independent."
  (if (or (zero? n) (neg? n))
    0
    (let [initial {:n-val n
                   :add   0}
          initial (for [x (range 1 n)]
                    (update initial :add (+ x)))]
      (:add (seq initial)))))

(defn triangular_reduce_not_map [n]
  "To increment :add and return the final value, use reduce to accumulate the sum:"
  (if (or (zero? n) (neg? n))
    0
    (reduce + (range 1 (inc n)))))

(defn triangular_reduce_map [n]
  "To increment :add and return the final value, use reduce to accumulate the sum:"
  (if (or (zero? n) (neg? n))
    0
    (:add (reduce (fn [m x] (update m :add + x))
                  {:add 0}
                  (range 1 (inc n))))))


(deftest triangular-test
  "Triangular numbers are so called because of the equilateral \n
  triangular shape that they occupy when laid out as dots. \ni.e.
  1st (1)   2nd (3)    3rd (6)\n
  *          **        ***

  "
  (let [title "When value is %s"
        should "then the result is %s"]
    (doseq [test-scenario [
                           {:value 1 :expected 1}
                           {:value 0 :expected 0}
                           {:value -10 :expected 0}
                           {:value 2 :expected 3}
                           {:value 7 :expected 28}
                           {:value 25 :expected 325}
                           ]]
      (let [{:keys [value expected]} test-scenario]
        (testing (format title value)
          (let [result (triangular value)]
            (is (= expected result) (format should result))))))))


(defn triangularTDD [n]
  " el zero es opcional ..
    y si agregas el 1 a range es un loop menos. y no es recursivo."
  (let [ value (reduce (fn [origin val] (+ origin val)) 0 (range n))]
    value)
)


(deftest basic-tests
  (is (= (triangularTDD 2) 3))
  (is (= (triangularTDD 3) 6))
  (is (= (triangularTDD 4) 10))
  (is (= (triangularTDD 10000) 50005000))
  )

(deftest basic-tests
  (is (= (triangular 2) 3))
  ;;(is (= (triangular 7) 28))
  ;;(is (= (triangular 12) 78))
  ;;(is (= (triangular 25) 325))
  ;;(is (= (triangular 50) 1275))
  ;;(is (= (triangular 1000) 500500))
  ;;(is (= (triangular 5000) 12502500))
  ;;(is (= (triangular 10000) 50005000))
  ;;(is (= (triangular 0) 0))
  ;;(is (= (triangular -1) 0))
  ;;(is (= (triangular -5) 0))
  )