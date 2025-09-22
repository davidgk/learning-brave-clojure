(ns blanck.test.prueba-test
  (:require [clojure.test :refer :all]))

(defn add-one [n]
  (+ n 1))

(deftest add-one-test
  ""
  (let [title "When value is %s"
        should "then the result is %s"]
    (doseq [test-scenario [
                           {:value 1 :expected 2}
                           ]]
      (let [{:keys [value expected]} test-scenario]
        (testing (format title value)
          (let [result (addOne value)]
            (is (= expected result) (format should result))))))))
