(ns passprhrases.test.prueba-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]))

(def numbers-encoded {:1 8
                      :2 7
                      :3 6
                      :4 5
                      :5 4
                      :6 3
                      :7 2
                      :8 1
                      :9 0
                      :0 9
                      })

(defn replace-a-letter [base ch shift is-lower]
  (let [int-ch (int (first ch))
        is-number (re-matches #"\d" ch)
        next-letter (if is-number
                      (numbers-encoded (keyword ch))
                      (if (and (>= int-ch (int \A)) (<= int-ch (int \Z)))
                        (let [base (int \A)
                              offset (mod (+ (- int-ch base) shift) 26)]
                          (str (char (+ base offset))))
                        ch))
        next-letter (if is-lower
                      (str/lower-case next-letter)
                      next-letter)]
    (str base next-letter)))

(deftest replace-a-letter-test
  (is (= "B" (replace-a-letter "" "A" 1 false)) "#1")
  (is (= "AC" (replace-a-letter "A" "B" 1 false)) "#2")
  (is (= "AZ" (replace-a-letter "A" "Y" 1 false)) "#3")
  (is (= "AA" (replace-a-letter "A" "Z" 1 false)) "#4")
  (is (= "Aa" (replace-a-letter "A" "Z" 1 true)) "#4")
  (is (= "A1" (replace-a-letter "A" "7" 1 false)) "#5")
  (is (= "A1" (replace-a-letter "A" "7" 1 true)) "#5")
  (is (= "A!" (replace-a-letter "A" "!" 1 false)) "#6")
  (is (= "A!" (replace-a-letter "A" "!" 1 true)) "#6")
  (is (= "AC" (replace-a-letter "A" "A" 2 false)) "#7")
  (is (= "AA" (replace-a-letter "A" "Y" 2 false)) "#8")
  (is (= "AB" (replace-a-letter "A" "Z" 2 false)) "#9")
  )

(defn replace-letters [word-manager shift]
  (let [word-manager (reduce (fn [wm b]
                               (let [is-lower (wm :is-lower)
                                     word-replaced (replace-a-letter (wm :word-replaced) b shift is-lower)
                                     wm (assoc wm :is-lower (not is-lower))
                                     wm (assoc wm :word-replaced word-replaced)]
                                 wm
                                 )
                               ) word-manager (str/split (word-manager :a-word) #""))]
    (assoc word-manager :is-lower (not (word-manager :is-lower)))))

(deftest replace-letters-test
  (let [title "When value is %s"
        should " then the result is %s"]
    (doseq [test-scenario [
                           {:value {:a-word "ABABAB" :is-lower true} :shift 1 :expected {:a-word "ABABAB" :word-replaced "bCbCbC" :is-lower false}}
                           {:value {:a-word "ABABAB" :is-lower false} :shift 1 :expected {:a-word "ABABAB" :word-replaced "BcBcBc" :is-lower true}}
                           {:value {:a-word "CARLOS" :is-lower true} :shift 1 :expected {:a-word "CARLOS" :word-replaced "dBsMpT" :is-lower false}}
                           ]]
      (let [{:keys [value expected shift]} test-scenario]
        (testing (format title value should expected)
          (with-redefs []
            (let [
                  result (replace-letters value shift)]
              (is (= (expected :a-word) (result :a-word)))
              (is (= (expected :is-lower) (result :is-lower)))
              (is (= (expected :word-replaced) (result :word-replaced)))
              )))))))

(defn replace-words [phrase-split shift]
  (let [is-lower false
        changed (reduce (fn [wm a-word]
                          (let [wm (assoc wm :a-word a-word)
                                wm (replace-letters wm shift)
                                wm (assoc wm :new-list (conj (wm :new-list) (wm :word-replaced)))
                                wm (assoc wm :word-replaced "")
                                ]
                            wm)) {:is-lower is-lower :new-list []} phrase-split)]
    (changed :new-list)))

(deftest replace-words-test
  (testing "1 word"
    (let [{:keys [value shift expected]} {:value '("CARLOS") :shift 1 :expected ["DbSmPt"]}]
      (let [result (replace-words value shift)]
        (is (= (nth expected 0) (nth result 0)) ))))
  (testing "2 word"
    (let [{:keys [value shift expected]} {:value ["CARLOS" "CARLOS"] :shift 1 :expected ["DbSmPt" "dBsMpT"]}]
      (let [result (replace-words value shift)]
        (is (= (nth expected 0) (nth result 0)) )
        (is (= (nth expected 1) (nth result 1)) ))))
  (testing "3 word"
    (let [{:keys [value shift expected]} {:value ["!!!CARLOS" "CARLOS" "2015"] :shift 1 :expected ["!!!dBsMpT" "DbSmPt" "7984"]}]
      (let [result (replace-words value shift)]
        (is (= (nth expected 0) (nth result 0)) )
        (is (= (nth expected 1) (nth result 1)) )
        (is (= (nth expected 2) (nth result 2)) ))))
  )

(defn play-pass [phrase shift]
  (let [phrase-split (str/split phrase #"\s+")
        phrase-split (replace-words phrase-split shift)
        phrase (str/reverse (str/join " " phrase-split))]
    phrase))

(deftest play-pass-test
  ""
  (let [title "When value is %s"
        should "then the result is %s"]
    (doseq [test-scenario [
                           {:value "I!" :shift 1 :expected "!J"}
                           {:value "I LOVE YOU!!!" :shift 1 :expected "!!!vPz fWpM J"}
                           {:value "MY GRANMA CAME FROM NY ON THE 23RD OF APRIL 2015" :shift 2 :expected "4897 NkTrC Hq fT67 GjV Pq aP OqTh gOcE CoPcTi aO"}
                           {:value "ONCE UPON A TIME YOU DRESSED SO FINE (1968)" :shift 12 :expected ")1308( qZuR Ae pQeEqDp gAk qYuF M ZaBg qOzA"}
                           {:value "AZ12345678ZA" :shift 1 :expected "bA12345678aB"}
                           ]]
      (let [{:keys [value shift expected]} test-scenario]
        (testing (format title value)
          (let [result (play-pass value shift)]
            (is (= expected result) (format should result))))))))

;;Winner in code wars.

(defn recoder [n]
  (fn [i ch]
    (char (let [asc (int ch)] (cond
                                (<= 65 asc 90) (+ (mod (- asc 65 (- n)) 26) (if (even? i) 65 97))
                                (<= 48 asc 57) (- 105 asc)
                                :else asc )))))

(defn play-pass-2 [s n]
  (apply str (reverse (map-indexed (recoder n) s))) )


(deftest play-pass-2-test
  ""
  (let [title "When value is %s"
        should "then the result is %s"]
    (doseq [test-scenario [
                           {:value "I!" :shift 1 :expected "!J"}
                           {:value "I LOVE YOU!!!" :shift 1 :expected "!!!vPz fWpM J"}
                           {:value "MY GRANMA CAME FROM NY ON THE 23RD OF APRIL 2015" :shift 2 :expected "4897 NkTrC Hq fT67 GjV Pq aP OqTh gOcE CoPcTi aO"}
                           {:value "ONCE UPON A TIME YOU DRESSED SO FINE (1968)" :shift 12 :expected ")1308( qZuR Ae pQeEqDp gAk qYuF M ZaBg qOzA"}
                           {:value "AZ12345678ZA" :shift 1 :expected "bA12345678aB"}
                           ]]
      (let [{:keys [value shift expected]} test-scenario]
        (testing (format title value)
          (let [result (play-pass-2 value shift)]
            (is (= expected result) (format should result))))))))
