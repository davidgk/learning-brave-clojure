(ns prueba-clj-03.essential-patterns.sliding-window.sld-wind-test
  (:require [clojure.test :refer :all]))

(defn sliding-windows [value]
  ;; TODO
  )


(deftest sliding-windows-test
  "Find the longest substring with at most k distinct characters."
  (let [title "When value is %s"
        should "then the result is %s"]
    (doseq [test-scenario [:value "a"
                           :expected 1]]
      (let [{:keys [value expected]} test-scenario]
        (testing (format title value)
          (let [result (sliding-windows value)]
            (is (= expected result) (format should result))))))))
